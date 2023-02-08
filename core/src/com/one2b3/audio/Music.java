package com.one2b3.audio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import com.one2b3.audio.listeners.CompletionListener;
import com.one2b3.audio.listeners.LoopListener;
import com.one2b3.audio.listeners.MusicProcessor;
import com.one2b3.audio.stream.MusicStream;
import com.one2b3.audio.stream.PcmCache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Music {

	static final int bufferSize = 1024 * 10;
	static final int bufferCount = 3;
	static final int bytesPerSample = 2;
	static final PcmCache lastCache = new PcmCache();

	final ByteBuffer tempBuffer = ByteBuffer.allocateDirect(bufferSize).order(ByteOrder.nativeOrder());
	final byte[] tempBytes = new byte[bufferSize];
	final short[] samples = new short[bufferSize / 2];

	final MusicStream stream;
	AudioDevice device;
	public MusicProcessor musicProcessor;

	PcmCache currentCache, firstCache;

	public CompletionListener completionListener;
	public LoopListener loopListener;

	public boolean looping;
	boolean caching;
	@Getter
	boolean playing;

	public float loopPosition;
	@Getter
	float position;
	public float volume;
	float pan;

	private float getBufferedTime(int bytes) {
		return (float) bytes / (bytesPerSample * stream.getChannels() * stream.getSamplingRate());
	}

	public void setMusicProcessor(MusicProcessor musicProcessor) {
		this.musicProcessor = musicProcessor;
		if (musicProcessor != null) {
			musicProcessor.initialize(stream.getSamplingRate());
		}
	}

	public void setPan(float pan, float volume) {
		this.pan = pan;
		this.volume = volume;
	}

	protected void play() {
		playing = true;
		if (device == null) {
			stream.init();
			device = Gdx.audio.newAudioDevice(stream.getSamplingRate(), stream.getChannels() == 1);
		}
	}

	protected void pause() {
		playing = false;
	}

	protected void stop() {
		reset();
		playing = false;
	}

	public void update() {
		if (playing) {
			int length = read(tempBytes);
			if (length == -1) {
				if (completionListener != null) {
					completionListener.onCompletion(this);
				}
				if (looping) {
					loop();
					if (loopPosition == 0.0F) {
						length = read(tempBytes);
					} else {
						length = 0;
						position = 0.0F;
						while (position < (loopPosition - getBufferedTime(length))) {
							length = skip(tempBytes);
							if (length <= 0) {
								break;
							}
							position += getBufferedTime(length);
						}
						if (length >= 0) {
							length = read(tempBytes);
						}
					}
				} else {
					stop();
				}
			}
			if (length != -1) {
				tempBuffer.clear();
				if (musicProcessor != null) {
					musicProcessor.process(tempBytes, length, tempBuffer);
				} else {
					tempBuffer.put(tempBytes, 0, length);
				}
				tempBuffer.flip();
				for (int i = 0; i < length / 2; i++) {
					samples[i] = tempBuffer.getShort();
				}
				// TODO Check why device can be null at this point.
				if (device != null) {
					device.setVolume(volume);
					device.writeSamples(samples, 0, length / 2);
				}
				position += getBufferedTime(length);
			}
		}
	}

	private int read(byte[] buffer) {
		if (caching) {
			if (currentCache == null || currentCache.next == null) {
				int length = stream.read(buffer);
				if (length >= 0) {
					PcmCache bufferCache = new PcmCache(buffer, length);
					if (currentCache == null) {
						currentCache = firstCache = bufferCache;
					} else {
						currentCache.next = bufferCache;
						currentCache = currentCache.next;
					}
				} else {
					currentCache.next = lastCache;
				}
				return length;
			} else if (currentCache != lastCache) {
				System.arraycopy(currentCache.buffer, 0, buffer, 0, buffer.length);
				int length = currentCache.length;
				currentCache = currentCache.next;
				return length;
			} else {
				return -1;
			}
		} else {
			return stream.read(buffer);
		}
	}

	private int skip(byte[] buffer) {
		if (currentCache == null || currentCache.next == null) {
			return read(buffer);
		} else {
			int length = currentCache.length;
			currentCache = currentCache.next;
			return length;
		}
	}

	private void reset() {
		if (firstCache == null) {
			stream.reset();
		} else {
			currentCache = firstCache;
		}
		position = 0.0F;
	}

	protected final void loop() {
		if (firstCache == null) {
			stream.loop();
		} else {
			currentCache = firstCache;
		}
		if (loopListener != null) {
			loopListener.onLoop(this);
		}
	}

	protected void dispose() {
		playing = false;
		if (device != null) {
			device.dispose();
			device = null;
		}
		stream.dispose();
		currentCache = firstCache = null;
	}

	public void setCachePcm(boolean cache) {
		this.caching = cache;
		if (!cache) {
			currentCache = firstCache = null;
		}
	}

	protected void setPosition(float position) {
		reset();
		int length = 0;
		while (this.position < (position - getBufferedTime(length))) {
			if ((length = skip(tempBytes)) <= 0) {
				break;
			}
			length = read(tempBytes);
			position += getBufferedTime(length);
		}
		this.position = position;
	}

}
