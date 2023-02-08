package com.one2b3.endcycle.core.painting;

import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.one2b3.endcycle.engine.screens.GameScreen;

public interface GamePainter {

	float getMarginX();

	float getMarginY();

	void setScale(int scale);

	int getScale();

	void draw(GameScreen screen);

	FrameBuffer getFrameBuffer();

	void resize(int width, int height);

	void changeOrientation(boolean landscape);

	int toLocalX(int x);

	int toLocalY(int y);

	default boolean supportsPortrait() {
		return false;
	}
}
