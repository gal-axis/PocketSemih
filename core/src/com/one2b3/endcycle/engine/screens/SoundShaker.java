package com.one2b3.endcycle.engine.screens;

import com.one2b3.endcycle.engine.audio.sound.SoundInfo;
import com.one2b3.endcycle.engine.audio.sound.SoundManager;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SoundShaker extends Shaker {

	final GameScreen screen;

	SoundInfo sound;
	float soundMax = 4.0F, soundLoop = 0.3F;

	float soundTimer;

	@Override
	public void start(float x, float y, float duration) {
		super.start(x, y, duration);
		soundTimer = 0.0F;
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		if (!Float.isNaN(interp)) {
			if (soundTimer <= 0.0F) {
				soundTimer += soundLoop;
				sound.volume = Math.min(1.0F, Math.max(nextX, nextY) / soundMax);
				SoundManager.playSound(screen.audio, sound);
			}
			soundTimer -= delta;
		}
	}
}
