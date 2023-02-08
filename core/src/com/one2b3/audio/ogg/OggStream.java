package com.one2b3.audio.ogg;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.StreamUtils;
import com.one2b3.audio.stream.MusicStream;

public class OggStream implements MusicStream {

	final FileHandle file;
	OggInputStream input, previousInput;

	public OggStream(FileHandle file) {
		this.file = file;
		input = new OggInputStream(file.read());
	}

	@Override
	public void init() {
		if (input == null) {
			input = new OggInputStream(file.read(), previousInput);
			previousInput = null;
		}
	}

	@Override
	public int read(byte[] buffer) {
		return input.read(buffer);
	}

	@Override
	public void reset() {
		StreamUtils.closeQuietly(input);
		previousInput = input;
		input = null;
		init();
	}

	@Override
	public void loop() {
		reset();
	}

	@Override
	public void dispose() {
		StreamUtils.closeQuietly(input);
		StreamUtils.closeQuietly(previousInput);
		input = null;
		previousInput = null;
	}

	@Override
	public int getSamplingRate() {
		return input.getSampleRate();
	}

	@Override
	public int getChannels() {
		return input.getChannels();
	}

}
