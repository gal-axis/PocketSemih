package com.one2b3.utils;

import com.badlogic.gdx.graphics.Color;

public class CColor extends Color {

	public CColor() {
	}

	public CColor(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public CColor(Color color) {
		if (color == null) {
			return;
		}
		this.r = color.r;
		this.g = color.g;
		this.b = color.b;
		this.a = color.a;
	}

	@Override
	public CColor set(int rgba) {
		super.set(rgba);
		return this;
	}

	@Override
	public CColor add(float r, float g, float b, float a) {
		this.r += r;
		this.g += g;
		this.b += b;
		this.a += a;
		return this;
	}

	@Override
	public CColor lerp(Color target, float t) {
		if (target == null) {
			return this;
		}
		this.r += t * (target.r - this.r);
		this.g += t * (target.g - this.g);
		this.b += t * (target.b - this.b);
		this.a += t * (target.a - this.a);
		return this;
	}

	@Override
	public CColor lerp(final float r, final float g, final float b, final float a, final float t) {
		this.r += t * (r - this.r);
		this.g += t * (g - this.g);
		this.b += t * (b - this.b);
		this.a += t * (a - this.a);
		return this;
	}

	@Override
	public CColor add(Color color) {
		if (color == null) {
			return this;
		}
		this.r += color.r;
		this.g += color.g;
		this.b += color.b;
		this.a += color.a;
		return this;
	}

	@Override
	public CColor set(Color color) {
		if (color == null) {
			this.r = this.g = this.b = this.a = 1.0F;
			return this;
		}
		this.r = color.r;
		this.g = color.g;
		this.b = color.b;
		this.a = color.a;
		return this;
	}

	@Override
	public CColor set(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		return this;
	}

	@Override
	public CColor sub(float r, float g, float b, float a) {
		this.r -= r;
		this.g -= g;
		this.b -= b;
		this.a -= a;
		return this;
	}

	@Override
	public CColor mul(float r, float g, float b, float a) {
		this.r *= r;
		this.g *= g;
		this.b *= b;
		this.a *= a;
		return this;
	}

	@Override
	public CColor mul(Color color) {
		if (color == null) {
			return this;
		}
		this.r *= color.r;
		this.g *= color.g;
		this.b *= color.b;
		this.a *= color.a;
		return this;
	}

	@Override
	public CColor mul(float value) {
		this.r *= value;
		this.g *= value;
		this.b *= value;
		this.a *= value;
		return this;
	}

	public CColor mulAlpha(float alpha) {
		this.a *= alpha;
		return this;
	}

	@Override
	public CColor sub(Color color) {
		if (color == null) {
			return this;
		}
		this.r -= color.r;
		this.g -= color.g;
		this.b -= color.b;
		this.a -= color.a;
		return this;
	}

	@Override
	public CColor cpy() {
		return new CColor(this);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !(o instanceof Color)) {
			return false;
		}
		Color color = (Color) o;
		return color.r == r && color.g == g && color.b == b && color.a == a;
	}
}
