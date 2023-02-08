package com.one2b3.endcycle.engine.graphics.data;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.one2b3.endcycle.engine.EngineProperties;
import com.one2b3.endcycle.engine.assets.Assets;
import com.one2b3.endcycle.engine.graphics.data.info.TextureInfo;
import com.one2b3.endcycle.engine.graphics.data.info.TextureInfoLoader;
import com.one2b3.endcycle.engine.graphics.mock.MockRegion;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TextureModLoader {

	private static final TextureRegion NO_TEXTURE = new TextureRegion();

	final Map<String, Texture> textures = new HashMap<>();
	final Map<String, TextureRegion> cachedRegions = new HashMap<>();

	TextureAtlas packed;

	public void loadAtlas() {
		FileHandle handle = Assets.getHandle("images-packed/pack.atlas");
		if (handle.exists()) {
			try {
				packed = new TextureAtlas(handle);
			} catch (Exception e) {
				Gdx.app.error("Atlas", "Unable to load atlas!", e);
			}
		}
	}

	public void dispose() {
		for (Texture texture : textures.values()) {
			texture.dispose();
		}
		textures.clear();
		if (packed != null) {
			packed.dispose();
			packed = null;
		}
		cachedRegions.clear();
	}

	public Texture dispose(String texturePath) {
		TextureRegion region = cachedRegions.remove(texturePath);
		if (region == null || region.getTexture() == null) {
			return null;
		}
		Gdx.app.debug("TextureLoader", "Disposed texture: " + texturePath);
		region.getTexture().dispose();
		return region.getTexture();
	}

	public TextureRegion loadTexture(String texturePath, boolean required) {
		if (texturePath == null) {
			return null;
		}
		TextureRegion region = cachedRegions.get(texturePath);
		if (region == null) {
			if (useAtlas()) {
				region = getAtlasRegion(texturePath);
			}
			if (region == null) {
				FileHandle handle = getHandle(texturePath);
				if (handle.exists()) {
					try {
						Texture texture = new Texture(handle);
						textures.put(texturePath, texture);
						region = new AtlasRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
						Gdx.app.debug("TextureLoader", "Loaded texture: " + texturePath);
					} catch (Throwable t) {
						Gdx.app.error("TextureLoader", "Error loading texture: " + texturePath, t);
						region = new MockRegion();
					}
				} else if (required) {
					Gdx.app.error("TextureLoader", "Texture missing: " + texturePath);
					region = new MockRegion();
				} else {
					region = NO_TEXTURE;
				}
			}
			cachedRegions.put(texturePath, region);
		}
		return region == NO_TEXTURE ? null : region;
	}

	public AtlasRegion getAtlasRegion(String texture) {
		if (packed == null) {
			return null;
		}
		TextureInfo info = TextureInfoLoader.getInfo(texture);
		if (info != null && info.atlas != null) {
			texture = info.atlas + texture.substring(texture.lastIndexOf('/') + 1);
		}
		int index = -1, extension = -1;
		for (int i = texture.length() - 1; i >= 0; i--) {
			char c = texture.charAt(i);
			if (extension == -1) {
				if (c == '.') {
					extension = i;
				}
			} else if (c == '_') {
				// Make sure we dont mark "_.jpg" as having an index
				if (extension != i + 1) {
					index = Integer.parseInt(texture.substring(i + 1, extension));
					texture = texture.substring(0, i);
				}
				break;
			} else if (c < '0' || c > '9') {
				break;
			}
		}
		if (index == -1 && extension != -1) {
			texture = texture.substring(0, extension);
		}
		return packed.findRegion(texture, index);
	}

	public TextureRegion getAtlasRegion(String texture, int index) {
		String key = texture + "_" + index;
		TextureRegion region = cachedRegions.get(key);
		if (region == null) {
			if (packed != null) {
				region = packed.findRegion(texture, index);
				if (region != null) {
					cachedRegions.put(key, region);
				}
			}
		}
		return region;
	}

	public boolean useAtlas() {
		return EngineProperties.USE_ATLAS;
	}

	public FileHandle getHandle(String texture) {
		texture = DrawableLoader.IMAGE_PATH + texture;
		FileHandle file = Assets.findHandle(texture);
		return file;
	}

}
