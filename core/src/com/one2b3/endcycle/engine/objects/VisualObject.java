package com.one2b3.endcycle.engine.objects;

import com.one2b3.endcycle.engine.screens.GameScreenObject;
import com.one2b3.endcycle.utils.objects.Named;

public abstract class VisualObject extends GameScreenObject implements Named {

	public VisualObject() {
	}

	// Getters are needed for shadow entities!

	@Override
	public String getName() {
		return null;
	}
}
