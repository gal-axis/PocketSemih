package com.one2b3.endcycle.features.models;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public interface PreviewDrawable {

	default void act(float delta) {
	}

	default float getScale(Actor actor) {
		return 1.0F;
	}

	void set(Actor actor, Object parent, Object object);

	default void update() {
	}

	void draw(Batch batch, float x, float y, float width, float height);

	default float getHeight() {
		return 0.0F;
	}

	default float getWidth() {
		return 0.0F;
	}
}
