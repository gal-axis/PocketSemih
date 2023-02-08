package com.one2b3.endcycle.engine.audio.sound;

import com.one2b3.endcycle.engine.screens.GameScreen;

public final class SoundTimer {

	public SoundInfo sound;

	final float[] times;

	public float timer;

	public GameScreen screen;

	public SoundTimer(SoundInfo sound, float... times) {
		this.sound = sound;
		this.times = times;
	}

	public boolean update(float x, float y, float delta) {
		boolean played = false;
		float old = timer;
		timer += delta;
		for (int i = 0; i < times.length; i++) {
			if (old < times[i] && timer >= times[i]) {
				screen.audio.play(x, y, sound);
				played = true;
			}
		}
		return played;
	}
}
