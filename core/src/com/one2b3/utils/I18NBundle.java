/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.one2b3.utils;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.PropertiesUtils;
import com.badlogic.gdx.utils.StreamUtils;
import com.one2b3.endcycle.engine.files.PropertiesUtil;

import lombok.Getter;

/**
 * A {@code I18NBundle} provides {@code Locale}-specific resources loaded from
 * property files. A bundle contains a number of named resources, whose names
 * and values are {@code Strings}. A bundle may have a parent bundle, and when a
 * resource is not found in a bundle, the parent bundle is searched for the
 * resource. If the fallback mechanism reaches the base bundle and still can't
 * find the resource it throws a {@code MissingResourceException}.
 *
 * <ul>
 * <li>All bundles for the same group of resources share a common base bundle.
 * This base bundle acts as the root and is the last fallback in case none of
 * its children was able to respond to a request.</li>
 * <li>The first level contains changes between different languages. Only the
 * differences between a language and the language of the base bundle need to be
 * handled by a language-specific {@code I18NBundle}.</li>
 * <li>The second level contains changes between different countries that use
 * the same language. Only the differences between a country and the country of
 * the language bundle need to be handled by a country-specific
 * {@code I18NBundle}.</li>
 * <li>The third level contains changes that don't have a geographic reason
 * (e.g. changes that where made at some point in time like {@code PREEURO}
 * where the currency of come countries changed. The country bundle would return
 * the current currency (Euro) and the {@code PREEURO} variant bundle would
 * return the old currency (e.g. DM for Germany).</li>
 * </ul>
 *
 * <strong>Examples</strong>
 * <ul>
 * <li>BaseName (base bundle)
 * <li>BaseName_de (german language bundle)
 * <li>BaseName_fr (french language bundle)
 * <li>BaseName_de_DE (bundle with Germany specific resources in german)
 * <li>BaseName_de_CH (bundle with Switzerland specific resources in german)
 * <li>BaseName_fr_CH (bundle with Switzerland specific resources in french)
 * <li>BaseName_de_DE_PREEURO (bundle with Germany specific resources in german
 * of the time before the Euro)
 * <li>BaseName_fr_FR_PREEURO (bundle with France specific resources in french
 * of the time before the Euro)
 * </ul>
 *
 * It's also possible to create variants for languages or countries. This can be
 * done by just skipping the country or language abbreviation:
 * BaseName_us__POSIX or BaseName__DE_PREEURO. But it's not allowed to
 * circumvent both language and country: BaseName___VARIANT is illegal.
 *
 * @see PropertiesUtils
 *
 * @author davebaol
 */
public final class I18NBundle {

	public static final String DEFAULT_ENCODING = "UTF-8";

	public static final Locale ROOT_LOCALE = Locale.ENGLISH;

	/**
	 * The parent of this {@code I18NBundle} that is used if this bundle doesn't
	 * include the requested resource.
	 */
	private I18NBundle parent;

	private Locale locale;

	@Getter
	final ObjectMap<String, String> properties = new ObjectMap<>();
	@Getter
	final ObjectMap<String, String> overloads = new ObjectMap<>();

	private TextFormatter formatter;

	public static I18NBundle createBundle(FileHandle baseFileHandle, Locale locale) {
		return createBundle(baseFileHandle, locale, DEFAULT_ENCODING);
	}

	public static I18NBundle createBundle(FileHandle baseFileHandle, Locale locale, String encoding) {
		if (baseFileHandle == null || locale == null || encoding == null) {
			throw new NullPointerException();
		}

		I18NBundle bundle = null;
		I18NBundle baseBundle = null;
		Locale targetLocale = locale;
		do {
			List<Locale> candidateLocales = getCandidateLocales(targetLocale);
			bundle = loadBundleChain(baseFileHandle, encoding, candidateLocales, 0, baseBundle);

			// Check the loaded bundle (if any)
			if (bundle != null) {
				Locale bundleLocale = bundle.getLocale(); // WTH? GWT can't
															// access
															// bundle.locale
															// directly
				boolean isBaseBundle = bundleLocale.equals(ROOT_LOCALE);

				if (!isBaseBundle || bundleLocale.equals(locale)) {
					// Found the bundle for the requested locale
					break;
				}
				if (candidateLocales.size() == 1 && bundleLocale.equals(candidateLocales.get(0))) {
					// Found the bundle for the only candidate locale
					break;
				}
				if (isBaseBundle && baseBundle == null) {
					// Store the base bundle and keep on processing the
					// remaining fallback locales
					baseBundle = bundle;
				}
			}
			targetLocale = getFallbackLocale(targetLocale);
		} while (targetLocale != null);

		if (bundle == null) {
			if (baseBundle == null) {
				throw new MissingResourceException(
						"Can't find bundle for base file handle " + baseFileHandle.path() + ", locale " + locale,
						baseFileHandle + "_" + locale, "");
			}
			bundle = baseBundle;
		}

		return bundle;
	}

