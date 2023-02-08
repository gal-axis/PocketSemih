package com.one2b3.endcycle.engine.fonts.bitmap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class BMCache extends BitmapFontCache {

	public float width, height;

	public BMCache(BitmapFont font) {
		super(font);
	}

	@Override
	public void clear() {
		super.clear();
		width = height = 0;
	}

	@Override
	public void addText(GlyphLayout layout, float x, float y) {
		super.addText(layout, x, y);
		width = Math.max(layout.width + x, width);
		height = Math.max(layout.height - y + getFont().getData().ascent, height);
	}

	@Override
	public void setColor(Color color) {
		Color current = getColor();
		if (color == null) {
			color = Color.WHITE;
		}
		current.r = color.r;
		current.g = color.g;
		current.b = color.b;
		current.a = color.a;
	}

	@Override
	public void setColor(float r, float g, float b, float a) {
		Color current = getColor();
		current.r = r;
		current.g = g;
		current.b = b;
		current.a = a;
	}
}