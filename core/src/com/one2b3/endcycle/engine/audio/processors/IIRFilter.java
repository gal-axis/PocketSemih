package com.one2b3.endcycle.engine.audio.processors;

import java.nio.ByteBuffer;

import lombok.Getter;

public abstract class IIRFilter extends FloatMusicProcessor {

	float[] aCoeffecient, bCoeffecient;

	public float frequency;

	float[] in, out;
	int inPosition, outPosition;

	@Getter
	int sampleRate;

	@Override
	public void initialize(int sampleRate) {
		this.sampleRate = sampleRate;
		calculateCoefficients();
		in = new float[aCoeffecient.length];
		out = new float[bCoeffecient.length];
	}

	public abstract void calculateCoefficients();

	public void setACoeffecients(float... aCoeffecient) {
		this.aCoeffecient = aCoeffecient;
	}

	public void setBCoeffecients(float... bCoeffecient) {
		this.bCoeffecient = bCoeffecient;
	}

	@Override
	public void process(byte[] inputBytes, int length, ByteBuffer outputBytes) {
		for (int i = 0; i < length; i += BYTES_PER_FLOAT) {
			inPosition = (inPosition - 1 + in.length) % in.length;
			in[inPosition] = getFloat(inputBytes, i);

			float result = 0;
			for (int j = 0; j < aCoeffecient.length; j++) {
				result += aCoeffecient[j] * in[(inPosition + j) % in.length];
			}
			for (int j = 0; j < bCoeffecient.length; j++) {
				result += bCoeffecient[j] * out[(outPosition + j) % out.length];
			}
			outPosition = (outPosition - 1 + out.length) % out.length;
			out[outPosition] = result;

			outputBytes.put(getBytes(result));
		}
	}

}
