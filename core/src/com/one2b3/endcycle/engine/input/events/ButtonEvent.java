package com.one2b3.endcycle.engine.input.events;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.math.Vector2;
import com.one2b3.endcycle.engine.input.InputManager;
import com.one2b3.endcycle.engine.input.InputType;
import com.one2b3.endcycle.engine.input.KeyCode;
import com.one2b3.endcycle.engine.input.KeyCodeCategory;
import com.one2b3.endcycle.engine.input.binders.KeyBinder;
import com.one2b3.endcycle.utils.ControllerUtils;
import com.one2b3.revo.Revo;

import lombok.Getter;
import lombok.experimental.Accessors;

public class ButtonEvent {

	public Vector2 axis;

	@Getter
	public int buttonId;

	@Getter
	@Accessors(chain = true)
	public Controller controller;

	@Getter
	KeyBinder binder;

	public InputType type;

	public ButtonEvent(int buttonId, InputType type) {
		setProperties(controller, buttonId, null, type);
	}

	public ButtonEvent() {
	}

	public void setProperties(Controller controller, int buttonId, Vector2 axis, InputType type) {
		this.axis = axis;
		setType(type);
		setController(controller);
		setID(buttonId);
	}

	public void setType(InputType type) {
		this.type = type;
	}

	public void setID(int buttonId) {
		this.buttonId = buttonId;
	}

	public void setController(Controller controller) {
		this.controller = controller;
		if (controller == null) {
			binder = InputManager.EMPTY_KEYBINDER;
		} else if (controller == InputManager.KEYBOARD) {
			binder = InputManager.KEYBOARD.binder;
		} else {
			binder = Revo.cast(controller, KeyBinder.class);
		}
	}

	public boolean isPressed() {
		return type == InputType.PRESSED;
	}

	public boolean isReleased() {
		return type == InputType.RELEASED;
	}

	public boolean isMoved() {
		return type == InputType.MOVED;
	}

	@Override
	public String toString() {
		return "[" + (controller != null ? ControllerUtils.getName(controller) : null) + ", ID: " + buttonId
				+ ", Type: " + (type == null ? "null" : type.name()) + "]";
	}

	public boolean isKey(KeyCode code) {
		return binder != null && code != null && binder.isKey(code, buttonId);
	}

	public KeyCode getCode(KeyCodeCategory category) {
		return binder == null ? null : binder.getCode(category, buttonId);
	}
}
