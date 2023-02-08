package com.one2b3.endcycle.engine.fonts.bitmap;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class BMFont extends BitmapFont {

	public BMFont(BitmapFontData data, Array<TextureRegion> pageRegions, boolean integer) {
		super(data, pageRegions, integer);
	}

	@Override
	public BMCache newFontCache() {
		return new BMCache(this);
	}
}
