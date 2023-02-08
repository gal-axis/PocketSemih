package com.one2b3.endcycle.engine.input;

import com.badlogic.gdx.controllers.Controller;
import com.one2b3.endcycle.engine.input.events.ButtonEvent;
import com.one2b3.endcycle.engine.input.events.TouchEvent;

public interface InputListener {

	/**
	 * Triggers a button event, that is caused by input
	 */
	default boolean triggerButton(ButtonEvent event) {
		return false;
	}

	/**
	 * Triggers a type event, that is caused by input
	 */
	default boolean triggerType(int character) {
		return false;
	}

	/**
	 * Triggers a touch event, that is caused by input
	 */
	default boolean triggerTouch(TouchEvent event) {
		return false;
	}

	default boolean keyDown(int keycode) {
		return false;
	}

	default boolean keyTyped(char character) {
		return false;
	}

	default boolean keyUp(int keycode) {
		return false;
	}

	default void controllerConnected(Controller controller) {
	}

	default void controllerDisconnected(Controller controller) {
	}

	default int getInputPriority() {
		return 0;
	}

}
