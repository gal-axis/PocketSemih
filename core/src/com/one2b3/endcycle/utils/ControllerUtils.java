package com.one2b3.endcycle.utils;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.one2b3.endcycle.engine.input.InputManager;

public final class ControllerUtils {

	private ControllerUtils() {
	}

	public static boolean isConnected(Controller controller) {
		return controller == InputManager.KEYBOARD || controller == InputManager.TOUCH_KEYBOARD
				|| Controllers.getControllers().contains(controller, true);
	}

	public static String getName(Controller controller) {
		return controller.getName() == null ? "Controller" : controller.getName();
	}
}
