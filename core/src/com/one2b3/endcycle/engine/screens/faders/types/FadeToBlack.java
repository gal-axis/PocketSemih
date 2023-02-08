package com.one2b3.endcycle.engine.screens.faders.types;

import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.screens.faders.FadeProcessor;

public class FadeToBlack extends FadeProcessor {

	static final int FADING_IN = 0, STAYING_FADED = 1, FADING_OUT = 2, DONE = 3;
	public static final float FADE_IN_TIME = 0.15F, FADE_STAY_TIME = 0.05F, FADE_OUT_TIME = 0.15F;

	int step;

	float currentAlpha;
	float timeSinceLastStep;

	@Override
	public void start() {
		currentAlpha = 0.0F;
		timeSinceLastStep = 0.0F;
		step = FADING_IN;
	}

	@Override
	public void update(float delta) {
		switch (step) {
		case FADING_IN:
			updateOldScreen(delta);
			currentAlpha += 1.0F / FADE_IN_TIME * delta;
			if (currentAlpha >= 1.0F) {
				currentAlpha = 1.0F;
				setStep(STAYING_FADED);
			}
			break;
		case STAYING_FADED:
			timeSinceLastStep += delta;
			if (timeSinceLastStep >= FADE_STAY_TIME) {
				disposeOldScreen();
				initNewScreen();
				setStep(FADING_OUT);
			}
			break;
		case FADING_OUT:
			if (!fader.isLoading()) {
				updateNewScreen(delta);
				currentAlpha -= 1.0F / FADE_OUT_TIME * delta;
				if (currentAlpha <= 0.0F) {
					currentAlpha = 0.0F;
					setStep(DONE);
				}
			}
			break;
		}
	}

	public void setStep(final int step) {
		this.step = step;
		timeSinceLastStep = 0.0F;
	}

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
		batch.drawScreenTint(currentAlpha);
	}

	@Override
	public void dispose() {
		step = DONE;
	}

	@Override
	public boolean done() {
		return step == DONE;
	}

}
