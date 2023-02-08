package com.one2b3.endcycle.core.load;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.engine.assets.Assets;
import com.one2b3.endcycle.engine.assets.GameAsset;
import com.one2b3.endcycle.engine.assets.GameLoader;
import com.one2b3.endcycle.engine.audio.music.MusicCatalog;
import com.one2b3.endcycle.engine.audio.sound.SoundManager;
import com.one2b3.endcycle.engine.drawing.ObjectPainterFactory;
import com.one2b3.endcycle.engine.fonts.bitmap.BMFonts;
import com.one2b3.endcycle.engine.graphics.data.DrawableLoader;
import com.one2b3.endcycle.engine.input.binders.bindings.images.ButtonImages;
import com.one2b3.endcycle.engine.language.Localizer;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.faders.FadeType;
import com.one2b3.endcycle.engine.screens.faders.ScreenFader;
import com.one2b3.endcycle.engine.shaders.ShaderCatalog;
import com.one2b3.endcycle.features.theme.ActiveTheme;
import com.one2b3.endcycle.features.theme.MenuThemeCatalog;

public abstract class DefaultLoader implements GameLoader {

	static final int MILLISECONDS = 16;
	final Array<GameAsset> loaded = new Array<>(), toLoad = new Array<>();

	int current;

	@Override
	public void initialize() {
		DrawableLoader.init();
		// Visual assets
		ObjectPainterFactory.loadPainters();
		for (BMFonts font : BMFonts.values()) {
			font.load();
		}
	}

	@Override
	public void restart() {
		GameScreen current = Cardinal.game.getGameScreen();
		if (current != null) {
			try {
				current.dispose();
			} catch (Throwable t) {
			}
		}
		ScreenFader fader = new ScreenFader(null, FadeType.FADE_TO_WHITE);
		fader.setOutScreen(null);
		fader.setInScreen(createOpeningScreen());
		if (current == null) {
			Cardinal.game.setScreen(fader);
		} else {
			current.setScreen(fader);
		}
	}

	@Override
	public void reload(boolean mods) {
		Localizer.reload();
		loadAssets(mods);
		ActiveTheme.reload();
	}

	private void loadAssets(boolean mods) {
		try {
			current = 0;
			loadAssetsUnsafe(mods);
		} catch (Throwable throwable) {
			Gdx.app.error("Loading", "Error while loading assets!", throwable);
			Gdx.app.exit();
		}
	}

	public void loadAssetsUnsafe(boolean mods) {
		// General
		loadAsync(new MusicCatalog());
		SoundManager.load();
		// Visuals
		DrawableLoader.get().load(this, mods);
		ButtonImages.loadAll(this);
		ShaderCatalog.load(this);
		// Others
		loadAsync(new MenuThemeCatalog());

		// Load all assets
		loadGame();
	}

	protected abstract void loadGame();

	@Override
	public void dispose() {
		DrawableLoader.get().dispose(false);
	}

	@Override
	public FileHandle resolve(String path) {
		return Assets.findHandle(path);
	}

	@Override
	public void loadAsync(GameAsset loader) {
		toLoad.add(loader);
	}

	@Override
	public synchronized void update() {
		if (current == toLoad.size) {
			return;
		}
		long finishTime = System.currentTimeMillis() + MILLISECONDS;
		do {
			GameAsset asset = toLoad.get(current++);
			asset.load(this);
			if (asset.needsDispose()) {
				loaded.add(asset);
			}
		} while (current < toLoad.size && System.currentTimeMillis() < finishTime);
		// Clear load queue if we reached end!
		if (current == toLoad.size) {
			toLoad.clear();
			current = 0;
		}
	}

	@Override
	public void dispose(GameAsset asset) {
		if (loaded.removeValue(asset, true)) {
			asset.dispose();
		}
	}

	@Override
	public float getProgress() {
		return toLoad.size == 0.0F ? 1.0F : current / (float) toLoad.size;
	}

	@Override
	public boolean isLoading() {
		return toLoad.size > 0;
	}
}
