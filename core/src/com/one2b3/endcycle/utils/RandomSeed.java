package com.one2b3.endcycle.utils;

import com.badlogic.gdx.math.MathUtils;

public final class RandomSeed {

	public static final String SYMBOLS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private RandomSeed() {
	}

	public static String get() {
		StringBuilder builder = new StringBuilder(14);
		for (int i = 0; i < 12; i++) {
			if (i > 0 && i % 4 == 0) {
				builder.append('-');
			}
			builder.append(SYMBOLS.charAt(MathUtils.random(SYMBOLS.length() - 1)));
		}
		return builder.toString();
	}
}
