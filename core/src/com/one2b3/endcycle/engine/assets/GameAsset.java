package com.one2b3.endcycle.engine.assets;

public interface GameAsset {

	void load(GameLoader loader);

	default boolean needsDispose() {
		return false;
	}

	default void dispose() {
	}

}
