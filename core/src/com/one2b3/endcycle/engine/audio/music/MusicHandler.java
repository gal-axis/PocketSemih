package com.one2b3.endcycle.engine.audio.music;

import java.util.HashMap;
import java.util.Map;

import com.one2b3.endcycle.utils.ID;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MusicHandler {

	public static final MusicHandler instance = new MusicHandler();

	public static final float SHIFT_SPEED_DEFAULT = 1.0F;

	final Map<ID, MusicContainer> cached = new HashMap<>();
	final BeatMeasurer measurer = new BeatMeasurer();

	MusicContainer current;
	MusicData next;
	float defaultVolume;
	float realVolume;

	float shiftSpeed = SHIFT_SPEED_DEFAULT;
	boolean shifting, stopLast;

	public void transitionTo(ID next) {
		transitionTo(MusicCatalog.get(next), SHIFT_SPEED_DEFAULT);
	}

	public void transitionTo(MusicData next) {
		transitionTo(next, SHIFT_SPEED_DEFAULT);
	}

	public void transitionTo(MusicData next, float speed) {
		transitionTo(next, speed, true);
	}

	public void transitionTo(MusicData next, float speed, boolean stopLast) {
		boolean nextIsCurrent = (this.current == null && next == null) || (current != null && current.data == next);
		if (this.next != next || !nextIsCurrent) {
			this.shiftSpeed = speed;
			if (nextIsCurrent) {
				shiftBack();
			} else {
				if (!shifting) {
					this.stopLast = stopLast;
					shifting = true;
					shiftVolume(0.0F);
				}
				this.next = next;
			}
		}
	}

	public void shiftBack() {
		if (shifting) {
			shifting = false;
			this.next = null;
			shiftVolume(getDefaultVolume());
		}
	}

	public void setCurrentVolume(float volume) {
		shiftVolume(volume);
		if (current != null) {
			current.setVolume(volume);
		}
	}

	public void shiftVolume(float volume) {
		this.realVolume = volume;
	}

	public float getVolume() {
		return realVolume;
	}

	public float getCurrentVolume() {
		if (current == null) {
			return getVolume();
		} else {
			return current.getVolume();
		}
	}

	public void setDefaultVolume(float defaultVolume) {
		this.defaultVolume = defaultVolume;
		if (!shifting) {
			setCurrentVolume(defaultVolume);
		}
	}

	public float getDefaultVolume() {
		return defaultVolume;
	}

	public void update(float delta) {
		if (shifting) {
			if (current == null || current.getVolume() == 0.0F) {
				shifting = false;
				play(next, 0.0F, -1.0F, !stopLast);
				shiftVolume(getDefaultVolume());
				if (next == null) {
					setCurrentVolume(getDefaultVolume());
				}
				next = null;
			}
		}
		if (current != null) {
			if (current.getVolume() != realVolume) {
				float oldVol = current.getVolume();
				if (oldVol < realVolume) {
					oldVol += delta * shiftSpeed;
					if (oldVol > realVolume) {
						oldVol = realVolume;
					}
				} else {
					oldVol -= delta * shiftSpeed;
					if (oldVol < realVolume) {
						oldVol = realVolume;
					}
				}
				current.setVolume(oldVol);
			}
		}
		measurer.update(delta);
	}

	public float timeToBeat(int beat) {
		return measurer.timeToBeat(beat);
	}

	public boolean isOnBeat() {
		return measurer.isOnBeat();
	}

	public int beat() {
		return measurer.beat();
	}

	public MusicContainer play(ID music) {
		return play(MusicCatalog.get(music));
	}

	public MusicContainer play(ID music, boolean pause) {
		return play(MusicCatalog.get(music), pause);
	}

	public MusicContainer play(MusicData m) {
		return play(m, realVolume);
	}

	public MusicContainer play(MusicData m, float volume) {
		return play(m, volume, -1.0F, false);
	}

	public MusicContainer play(MusicData m, boolean pause) {
		return play(m, realVolume, -1.0F, pause);
	}

	public MusicContainer play(MusicData m, float volume, float position, boolean pause) {
		if (current == null || current.data != m || shifting) {
			if (pause && (!shifting || !stopLast)) {
				pause();
				current = null;
			} else {
				stop();
			}
			if (m != null) {
				if (shifting) {
					shifting = false;
					this.next = null;
					setCurrentVolume(getDefaultVolume());
				}
				current = getContainer(m);
				if (current != null) {
					if (position >= 0.0F) {
						current.setPosition(position);
					}
					current.setVolume(volume);
					current.play();
				}
				measurer.setBeat(m.bpm);
				return current;
			}
		}
		return null;
	}

	private MusicContainer getContainer(MusicData data) {
		MusicContainer container = cached.get(data.getId());
		if (container == null) {
			container = new MusicContainer(data);
			cached.put(data.getId(), container);
		}
		return container;
	}

	public void stop() {
		if (current != null) {
			clearContainer(current.data);
			current = null;
		}
	}

	public MusicContainer getCurrent() {
		return current;
	}

	private void clearContainer(MusicData data) {
		MusicContainer container = cached.remove(data.getId());
		if (container != null) {
			container.dispose();
		}
	}

	public boolean isPlaying() {
		return current != null && current.isPlaying();
	}

	public void pause() {
		if (current != null) {
			current.pause();
		}
	}

	public void play() {
		if (current != null) {
			current.play();
		}
	}

	public void clear() {
		stop();
		shifting = false;
		next = null;
		for (MusicContainer music : cached.values()) {
			music.dispose();
		}
		cached.clear();
	}
}
