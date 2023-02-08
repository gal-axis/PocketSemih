package com.one2b3.endcycle.engine.screens.faders.types;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.graphics.Drawable;
import com.one2b3.endcycle.engine.screens.faders.FadeProcessor;
import com.one2b3.endcycle.utils.ScreenShotFactory;

public class FadeToNext extends FadeProcessor {

	static final float FADE_SPEED = 5.0F;

	Texture texture;
	Drawable screenshot;
	float alpha;

	@Override
	public void start() {
		alpha = 1.0F;
		// Setup texture
		texture = new Texture(ScreenShotFactory.captureScreen());
		TextureRegion region = new TextureRegion(texture);
		region.flip(false, true);
		screenshot = new Drawable(region);
		initNewScreen();
	}

	@Override
	public void update(float delta) {
		updateOldScreen(delta);
		updateNewScreen(delta);
		if (alpha > 0) {
			alpha -= delta * FADE_SPEED;
			if (alpha < 0) {
				alpha = 0.0F;
			}
		}
	}

	@Override
	public void draw(CustomSpriteBatch batch) {
		drawNewScreen(batch);
		Painter.on(batch).alpha(alpha).scale(1.0F / batch.getScaleX(), 1.0F / batch.getScaleY()).paint(screenshot);
	}

	@Override
	public void dispose() {
		disposeOldScreen();
		texture.dispose();
		screenshot = null;
		alpha = 0.0F;
	}

	@Override
	public boolean done() {
		return alpha <= 0;
	}

}
