package com.one2b3.endcycle.engine.graphics.data;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.one2b3.endcycle.engine.assets.GameLoader;

public class TextureLoader {

	final TextureModLoader loader = new TextureModLoader();

	public void load(GameLoader loader, boolean mods) {
		if (loader == null) {
			loadAtlas();
		} else {
			loader.loadAsync(l -> loadAtlas());
		}
	}

	private void loadAtlas() {
		loader.loadAtlas();
	}

	public void dispose(boolean mods) {
		if (!mods || loader != null) {
			loader.dispose();
		}
	}

	public Texture dispose(String texturePath) {
		return getLoader().dispose(texturePath);
	}

	public TextureRegion loadTexture(String texturePath, boolean required) {
		return getLoader().loadTexture(texturePath, required);
	}

	public TextureRegion getAtlasRegion(String path, int index) {
		return getLoader().getAtlasRegion(path, index);
	}

	private TextureModLoader getLoader() {
		return loader;
	}

}
