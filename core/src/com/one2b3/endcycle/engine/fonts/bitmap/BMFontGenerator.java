package com.one2b3.endcycle.engine.fonts.bitmap;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Array;

public class BMFontGenerator extends FreeTypeFontGenerator {

	public BMFontGenerator(FileHandle fontFile) {
		super(fontFile);
	}

	@Override
	public BMFont generateFont(FreeTypeFontParameter parameter) {
		return (BMFont) super.generateFont(parameter, new MultiLocaleFontData());
	}

	@Override
	public FreeTypeBitmapFontData generateData(FreeTypeFontParameter parameter) {
		return super.generateData(parameter, new MultiLocaleFontData());
	}

	@Override
	public BMFont generateFont(FreeTypeFontParameter parameter, FreeTypeBitmapFontData data) {
		return (BMFont) super.generateFont(parameter, data);
	}

	@Override
	protected BMFont newBitmapFont(BitmapFontData data, Array<TextureRegion> pageRegions, boolean integer) {
		return new BMFont(data, pageRegions, integer);
	}
}
