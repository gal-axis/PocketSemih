package com.one2b3.endcycle.engine.screens.faders.types;

import com.one2b3.endcycle.core.painting.CustomSpriteBatch;

public class FadeToWhite extends FadeToBlack {

	@Override
	public void draw(CustomSpriteBatch batch) {
		switch (step) {
		case FADING_IN:
			drawOldScreen(batch);
			break;
		case FADING_OUT:
		case DONE:
			drawNewScreen(batch);
			break;
		}
		batch.drawScreenTint(1.0F, 1.0F, 1.0F, currentAlpha);
	}
}
