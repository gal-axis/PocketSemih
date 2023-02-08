package com.one2b3.endcycle.engine.screens;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.audio.AudioHandler;
import com.one2b3.endcycle.engine.events.EventType;
import com.one2b3.endcycle.engine.events.GameEventHandler;
import com.one2b3.endcycle.engine.input.InputUtils;
import com.one2b3.endcycle.engine.screens.faders.FadeType;
import com.one2b3.endcycle.engine.screens.faders.ScreenFader;
import com.one2b3.endcycle.engine.screens.timer.GameScreenTimer;

import lombok.Getter;
import lombok.Setter;

public class GameScreen {

	public static final float MAX_DELTA_ORIGINAL = 0.04F;
	public static float MAX_DELTA = MAX_DELTA_ORIGINAL;

	public GameScreenInfo info;

	public AudioHandler audio = new AudioHandler(this);
	public Layers layers = new Layers(this);
	public GameEventHandler events = new GameEventHandler(this);
	public ScreenInputHandler input = new ScreenInputHandler(this);
	public GameScreenTimer timer = new GameScreenTimer();
	public Shaker shaker = new SoundShaker(this);

	public float timeScale = 1.0F;

	@Getter
	@Setter
	GameScreen previousScreen;

	Controller vibrating;

	public void init() {
	}

	public void update(float delta) {
	}

	public void updateShake(float delta) {
		shaker.update(delta);
		if (!shaker.isActive()) {
			vibrating = null;
		}
	}

	public void shake(float x, float y, float duration) {
		resetShake();
		shaker.start(x, y, duration);
		shakeVibrate(x, y, duration);
	}

	public void shakeVibrate(float x, float y, float duration) {
		vibrating = Cardinal.getInput().getLast();
		InputUtils.vibrate(vibrating, duration * 0.8F, Math.max(x, y) * 0.3F);
	}

	public void resetShake() {
		shaker.stop();
		if (vibrating != null) {
			InputUtils.cancelVibrate(vibrating);
			vibrating = null;
		}
	}

	public boolean isShaking() {
		return shaker.isActive();
	}

	public void draw(CustomSpriteBatch batch) {
		draw(batch, shaker.getX(), shaker.getY());
	}

	public void drawSpecial(CustomSpriteBatch batch) {
		draw(batch);
	}

	// LAYER METHODS

	public void addRenderProcessor(RenderProcessor renderProcessor) {
		layers.addRenderProcessor(renderProcessor);
	}

	public void removeRenderProcessor(RenderProcessor renderProcessor) {
		layers.removeRenderProcessor(renderProcessor);
	}

	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		layers.draw(batch, xOfs, yOfs);
	}

	public <T extends ScreenObject> T addObject(T object) {
		return layers.addObject(object);
	}

	public <T extends ScreenObject> boolean removeObject(Class<T> object) {
		return removeObject(getObject(object));
	}

	public boolean removeObject(ScreenObject object) {
		return layers.removeObject(object);
	}

	public void updateObjects(float delta) {
		layers.updateObjects(delta);
	}

	public void clearObjects() {
		layers.clearObjects();
	}

	public ScreenObject getObject(String name) {
		return layers.getObject(name);
	}

	public <T extends ScreenObject> T getObject(Class<T> object) {
		return layers.getObject(object);
	}

	public Array<ScreenObject> getObjects() {
		return layers.objects;
	}

	public Array<RenderProcessor> getRenderProcessors() {
		return layers.renderProcessors;
	}

	public GameScreen get() {
		return this;
	}

	public void toPreviousScreen() {
		setScreen(previousScreen);
	}

	public void toPreviousScreen(FadeType type) {
		setScreen(new ScreenFader(this, previousScreen, type).setChangesBefore(false));
	}

	public void setScreen(GameScreen screen, FadeType fade) {
		setScreen(screen, fade, true);
	}

	public void setScreen(GameScreen screen, FadeType fade, boolean changesBefore) {
		if (fade == null) {
			setScreen(screen);
		} else {
			setScreen(new ScreenFader(this, screen.get(), fade).setChangesBefore(changesBefore));
		}
	}

	public void setScreen(GameScreen screen) {
		changeTo(screen, true);
	}

	public void changeTo(GameScreen screen, boolean init) {
		if (screen != null) {
			screen.changeFrom(this, init);
		}
	}

	public void changeFrom(GameScreen screen, boolean init) {
		if (init) {
			GameScreen realScreen = get();
			if (realScreen != screen && realScreen != null && screen != null) {
				realScreen.setPreviousScreen(screen);
				realScreen.input.lastTouch = screen.input.lastTouch;
			}
			init();
		}
		Cardinal.game.setScreen(this);
	}

	public static GameScreen getCurrentScreen() {
		return Cardinal.game.getScreen();
	}

	public boolean isCurrentScreen() {
		return Cardinal.game == null || getCurrentScreen() == this;
	}

	public void render(float delta) {
		audio.update(delta);
		delta *= timeScale;
		update(delta);
		updateObjects(delta);
		updateShake(delta);
		updateTimer(delta);
	}

	public void updateTimer(float delta) {
		timer.update(delta);
	}

	public void resize(int width, int height) {
		layers.resize(width, height);
	}

	public void show() {
		audio.show();
		layers.show();
		events.trigger(EventType.SCREEN_SHOW, this);
	}

	public void hide() {
		audio.hide();
		layers.hide();
		events.trigger(EventType.SCREEN_HIDE, this);
	}

	public void dispose() {
	}

	public void resetTimer() {
		timer.clear();
	}

	public void pause() {
		audio.hide();
		events.trigger(EventType.SCREEN_PAUSE, this);
	}

	public void resume() {
		audio.show();
		events.trigger(EventType.SCREEN_RESUME, this);
	}

	public Object[] getTextArguments() {
		return null;
	}

	public boolean isLoading() {
		return Cardinal.game.loader.isLoading();
	}
}
