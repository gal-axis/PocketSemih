package com.one2b3.utils;

import com.badlogic.gdx.math.Interpolation;

import lombok.RequiredArgsConstructor;

public final class Interpolations {

	private Interpolations() {
	}

	@RequiredArgsConstructor
	public static class SplitInterpolation extends Interpolation {
		final Interpolation interpolation1;
		final float amount;
		final Interpolation interpolation2;

		@Override
		public float apply(float a) {
			return interpolation1.apply(a) * amount + interpolation2.apply(a) * (1.0F - amount);
		}
	}
}
