package com.one2b3.endcycle.engine.input.controllers;

import java.util.List;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.ControllerMapping;
import com.badlogic.gdx.controllers.ControllerPowerLevel;
import com.one2b3.endcycle.engine.graphics.DrawableImage;
import com.one2b3.endcycle.engine.input.KeyCode;
import com.one2b3.endcycle.engine.input.KeyCodeCategory;
import com.one2b3.endcycle.engine.input.binders.KeyBinder;
import com.one2b3.endcycle.engine.input.binders.bindings.images.ButtonImages;
import com.one2b3.utils.java.SingletonList;

public class MockController implements Controller, KeyBinder {

	long buttons = 0;

	public void setButton(KeyCode buttonCode, boolean pressed) {
		setButton(buttonCode.ordinal(), pressed);
	}

	public void setButton(int buttonCode, boolean pressed) {
		if (pressed) {
			buttons |= 1 << buttonCode;
		} else {
			buttons &= (1 << buttonCode) ^ -1;
		}
	}

	@Override
	public boolean getButton(int buttonCode) {
		return (buttons & 1 << buttonCode) != 0;
	}

	public void clearButtons() {
		buttons = 0;
	}

	@Override
	public float getAxis(int axisCode) {
		return 0;
	}

	@Override
	public String getName() {
		return "Mock" + hashCode();
	}

	@Override
	public void addListener(ControllerListener listener) {
	}

	@Override
	public void removeListener(ControllerListener listener) {
	}

	@Override
	public boolean isChanged() {
		return false;
	}

	@Override
	public void reset() {
	}

	@Override
	public void clear() {
	}

	@Override
	public void load() {
	}

	@Override
	public void save() {
	}

	@Override
	public void setKey(KeyCode code, int key) {
	}

	@Override
	public void removeKey(KeyCode code, int key) {
	}

	@Override
	public void setButtonImage(int button, ButtonImages image) {
	}

	@Override
	public Integer getKey(KeyCode code) {
		return code == null ? null : code.ordinal();
	}

	@Override
	public boolean isKey(KeyCode code, int button) {
		return code != null && code.ordinal() == button;
	}

	@Override
	public List<Integer> getKeys(KeyCode code) {
		return new SingletonList<>(code.ordinal());
	}

	@Override
	public void clearImages() {
	}

	@Override
	public KeyCode getCode(KeyCodeCategory category, int button) {
		KeyCode[] keys = KeyCode.values();
		if (button < 0 || button >= keys.length) {
			return null;
		}
		KeyCode code = keys[button];
		return code.getCategory() == category ? code : null;
	}

	@Override
	public DrawableImage getButtonImage(KeyCode code) {
		return null;
	}

	@Override
	public List<DrawableImage> getButtonImages(KeyCode code) {
		return null;
	}

	@Override
	public String getUniqueId() {
		return getName();
	}

	@Override
	public int getMinButtonIndex() {
		return 0;
	}

	@Override
	public int getMaxButtonIndex() {
		return KeyCode.values().length;
	}

	@Override
	public int getAxisCount() {
		return 0;
	}

	@Override
	public boolean isConnected() {
		return true;
	}

	@Override
	public boolean canVibrate() {
		return false;
	}

	@Override
	public boolean isVibrating() {
		return false;
	}

	@Override
	public void startVibration(int duration, float strength) {
	}

	@Override
	public void cancelVibration() {
	}

	@Override
	public boolean supportsPlayerIndex() {
		return false;
	}

	@Override
	public int getPlayerIndex() {
		return 0;
	}

	@Override
	public void setPlayerIndex(int index) {
	}

	@Override
	public ControllerMapping getMapping() {
		return null;
	}

	@Override
	public ControllerPowerLevel getPowerLevel() {
		return ControllerPowerLevel.POWER_UNKNOWN;
	}

	@Override
	public void load(Controller controller) {
	}

}
