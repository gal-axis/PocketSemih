package com.one2b3.endcycle.features.models;

import com.one2b3.endcycle.engine.screens.GameScreen;

public interface Testable {

	default TestParam[] getTestParameters() {
		return null;
	}

	GameScreen test(GameScreen last, Object parent, Object[] parameters);

}
