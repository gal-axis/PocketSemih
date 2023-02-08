package com.one2b3.endcycle.engine.fonts.bitmap;

import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;

public class MultiLocaleFontData extends FreeTypeBitmapFontData {

	public MultiLocaleFontData() {
	}

	@Override
	public Glyph getGlyph(char ch) {
		return super.getGlyph(ch);
	}
}
