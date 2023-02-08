package com.one2b3.endcycle.engine.graphics.mock;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MockRegion extends TextureRegion {

	int regionX, regionY;
	int regionWidth, regionHeight;

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
