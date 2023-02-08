package com.one2b3.endcycle.engine.audio;

import com.one2b3.endcycle.engine.audio.music.MusicContainer;
import com.one2b3.endcycle.engine.audio.music.MusicData;
import com.one2b3.endcycle.engine.audio.sound.SoundContainer;
import com.one2b3.endcycle.engine.events.EventType;
import com.one2b3.endcycle.engine.screens.GameScreen;

public class MockAudioHandler extends AudioHandler {

	public MockAudioHandler(GameScreen parent) {
		super(parent);
	}

	@Override
	public SoundContainer play(SoundProvider sound) {
		if (sound != null) {
			parent.events.trigger(EventType.SOUND_PLAY, sound.getKey());
		}
		return null;
	}

	@Override
	public SoundContainer play(String sound) {
		if (sound != null) {
			parent.events.trigger(EventType.SOUND_PLAY, sound);
		}
		return null;
	}

	@Override
	public SoundContainer play(float x, float y, SoundProvider sound) {
		if (sound != null) {
			parent.events.trigger(EventType.SOUND_PLAY, sound.getKey(), x, y);
		}
		return null;
	}

	@Override
	public MusicContainer play(MusicData musicData, float volume, float position, boolean pause) {
		return null;
	}

	@Override
	public void transitionTo(MusicData musicData, float speed, boolean stopLast) {
	}

	@Override
	public float getVolume() {
		return 0.0F;
	}

	@Override
	public MusicContainer getCurrent() {
		return null;
	}

}
