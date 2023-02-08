package com.one2b3.endcycle.engine.audio.sound;

import com.one2b3.endcycle.engine.audio.SoundProvider;
import com.one2b3.endcycle.engine.proguard.KeepClass;
import com.one2b3.endcycle.features.models.FileType;
import com.one2b3.endcycle.features.models.Name;
import com.one2b3.endcycle.features.models.NullOnEmpty;
import com.one2b3.endcycle.features.models.Shrink;
import com.one2b3.endcycle.features.models.primitives.FloatRange;
import com.one2b3.endcycle.features.models.primitives.Percent;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@KeepClass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Shrink
public final class SoundInfo implements SoundProvider {

	@Name("")
	@FileType(path = "sound/", value = "wav")
	@NullOnEmpty
	public String sound;
	@FloatRange(min = 0.0F, max = 1.0F)
	@Percent
	@Getter
	public float volume = 1.0F;
	@FloatRange(min = 0.5F, max = 2.0F)
	@Percent
	@Getter
	public float pitch = 1.0F;

	public SoundInfo(SoundInfo soundInfo) {
		this(soundInfo.sound, soundInfo.volume, soundInfo.pitch);
	}

	public SoundInfo(String sound) {
		this(sound, 1.0F, 1.0F);
	}

	public SoundInfo volumeAndPitch(float volume, float pitch) {
		return new SoundInfo(sound, volume, pitch);
	}

	public SoundInfo volume(float volume) {
		return new SoundInfo(sound, volume, pitch);
	}

	public SoundInfo pitch(float pitch) {
		return new SoundInfo(sound, volume, pitch);
	}

	public void set(SoundInfo info) {
		if (info == null) {
			sound = null;
			volume = 1.0F;
			pitch = 1.0F;
		} else {
			this.sound = info.sound;
			this.volume = info.volume;
			this.pitch = info.pitch;
		}
	}

	@Override
	public String getKey() {
		return sound;
	}
}
