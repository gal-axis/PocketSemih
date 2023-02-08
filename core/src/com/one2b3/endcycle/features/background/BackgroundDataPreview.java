package com.one2b3.endcycle.features.background;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.features.models.PreviewDrawable;
import com.one2b3.endcycle.screens.background.BackgroundObject;

public class BackgroundDataPreview implements PreviewDrawable {

	BackgroundObject object = new BackgroundObject(null);

	@Override
	public void update() {
		object.createFrames(object.data);
	}

	@Override
	public void act(float delta) {
		object.update(delta);
	}

	@Override
	public void set(Actor actor, Object parent, Object object) {
		this.object.createFrames((BackgroundData) object);
	}

	@Override
	public float getWidth() {
		return 416.0F;
	}

	@Override
	public float getScale(Actor actor) {
		return actor.getHeight() / 234.0F;
	}

	@Override
	public void draw(Batch batch, float x, float y, float width, float height) {
		object.drawBackground((CustomSpriteBatch) batch, x, y, x, 0, width, height);
	}

}
