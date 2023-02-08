package com.one2b3.utils.java;

import java.util.Map;

public final class Objects {

	public static boolean equals(Object a, Object b) {
		return (a == b) || (a != null && a.equals(b));
	}

	public static <K, V> V get(Map<K, V> map, K key, V defaultValue) {
		V value = map.get(key);
		return value == null ? defaultValue : value;
	}

	public static boolean isSplitCharacter(char c) {
		return !isAlphabetic(c) && c != '"' && c != '\'';
	}

	public static boolean isAlphabetic(int codePoint) {
		return Character.isDigit(codePoint) || Character.isLetter(codePoint);
	}

}
