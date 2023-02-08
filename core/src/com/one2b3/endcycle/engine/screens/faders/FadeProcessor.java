package com.one2b3.endcycle.engine.screens.faders;

import com.badlogic.gdx.Gdx;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;

public abstract class FadeProcessor {

	public RenderFader fader;

	public void setFader(RenderFader fader) {
		this.fader = fader;
	}

	public void updateOldScreen(float delta) {
		try {
			fader.updateOld(delta);
		} catch (Throwable t) {
			Gdx.app.error("Screen", "Error updating old screen!", t);
		}
	}

	public void disposeOldScreen() {
		try {
			fader.disposeOld();
		} catch (Throwable t) {
			Gdx.app.error("Screen", "Error disposing old screen!", t);
		}
	}

	public void updateNewScreen(float delta) {
		try {
			fader.updateNew(delta);
		} catch (Throwable t) {
			Gdx.app.error("Screen", "Error updating new screen!", t);
		}
	}

	public void initNewScreen() {
		try {
			fader.initNew();
		} catch (Throwable t) {
			Gdx.app.error("Screen", "Error initializing new screen!", t);
		}
	}

	public void drawOldScreen(CustomSpriteBatch batch) {
		try {
			fader.drawOld(batch);
		} catch (Throwable t) {
			Gdx.app.error("Screen", "Error drawing old screen!", t);
		}
	}

	public void drawOldScreen(CustomSpriteBatch batch, float x, float y) {
		try {
			fader.drawOld(batch, x, y);
		} catch (Throwable t) {
			Gdx.app.error("Screen", "Error drawing old screen!", t);
		}
	}

	public void drawNewScreen(CustomSpriteBatch batch) {
		try {
			fader.drawNew(batch);
		} catch (Throwable t) {
			Gdx.app.error("Screen", "Error drawing new screen!", t);
		}
	}

	public void drawNewScreen(CustomSpriteBatch batch, float x, float y) {
		try {
			fader.drawNew(batch, x, y);
		} catch (Throwable t) {
			Gdx.app.error("Screen", "Error drawing new screen!", t);
		}
	}

	public abstract void start();

	public abstract void update(float delta);

	public abstract void draw(CustomSpriteBatch batch);

	public abstract void dispose();

	public abstract boolean done();
}
