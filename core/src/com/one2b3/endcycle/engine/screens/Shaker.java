package com.one2b3.endcycle.engine.screens;

import com.badlogic.gdx.math.MathUtils;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC)
public class Shaker {

	float shakeX, shakeY, shakeDuration;
	float current;
	float interp = Float.NaN;
	float lastX, lastY, nextX, nextY;

	public void start(float x, float y, float duration) {
		shakeX = x;
		shakeY = y;
		shakeDuration = duration;
		current = 0.0F;
		setupNext();
	}

	public void setupNext() {
		if (!isActive() && nextX == 0.0F && nextY == 0.0F) {
			interp = Float.NaN;
		} else {
			interp = 0.0F;
			lastX = nextX;
			lastY = nextY;
			float percent = getShakePercent();
			percent *= percent;
			nextX = MathUtils.random(percent, percent * 0.7F) * shakeX * MathUtils.randomSign();
			nextY = MathUtils.random(percent, percent * 0.7F) * shakeY * MathUtils.randomSign();
		}
	}

	public void update(float delta) {
		if (current < shakeDuration) {
			current += delta;
			if (current >= shakeDuration) {
				stop();
			}
		}
		if (!Float.isNaN(interp) && interp < 1.0F) {
			interp += delta * 100.0F;
			if (interp >= 1.0F) {
				setupNext();
			}
		}
	}

	public void stop() {
		shakeX = 0;
		shakeY = 0;
		shakeDuration = 0.0F;
	}

	public boolean isActive() {
		return shakeDuration > 0.0F;
	}

	public float getX() {
		return Float.isNaN(interp) ? 0.0F : MathUtils.lerp(lastX, nextX, interp);
	}

	public float getY() {
		return Float.isNaN(interp) ? 0.0F : MathUtils.lerp(lastY, nextY, interp);
	}

	public float getShakePercent() {
		return isActive() ? 1.0F - (current / shakeDuration) : 0.0F;
	}

}
