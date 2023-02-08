package com.one2b3.endcycle.engine.input.controllers;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.engine.input.InputManager;
import com.one2b3.endcycle.engine.input.InputType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ControllerHandler {

	final InputManager manager;
	final Controller controller;

	final float axisDeadzone = 0.75F;

	final Map<Integer, Vector2> axisVectors = new HashMap<>();
	final Array<Integer> pressed = new Array<>(4);

	public void axisMoved(int axisCode, float value) {
		boolean horizontal = axisCode % 2 == 0;
		boolean active;
		int id = axisCode / 2;
		if (!axisVectors.containsKey(id)) {
			axisVectors.put(id, new Vector2());
		}
		Vector2 vec = axisVectors.get(id);
		if (horizontal) {
			vec.x = value;
		} else {
			vec.y = -value;
		}
		active = vec.len2() > axisDeadzone && Math.abs(value) > axisDeadzone * 0.5F;

		axisCode = (axisCode + InputManager.AXIS_ADDER) * (value < 0 ? 1 : -1);
		manager.movedAxis(controller, id, axisCode, vec);
		if (active) {
			if (!pressed.contains(axisCode, false)) {
				if (pressed.removeValue(-axisCode, false)) {
					manager.pressAxis(controller, id, -axisCode, InputType.RELEASED);
				}
				manager.pressAxis(controller, id, axisCode, InputType.PRESSED);
				pressed.add(axisCode);
			}
		} else {
			if (pressed.removeValue(axisCode, false)) {
				manager.pressAxis(controller, id, axisCode, InputType.RELEASED);
			}
			if (pressed.removeValue(-id, false)) {
				manager.pressAxis(controller, id, -axisCode, InputType.RELEASED);
			}
		}
	}

	public Vector2 getAxis(int axisCode) {
		int id = axisCode / 2;
		Vector2 axis = axisVectors.get(id);
		return axis == null ? Vector2.Zero : axis;
	}

	public boolean isPressed(int code) {
		return pressed.contains(code, false);
	}
}
