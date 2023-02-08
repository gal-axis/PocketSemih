package com.one2b3.endcycle.engine.input;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.engine.EngineProperties;
import com.one2b3.endcycle.engine.EngineProperties.Platform;
import com.one2b3.endcycle.engine.input.binders.EmptyKeybinder;
import com.one2b3.endcycle.engine.input.binders.KeyBinder;
import com.one2b3.endcycle.engine.input.controllers.ControllerHandler;
import com.one2b3.endcycle.engine.input.controllers.KeyboardController;
import com.one2b3.endcycle.engine.input.controllers.MockController;
import com.one2b3.endcycle.engine.input.events.ButtonEvent;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.features.revo.GameData;

import lombok.Getter;

public final class InputManager implements ControllerListener, InputProcessor {

	public static final KeyboardController KEYBOARD = new KeyboardController();
	public static final MockController TOUCH_KEYBOARD = new MockController();
	public static final MockController NO_CONTROLLER = new MockController();
	public static final KeyBinder EMPTY_KEYBINDER = new EmptyKeybinder();

	public static final int AXIS_ADDER = 420;

	static final TouchEvent touchEvent = new TouchEvent();
	@Getter
	static final ButtonEvent buttonEvent = new ButtonEvent();

	static final Map<Controller, ControllerHandler> controllers = new HashMap<>(2);

	final InputHandler handler = new InputHandler();

	public static boolean isPressed(Controller controller, int axisCode) {
		ControllerHandler handler = controllers.get(controller);
		return handler != null && handler.isPressed(axisCode);
	}

	public static String translateKey(int keycode) {
		return Cardinal.game == null ? Keys.toString(keycode) : Cardinal.getPlatform().translateKey(keycode);
	}

	public static int translateKeyCode(int keycode) {
		return Cardinal.game == null ? keycode : Cardinal.getPlatform().translateKeyCode(keycode);
	}

	public static boolean isTranslatedKeyPressed(int keycode) {
		return Gdx.input.isKeyPressed(translateKeyCode(keycode));
	}

	final Cardinal cardinal;

	public InputManager(Cardinal cardinal) {
		this.cardinal = cardinal;
		Controllers.addListener(this);
		for (Controller controller : Controllers.getControllers()) {
			controllers.put(controller, new ControllerHandler(this, controller));
		}
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchKey(Keys.BACK, true);
		Gdx.input.setCatchKey(Keys.VOLUME_DOWN, false);
		Gdx.input.setCatchKey(Keys.VOLUME_UP, false);
	}

	public void addPermanentListener(InputListener listener) {
		handler.add(listener);
	}

	public boolean removePermanentListener(InputListener listener) {
		return handler.remove(listener);
	}

	public boolean isFree() {
		return handler.isFree();
	}

	public void triggerButton(ButtonEvent event) {
		triggerButton(event, true);
	}

	public void triggerButton(ButtonEvent event, boolean log) {
		InputListener consumer = handler.button(event);
		if (log) {
			if (consumer == null) {
				debug(event.toString());
			} else {
				debug(event.toString() + "->" + GameData.toString(consumer));
			}
		}
	}

	public void triggerTouch(TouchEvent event) {
		triggerTouch(event, !event.isMoved());
	}

	public void triggerTouch(TouchEvent event, boolean log) {
		InputListener consumer = handler.touch(event);
		if (log) {
			if (consumer == null) {
				debug(event.toString());
			} else {
				debug(event.toString() + "->" + GameData.toString(consumer));
			}
		}
	}

	@Override
	public void connected(Controller controller) {
		controllers.put(controller, new ControllerHandler(this, controller));
		handler.controllerConnected(controller);
	}

	@Override
	public void disconnected(Controller controller) {
		controllers.remove(controller);
		handler.controllerDisconnected(controller);
	}

	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {
		buttonEvent.setProperties(controller, buttonCode, null, InputType.PRESSED);
		triggerButton(buttonEvent);
		return false;
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonCode) {
		buttonEvent.setProperties(controller, buttonCode, null, InputType.RELEASED);
		triggerButton(buttonEvent);
		return false;
	}

	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
		ControllerHandler handler = controllers.get(controller);
		if (handler != null) {
			handler.axisMoved(axisCode, value);
		}
		return false;
	}

	public void movedAxis(Controller controller, int axisId, int buttonId, Vector2 axis) {
		buttonEvent.setProperties(controller, buttonId, axis, InputType.MOVED);
		triggerButton(buttonEvent);
	}

	public void pressAxis(Controller controller, int axisId, int buttonId, InputType type) {
		buttonEvent.setProperties(controller, buttonId, null, type);
		triggerButton(buttonEvent);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (isKeyboardDisabled(keycode)) {
			return false;
		}
		int k = (keycode == Keys.CENTER ? Keys.SPACE : keycode);
		InputListener consumer = handler.down(k);
		if (consumer == null) {
			buttonEvent.setProperties(KEYBOARD, k, null, InputType.PRESSED);
			triggerButton(buttonEvent);
		} else {
			debug("Key down: " + Keys.toString(k) + "->" + GameData.toString(consumer));
		}
		return false;
	}

	private boolean isKeyboardDisabled(int keycode) {
		return Cardinal.isPortable() && EngineProperties.PLATFORM != Platform.DEV && //
		// Allowed keys:
				keycode != Keys.ENTER && keycode != Keys.CENTER && keycode != Keys.ESCAPE;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (isKeyboardDisabled(keycode)) {
			return false;
		}
		int k = (keycode == Keys.CENTER ? Keys.SPACE : keycode);
		InputListener consumer = handler.up(k);
		if (consumer == null) {
			buttonEvent.setProperties(KEYBOARD, k, null, InputType.RELEASED);
			triggerButton(buttonEvent);
		} else {
			debug("Key up: " + Keys.toString(k) + "->" + GameData.toString(consumer));
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		InputListener consumer = handler.type(character);
		if (consumer == null) {
			debug("Typed: " + character);
		} else {
			debug("Typed: " + character + "->" + GameData.toString(consumer));
		}
		return false;
	}

	private void debug(String message) {
		if (EngineProperties.PLATFORM == Platform.DEV || Cardinal.DEBUGGING) {
			Gdx.app.debug("Input", message);
		}
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		touchEvent.setProperties(InputType.PRESSED, pointer, button, getX(screenX), getY(screenY));
		triggerTouch(touchEvent);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		touchEvent.setProperties(InputType.RELEASED, pointer, button, getX(screenX), getY(screenY));
		triggerTouch(touchEvent);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		touchEvent.setProperties(InputType.MOVED, pointer, 0, getX(screenX), getY(screenY));
		triggerTouch(touchEvent);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		if (EngineProperties.PLATFORM != Platform.DEV || !Gdx.input.isKeyPressed(Keys.ALT_LEFT)) {
			touchEvent.setProperties(InputType.MOVED, -1, -1, getX(screenX), getY(screenY));
			triggerTouch(touchEvent);
		}
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			amountX = amountY;
			amountY = 0;
		}
		touchEvent.setProperties(InputType.MOVED, -2, -2, (int) amountX, (int) amountY);
		triggerTouch(touchEvent);
		return false;
	}

	private int getX(int screenX) {
		return cardinal.painter.toLocalX(screenX);
	}

	private int getY(int screenY) {
		return cardinal.painter.toLocalY(Gdx.graphics.getHeight() - screenY);
	}

	public Controller getLast() {
		return handler.last;
	}

	public boolean isLastTouch() {
		return handler.lastTouch;
	}

	public Array<InputListener> getListeners() {
		return handler.listeners;
	}

}
