package com.one2b3.endcycle.utils;

import com.one2b3.endcycle.core.Cardinal;

public final class Modulator {

	private Modulator() {
	}

	public static float getCosine(float min, float max, float speed) {
		return getCosine(speed) * (max - min) + min;
	}

	public static float getCosine(float min, float max) {
		return getCosine() * (max - min) + min;
	}

	public static float getCosine() {
		return getCosine(1.0F);
	}

	public static float getCosine(float speed) {
		return (float) (Math.cos(Cardinal.TIME_ACTIVE * Math.PI * speed) * 0.5 + 0.5);
	}

	public static float getLinear(float min, float max, float speed) {
		return getLinear(speed) * (max - min) + min;
	}

	public static float getLinear(float min, float max) {
		return getLinear() * (max - min) + min;
	}

	public static float getLinear() {
		return getLinear(1.0F);
	}

	public static float getLinear(float speed) {
		return (float) (Math.abs((Cardinal.TIME_ACTIVE * speed) % 2 - 1) % 1.0);
	}

	public static float getRainbow(float time) {
		return Math.abs(time % 2.0F - 1.0F) % 1 * 0.8F + 0.2F;
	}

	public static float get(float amount) {
		return Math.abs(amount % 2.0F - 1.0F) % 1.0F;
	}
}
