package com.one2b3.endcycle.engine.audio;

import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.engine.audio.music.MusicCatalog;
import com.one2b3.endcycle.engine.audio.music.MusicContainer;
import com.one2b3.endcycle.engine.audio.music.MusicData;
import com.one2b3.endcycle.engine.audio.music.MusicHandler;
import com.one2b3.endcycle.engine.audio.sound.SoundContainer;
import com.one2b3.endcycle.engine.audio.sound.SoundManager;
import com.one2b3.endcycle.engine.events.EventType;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.utils.ID;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AudioHandler {

	final GameScreen parent;

	Array<SoundContainer> playing = new Array<>();
	Array<Float> time = new Array<>();

	public void addPlaying(SoundContainer container) {
		if (container != null && !playing.contains(container, true)) {
			this.playing.add(container);
		}
	}

	public void removePlaying(SoundContainer container) {
		this.playing.removeValue(container, true);
	}

	public void show() {
		for (int i = 0; i < playing.size; i++) {
			playing.get(i).resume();
		}
	}

	public void update(float delta) {
		for (int i = playing.size - 1; i >= 0; i--) {
			SoundContainer current = playing.get(i);
			if (current.update(delta)) {
				playing.removeIndex(i);
			}
		}
	}

	public void hide() {
		for (int i = playing.size - 1; i >= 0; i--) {
			SoundContainer current = playing.get(i);
			current.pause();
			playing.add(current);
		}
	}

	public SoundContainer play(SoundProvider sound) {
		if (sound != null) {
			parent.events.trigger(EventType.SOUND_PLAY, sound.getKey());
			return SoundManager.playSound(this, sound);
		}
		return null;
	}

	public SoundContainer play(String sound) {
		if (sound != null) {
			parent.events.trigger(EventType.SOUND_PLAY, sound);
			return SoundManager.playSound(this, sound);
		}
		return null;
	}

	public SoundContainer play(float x, float y, SoundProvider sound) {
		if (sound != null) {
			parent.events.trigger(EventType.SOUND_PLAY, sound.getKey(), x, y);
			return SoundManager.playSound(this, sound);
		}
		return null;
	}

	public void stop() {
		MusicHandler.instance.stop();
	}

	public MusicContainer play(ID song) {
		return play(MusicCatalog.get(song));
	}

	public MusicContainer play(MusicData musicData) {
		return play(musicData, false);
	}

	public void transitionTo(ID song) {
		transitionTo(MusicCatalog.get(song), 1.0F, true);
	}

	public MusicContainer play(MusicData musicData, boolean pause) {
		return play(musicData, MusicHandler.instance.getVolume(), -1.0F, pause);
	}

	public MusicContainer play(MusicData musicData, float volume, float position, boolean pause) {
		return MusicHandler.instance.play(musicData, volume, position, pause);
	}

	public void transitionTo(MusicData musicData, float speed, boolean stopLast) {
		MusicHandler.instance.transitionTo(musicData, speed, stopLast);
	}

	public float getVolume() {
		return MusicHandler.instance.getDefaultVolume();
	}

	public MusicContainer getCurrent() {
		return MusicHandler.instance.getCurrent();
	}
}
