package com.one2b3.endcycle.screens.menus;

import com.badlogic.gdx.math.MathUtils;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.utils.Modulator;
import com.one2b3.utils.CColor;

public class RainbowColor extends CColor {

	public void update() {
		update(Cardinal.getTime());
	}

	public void random() {
		update(MathUtils.random(0.0F, 2.0F));
	}

	public void update(float seed) {
		r = Modulator.getRainbow(seed);
		g = Modulator.getRainbow(seed + 0.666F);
		b = Modulator.getRainbow(seed + 1.222F);
		a = 1.0F;
	}
}
