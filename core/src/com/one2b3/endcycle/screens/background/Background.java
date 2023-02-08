package com.one2b3.endcycle.screens.background;

import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.endcycle.engine.screens.ScreenObject;

public interface Background extends ScreenObject {

	@Override
	default byte getLayer() {
		return Layers.LAYER_0;
	}
}
