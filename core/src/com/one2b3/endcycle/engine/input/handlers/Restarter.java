package com.one2b3.endcycle.engine.input.handlers;

import com.badlogic.gdx.Gdx;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.engine.audio.music.MusicHandler;
import com.one2b3.endcycle.engine.events.GameEventHandler;
import com.one2b3.endcycle.engine.input.InputListener;
import com.one2b3.endcycle.engine.input.KeyCode;
import com.one2b3.endcycle.engine.input.events.ButtonEvent;
import com.one2b3.endcycle.engine.input.events.TouchEvent;

public class Restarter implements Runnable, InputListener {

	boolean l, r, st, se;

	@Override
	public boolean triggerButton(ButtonEvent event) {
		if (event.isKey(KeyCode.RESTART_1)) {
			l = event.isPressed();
		}
		if (event.isKey(KeyCode.RESTART_2)) {
			r = event.isPressed();
		}
		if (event.isKey(KeyCode.RESTART_3)) {
			st = event.isPressed();
		}
		if (event.isKey(KeyCode.RESTART_4)) {
			se = event.isPressed();
		}
		if (event.isPressed() && l && r && st && se) {
			Gdx.app.postRunnable(this);
			l = r = st = se = false;
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		restart();
	}

	public static void restart() {
		MusicHandler.instance.clear();
		GameEventHandler.clearStaticListeners();
		Cardinal.game.loader.restart();
	}

	@Override
	public boolean triggerTouch(TouchEvent event) {
		return false;
	}

	@Override
	public int getInputPriority() {
		return -1;
	}

}
