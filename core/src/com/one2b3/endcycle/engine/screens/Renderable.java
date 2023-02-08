package com.one2b3.endcycle.engine.screens;

import com.one2b3.endcycle.core.painting.CustomSpriteBatch;

public interface Renderable {

	default boolean isHidden() {
		return false;
	}

	default byte getLayer() {
		return 0;
	}

	default float getComparisonKey() {
		return 0.0F;
	}

	default void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
	}
}
