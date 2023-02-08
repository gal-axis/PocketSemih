package com.one2b3.endcycle.engine.fonts;

import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.language.Localizer;

public class LocalizedFont implements GameFont {

	public final GameFont[] fonts;

	public LocalizedFont(GameFont... fonts) {
		this.fonts = fonts;
	}

	public GameFont getFont() {
		int locale = Localizer.getCurrentLocale().ordinal();
		return locale < fonts.length && fonts[locale] != null ? fonts[locale] : fonts[0];
	}

	@Override
	public FontCache getCache(String object, float fontSize, float width) {
		return getFont().getCache(object, fontSize, width);
	}

	@Override
	public FontCache getCache(String object) {
		return getFont().getCache(object);
	}

	@Override
	public FontCache getCache(String object, float width) {
		return getFont().getCache(object, width);
	}

	@Override
	public void paint(String object, Painter painter) {
		getFont().paint(object, painter);
	}

	@Override
	public void paint(String object, Painter painter, int from, int to) {
		getFont().paint(object, painter, from, to);
	}

	@Override
	public boolean hasCharacter(char character) {
		return getFont().hasCharacter(character);
	}
}
