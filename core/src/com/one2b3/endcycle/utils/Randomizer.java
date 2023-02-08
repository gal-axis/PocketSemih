package com.one2b3.endcycle.utils;

import java.util.List;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.one2b3.utils.java.Random;

public final class Randomizer {

	private Randomizer() {
	}

	public static int getIndexChance(Random random, List<? extends RandomChance> chances) {
		if (chances == null || chances.isEmpty()) {
			return 0;
		}
		float current = 0.0F;
		for (int i = 0; i < chances.size(); i++) {
			current += chances.get(i).getRandomChance();
		}
		float number = random.nextFloat() * current;
		current = 0.0F;
		for (int i = 0; i < chances.size(); i++) {
			current += chances.get(i).getRandomChance();
			if (number < current) {
				return i;
			}
		}
		return chances.size() - 1;
	}

	public static <T extends RandomChance> T getChance(Random random, List<T> chances) {
		return chances == null || chances.isEmpty() ? null : chances.get(getIndexChance(random, chances));
	}

	public static int getIndexChance(Random random, Array<? extends RandomChance> chances) {
		if (chances == null || chances.isEmpty()) {
			return 0;
		}
		float current = 0.0F;
		for (int i = 0; i < chances.size; i++) {
			current += chances.get(i).getRandomChance();
		}
		float number = random.nextFloat() * current;
		current = 0.0F;
		for (int i = 0; i < chances.size; i++) {
			current += chances.get(i).getRandomChance();
			if (number < current) {
				return i;
			}
		}
		return chances.size - 1;
	}

	public static <T extends RandomChance> T getChance(Random random, Array<T> chances) {
		return chances == null || chances.isEmpty() ? null : chances.get(getIndexChance(random, chances));
	}

	@SafeVarargs
	public static <T> T get(T... values) {
		return get(Random.instance, values);
	}

	public static <T> T get(Array<T> values) {
		return get(Random.instance, values);
	}

	@SafeVarargs
	public static <T> T get(Random random, T... values) {
		return values[getInt(random, 0, values.length - 1)];
	}

	public static <T> T get(List<T> values) {
		return get(Random.instance, values);
	}

	public static <T> T get(Random random, List<T> values) {
		return values.get(getInt(random, 0, values.size() - 1));
	}

	public static <T> T get(Random random, Array<T> values) {
		return values.get(getInt(random, 0, values.size - 1));
	}

	public static int getIndex(List<?> values) {
		return MathUtils.random(values.size() - 1);
	}

	public static int getIndex(Random random, List<?> values) {
		return getInt(random, 0, values.size() - 1);
	}

	public static int getIndex(Random random, Array<?> values) {
		return getInt(random, 0, values.size - 1);
	}

	public static int getInt(Random random, int start, int end) {
		return start + random.nextInt(end - start + 1);
	}

	static public boolean getBoolean(Random random, float chance) {
		return random.nextFloat() < chance;
	}

	public static float getFloat(Random random, float start, float end) {
		return start + random.nextFloat() * (end - start);
	}

	public static int getSign(Random random) {
		return 1 | (random.nextInt() >> 31);
	}

	public interface RandomChance {
		float getRandomChance();
	}
}
