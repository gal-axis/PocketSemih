package com.one2b3.endcycle.engine.input;

import com.badlogic.gdx.controllers.Controller;
import com.one2b3.endcycle.engine.input.events.ButtonEvent;
import com.one2b3.endcycle.engine.input.events.TouchEvent;

public interface InputBlocker extends InputListener {

	@Override
	default boolean triggerButton(ButtonEvent event) {
		return true;
	}

	@Override
	default boolean triggerType(int character) {
		return true;
	}

	@Override
	default boolean triggerTouch(TouchEvent event) {
		return true;
	}

	@Override
	default boolean keyDown(int keycode) {
		return false;
	}

	@Override
	default boolean keyTyped(char character) {
		return false;
	}

	@Override
	default boolean keyUp(int keycode) {
		return false;
	}

	@Override
	default void controllerConnected(Controller controller) {
	}

	@Override
	default void controllerDisconnected(Controller controller) {
	}

	@Override
	default int getInputPriority() {
		return 1;
	}

}
