package com.one2b3.endcycle.engine.screens.faders.types;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.graphics.Drawable;
import com.one2b3.endcycle.engine.screens.faders.FadeProcessor;
import com.one2b3.endcycle.utils.ScreenShotFactory;
import com.one2b3.endcycle.utils.bounded.BoundedFloat;

public class BattleFade2 extends FadeProcessor {

	Texture texture;
	Drawable screenshot;
	BoundedFloat alpha = new BoundedFloat(0.0F, 1.0F, 6.0F);
	BoundedFloat stay = new BoundedFloat(0.1F);

	@Override
	public void start() {
		alpha.toMin();
		stay.toMin();
		texture = new Texture(ScreenShotFactory.captureScreen());
		TextureRegion region = new TextureRegion(texture);
		region.flip(false, true);
		screenshot = new Drawable(region);
	}

	@Override
	public void update(float delta) {
		if (stay.atMax()) {
			updateNewScreen(delta);
			alpha.decrease(delta);
		} else if (!alpha.increase(delta)) {
			stay.increase(delta);
			if (stay.atMax()) {
				disposeOldScreen();
				initNewScreen();
			} else {
				updateOldScreen(delta);
			}
		}
	}

	@Override
	public void draw(CustomSpriteBatch batch) {
		if (stay.atMax()) {
			drawNewScreen(batch);
		} else {
			float scale = alpha.getVal() * 1.5F + 1.0F;
			Painter.on(batch).at(Cardinal.getWidth() * 0.5F, Cardinal.getHeight() * 0.5F)
					.scale(scale / batch.getScaleX(), scale / batch.getScaleY()).align(0).paint(screenshot);
		}
		batch.drawScreenTint(1.0F, 1.0F, 1.0F, alpha.getVal());
	}

	@Override
	public void dispose() {
		texture.dispose();
		screenshot = null;
	}

	@Override
	public boolean done() {
		return alpha.atMin() && stay.atMax();
	}

}
