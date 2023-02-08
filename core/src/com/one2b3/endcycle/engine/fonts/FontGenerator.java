package com.one2b3.endcycle.engine.fonts;

import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.IntMap;
import com.one2b3.endcycle.engine.assets.Assets;
import com.one2b3.endcycle.engine.fonts.bitmap.BMFont;
import com.one2b3.endcycle.engine.fonts.bitmap.BMFontGenerator;

public class FontGenerator {

	String path;
	BMFontGenerator generator;
	IntMap<BMFont> fonts, borderFonts;

	public void load() {
		generator = new BMFontGenerator(Assets.findHandle("fonts/", path + ".ttf"));
		fonts = new IntMap<>(8);
		borderFonts = new IntMap<>(8);
	}

	public void dispose() {
		if (generator != null) {
			generator.dispose();
			generator = null;
		}
		if (fonts != null) {
			for (BMFont font : fonts.values()) {
				font.dispose();
			}
			fonts = null;
		}
		if (borderFonts != null) {
			for (BMFont font : borderFonts.values()) {
				font.dispose();
			}
			borderFonts = null;
		}
	}

	public BMFont generateFont(int size, boolean border, FreeTypeFontParameter parameter) {
		BMFont font = generateFont(parameter);
		(border ? this.borderFonts : this.fonts).put(size, font);
		return font;
	}

	public BMFont getFont(int size, boolean border) {
		return (border ? this.borderFonts : this.fonts).get(size);
	}

	public BMFont generateFont(FreeTypeFontParameter parameter) {
		BMFont font = generator.generateFont(parameter);
		// Add Tab since libGDX won't...
		Glyph tab = new Glyph();
		tab.id = '\t';
		Glyph space = font.getData().getGlyph(' ');
		tab.width = space.width * 4;
		tab.xadvance = space.xadvance * 4;
		font.getData().setGlyph('\t', tab);
		if (path.endsWith("mono")) {
			font.setFixedWidthGlyphs("0123456789");
		}
		font.setUseIntegerPositions(false);
		float lineOffset = MathUtils.ceil(font.getData().lineHeight * 0.75F);
		font.getData().setLineHeight(lineOffset);
		return font;
	}
}
