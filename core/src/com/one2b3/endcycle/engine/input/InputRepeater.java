package com.one2b3.endcycle.engine.input;

import com.badlogic.gdx.controllers.Controller;
import com.one2b3.endcycle.engine.input.events.ButtonEvent;
import com.one2b3.endcycle.utils.bounded.BoundedFloat;

public final class InputRepeater {

	final InputListener listener;
	final ButtonEvent event = new ButtonEvent();

	public KeyCode[] listenTo;

	Controller controller;
	int buttonId;
	boolean pressed;
	BoundedFloat acceleration = new BoundedFloat(0.0F, 0.5F);

	public InputRepeater(InputListener listener, KeyCode... keys) {
		this.listener = listener;
		this.listenTo = keys;
	}

	public void update(float delta) {
		if (pressed) {
			acceleration.increase(delta);
			if (acceleration.atMax()) {
				event.setProperties(controller, buttonId, null, InputType.PRESSED);
				listener.triggerButton(event);
				acceleration.setVal(acceleration.getMax() - 0.075F);
			}
		}
	}

	public void stop() {
		pressed = false;
	}

	public void triggerButton(ButtonEvent trigger) {
		if (trigger.isPressed() && (!pressed || trigger.getButtonId() != buttonId)) {
			pressed = false;
			if (isListening(trigger)) {
				controller = trigger.getController();
				acceleration.toMin();
				buttonId = trigger.buttonId;
				pressed = true;
			}
		} else if (trigger.getController() == controller && trigger.buttonId == buttonId && trigger.isReleased()) {
			stop();
		}
	}

	private boolean isListening(ButtonEvent trigger) {
		if (listenTo == null || listenTo.length == 0) {
			return true;
		}
		for (KeyCode code : listenTo) {
			if (trigger.isKey(code)) {
				return true;
			}
		}
		return false;
	}
}
