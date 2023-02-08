package com.one2b3.utils.java;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Random {

	public static final Random instance = new Random();

	private static final long multiplier = 0x5DEECE66DL;
	private static final long addend = 0xBL;
	private static final long mask = (1L << 48) - 1;

	public long seed;

	public Random() {
		seed = System.nanoTime();
	}

	protected int next(int bits) {
		long oldseed, nextseed;
		do {
			oldseed = seed;
			nextseed = (oldseed * multiplier + addend) & mask;
		} while (!compareAndSet(oldseed, nextseed));
		return (int) (nextseed >>> (48 - bits));
	}

	private boolean compareAndSet(long expect, long update) {
		if (seed != expect) {
			return false;
		}
		seed = update;
		return true;
	}

	public float nextFloat() {
		return next(24) / ((float) (1 << 24));
	}

	public int nextInt(int bound) {
		if (bound <= 0) {
			return 0;
		}
		int r = next(31);
		int m = bound - 1;
		if ((bound & m) == 0) {
			r = (int) ((bound * (long) r) >> 31);
		} else {
			for (int u = r; u - (r = u % bound) + m < 0; u = next(31)) {
			}
		}
		return r;
	}

	public int nextInt() {
		return next(32);
	}

	public long nextLong() {
		return ((long) (next(32)) << 32) + next(32);
	}

	public boolean nextBoolean() {
		return next(1) != 0;
	}

	public Random nextRandom() {
		return new Random(nextLong());
	}

	public Random copy() {
		return new Random(seed);
	}

}
