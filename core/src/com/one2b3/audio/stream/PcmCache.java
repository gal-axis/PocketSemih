package com.one2b3.audio.stream;

public class PcmCache {

	public int length;
	public byte[] buffer;
	public PcmCache next;

	public PcmCache() {
	}

	public PcmCache(int length) {
		this.buffer = new byte[length];
		this.length = length;
	}

	public PcmCache(byte[] buffer, int length) {
		this.buffer = new byte[buffer.length];
		System.arraycopy(buffer, 0, this.buffer, 0, buffer.length);
		this.length = length;
	}
}