package com.one2b3.endcycle.engine.screens.faders;

import com.one2b3.endcycle.core.painting.CustomSpriteBatch;

public interface RenderFader {

	void updateOld(float delta);

	void disposeOld();

	void updateNew(float delta);

	void initNew();

	void drawOld(CustomSpriteBatch batch);

	void drawOld(CustomSpriteBatch batch, float x, float y);

	void drawNew(CustomSpriteBatch batch);

	void drawNew(CustomSpriteBatch batch, float x, float y);

	default boolean isLoading() {
		return false;
	}

}
