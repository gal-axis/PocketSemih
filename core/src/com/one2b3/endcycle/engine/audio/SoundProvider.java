package com.one2b3.endcycle.engine.audio;

import com.one2b3.endcycle.engine.audio.sound.SoundInfo;

public interface SoundProvider {

	String getKey();

	default float getVolume() {
		return 1.0F;
	}

	default float getPitch() {
		return 1.0F;
	}

	default SoundInfo get() {
		return new SoundInfo(getKey());
	}
}
