package com.one2b3.audio.listeners;

import java.nio.ByteBuffer;

/**
 * Interface definition for a processor that can process the PCM bytes of a
 * song before they are sent to the output device
 */
public interface MusicProcessor {

	/**
	 * Called when the music processor is set for a certain track.
	 * @param sampleRate
	 *            the sample rate of the music
	 */
	public abstract void initialize(int sampleRate);

	/**
	 * Called when PCM bytes are read
	 * @param inputBytes
	 *            the PCM bytes that were processed
	 * @param length
	 *            the length of the bytes
	 * @param outputBytes
	 *            the bytes to output to
	 */
	public abstract void process(byte[] inputBytes, int length, ByteBuffer outputBytes);
}