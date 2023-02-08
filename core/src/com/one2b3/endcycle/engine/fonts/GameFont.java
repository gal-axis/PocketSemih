package com.one2b3.endcycle.engine.fonts;

import com.one2b3.endcycle.engine.drawing.Painter;

public interface GameFont {

	void paint(String object, Painter painter);

	default void paint(String object, Painter painter, int from, int to) {
		paint(object, painter);
	}

	default FontCache getCache(String object) {
		return getCache(object, 1.0F, 0.0F);
	}

	default FontCache getCache(String object, float width) {
		return getCache(object, 1.0F, width);
	}

	FontCache getCache(String object, float fontSize, float width);

	boolean hasCharacter(char character);

}
