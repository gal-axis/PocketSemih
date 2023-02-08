package com.one2b3.endcycle.engine.screens;

import com.badlogic.gdx.utils.Disposable;
import com.one2b3.endcycle.utils.objects.DeltaUpdateable;

public interface ScreenObject extends Renderable, DeltaUpdateable, Disposable {

	default void init(GameScreen screen) {
	}

	default void show(GameScreen screen) {
	}

	default void hide(GameScreen screen) {
	}

	default String getName() {
		return null;
	}

	@Override
	default void dispose() {
	}

	default void resize(int width, int height) {
	}

	default boolean remove() {
		return false;
	}
}
