package com.one2b3.endcycle.engine.input.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.ControllerMapping;
import com.badlogic.gdx.controllers.ControllerPowerLevel;
import com.one2b3.endcycle.engine.input.binders.KeyboardBinder;

public final class KeyboardController implements Controller {

	public final KeyboardBinder binder = new KeyboardBinder();

	@Override
	public boolean getButton(int buttonCode) {
		return Gdx.input.isKeyPressed(buttonCode);
	}

	@Override
	public float getAxis(int axisCode) {
		return 0;
	}

	@Override
	public String getName() {
		return binder.controller;
	}

	@Override
	public void addListener(ControllerListener listener) {
	}

	@Override
	public void removeListener(ControllerListener listener) {
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
		return Keys.MAX_KEYCODE;
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

	public void load() {
		binder.load(this);
	}

	public void save() {
		binder.save();
	}

}
