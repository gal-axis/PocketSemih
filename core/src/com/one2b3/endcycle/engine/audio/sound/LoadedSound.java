package com.one2b3.endcycle.engine.audio.sound;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.one2b3.endcycle.core.Cardinal;

public class LoadedSound {

	public final FileHandle handle;
	public long modified;
	public Sound sound;

	public LoadedSound(FileHandle handle) {
		this.handle = handle;
		reload();
	}

	public void reload() {
		if (sound != null) {
			dispose();
		}
		modified = handle.lastModified();
		sound = Cardinal.newSound(handle);
	}

	public void update() {
		if (modified != handle.lastModified()) {
			reload();
		}
	}

	public void dispose() {
		sound.dispose();
		sound = null;
	}
}
