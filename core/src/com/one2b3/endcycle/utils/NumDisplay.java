package com.one2b3.endcycle.utils;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString
@EqualsAndHashCode
public final class NumDisplay {

	public float speed;
	public float val, real;

	public NumDisplay() {
	}

	public NumDisplay(float speed) {
		this.speed = speed;
	}

	public NumDisplay(float speed, float val) {
		this(speed);
		this.val = real = val;
	}

	public void sync() {
		val = real;
	}

	public float getDelta() {
		return val - real;
	}

	public boolean update(float delta) {
		if (val < real) {
			val += speed * delta;
			if (val > real) {
				val = real;
			}
			return true;
		} else if (val > real) {
			val -= speed * delta;
			if (val < real) {
				val = real;
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean isSynced() {
		return val == real;
	}

	public static int positiveMod(int number, int modulo) {
		return ((number % modulo) + modulo) % modulo;
	}

	public static float positiveMod(float number, float modulo) {
		return ((number % modulo) + modulo) % modulo;
	}
}
