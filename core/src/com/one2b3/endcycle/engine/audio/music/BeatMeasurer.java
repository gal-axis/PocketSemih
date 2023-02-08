package com.one2b3.endcycle.engine.audio.music;

public final class BeatMeasurer {

	double beatTimer;
	int beatPos;
	boolean onBeat;
	double beatDuration;

	public BeatMeasurer() {
	}

	public void setBeat(float bpm) {
		if (bpm != 0) {
			beatDuration = 60.0F / bpm;
			onBeat = true;
		} else {
			beatDuration = 0;
			onBeat = false;
		}
		beatTimer = -0.056F;
		beatPos = 0;
	}

	public void update(float delta) {
		beatTimer += delta;
		updateBeat();
	}

	private void updateBeat() {
		if (beatDuration == 0.0F) {
			return;
		}
		if (beatTimer >= beatDuration) {
			beatPos++;
			beatPos = beatPos % 4;
			onBeat = true;
			reduceTime();
		} else {
			onBeat = false;
		}
	}

	private void reduceTime() {
		while (beatTimer > beatDuration) {
			beatTimer -= beatDuration;
		}
	}

	public boolean isOnBeat() {
		return onBeat;
	}

	public int beat() {
		return beatPos;
	}

	public float timeToBeat(int beat) {
		return beatDuration == 0 ? beat * 0.1F : (float) (beatDuration * beat - beatTimer);
	}
}
