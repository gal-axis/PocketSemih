package com.one2b3.audio.stream;

public interface MusicStream {

	void init();

	int getSamplingRate();

	int getChannels();

	int read(byte[] buffer);

	void reset();

	void loop();

	void dispose();

}
