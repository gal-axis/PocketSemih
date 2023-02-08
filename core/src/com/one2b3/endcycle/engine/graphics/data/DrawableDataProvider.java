package com.one2b3.endcycle.engine.graphics.data;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

public interface DrawableDataProvider {

	void setSpeed(double value);

	double getSpeed();

	void setPlayMode(PlayMode mode);

	PlayMode getPlayMode();

	List<DrawableFrameData> getFrames();

}
