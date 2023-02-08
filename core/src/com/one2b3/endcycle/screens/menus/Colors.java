package com.one2b3.endcycle.screens.menus;

import com.badlogic.gdx.graphics.Color;
import com.one2b3.utils.CColor;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Colors {

	public static final CColor temp = new CColor();

	public static final CColor MENU_COLOR = color(1.0F, 0.5F, 0.0F), //
			MENU_SELECT_COLOR = color(1.0F, 0.5F, 0.0F), //
			MENU_DEFAULT_COLOR = color(0.1F, 0.6F, 0.85F), //
			MENU_DISABLED_COLOR = color(0.3F, 0.3F, 0.3F), //
			MENU_TITLE_COLOR = color(0.44F, 0.6F, 0.63F);

	public static final Color HEAL = color(0.1F, 1.0F, 0.1F), //
			DAMAGE = color(1.0F, 0.1F, 0.1F), //
			DAMAGE_WEAK = color(0.7F, 0.2F, 0.7F), //
			DAMAGE_STRONG = color(1.0F, 0.6F, 0.1F);

	public static final CColor PLAYER_OUTLINE_ENEMY = color(80, 100, 255, 255), //
			PLAYER_OUTLINE_FRIENDLY = color(255, 60, 50, 255);

	public static final CColor ENEMY = color(50, 50, 250, 255), //
			FRIENDLY = color(230, 30, 30, 255);

	public static final CColor BLACK_COLOR = color(0.085F, 0.085F, 0.085F);

	public static final RainbowColor rainbow = new RainbowColor();

	public static CColor temp(float r, float g, float b, float a) {
		return temp.set(r, g, b, a);
	}

	public static CColor temp(Color color) {
		return temp.set(color);
	}

	public static CColor temp(int rgba) {
		return temp.set(rgba);
	}

	public static CColor color(float r, float g, float b) {
		return new CColor(r, g, b, 1.0F);
	}

	public static CColor color(int r, int g, int b, int a) {
		return new CColor(r / 255.0F, g / 255.0F, b / 255.0F, a / 255.0F);
	}

	public static CColor darken(Color color) {
		return multiply(color, 0.75F);
	}

	public static CColor multiply(Color color, float factor) {
		return temp.set(color).mul(factor, factor, factor, 1.0F);
	}

	public static float difference(int color1, Color color2) {
		float r = ((color1 & 0xff000000) >>> 24) / 255f;
		float g = ((color1 & 0x00ff0000) >>> 16) / 255f;
		float b = ((color1 & 0x0000ff00) >>> 8) / 255f;
		return difference(r, g, b, color2.r, color2.g, color2.b);
	}

	public static float difference(Color color1, Color color2) {
		return difference(color1.r, color1.g, color1.b, color2.r, color2.g, color2.b);
	}

	public static float difference(float r1, float g1, float b1, float r2, float g2, float b2) {
		r1 -= r2;
		g1 -= g2;
		b1 -= b2;
		return r1 * r1 + g1 * g1 + b1 * b1;
	}
}
