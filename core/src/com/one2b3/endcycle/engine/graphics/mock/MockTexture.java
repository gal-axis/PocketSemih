package com.one2b3.endcycle.engine.graphics.mock;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;

public class MockTexture extends Texture {

	public MockTexture() {
		super(GL20.GL_TEXTURE_2D, 0, new MockTextureData());
	}

	@Override
	public void load(TextureData data) {
	}

	@Override
	public void bind() {
	}

	@Override
	public void bind(int unit) {
	}

	@Override
	public int getWidth() {
		return 0;
	}

	@Override
	public int getHeight() {
		return 0;
	}

	@Override
	public void draw(Pixmap pixmap, int x, int y) {
	}

	@Override
	public boolean isManaged() {
		return false;
	}

	@Override
	public void dispose() {
	}

	@Override
	public String toString() {
		return "Mock Texture";
	}
}
