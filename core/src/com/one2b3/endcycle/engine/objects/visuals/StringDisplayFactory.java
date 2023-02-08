package com.one2b3.endcycle.engine.objects.visuals;

import com.one2b3.endcycle.engine.fonts.GameFont;
import com.one2b3.endcycle.engine.fonts.GameFonts;

public final class StringDisplayFactory {

	private StringDisplayFactory() {
	}

	public static StringDisplay spawn(String display, float x, float y) {
		return spawn(display, x, y, 0.5F);
	}

	public static StringDisplay spawn(String display, float x, float y, float time) {
		return spawn(GameFonts.TextBorder, display, x, y, time);
	}

	public static StringDisplay spawn(GameFont font, String display, float x, float y, float time) {
		StringDisplay stringDisplay = new StringDisplay();
		stringDisplay.init(font, display, x, y, time);
		return stringDisplay;
	}
}
