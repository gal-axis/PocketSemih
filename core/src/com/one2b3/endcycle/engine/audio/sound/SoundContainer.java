package com.one2b3.endcycle.engine.audio.sound;

import com.badlogic.gdx.audio.Sound;
import com.one2b3.endcycle.engine.audio.AudioHandler;

public class SoundContainer {

	public final AudioHandler handler;
	public final String path;
	Sound sound;
	long id;

	boolean looping;
	float time;

	public SoundContainer(AudioHandler handler, String path, Sound sound, long id) {
		this.handler = handler;
		this.path = path;
		this.sound = sound;
		this.id = id;
		add();
	}

	private void remove() {
		if (handler != null) {
			this.handler.removePlaying(this);
		}
	}

	private void add() {
		if (handler != null) {
			this.handler.addPlaying(this);
		}
	}

	public SoundContainer play() {
		return new SoundContainer(handler, path, sound, sound.play());
	}

	public void resume() {
		sound.resume(id);
		add();
	}

	public void setLooping(boolean looping) {
		if (this.looping != looping) {
			sound.setLooping(id, looping);
			this.looping = looping;
		}
	}

	public void stop() {
		time = 0.0F;
		sound.stop(id);
		remove();
	}

	public void pause() {
		sound.pause();
		remove();
	}

	public void setVolume(float volume) {
		sound.setVolume(id, volume);
	}

	public void setPitch(float pitch) {
		sound.setPitch(id, pitch);
	}

	public void setPan(float pan, float volume) {
		sound.setPan(id, pan, volume);
	}

	public boolean update(float delta) {
		if (looping) {
			return false;
		}
		time += delta;
		return time > 5.0F;
	}
}