	private static Locale getFallbackLocale(Locale locale) {
		Locale defaultLocale = ROOT_LOCALE;
		return locale.equals(defaultLocale) ? null : defaultLocale;
	}

	private static List<Locale> getCandidateLocales(Locale locale) {
		String language = locale.getLanguage();
		String country = locale.getCountry();
		String variant = locale.getVariant();

		List<Locale> locales = new ArrayList<>(4);
		if (!variant.isEmpty()) {
			locales.add(locale);
		}
		if (!country.isEmpty()) {
			locales.add(locales.isEmpty() ? locale : new Locale(language, country));
		}
		if (!language.isEmpty()) {
			locales.add(locales.isEmpty() ? locale : new Locale(language));
		}
		locales.add(ROOT_LOCALE);
		return locales;
	}

	private static I18NBundle loadBundleChain(FileHandle baseFileHandle, String encoding, List<Locale> candidateLocales,
			int candidateIndex, I18NBundle baseBundle) {
		Locale targetLocale = candidateLocales.get(candidateIndex);
		I18NBundle parent = null;
		if (candidateIndex != candidateLocales.size() - 1) {
			// Load recursively the parent having the next candidate locale
			parent = loadBundleChain(baseFileHandle, encoding, candidateLocales, candidateIndex + 1, baseBundle);
		} else if (baseBundle != null && targetLocale.equals(ROOT_LOCALE)) {
			return baseBundle;
		}

		// Load the bundle
		I18NBundle bundle = loadBundle(baseFileHandle, encoding, targetLocale);
		if (bundle != null) {
			bundle.parent = parent;
			return bundle;
		}

		return parent;
	}

	// Tries to load the bundle for the given locale.
	private static I18NBundle loadBundle(FileHandle baseFileHandle, String encoding, Locale targetLocale) {
		I18NBundle bundle = null;
		Reader reader = null;
		try {
			FileHandle fileHandle = toFileHandle(baseFileHandle, targetLocale);
			if (checkFileExistence(fileHandle)) {
				// Instantiate the bundle
				bundle = new I18NBundle();

				// Load bundle properties from the stream with the specified
				// encoding
				reader = fileHandle.reader(encoding);
				bundle.load(reader);
			}
		} catch (IOException e) {
			throw new GdxRuntimeException(e);
		} finally {
			StreamUtils.closeQuietly(reader);
		}
		if (bundle != null) {
			bundle.setLocale(targetLocale);
		}

		return bundle;
	}

	// On Android this is much faster than fh.exists(), see
	// https://github.com/libgdx/libgdx/issues/2342
	// Also this should fix a weird problem on iOS, see
	// https://github.com/libgdx/libgdx/issues/2345
	private static boolean checkFileExistence(FileHandle fh) {
		try {
			fh.read().close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	protected void load(Reader reader) throws IOException {
		properties.clear();
		PropertiesUtil.load(properties, reader);
	}

	public void overload(FileHandle baseFileHandle, Locale locale) {
		I18NBundle bundle = I18NBundle.createBundle(baseFileHandle, locale);
		overloads.putAll(bundle.getProperties());
	}

	public static FileHandle toFileHandle(FileHandle baseFileHandle, Locale locale) {
		StringBuilder sb = new StringBuilder(baseFileHandle.name());
		if (!locale.equals(ROOT_LOCALE)) {
			String language = locale.getLanguage();
			String country = locale.getCountry();
			String variant = locale.getVariant();
			boolean emptyLanguage = language.isEmpty();
			boolean emptyCountry = country.isEmpty();
			boolean emptyVariant = variant.isEmpty();

			if (!(emptyLanguage && emptyCountry && emptyVariant)) {
				sb.append('_');
				if (!emptyVariant) {
					sb.append(language).append('_').append(country).append('_').append(variant);
				} else if (!emptyCountry) {
					sb.append(language).append('_').append(country);
				} else {
					sb.append(language);
				}
			}
		}
		return baseFileHandle.sibling(sb.append(".properties").toString());
	}

	/**
	 * Returns the locale of this bundle. This method can be used after a call to
	 * <code>createBundle()</code> to determine whether the resource bundle returned
	 * really corresponds to the requested locale or is a fallback.
	 *
	 * @return the locale of this bundle
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Sets the bundle locale. This method is private because a bundle can't change
	 * the locale during its life.
	 *
	 * @param locale
	 */
	private void setLocale(Locale locale) {
		this.locale = locale;
		this.formatter = new TextFormatter(locale, true);
	}

	public String get(String key) {
		if (key == null) {
			return null;
		}
		String result = overloads.get(key);
		if (result == null) {
			result = properties.get(key);
		}
		if (result == null) {
			if (parent != null) {
				result = parent.get(key);
			}
			if (result == null) {
				return key;
			}
		}
		return result;
	}

	public String format(String key, Object... args) {
		return formatter.format(get(key), args);
	}

}
