package com.one2b3.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.LifecycleListener;
import com.one2b3.endcycle.core.Cardinal;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AudioUpdateThread implements Runnable, LifecycleListener {

	static final long UPDATE_MILLIS = 1000L / 100L;

	final AudioManager audio;
	boolean alive;

	public void start() {
		if (audio != null) {
			Gdx.app.addLifecycleListener(this);
			alive = true;
			Thread thread = new Thread(this, "Audio");
			thread.setDaemon(true);
			thread.setPriority(Thread.MAX_PRIORITY);
			thread.start();
		}
	}

	@Override
	public void run() {
		while (alive) {
			audio.update();
		}
		audio.dispose();
		Gdx.app.removeLifecycleListener(this);
	}

	@Override
	public void pause() {
		if (Cardinal.isPhone()) {
			audio.paused = true;
		}
	}

	@Override
	public void resume() {
		audio.paused = false;
	}

	@Override
	public void dispose() {
		alive = false;
	}

}
