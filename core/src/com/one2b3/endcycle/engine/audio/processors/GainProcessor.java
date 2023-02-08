package com.one2b3.endcycle.engine.audio.processors;

import java.nio.ByteBuffer;

import com.badlogic.gdx.math.MathUtils;
import com.one2b3.audio.listeners.MusicProcessor;

public final class GainProcessor implements MusicProcessor {

	float gainPercentage;

	public GainProcessor(float gain) {
		this.gainPercentage = gain;
	}

	public void setGainPercentage(float gain) {
		this.gainPercentage = gain;
	}

	@Override
	public void initialize(int sampleRate) {
	}

	@Override
	public void process(byte[] inputBytes, int length, ByteBuffer outputBytes) {
		float gain = (gainPercentage + 1.0F);
		for (int i = 0; i < length; i++) {
			byte gained = (byte) MathUtils.clamp(inputBytes[i] * gain, Byte.MIN_VALUE, Byte.MAX_VALUE);
			outputBytes.put(gained);
		}
	}

}
