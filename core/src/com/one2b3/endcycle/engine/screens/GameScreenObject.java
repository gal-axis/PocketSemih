package com.one2b3.endcycle.engine.screens;

import com.badlogic.gdx.Gdx;
import com.one2b3.endcycle.engine.audio.SoundProvider;
import com.one2b3.endcycle.engine.audio.sound.SoundContainer;

public abstract class GameScreenObject implements ScreenObject {

	public GameScreen screen;
	public boolean showing;

	@Override
	public void init(GameScreen screen) {
		this.screen = screen;
		this.showing = screen.isCurrentScreen();
	}

	@Override
	public void show(GameScreen screen) {
		this.screen = screen;
		this.showing = true;
	}

	@Override
	public void hide(GameScreen screen) {
		this.showing = false;
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean remove() {
		return false;
	}

	public final void removeObject() {
		if (screen != null) {
			screen.removeObject(this);
		}
	}

	public <T extends GameScreen> T getScreenAs(Class<T> screenClass) {
		if (screen != null && screenClass.isAssignableFrom(screen.getClass())) {
			return screenClass.cast(screen);
		} else {
			return null;
		}
	}

	public int triggerEvent(Object type, Object trigger, Object... parameters) {
		return (screen == null ? 0 : screen.events.trigger(type, trigger, parameters));
	}

	public SoundContainer playSound(SoundProvider sound) {
		return screen == null ? null : screen.audio.play(sound);
	}

	public SoundContainer playSound(float x, float y, SoundProvider sound) {
		if (screen == null) {
			Gdx.app.error("Sound", "Trying to play audio without screen on object: " + this);
			return null;
		}
		return screen.audio.play(x, y, sound);
	}

	@Override
	public final boolean equals(Object obj) {
		return this == obj;
	}

	@Override
	public final int hashCode() {
		return super.hashCode();
	}

	public final <T extends ScreenObject> T addObject(T object) {
		return screen == null ? object : screen.addObject(object);
	}
}
