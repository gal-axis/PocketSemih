package com.one2b3.endcycle.engine.screens.timer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleGameScreenTimerTask extends GameScreenTimerTask {

	public final Runnable runnable;

	@Override
	public void run() {
		if (runnable != null) {
			runnable.run();
		}
	}
}
