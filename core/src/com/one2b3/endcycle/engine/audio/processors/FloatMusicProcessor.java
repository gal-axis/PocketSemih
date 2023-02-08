package com.one2b3.endcycle.engine.audio.processors;

import com.one2b3.audio.listeners.MusicProcessor;

public abstract class FloatMusicProcessor implements MusicProcessor {

	static final int BYTES_PER_FLOAT = 2;
	final byte[] bytes = new byte[BYTES_PER_FLOAT];

	public float getFloat(byte[] buff, int position) {
		return ((short) ((buff[position++] & 0xFF) | (buff[position++] << 8))) * (1.0f / 32767.0f);
	}

	public byte[] getBytes(float f) {
		int x = (int) (f * 32767.0);
		bytes[0] = (byte) x;
		bytes[1] = (byte) (x >>> 8);
		return bytes;
	}

}
