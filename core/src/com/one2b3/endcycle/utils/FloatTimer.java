package com.one2b3.endcycle.utils;

public final class FloatTimer {

	float timer;
	final float[] times;
	public FloatTimerAction action;

	public FloatTimer(float... times) {
		this.times = times;
		timer = 0.0F;
	}

	public void reset() {
		timer = 0.0F;
	}

	public boolean update(float delta) {
		timer += delta;
		if (action != null) {
			action.updateTimer(timer);
		}
		boolean landed = false;
		for (int i = 0; i < times.length; i++) {
			if (times[i] > 0.0F) {
				times[i] -= delta;
				if (times[i] <= 0.0F) {
					if (action != null) {
						action.timerFinished(times.length, i);
					}
					landed = true;
				}
			}
		}
		return landed;
	}

	public interface FloatTimerAction {
		default void updateTimer(float timer) {
		}

		void timerFinished(int amount, int timer);
	}
}
