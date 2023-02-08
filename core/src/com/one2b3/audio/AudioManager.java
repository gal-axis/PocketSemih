package com.one2b3.audio;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.one2b3.audio.ogg.OggStream;

public class AudioManager {

	final Array<Music> playing = new Array<>();

	List<Runnable> actions = new ArrayList<>(), //
			temp = new ArrayList<>();

	public boolean paused;

	public void update() {
		if (paused || (playing.size == 0 && actions.size() == 0)) {
			try {
				Thread.sleep(16L);
			} catch (InterruptedException e) {
			}
		}
		for (int i = playing.size - 1; i >= 0; i--) {
			Music m = playing.get(i);
			if (!paused) {
				m.update();
			}
			if (!m.isPlaying()) {
				playing.removeIndex(i);
			}
		}
		synchronized (this) {
			List<Runnable> a = temp;
			temp = actions;
			actions = a;
		}
		for (int i = 0; i < temp.size(); i++) {
			try {
				temp.get(i).run();
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
		temp.clear();
	}

	public Music createMusic(FileHandle handle) {
		return new Music(new OggStream(handle));
	}

	public Sound createSound(FileHandle handle) {
		return Gdx.audio.newSound(handle);
	}

	public synchronized void play(Music music) {
		actions.add(() -> {
			playing.add(music);
			music.play();
		});
	}

	public synchronized void stop(Music music) {
		actions.add(() -> {
			playing.removeValue(music, true);
			music.stop();
		});
	}

	public synchronized void dispose(Music music) {
		actions.add(() -> {
			playing.removeValue(music, true);
			music.dispose();
		});
	}

	public synchronized void pause(Music music) {
		actions.add(() -> {
			playing.removeValue(music, true);
			music.pause();
		});
	}

	public void setPosition(Music music, float position) {
		actions.add(() -> music.setPosition(position));
	}

	public void dispose() {
		while (playing.size > 0) {
			playing.pop().dispose();
		}
	}

}
