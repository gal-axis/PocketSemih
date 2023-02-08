package com.one2b3.endcycle.engine.audio.sound;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.badlogic.gdx.files.FileHandle;
import com.one2b3.endcycle.engine.assets.Assets;

public class SoundFinder {

	public static List<String> findSounds() {
		Set<String> files = new HashSet<>();
		FileHandle handle = Assets.findHandle("sound");
		addSoundFiles(handle.file(), handle.file(), files);
		List<String> list = new ArrayList<>(files);
		Collections.sort(list);
		return list;
	}

	private static void addSoundFiles(File rootFile, File file, Set<String> files) {
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				addSoundFiles(rootFile, f, files);
			}
		} else {
			if (file.getPath().endsWith(".wav")) {
				String path = file.getAbsolutePath().substring(rootFile.getAbsolutePath().length() + 1).replace('\\',
						'/');
				files.add(path);
			}
		}
	}

}
