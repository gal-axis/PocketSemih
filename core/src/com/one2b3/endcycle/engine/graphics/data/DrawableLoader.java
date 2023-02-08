package com.one2b3.endcycle.engine.graphics.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.one2b3.endcycle.engine.EngineProperties;
import com.one2b3.endcycle.engine.EngineProperties.Platform;
import com.one2b3.endcycle.engine.assets.GameLoader;
import com.one2b3.endcycle.engine.graphics.Drawable;
import com.one2b3.endcycle.engine.graphics.DrawableImage;
import com.one2b3.endcycle.engine.graphics.DrawableImageFrame;
import com.one2b3.endcycle.engine.graphics.data.info.TextureInfo;
import com.one2b3.endcycle.engine.graphics.data.info.TextureInfoLoader;
import com.one2b3.endcycle.engine.graphics.mock.MockRegion;
import com.one2b3.endcycle.features.models.connect.ModdableConnector;
import com.one2b3.endcycle.utils.ID;
import com.one2b3.modding.diff.ModdedList;
import com.one2b3.utils.DrawableAnimation;

import lombok.Getter;

public final class DrawableLoader implements ModdableConnector<DrawableData> {

	public static final String IMAGE_PATH = "images/";
	public static final String DATA_PATH = "data/visuals/drawables.json";

	static DrawableLoader loader;

	public static void init() {
		if (loader != null) {
			loader.dispose(false);
		}
		loader = new DrawableLoader();
	}

	public static DrawableLoader get() {
		return loader;
	}

	public static DrawableImage getImage(long group, long id) {
		return loader.getImage(ID.temp(group, id));
	}

	@Getter
	static boolean mock = false;

	final Map<Integer, TextureRegion> loadedRegions = new HashMap<>();

	final TextureLoader textureLoader = new TextureLoader();

	public final ModdedList<DrawableData> drawableMap = new ModdedList<>(DATA_PATH, null, DrawableData.class, true);

	final Map<ID, DrawableImage> drawablesById = new HashMap<>();

	public static void enableMock() {
		mock = true;
		debug("Enabled mocking! No more images will be loaded");
	}

	private static void debug(String debug) {
		if (Gdx.app == null) {
			System.out.println(debug);
		} else {
			Gdx.app.debug("Images", debug);
		}
	}

	@Override
	public String getName() {
		return "Drawable";
	}

	@Override
	public String getToolboxGroup() {
		return "Animations/Drawables";
	}

	@Override
	public List<DrawableData> getValues() {
		return getAllData();
	}

	@Override
	public ModdedList<DrawableData> getList() {
		return drawableMap;
	}

	public void load(GameLoader loader, boolean mods) {
		dispose(mods);
		if (!mock) {
			textureLoader.load(loader, mods);
		}
		ModdableConnector.super.load(loader);
		for (DrawableData data : drawableMap.objects) {
			DrawableImage image = getImage(data.getId());
			if (image == null) {
				createDrawableImage(loader, data);
			} else {
				updateImage(loader, image, data);
			}
		}
	}

	public List<DrawableData> getAllData() {
		return drawableMap.objects;
	}

	public TextureRegion reloadTexture(String path) {
		Texture oldTexture = textureLoader.dispose(path);
		TextureRegion newRegion = loadTexture(path);
		if (oldTexture != null) {
			for (TextureRegion region : loadedRegions.values()) {
				if (region.getTexture() == oldTexture) {
					int x = region.getRegionX(), y = region.getRegionY(), w = region.getRegionWidth(),
							h = region.getRegionHeight();
					boolean flipX = region.isFlipX(), flipY = region.isFlipY();
					region.setRegion(newRegion, (flipX ? x - w : x), (flipY ? y - h : y), w, h);
					region.flip(flipX, flipY);
				}
			}
			for (DrawableImage image : drawablesById.values()) {
				if (image.getNinePatch() != null && image.getNinePatch().getTexture() == oldTexture) {
					image.loadNinePatch();
				}
			}
		}
		return newRegion;
	}

	public TextureRegion loadTexture(String path) {
		TextureRegion region = textureLoader.loadTexture(path, true);
		return region == null ? new MockRegion() : region;
	}

