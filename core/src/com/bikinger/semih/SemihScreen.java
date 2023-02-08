package com.bikinger.semih;

import com.bikinger.semih.clicker.SemihClicker;
import com.bikinger.semih.clicker.SemihPoints;
import com.one2b3.endcycle.engine.screens.GameScreen;

public class SemihScreen extends GameScreen {

	public SemihScreen() {
	}

	@Override
	public void init() {
		super.init();
		SemihPoints points = new SemihPoints();
		addObject(points);
		addObject(new SemihClicker(points));
	}
}
