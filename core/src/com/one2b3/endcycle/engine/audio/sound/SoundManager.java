package com.one2b3.endcycle.engine.audio.sound;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.one2b3.endcycle.engine.assets.Assets;
import com.one2b3.endcycle.engine.audio.AudioHandler;
import com.one2b3.endcycle.engine.audio.SoundProvider;
import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.utils.java.Objects;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SoundManager {
	static final String PATH = "sound";

	static final Map<String, Long> framePlayed = new HashMap<>();

	static final Map<String, LoadedSound> loadedSounds = new HashMap<>();

	public static float defaultVolume;
	public static boolean debugging;

	public static void load() {
		clearSounds();
	}

	public static void clearSounds() {
		for (LoadedSound sound : loadedSounds.values()) {
			sound.dispose();
		}
		loadedSounds.clear();
	}

	public static Sound load(String soundId) {
		if (loadedSounds.containsKey(soundId)) {
			LoadedSound loaded = loadedSounds.get(soundId);
			if (debugging) {
				loaded.update();
			}
			return loaded.sound;
		} else {
			FileHandle handle = Assets.findHandle(PATH, soundId);
			if (handle.exists()) {
				try {
					LoadedSound loaded = new LoadedSound(handle);
					if (loaded.sound != null) {
						loadedSounds.put(soundId, loaded);
						return loaded.sound;
					}
				} catch (GdxRuntimeException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
	}

	private static SoundContainer playSound(AudioHandler handler, String soundId, float volume, float pitch) {
		if (soundId != null && Gdx.app.getType() != ApplicationType.HeadlessDesktop) {
			volume *= defaultVolume;
			if (volume > 0.0F && Objects.get(framePlayed, soundId, -1L) != Gdx.graphics.getFrameId()) {
				framePlayed.put(soundId, Gdx.graphics.getFrameId());
				Sound sound = load(soundId);
				if (sound != null) {
					long id = sound.play(volume, pitch, 0.0F);
					SoundContainer container = new SoundContainer(handler, soundId, sound, id);
					if (debugging) {
						PlayingSoundMessage message = Layers.global.getObject(PlayingSoundMessage.class);
						if (message == null) {
							message = new PlayingSoundMessage();
							Layers.global.addObject(message);
						}
						message.add(container);
					}
					return container;
				}
			}
		}
		return null;
	}

	public static SoundContainer playSound(AudioHandler handler, SoundProvider sound) {
		return sound == null ? null : playSound(handler, sound.getKey(), sound.getVolume(), sound.getPitch());
	}

	public static SoundContainer playSound(AudioHandler handler, String sound) {
		return playSound(handler, sound, 1.0F, 1.0F);
	}

}