	public TextureRegion getAtlasRegion(String path, int index) {
		if (mock || !EngineProperties.USE_ATLAS) {
			return null;
		}
		return textureLoader.getAtlasRegion(path, index);
	}

	public DrawableData getData(ID id) {
		for (DrawableData data : drawableMap.objects) {
			if (data.getId().equals(id)) {
				return data;
			}
		}
		return null;
	}

	public Drawable createDrawable(ID id) {
		DrawableImage image = getImage(id);
		if (image == null) {
			if (EngineProperties.PLATFORM == Platform.DEV) {
				Gdx.app.error("Images", id + " not found!");
			}
			image = new DrawableImage(id);
			drawablesById.put(id, image);
		}
		return new Drawable(image);
	}

	public DrawableImage getImage(ID id) {
		return drawablesById.get(id);
	}

	public DrawableImage createDrawableImage(GameLoader loader, DrawableDataProvider data) {
		if (data == null) {
			return null;
		} else if (data instanceof DrawableData) {
			return createDrawableImage(loader, (DrawableData) data);
		} else {
			DrawableImage image = new DrawableImage();
			updateImage(loader, image, data);
			return image;
		}
	}

	public DrawableImage createDrawableImage(GameLoader loader, DrawableData data) {
		DrawableImage image = drawablesById.get(data.getId());
		if (image == null) {
			image = new DrawableImage();
			drawablesById.put(data.getId(), image);
		}
		updateImage(loader, image, data);
		return image;
	}

	public void updateImage(GameLoader loader, DrawableImage image, DrawableDataProvider data) {
		DrawableImageFrame[] frames = new DrawableImageFrame[data.getFrames().size()];
		for (int i = 0; i < data.getFrames().size(); i++) {
			DrawableFrameData frameData = data.getFrames().get(i);

			TextureInfo info = TextureInfoLoader.getInfo(frameData.texturePath);
			frames[i] = new DrawableImageFrame(null, null, frameData.xOffset, frameData.yOffset, info.filter,
					info.wrap);

			if (loader == null) {
				load(frameData, frames[i]);
			} else {
				DrawableImageFrame frame = frames[i];
				loader.loadAsync(l -> load(frameData, frame));
			}
		}
		if (loader == null) {
			image.setAnimation(new DrawableAnimation(data.getSpeed(), data.getPlayMode(), frames));
		} else {
			loader.loadAsync(
					l -> image.setAnimation(new DrawableAnimation(data.getSpeed(), data.getPlayMode(), frames)));
		}
	}

	private void load(DrawableFrameData data, DrawableImageFrame frame) {
		frame.region = getRegion(data, false);
		frame.colorblindRegion = getRegion(data, true);
	}

	public TextureRegion getRegion(DrawableFrameData data, boolean colorblind) {
		if (mock) {
			return getRegion(data.texturePath, false, data.x, data.x, data.width, data.height);
		} else {
			String path = data.texturePath;
			if (colorblind) {
				path = getColorblindPath(path);
			}
			return getRegion(path, !colorblind, data.x, data.y, data.width, data.height);
		}
	}

	public TextureRegion getRegion(String path, boolean required, int x, int y, int width, int height) {
		int w = Math.abs(width);
		int h = Math.abs(height);
		if (mock) {
			return new MockRegion(x, y, w, h);
		} else {
			int key = getKey(path, x, y, width, height);
			TextureRegion region = loadedRegions.get(key);
			if (region == null) {
				TextureRegion loaded = textureLoader.loadTexture(path, required);
				if (loaded == null) {
					return null;
				} else if (loaded instanceof MockRegion) {
					region = new MockRegion(x, y, w, h);
				} else {
					region = new TextureRegion(loaded, x, y, w, h);
				}
				region.flip(width < 0, height < 0);
				loadedRegions.put(key, region);
			}
			return region;
		}
	}

	private int getKey(String path, int x, int y, int width, int height) {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + height;
		result = prime * result + width;
		return result;
	}

	public static String getColorblindPath(String path) {
		int index = path.lastIndexOf(".");
		path = path.substring(0, index) + "_cb" + path.substring(index);
		return path;
	}

	public void dispose(boolean mods) {
		textureLoader.dispose(mods);
		loadedRegions.clear();
		TextureInfoLoader.clear();
	}

}
