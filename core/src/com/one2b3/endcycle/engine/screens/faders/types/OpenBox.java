package com.one2b3.endcycle.engine.screens.faders.types;

import com.badlogic.gdx.graphics.Color;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.objects.forms.BatchRectangle;
import com.one2b3.endcycle.engine.screens.faders.FadeProcessor;
import com.one2b3.endcycle.utils.bounded.BoundedFloat;

public class OpenBox extends FadeProcessor {
	static final int CLOSING = 0, OPENING = 1, DONE = 2;

	final BatchRectangle rect = new BatchRectangle();

	int state;
	BoundedFloat yOfs = new BoundedFloat(0.0F, 1.1F, 6.0F), alpha = new BoundedFloat(0.0F, 1.0F, 8.0F);

	public OpenBox() {
	}

	@Override
	public void start() {
		rect.width = Cardinal.getWidth();
		rect.setTint(Color.BLACK);
		state = CLOSING;
		alpha.toMin();
		yOfs.toMax();
	}

	@Override
	public void update(float delta) {
		if (state == CLOSING) {
			updateOldScreen(delta);
			if (!alpha.increase(delta)) {
				state = OPENING;
			}
		} else if (state == OPENING) {
			if (alpha.atMax()) {
				disposeOldScreen();
				initNewScreen();
				alpha.toMin();
			}
			updateNewScreen(delta);
			if (!yOfs.decrease(delta)) {
				dispose();
			}
		}
	}

	@Override
	public void draw(CustomSpriteBatch batch) {
		if (state == CLOSING) {
			drawOldScreen(batch);
			batch.drawScreenTint(alpha.getVal());
		} else if (state == OPENING) {
			drawNewScreen(batch);
			rect.height = yOfs.getVal() * Cardinal.getHeight() * 0.52F;
			rect.draw(batch, 0, Cardinal.getHeight() + 1.0F - rect.height);
			rect.draw(batch, 0, 0);
		} else {
			drawNewScreen(null, 0, 0);
		}
	}

	@Override
	public void dispose() {
		yOfs.toMin();
		state = DONE;
		alpha.toMin();
	}

	@Override
	public boolean done() {
		return state == DONE;
	}

}
