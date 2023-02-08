package com.one2b3.utils;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.engine.graphics.DrawableImageFrame;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC)
public final class DrawableAnimation {

	DrawableImageFrame[] keyFrames;
	double frameDuration;
	int lastFrameNumber;
	double lastStateTime;
	PlayMode playMode = PlayMode.NORMAL;

	public DrawableAnimation(double frameDuration, Array<DrawableImageFrame> keyFrames) {
		this.frameDuration = frameDuration;
		this.keyFrames = keyFrames.toArray();
	}

	public DrawableAnimation(double frameDuration, Array<DrawableImageFrame> keyFrames, PlayMode playMode) {
		this(frameDuration, keyFrames);
		this.playMode = playMode;
	}

	public DrawableAnimation(double frameDuration, PlayMode playMode, DrawableImageFrame... keyFrames) {
		this.frameDuration = frameDuration;
		this.playMode = playMode;
		this.keyFrames = keyFrames;
	}

	public DrawableAnimation(double frameDuration, DrawableImageFrame... keyFrames) {
		this.frameDuration = frameDuration;
		this.keyFrames = keyFrames;
	}

	public DrawableImageFrame getKeyFrame(double stateTime) {
		int frameNumber = getKeyFrameIndex(stateTime);
		return getKeyFrame(frameNumber);
	}

	public DrawableImageFrame getKeyFrame(int frameNumber) {
		return frameNumber < 0 || frameNumber >= keyFrames.length ? null : keyFrames[frameNumber];
	}

	public int getKeyFrameIndex(double stateTime) {
		if (keyFrames.length <= 1 || stateTime < 0.0F) {
			return 0;
		}
		int frameNumber = (int) (stateTime / frameDuration);
		switch (playMode) {
		case NORMAL:
			frameNumber = Math.min(keyFrames.length - 1, frameNumber);
			break;
		case LOOP:
			frameNumber = frameNumber % keyFrames.length;
			break;
		case LOOP_PINGPONG:
			frameNumber = frameNumber % ((keyFrames.length * 2) - 2);
			if (frameNumber >= keyFrames.length) {
				frameNumber = keyFrames.length - 2 - (frameNumber - keyFrames.length);
			}
			break;
		case LOOP_RANDOM:
			int lastFrameNumber = (int) ((lastStateTime) / frameDuration);
			if (lastFrameNumber != frameNumber) {
				frameNumber = MathUtils.random(keyFrames.length - 1);
			} else {
				frameNumber = this.lastFrameNumber;
			}
			break;
		case REVERSED:
			frameNumber = Math.max(keyFrames.length - frameNumber - 1, 0);
			break;
		case LOOP_REVERSED:
			frameNumber = frameNumber % keyFrames.length;
			frameNumber = keyFrames.length - frameNumber - 1;
			break;
		}

		lastFrameNumber = frameNumber;
		lastStateTime = stateTime;

		return frameNumber;
	}

	public boolean isAnimationFinished(double stateTime) {
		int frameNumber = (int) (stateTime / frameDuration);
		return keyFrames.length - 1 < frameNumber;
	}

	public double getAnimationDuration() {
		return frameDuration * keyFrames.length;
	}
}
