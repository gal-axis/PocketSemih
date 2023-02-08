package com.one2b3.endcycle.engine.objects.tint.types;

import com.badlogic.gdx.graphics.Color;
import com.one2b3.endcycle.engine.objects.tint.TintShifter;
import com.one2b3.endcycle.engine.proguard.KeepClass;
import com.one2b3.utils.CColor;

@KeepClass
public class FadeTintTo implements TintShifter {

	static final float SHIFT_TIME = 0.02F;
	static Color temp = new CColor();

	Color to, from;
	float current, time;

	public FadeTintTo() {
		this(Color.WHITE);
	}

	public FadeTintTo(float r, float g, float b, float a) {
		this(new Color(r, g, b, a));
	}

	public FadeTintTo(Color newTint) {
		this(Color.WHITE, newTint);
	}

	public FadeTintTo(Color currentTint, Color newTint) {
		this.from = new CColor(currentTint);
		this.to = newTint;
	}

	@Override
	public void start(float time) {
		this.current = 0.0F;
		this.time = time;
	}

	@Override
	public Color getTint() {
		return temp.set(from).lerp(to, current / time);
	}

	@Override
	public void update(float delta) {
		current = Math.min(current + delta, time);
	}

	@Override
	public void stop() {
	}
}
