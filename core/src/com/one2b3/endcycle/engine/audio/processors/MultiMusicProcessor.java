package com.one2b3.endcycle.engine.audio.processors;

import java.nio.ByteBuffer;

import com.badlogic.gdx.utils.Array;
import com.one2b3.audio.listeners.MusicProcessor;

public class MultiMusicProcessor implements MusicProcessor {

	int sampleRate;
	Array<MusicProcessor> processors = new Array<>();
	transient byte[] tempBuffer;

	@Override
	public synchronized void initialize(int sampleRate) {
		this.sampleRate = sampleRate;
		for (int i = 0; i < processors.size; i++) {
			processors.get(i).initialize(sampleRate);
		}
	}

	public synchronized void addProcessor(MusicProcessor musicProcessor) {
		processors.add(musicProcessor);
		musicProcessor.initialize(sampleRate);
	}

	public synchronized void removeProcessor(MusicProcessor musicProcessor) {
		processors.removeValue(musicProcessor, true);
	}

	@Override
	public synchronized void process(byte[] inputBytes, int length, ByteBuffer outputBytes) {
		if (processors.isEmpty()) {
			outputBytes.put(inputBytes);
		} else {
			for (int i = 0; i < processors.size; i++) {
				processors.get(i).process(inputBytes, length, outputBytes);
				if (i + 1 < processors.size) {
					if (tempBuffer == null) {
						tempBuffer = new byte[4096 * 10];
					}
					outputBytes.flip();
					outputBytes.get(tempBuffer, 0, outputBytes.remaining());
					outputBytes.clear();
					inputBytes = tempBuffer;
				}
			}
		}
	}

}
