package com.one2b3.endcycle.engine.graphics.data;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.one2b3.endcycle.engine.proguard.KeepClass;
import com.one2b3.endcycle.features.models.Description;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@KeepClass
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
@Description("An image or animation that can be rendered by the game")
public final class NestedDrawableData implements DrawableDataProvider {

	@Setter
	double speed = 1.0;
	@Setter
	PlayMode playMode = PlayMode.NORMAL;
	List<DrawableFrameData> frames = new ArrayList<>();

	@Override
	public double getSpeed() {
		return speed;
	}

	@Override
	public PlayMode getPlayMode() {
		return playMode;
	}

	@Override
	public List<DrawableFrameData> getFrames() {
		return frames;
	}

}