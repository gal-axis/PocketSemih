package com.one2b3.endcycle.engine.screens;

import com.one2b3.endcycle.engine.events.EventType;
import com.one2b3.endcycle.engine.input.InputHandler;
import com.one2b3.endcycle.engine.input.InputListener;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ScreenInputHandler extends InputHandler {

	final GameScreen screen;

	@Override
	public void add(InputListener listener) {
		super.add(listener);
		screen.events.trigger(EventType.INPUT_ADD, listener);
	}

	@Override
	public boolean remove(InputListener listener) {
		if (super.remove(listener)) {
			screen.events.trigger(EventType.INPUT_REMOVE, listener);
			return true;
		}
		return false;
	}

}
