package com.one2b3.endcycle.engine.input;

import java.util.Comparator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.engine.input.controllers.MockController;
import com.one2b3.endcycle.engine.input.events.ButtonEvent;
import com.one2b3.endcycle.engine.input.events.KeyCodeEvent;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.features.revo.GameData;

public class InputHandler implements InputListener, Comparator<InputListener> {

	private static final ButtonEvent EMPTY_BUTTON = new ButtonEvent();

	public Controller last;
	public boolean lastTouch;
	public Array<InputListener> listeners;

	public InputHandler() {
		listeners = new Array<>(InputListener.class);
	}

	private void setLast(Controller controller) {
		if (controller != null && !(controller instanceof MockController)) {
			last = controller;
		}
	}

	public void add(InputListener listener) {
		if (listener != null) {
			// Make sure the listener gets put at end due to sorting!
			listeners.removeValue(listener, true);
			listeners.add(listener);
			listeners.sort(this);
		}
	}

	public boolean isFree() {
		return button(EMPTY_BUTTON) == null;
	}

	public void clear() {
		listeners.clear();
	}

	public boolean remove(InputListener listener) {
		return listeners.removeValue(listener, true);
	}

	@Override
	public boolean triggerButton(ButtonEvent event) {
		return button(event) != null;
	}

	@Override
	public boolean triggerTouch(TouchEvent event) {
		return touch(event) != null;
	}

	@Override
	public boolean triggerType(int character) {
		return type(character) != null;
	}

	public InputListener button(ButtonEvent event) {
		if (event != EMPTY_BUTTON && !(event instanceof KeyCodeEvent) && !(event.controller instanceof MockController)
				&& (event.controller != InputManager.KEYBOARD
						|| (event.buttonId != Keys.BACK && event.buttonId != Keys.MENU))) {
			lastTouch = false;
		}
		setLast(event.controller);
		InputListener listener = null;
		for (int i = listeners.size - 1; i >= 0; i--) {
			try {
				listener = listeners.get(i);
				if (listener instanceof InputHandler ? (listener = ((InputHandler) listener).button(event)) != null
						: listener.triggerButton(event)) {
					return listener;
				}
			} catch (Throwable t) {
				exception(listener, t);
			}
		}
		return null;
	}

	public InputListener touch(TouchEvent event) {
		if (!Cardinal.isPortable()) {
			setLast(InputManager.KEYBOARD);
		}
		lastTouch = true;
		InputListener listener = null;
		for (int i = listeners.size - 1; i >= 0; i--) {
			try {
				listener = listeners.get(i);
				if (listener instanceof InputHandler ? (listener = ((InputHandler) listener).touch(event)) != null
						: listener.triggerTouch(event)) {
					return listener;
				}
			} catch (Throwable t) {
				exception(listener, t);
			}
		}
		return null;
	}

	public InputListener type(int character) {
		lastTouch = false;
		InputListener listener = null;
		for (int i = listeners.size - 1; i >= 0; i--) {
			try {
				listener = listeners.get(i);
				if (listener instanceof InputHandler ? (listener = ((InputHandler) listener).type(character)) != null
						: listener.triggerType(character)) {
					return listener;
				}
			} catch (Throwable t) {
				exception(listener, t);
			}
		}
		return null;
	}

	@Override
	public void controllerConnected(Controller controller) {
		InputListener listener = null;
		for (int i = listeners.size - 1; i >= 0; i--) {
			try {
				listener = listeners.get(i);
				listener.controllerConnected(controller);
			} catch (Throwable t) {
				exception(listener, t);
			}
		}
	}

	@Override
	public void controllerDisconnected(Controller controller) {
		for (int i = listeners.size - 1; i >= 0; i--) {
			listeners.get(i).controllerDisconnected(controller);
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		return down(keycode) != null;
	}

	@Override
	public boolean keyTyped(char character) {
		lastTouch = false;
		InputListener listener = null;
		for (int i = listeners.size - 1; i >= 0; i--) {
			try {
				listener = listeners.get(i);
				if (listener.keyTyped(character)) {
					return true;
				}
			} catch (Throwable t) {
				exception(listener, t);
			}
		}
		return false;
	}

	public InputListener down(int keycode) {
		lastTouch = false;
		InputListener listener = null;
		for (int i = listeners.size - 1; i >= 0; i--) {
			try {
				listener = listeners.get(i);
				if (listener.keyDown(keycode)) {
					return listener;
				}
			} catch (Throwable t) {
				exception(listener, t);
			}
		}
		return null;
	}

	public InputListener up(int keycode) {
		lastTouch = false;
		InputListener listener = null;
		for (int i = listeners.size - 1; i >= 0; i--) {
			try {
				listener = listeners.get(i);
				if (listener.keyUp(keycode)) {
					return listener;
				}
			} catch (Throwable t) {
				exception(listener, t);
			}
		}
		return null;
	}

	private void exception(InputListener listener, Throwable t) {
		Gdx.app.error("Input", "Exception thrown during input handling of listener: " + GameData.toString(listener), t);
	}

	@Override
	public boolean keyUp(int keycode) {
		return up(keycode) != null;
	}

	@Override
	public int getInputPriority() {
		return -1;
	}

	@Override
	public int compare(InputListener o1, InputListener o2) {
		return Integer.compare(o1.getInputPriority(), o2.getInputPriority());
	}

}
