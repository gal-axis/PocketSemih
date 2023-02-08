package com.one2b3.endcycle.engine.audio.processors;

public final class HighPass extends IIRFilter {

	public HighPass(float frequency) {
		this.frequency = frequency;
	}

	@Override
	public void calculateCoefficients() {
		float fracFreq = frequency / getSampleRate();
		float x = (float) Math.exp(-2 * Math.PI * fracFreq);
		setACoeffecients((1 + x) / 2, -(1 + x) / 2);
		setBCoeffecients(x);
	}

}
