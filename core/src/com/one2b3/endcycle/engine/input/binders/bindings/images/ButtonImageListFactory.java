package com.one2b3.endcycle.engine.input.binders.bindings.images;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.controllers.Controller;
import com.one2b3.endcycle.engine.input.binders.bindings.images.makers.PlaystationImageMaker;
import com.one2b3.endcycle.engine.input.binders.bindings.images.makers.VJoyImageCollection;
import com.one2b3.endcycle.engine.input.binders.bindings.images.makers.XInputImageMaker;
import com.one2b3.endcycle.engine.input.binders.bindings.images.makers.XboxImageMaker;
import com.one2b3.utils.java.Objects;

public class ButtonImageListFactory {

	static final Map<String, ButtonImageMaker> BUTTON_IMAGE_COLLECTIONS;
	static final ButtonImageMaker FALLBACK;

	static {
		FALLBACK = new XInputImageMaker();
		BUTTON_IMAGE_COLLECTIONS = new HashMap<>();
		add("Wireless Controller", new PlaystationImageMaker());
		add("Xbox 360 Controller", new XboxImageMaker());
		add("Xbox Controller", new XboxImageMaker());
		add("Wireless Xbox Controller", new XboxImageMaker());
		add("vJoy Device", new VJoyImageCollection());
		add("XInput Controller", new XInputImageMaker());
	}

	private static void add(String controller, ButtonImageMaker maker) {
		BUTTON_IMAGE_COLLECTIONS.put(controller, maker);
	}

	public static ButtonImageList getButtonImages(String name, Controller controller) {
		return Objects.get(BUTTON_IMAGE_COLLECTIONS, name, FALLBACK).get(controller);
	}
}
