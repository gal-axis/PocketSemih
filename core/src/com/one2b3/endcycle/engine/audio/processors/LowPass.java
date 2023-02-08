package com.one2b3.endcycle.engine.audio.processors;

public final class LowPass extends IIRFilter {

	public LowPass(float frequency) {
		this.frequency = (frequency > 60 ? frequency : 60);
	}

	@Override
	public void calculateCoefficients() {
		float freqFrac = frequency / getSampleRate();
		float x = (float) Math.exp(-14.445 * freqFrac);
		setACoeffecients((float) Math.pow(1 - x, 4));
		setBCoeffecients(4 * x, -6 * x * x, 4 * x * x * x, -x * x * x * x);
	}
}
