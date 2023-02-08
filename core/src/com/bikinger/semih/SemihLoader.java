package com.bikinger.semih;

import com.one2b3.endcycle.core.load.DefaultLoader;
import com.one2b3.endcycle.core.platform.GamePlatform;
import com.one2b3.endcycle.engine.screens.GameScreen;

public class SemihLoader extends DefaultLoader {

	@Override
	public GamePlatform createPlatform() {
		return new SemihPlatform();
	}

	@Override
	public GameScreen createOpeningScreen() {
		return new SemihScreen();
	}

	@Override
	protected void loadGame() {
	}

}
