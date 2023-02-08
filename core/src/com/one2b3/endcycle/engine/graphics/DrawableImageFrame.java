package com.one2b3.endcycle.engine.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public final class DrawableImageFrame {

	TextureRegion region;
	TextureRegion colorblindRegion;
	float xOffset;
	float yOffset;
	TextureFilter filter;
	TextureWrap wrap;

	public TextureRegion getRegion(boolean colorblind) {
		return colorblind && colorblindRegion != null ? colorblindRegion : region;
	}

	public Texture getTexture() {
		return region.getTexture();
	}

	public int getWidth() {
		return region.getRegionWidth();
	}

	public int getHeight() {
		return region.getRegionHeight();
	}
}
