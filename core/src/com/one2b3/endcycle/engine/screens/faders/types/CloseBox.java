package com.one2b3.endcycle.engine.screens.faders.types;

import com.one2b3.endcycle.core.painting.CustomSpriteBatch;

public class CloseBox extends OpenBox {

	@Override
	public void start() {
		super.start();
		state = OPENING;
		alpha.toMax();
		yOfs.toMin();
	}

	@Override
	public void update(float delta) {
		if (state == CLOSING) {
			if (alpha.atMax()) {
				disposeOldScreen();
				initNewScreen();
			}
			updateNewScreen(delta);
			if (!alpha.decrease(delta)) {
				dispose();
			}
		} else if (state == OPENING) {
			updateOldScreen(delta);
			if (!yOfs.increase(delta)) {
				state = CLOSING;
				alpha.toMax();
			}
		}
	}

	@Override
	public void drawOldScreen(CustomSpriteBatch batch) {
		super.drawNewScreen(batch);
	}

	@Override
	public void drawNewScreen(CustomSpriteBatch batch) {
		super.drawOldScreen(batch);
	}
}
