package com.one2b3.endcycle.engine.screens;

import com.one2b3.endcycle.core.painting.CustomSpriteBatch;

public interface RenderProcessor {

	default void init(GameScreen screen) {
	}

	default void updateRender(float delta) {
	}

	default void resize(int width, int height) {
	}

	default void dispose() {
	}

	void startRendering(CustomSpriteBatch batch, RenderList objects, byte layer, float xOfs, float yOfs);

	void stopRendering(CustomSpriteBatch batch, byte layer);

}
