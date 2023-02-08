package com.one2b3.endcycle.engine.fonts.bitmap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.GlyphLayout.GlyphRun;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.IntMap;
import com.one2b3.endcycle.engine.assets.Assets;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.FontCache;
import com.one2b3.endcycle.engine.fonts.GameFont;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BMFonts implements GameFont {

	JetbrainsMono("jetbrainsmono", 26, 3, -2, 0.25F), //
	ShareTech("sharetech", 32, 3, -2, 0.25F), //
	ShareTechMono("comic", 42, 3, -3, 0.25F), //
	ChakraPetch("comic", 80, 4, 0, 0.25F), //
	DarkerGrotesque("darkergrotesque", 48, 3, 0, 0.25F), //
	;

	static final String CHARACTER_SET = FreeTypeFontGenerator.DEFAULT_CHARS + "\u30d0\u30c8\u30eb\u30b9\u30bf\u30fc\u6642\u9593"
			+ "\uc804\ud22c\uc2dc\uc791\uC2DC\uAC04";

	final String path;
	final int size;
	final float borderSize;
	final int spacingX;
	@Getter
	final float gameScale;

	BMFontGenerator generator;
	IntMap<BMFont> fonts, borderFonts;

	final GameFont border = new GameFont() {

		@Override
		public void paint(String object, Painter painter) {
			BMFonts.this.paint(object, painter, 0, object != null ? object.length() : 0, true);
		}

		@Override
		public void paint(String object, Painter painter, int from, int to) {
			BMFonts.this.paint(object, painter, from, to, true);
		}

		@Override
		public FontCache getCache(String object, float fontScale, float width) {
			if (object == null || object.length() == 0) {
				return FontCache.Empty;
			}
			BMFont borderFont = getFont(fontScale, true);
			GlyphLayout cache = BMFonts.this.getCache(object, null, width, true, borderFont.getCache());
			return new BMFontCache(borderFont, this, object, cache, fontScale, width > 0.0F);
		}

		@Override
		public boolean hasCharacter(char character) {
			return BMFonts.this.hasCharacter(character);
		}
	};

	public void load() {
		dispose();
		generator = new BMFontGenerator(Assets.findHandle("fonts/", path + ".ttf"));
		fonts = new IntMap<>(8);
		borderFonts = new IntMap<>(8);
	}

	public boolean isLoaded() {
		return true;
	}

	public GameFont getBorder() {
		return border;
	}

	public BMFont getFont(float fontScale, boolean border) {
		int size = this.size;
		if (fontScale != 1.0F && fontScale > 0.0F) {
			size = MathUtils.round(size * fontScale);
		}
		BMFont font = (border ? this.borderFonts : this.fonts).get(size);
		if (font == null) {
			font = getFont(size, border ? borderSize : 0, fontScale);
			(border ? this.borderFonts : this.fonts).put(size, font);
		}
		return font;
	}

	public BMFont getFont(int size, float borderSize, float fontScale) {
		return getFont(getParameter(size, borderSize, fontScale));
	}

	public FreeTypeFontParameter getParameter(int size, float borderSize, float fontScale) {
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.incremental = true;
		parameter.size = size;
		parameter.spaceX = MathUtils.round(spacingX * fontScale);
		parameter.borderWidth = borderSize * fontScale;
		parameter.minFilter = parameter.magFilter = TextureFilter.Linear;
		parameter.characters = CHARACTER_SET;
		return parameter;
	}

	public BMFont getFont(FreeTypeFontParameter parameter) {
		BMFont font = generator.generateFont(parameter);
		// Add Tab since libGDX won't...
		Glyph tab = new Glyph();
		tab.id = '\t';
		Glyph space = font.getData().getGlyph(' ');
		tab.width = space.width * 4;
		tab.xadvance = space.xadvance * 4;
		font.getData().setGlyph('\t', tab);
		if (name().endsWith("Mono")) {
			font.setFixedWidthGlyphs("0123456789");
		}
		font.setUseIntegerPositions(false);
		float lineOffset = MathUtils.ceil(font.getData().lineHeight * 0.75F);
		font.getData().setLineHeight(lineOffset);
		return font;
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

	@Override
	public void paint(String object, Painter painter) {
		paint(object, painter, 0, object != null ? object.length() : 0, false);
	}

	public void paint(String object, Painter painter, int from, int to, boolean border) {
		float xScale = painter.xScale, yScale = painter.yScale;
		if (xScale == 0.0F || yScale == 0.0F) {
			return;
		}
		int hAlign = painter.hAlign, vAlign = painter.vAlign;

		BitmapFontCache cache = getFont(painter.fontScale, border).getCache();
		GlyphLayout layout = getCache(object, painter, painter.width, border, cache);
		if (layout.runs.isEmpty()) {
			return;
		}
		int borderOffset = (border ? 2 : 0);
		float x = painter.x - painter.width * 0.5F * (hAlign + 1);
		float y = painter.y + (layout.height + borderOffset) * (1 - vAlign) * 0.5F - borderOffset * 0.5F;
		boolean drawFull = from == 0 && to == object.length();
		if (!drawFull) {
			int max = 0;
			for (int i = 0; i < layout.runs.size; i++) {
				max += layout.runs.get(i).glyphs.size;
			}
			to = Math.min(to, max);
			if (from == 0 && to == object.length()) {
				drawFull = true;
			} else if (to - from == 1 && (xScale != 1.0F || yScale != 1.0F) && hAlign != 0) {
				int current = 0, runIndex = 0;
				float realWidth = 0.0F;
				GlyphRun run = layout.runs.get(runIndex++);
				while (current + run.glyphs.size < from) {
					current += run.glyphs.size;
					run = layout.runs.get(runIndex++);
				}
				int start = 0;
				while (current + run.glyphs.size < to) {
					realWidth += run.width;
					current += run.glyphs.size;
					run = layout.runs.get(runIndex++);
				}
				for (int i = start; i < to - current; i++) {
					realWidth += run.xAdvances.get(i);
				}
				Glyph glyph = run.glyphs.get(from - current);
				float w = layout.width - glyph.width * 0.5F;
				x += (w - w / xScale) * 0.5F + realWidth / xScale - realWidth;
				y += (layout.height / yScale) * (1 - yScale) * 0.5F;
			}
		}
		float scaleRoundX = painter.batch.getScaleX(), scaleRoundY = painter.batch.getScaleY();
		float begin = layout.runs.first().x;
		cache.setPosition(MathUtils.round((x + begin) * scaleRoundX) / scaleRoundX - begin, MathUtils.round(y * scaleRoundY) / scaleRoundY);
		if (drawFull) {
			cache.draw(painter.batch);
		} else {
			cache.draw(painter.batch, from, to);
		}
	}

	@Override
	public void paint(String object, Painter painter, int from, int to) {
		paint(object, painter, from, to, false);
	}

	public GlyphLayout getCache(String object, Painter painter, float width, boolean border, BitmapFontCache cache) {
		BitmapFont font = cache.getFont();
		synchronized (font.getData()) {
			float oldScaleX = font.getData().scaleX, oldScaleY = font.getData().scaleY;
			float scaleX = oldScaleX * gameScale, scaleY = oldScaleY * gameScale;
			int hAlign = -1;
			if (painter == null) {
				cache.setColor(Color.WHITE);
			} else {
				painter.batch.setColor(painter.color);
				cache.setColor(painter.batch.getColor());
				hAlign = painter.hAlign;
				scaleX *= painter.xScale;
				scaleY *= painter.yScale;
			}
			font.getData().setScale(scaleX, scaleY);
			cache.clear();
			GlyphLayout layout = cache.addText(object, 0, 0, width, (hAlign == -1 ? Align.left : hAlign == 1 ? Align.right : Align.center),
					width > 0.0F);
			font.getData().setScale(oldScaleX, oldScaleY);
			return layout;
		}
	}

	@Override
	public FontCache getCache(String object, float fontScale, float width) {
		if (object == null || object.length() == 0) {
			return FontCache.Empty;
		}
		BMFont font = getFont(fontScale, false);
		GlyphLayout cache = getCache(object, null, width, false, font.getCache());
		return new BMFontCache(font, this, object, cache, fontScale, width > 0.0F);
	}

	@Override
	public boolean hasCharacter(char character) {
		BitmapFontData data = getFont(1.0F, false).getData();
		Glyph glyph = data.getGlyph(character);
		return glyph != null && glyph != data.missingGlyph;
	}

}
