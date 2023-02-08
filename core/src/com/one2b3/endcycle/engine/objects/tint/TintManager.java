package com.one2b3.endcycle.engine.objects.tint;

import com.badlogic.gdx.graphics.Color;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC)
public class TintManager {

	float time;
	TintShifter shifter;

	public TintShifter start(TintShifter shifter, float time) {
		if (active()) {
			stop();
		}
		if (shifter != null) {
			this.time = time;
			this.shifter = shifter;
			shifter.start(time);
		}
		return shifter;
	}

	public boolean active() {
		return time > 0.0F && shifter != null;
	}

	public void update(float delta) {
		if (time > 0.0F) {
			time -= delta;
			if (time <= 0.0F) {
				stop();
			} else {
				this.shifter.update(delta);
			}
		}
	}

	public Color getTint() {
		return (active() ? shifter.getTint() : Color.WHITE);
	}

	public void stop() {
		time = 0.0F;
		if (shifter != null) {
			shifter.stop();
		}
		shifter = null;
	}
}
