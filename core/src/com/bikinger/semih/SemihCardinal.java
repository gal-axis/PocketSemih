package com.bikinger.semih;

import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.Resolutions;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.core.painting.GamePainter;
import com.one2b3.endcycle.core.painting.painters.ScaledPainter;

public class SemihCardinal extends Cardinal {

	public SemihCardinal() {
		super(new SemihLoader(), null, Resolutions.L_HEIGHT, Resolutions.L_WIDTH, true);
	}

	@Override
	protected GamePainter createPainter(CustomSpriteBatch batch) {
		return new ScaledPainter(batch, Resolutions.L_HEIGHT, Resolutions.L_WIDTH, Resolutions.L_HEIGHT,
				Resolutions.L_WIDTH_MAX);
	}

}
