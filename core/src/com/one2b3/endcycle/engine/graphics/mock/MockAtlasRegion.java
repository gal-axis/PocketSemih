package com.one2b3.endcycle.engine.graphics.mock;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MockAtlasRegion extends AtlasRegion {

	int regionX, regionY;
	int regionWidth, regionHeight;

	public MockAtlasRegion(int x, int y, int width, int height) {
		super(null, x, y, width, height);
	}

	@Override
	public void setRegion(int x, int y, int width, int height) {
		regionX = x;
		regionY = y;
		regionWidth = width;
		regionHeight = height;
	}

	@Override
	public void flip(boolean x, boolean y) {
	}

}
