package com.one2b3.endcycle.engine.language;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.badlogic.gdx.files.FileHandle;
import com.one2b3.endcycle.engine.assets.Assets;
import com.one2b3.endcycle.engine.files.Data;
import com.one2b3.utils.I18NBundle;

import lombok.Getter;

public final class Localizer {

	public static final boolean DEBUGGING = false;

	private Localizer() {
	}

	@Getter
	static final List<BundleInfo> bundles;

	static {
		bundles = new ArrayList<>();
	}

	static final Map<String, I18NBundle> loadedBundles = new HashMap<>();
	@Getter
	static LocaleInfo currentLocale;

	static Formatter formatter;
	static DateFormat dateFormat;
	static final StringBuilder formatBuilder = new StringBuilder();
	static NumberFormat digits, comma, percentage;

	// static final Map<String, Set<String>> unused = new HashMap<>();

	public static void reload() {
		load(currentLocale);
	}

	/** Loads all bundles for this locale */
	public static void load(LocaleInfo localeInfo) {
		loadedBundles.clear();
		for (BundleInfo bundleInfo : bundles) {
			load(localeInfo, bundleInfo);
		}
		currentLocale = localeInfo;
		Locale locale = (currentLocale == null ? Locale.getDefault() : currentLocale.getLocale());
		formatter = new Formatter(formatBuilder, locale);
		dateFormat = new SimpleDateFormat("MMMM", locale);
		digits = NumberFormat.getNumberInstance(locale);
		comma = NumberFormat.getNumberInstance();
		percentage = NumberFormat.getPercentInstance(locale);
	}

	public static void load(LocaleInfo localeInfo, BundleInfo bundleInfo) {
		String path = bundleInfo.getPath();
		FileHandle handle = Assets.getHandle(path);
		I18NBundle bundle = null;
		try {
			Locale locale = localeInfo.getLocale();
			bundle = I18NBundle.createBundle(handle, locale);
		} catch (Exception e) {
		}
		if (bundle == null) {
			bundle = new I18NBundle();
		}
		loadedBundles.put(bundleInfo.group, bundle);

		// Set<String> all = new HashSet<>();
		// for (String key : bundle.getProperties().keys()) {
		// all.add(key);
		// }
		// unused.put(bundleInfo.group, all);
	}

	public static void save() {
		for (BundleInfo bundleInfo : bundles) {
			FileHandle handle = getHandle(bundleInfo);
			I18NBundle bundle = getBundle(bundleInfo.group);
			Data.save(handle, bundle.getProperties());
		}
	}

	public static FileHandle getHandle(BundleInfo bundleInfo) {
		return getHandle(bundleInfo, currentLocale);
	}

	public static FileHandle getHandle(BundleInfo bundle, LocaleInfo locale) {
		String path = bundle.getPath();
		FileHandle handle = Assets.findHandle(path);
		return I18NBundle.toFileHandle(handle, locale.getLocale());
	}

	public static I18NBundle getBundle(String group) {
		return loadedBundles.get(group);
	}

	public static BundleInfo getBundle(Class<?> messageClass) {
		for (BundleInfo info : bundles) {
			if (info.catalog == messageClass) {
				return info;
			}
		}
		return null;
	}

	public static boolean isEmpty(LocalizedMessage message) {
		if (message == null) {
			return true;
		}
		String str = message.format();
		return str == null || str.isEmpty();
	}

	public static boolean exists(LocalizedMessage message) {
		String group = message.getGroup();
		if (group == null || group.length() == 0) {
			return true;
		}
		I18NBundle bundle = loadedBundles.get(group);
		// unused.get(group).remove(message.getKey());
		return bundle.getProperties().containsKey(message.getKey());
	}

	// public static void printUnused() {
	// System.out.println("Unused messages:");
	// for (String group : unused.keySet()) {
	// Set<String> all = unused.get(group);
	// if (all.size() > 0) {
	// System.out.println("\tGroup: " + group);
	// Array<String> sorted = new Array<>();
	// for (String str : all) {
	// sorted.add(str);
	// }
	// sorted.sort();
	// for (String str : sorted) {
	// System.out.println("\t\t" + str);
	// }
	// }
	// }
	// }

	protected static String format(LocalizedMessage message, Object... args) {
		if (DEBUGGING) {
			return "LOCALIZED";
		}
		if (message == null) {
			return null;
		}
		String group = message.getGroup();
		if (group == null) {
			return message.getKey();
		}
		I18NBundle bundle = loadedBundles.get(group);
		if (bundle == null) {
			return null;
		} else if (args == null || args.length == 0) {
			return bundle.get(message.getKey());
		} else {
			return bundle.format(message.getKey(), args);
		}
	}

	public static String percent(float number, int commas, boolean digits) {
		percentage.setMinimumFractionDigits(commas);
		percentage.setMaximumFractionDigits(commas);
		percentage.setMinimumIntegerDigits(digits ? 3 : 0);
		return percentage.format(number);
	}

	public static String format(String str, Object... args) {
		formatBuilder.setLength(0);
		return formatter.format(str, args).toString();
	}

	public static String pad(int number, int digits) {
		Localizer.digits.setMinimumIntegerDigits(digits);
		Localizer.digits.setGroupingUsed(false);
		return Localizer.digits.format(number);
	}

	public static String comma(float value, int commas) {
		comma.setMinimumFractionDigits(commas);
		comma.setMaximumFractionDigits(commas);
		return comma.format(value);
	}

	public static String comma(float value, int digits, int commas) {
		comma.setMinimumIntegerDigits(digits);
		comma.setMinimumFractionDigits(commas);
		comma.setMaximumFractionDigits(commas);
		return comma.format(value);
	}

	public static String getMonth(int month) {
		Date date = new Date();
		date.setMonth(month);
		return dateFormat.format(date);
	}
}
