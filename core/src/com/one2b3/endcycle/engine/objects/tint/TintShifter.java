package com.one2b3.endcycle.engine.objects.tint;

import com.badlogic.gdx.graphics.Color;
import com.one2b3.endcycle.engine.proguard.KeepClass;

@KeepClass
public interface TintShifter {

	public void start(float time);

	public void update(float delta);

	public Color getTint();

	public void stop();
}
