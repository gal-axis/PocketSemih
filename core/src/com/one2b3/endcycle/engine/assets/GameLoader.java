package com.one2b3.endcycle.engine.assets;

import com.badlogic.gdx.files.FileHandle;
import com.one2b3.endcycle.core.platform.GamePlatform;
import com.one2b3.endcycle.engine.screens.GameScreen;

public interface GameLoader {

	FileHandle resolve(String path);

	void initialize();

	void reload(boolean mods);

	void dispose();

	void dispose(GameAsset asset);

	void loadAsync(GameAsset asset);

	void update();

	float getProgress();

	default boolean isLoading() {
		return getProgress() < 1.0F;
	}

	void restart();

	GameScreen createOpeningScreen();

	GamePlatform createPlatform();
}
