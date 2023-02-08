package com.one2b3.endcycle.engine.audio.music;

import com.badlogic.gdx.files.FileHandle;
import com.one2b3.audio.Music;
import com.one2b3.audio.listeners.CompletionListener;
import com.one2b3.audio.listeners.LoopListener;
import com.one2b3.audio.listeners.MusicProcessor;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.engine.assets.Assets;
import com.one2b3.endcycle.utils.ID;

public final class MusicContainer implements CompletionListener {

	public final MusicData data;
	final FileHandle handle;

	ID next;
	float volume;

	Music music;

	public MusicContainer(MusicData musicData) {
		this.data = musicData;
		handle = Assets.findHandle(musicData.filepath);
	}

	public void load() {
		if (music == null) {
			music = Cardinal.newMusic(handle);
			music.volume = volume;
			setNext(data.next);
		}
	}

	public void setNext(ID next) {
		this.next = next;
		if (music != null) {
			if (next == null) {
				if (data.loop > 0.0F) {
					music.setCachePcm(true);
					music.loopPosition = data.loop;
				} else {
					music.setCachePcm(false);
				}
				if (data.loop >= 0.0F) {
					setLooping(true);
				}
			} else {
				music.setCachePcm(false);
				setLooping(false);
				music.completionListener = this;
			}
		}
	}

	@Override
	public void onCompletion(Music music) {
		if (next != null) {
			MusicHandler.instance.play(next, false);
		}
		dispose();
	}

	public void play() {
		load();
		if (music != null) {
			Cardinal.getAudio().play(music);
		}
	}

	public void pause() {
		if (music != null) {
			Cardinal.getAudio().pause(music);
		}
	}

	public boolean isPlaying() {
		return music != null && music.isPlaying();
	}

	public void setLooping(boolean looping) {
		if (music != null) {
			music.looping = looping;
		}
	}

	public boolean isLooping() {
		return music != null && music.looping;
	}

	public void setVolume(float volume) {
		this.volume = volume;
		if (music != null) {
			music.volume = volume;
		}
	}

	public float getVolume() {
		return music == null ? 0.0F : music.volume;
	}

	public void setPan(float pan, float volume) {
		if (music != null) {
			music.setPan(pan, volume);
		}
	}

	public void setPosition(float position) {
		if (music != null) {
			Cardinal.getAudio().setPosition(music, position);
		}
	}

	public float getPosition() {
		return music == null ? 0.0F : music.getPosition();
	}

	public void dispose() {
		if (music != null) {
			Cardinal.getAudio().dispose(music);
			music = null;
		}
	}

	public void setCachePcm(boolean cache) {
		music.setCachePcm(cache);
	}

	public void setOnLoopListener(LoopListener listener) {
		music.loopListener = listener;
	}

	public void setMusicProcessor(MusicProcessor processor) {
		if (music != null) {
			music.setMusicProcessor(processor);
		}
	}

	public MusicProcessor getMusicProcessor() {
		return music == null ? null : music.musicProcessor;
	}
}
