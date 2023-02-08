package com.one2b3.endcycle.engine.fonts.bitmap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.GlyphLayout.GlyphRun;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.FloatArray;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.FontCache;
import com.one2b3.endcycle.engine.fonts.GameFont;
import com.one2b3.utils.CColor;

import lombok.Getter;

public class BMFontCache implements FontCache {

	static final Color TEMP = new CColor();

	final GameFont font;
	final String text;
	@Getter
	final float width, height;
	final float fontScale;
	final boolean wrap;

	final BMFont bmFont;
	final float fontOffset;
	final FloatArray glyphPositions;

	Color color;

	public BMFontCache(BMFont bmFont, GameFont font, String text, GlyphLayout layout, float fontScale, boolean wrap) {
		this.bmFont = bmFont;
		this.font = font;
		this.text = text;
		this.width = layout.width;
		this.height = layout.height;
		this.fontScale = fontScale;
		this.wrap = wrap;
		this.glyphPositions = new FloatArray(text.length());
		float x = 0;
		if (layout.runs.size > 0) {
			GlyphRun run = layout.runs.first();
			fontOffset = run.xAdvances.first();
			for (int r = 0; r < layout.runs.size; r++) {
				FloatArray xAdvances = layout.runs.get(r).xAdvances;
				for (int i = 1, n = xAdvances.size; i < n; i++) {
					glyphPositions.add(x);
					x += xAdvances.get(i);
				}
				glyphPositions.add(x);
			}
		} else {
			fontOffset = 0;
			glyphPositions.add(x);
		}
	}

	@Override
	public void paint(Painter painter) {
		if (color != null) {
			TEMP.set(painter.color);
			painter.mulColor(color);
		}
		float fontScale = painter.fontScale;
		painter.fontScale = this.fontScale;
		if (wrap) {
			float w = painter.width;
			if (w == 0.0F) {
				painter.width = width * painter.xScale;
			}
			font.paint(text, painter);
			painter.width = w;
		} else {
			font.paint(text, painter);
		}
		painter.fontScale = fontScale;
		if (color != null) {
			painter.color(TEMP);
		}
	}

	@Override
	public void paint(Painter painter, int start, int end) {
		if (color != null) {
			TEMP.set(painter.color);
			painter.mulColor(color);
		}
		float fontScale = painter.fontScale;
		painter.fontScale = this.fontScale;
		if (wrap) {
			float w = painter.width;
			if (w == 0.0F) {
				painter.width = width * painter.xScale;
			}
			font.paint(text, painter, start, end);
			painter.width = w;
		} else {
			font.paint(text, painter, start, end);
		}
		painter.fontScale = fontScale;
		if (color != null) {
			painter.color(TEMP);
		}
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public int length() {
		return text.length();
	}

	@Override
	public char charAt(int current) {
		return text.charAt(current);
	}

	@Override
	public int getIndex(float x, float y) {
		x -= fontOffset - glyphPositions.get(0);
		int n = this.glyphPositions.size;
		float[] glyphPositions = this.glyphPositions.items;
		for (int i = 1; i < n; i++) {
			if (glyphPositions[i] > x) {
				if (glyphPositions[i] - x < x - glyphPositions[i - 1] - 1) {
					return i;
				}
				return i - 1;
			}
		}
		return length();
	}

	@Override
	public Rectangle getPosition(int cursor) {
		float textOffset = cursor >= glyphPositions.size || cursor < 0 ? 0 : glyphPositions.get(cursor);
		float width = cursor + 1 >= glyphPositions.size ? 0 : glyphPositions.get(cursor + 1) - textOffset;
		return new Rectangle(textOffset + fontOffset + bmFont.getData().cursorX, 0, width, 0);
	}
}
