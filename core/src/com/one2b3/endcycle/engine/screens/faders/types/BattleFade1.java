package com.one2b3.endcycle.engine.screens.faders.types;

import com.badlogic.gdx.graphics.Color;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.graphics.Drawable;
import com.one2b3.endcycle.engine.graphics.Drawables;
import com.one2b3.endcycle.engine.screens.faders.FadeProcessor;
import com.one2b3.endcycle.utils.bounded.BoundedFloat;

public class BattleFade1 extends FadeProcessor {

	static final int SWIPE_OLD = 0, SWIPE_NEW = 1, DONE = 2;
	static final float SWIPE_SPEED = 900.0F;

	final BoundedFloat move = new BoundedFloat(0.0F, 1.0F, 6.0F);
	Drawable transition;

	int step;

	@Override
	public void start() {
		transition = Drawables.Transition_Rogue.get();
		move.toMax();
		step = SWIPE_OLD;
	}

	@Override
	public void update(float delta) {
		switch (step) {
		case SWIPE_OLD:
			updateOldScreen(delta);
			if (!move.decrease(delta)) {
				disposeOldScreen();
				initNewScreen();
				step = SWIPE_NEW;
			}
			break;
		case SWIPE_NEW:
			updateNewScreen(delta);
			if (!move.increase(delta)) {
				step = DONE;
			}
			break;
		}
	}

	@Override
	public void draw(CustomSpriteBatch batch) {
		float add = transition.getWidth();
		float x = (Cardinal.getWidth() + add * 2) * move.getVal() - add;
		if (step == SWIPE_OLD) {
			drawOldScreen(batch);
			for (float y = 0.0F; y < Cardinal.getHeight(); y += transition.getHeight()) {
				transition.draw(batch, x, y);
			}
			batch.drawRectangle(x + transition.getWidth(), 0, Cardinal.getWidth(), Cardinal.getHeight(), Color.BLACK);
		} else {
			drawNewScreen(batch);
			x = Cardinal.getWidth() - x;
			for (float y = 0.0F; y < Cardinal.getHeight(); y += transition.getHeight()) {
				transition.draw(batch, x + transition.getWidth(), y, -1.0F, 1.0F);
			}
			batch.drawRectangle(0, 0, x, Cardinal.getHeight(), Color.BLACK);
		}
	}

	@Override
	public boolean done() {
		return step == DONE;
	}

	@Override
	public void dispose() {
	}
}
