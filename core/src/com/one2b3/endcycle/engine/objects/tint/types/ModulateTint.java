package com.one2b3.endcycle.engine.objects.tint.types;

import com.badlogic.gdx.graphics.Color;
import com.one2b3.endcycle.engine.objects.tint.TintShifter;
import com.one2b3.endcycle.engine.proguard.KeepClass;
import com.one2b3.utils.CColor;

@KeepClass
public class ModulateTint implements TintShifter {

	static final float SHIFT_TIME = 0.02F;

	CColor newTint, currentTint;
	float current, time;

	public ModulateTint() {
		this(Color.WHITE);
	}

	public ModulateTint(float r, float g, float b, float a) {
		this(new CColor(r, g, b, a));
	}

	public ModulateTint(Color newTint) {
		this.currentTint = new CColor(CColor.WHITE);
		this.newTint = new CColor(newTint);
	}

	@Override
	public void start(float time) {
		this.current = 0.0F;
		this.time = time;
	}

	@Override
	public Color getTint() {
		return currentTint;
	}

	@Override
	public void update(float delta) {
		current += delta / time;
		float percent = Math.abs((current + 0.5F) % 1.0F - 0.5F) * 2.0F;
		currentTint.set(Color.WHITE).lerp(newTint, percent);
	}

	@Override
	public void stop() {
	}
}
