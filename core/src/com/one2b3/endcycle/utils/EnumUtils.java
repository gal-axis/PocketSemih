package com.one2b3.endcycle.utils;

public final class EnumUtils {

	private EnumUtils() {
	}

	public static <T extends Enum<T>> T safeValueOf(Class<T> enumType, String input) {
		return safeValueOf(enumType, input, null);
	}

	public static <T extends Enum<T>> T safeValueOf(Class<T> enumType, String input, T defaultValue) {
		T found = defaultValue;
		try {
			found = Enum.valueOf(enumType, input);
		} catch (IllegalArgumentException | NullPointerException e) {
		}
		return found;
	}

	public static <T extends Enum<T>> int compare(T enum1, T enum2) {
		if (enum1 == null && enum2 == null) {
			return 0;
		} else if (enum1 != null && enum2 == null) {
			return -1;
		} else if (enum2 != null && enum1 == null) {
			return 1;
		} else {
			return Integer.compare(enum1.ordinal(), enum2.ordinal());
		}
	}
}
