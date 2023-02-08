package com.one2b3.endcycle.engine.objects.tint.types;

import com.badlogic.gdx.graphics.Color;
import com.one2b3.endcycle.engine.objects.tint.TintShifter;
import com.one2b3.endcycle.engine.proguard.KeepClass;
import com.one2b3.utils.CColor;

@KeepClass
public class TintFlickerer implements TintShifter {

	static final float SHIFT_TIME = 0.02F;

	Color flicker;

	boolean shifted;
	float shift;

	public TintFlickerer() {
		this(0.0F, 0.0F, 0.0F, 0.0F);
	}

	public TintFlickerer(Color color) {
		flicker = new CColor(color);
	}

	public TintFlickerer(float r, float g, float b, float a) {
		flicker = new CColor(r, g, b, a);
	}

	@Override
	public void start(float time) {
		shift = SHIFT_TIME;
		shifted = false;
	}

	@Override
	public void update(float delta) {
		shift += delta;
		if (shift >= SHIFT_TIME) {
			shift -= SHIFT_TIME;
			shifted = !shifted;
		}
	}

	@Override
	public Color getTint() {
		return shifted ? flicker : Color.WHITE;
	}

	@Override
	public void stop() {
		shift = SHIFT_TIME;
		shifted = false;
	}
}
