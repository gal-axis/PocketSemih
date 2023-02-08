package com.one2b3.endcycle.engine.input.binders.bindings.images.makers;

import static com.badlogic.gdx.Input.Keys.*;
import static com.one2b3.endcycle.engine.input.binders.bindings.images.ButtonImages.*;

import java.util.HashMap;
import java.util.Map;

import com.one2b3.endcycle.engine.input.InputManager;
import com.one2b3.endcycle.engine.input.binders.bindings.images.ButtonImageList;
import com.one2b3.endcycle.engine.input.binders.bindings.images.ButtonImages;
import com.one2b3.endcycle.engine.input.binders.bindings.images.UnknownButtonImageDrawable;

public class KeyboardImageList extends ButtonImageList {

	Map<String, ButtonImages> images;

	public KeyboardImageList() {
		images = new HashMap<>();
		add(KEYBOARD_ENTER, ENTER);
		add(KEYBOARD_ALT_LEFT, ALT_RIGHT);
		add(KEYBOARD_ALT_RIGHT, ALT_RIGHT);
		add(KEYBOARD_BACKSPACE, BACKSPACE);
		add(KEYBOARD_CONTROL_LEFT, CONTROL_LEFT);
		add(KEYBOARD_CONTROL_RIGHT, CONTROL_RIGHT);
		add(KEYBOARD_ESCAPE, ESCAPE);
		add(KEYBOARD_PAGE_DOWN, PAGE_DOWN);
		add(KEYBOARD_PAGE_UP, PAGE_UP);
		add(KEYBOARD_SHIFT_RIGHT, SHIFT_RIGHT);
		add(KEYBOARD_SHIFT_LEFT, SHIFT_LEFT);
		add(KEYBOARD_SPACE, SPACE);
		add(KEYBOARD_TAB, TAB);
		add(KEYBOARD_F1, F1);
		add(KEYBOARD_F2, F2);
		add(KEYBOARD_F3, F3);
		add(KEYBOARD_F4, F4);
		add(KEYBOARD_F5, F5);
		add(KEYBOARD_F6, F6);
		add(KEYBOARD_F7, F7);
		add(KEYBOARD_F8, F8);
		add(KEYBOARD_F9, F9);
		add(KEYBOARD_F10, F10);
		add(KEYBOARD_F11, F11);
		add(KEYBOARD_F12, F12);
		add(KEYBOARD_DOWN, DOWN);
		add(KEYBOARD_UP, UP);
		add(KEYBOARD_LEFT, LEFT);
		add(KEYBOARD_RIGHT, RIGHT);
		add(KEYBOARD_0, NUM_0, NUMPAD_0);
		add(KEYBOARD_1, NUM_1, NUMPAD_1);
		add(KEYBOARD_2, NUM_2, NUMPAD_2);
		add(KEYBOARD_3, NUM_3, NUMPAD_3);
		add(KEYBOARD_4, NUM_4, NUMPAD_4);
		add(KEYBOARD_5, NUM_5, NUMPAD_5);
		add(KEYBOARD_6, NUM_6, NUMPAD_6);
		add(KEYBOARD_7, NUM_7, NUMPAD_7);
		add(KEYBOARD_8, NUM_8, NUMPAD_8);
		add(KEYBOARD_9, NUM_9, NUMPAD_9);
	}

	@Override
	public UnknownButtonImageDrawable createUnknownImage(int button) {
		String translated = InputManager.translateKey(button);
		if (translated == null || translated.equals("Unknown")) {
			translated = Character.toString((char) button);
		}
		translated = translated.toUpperCase();
		return new UnknownButtonImageDrawable(translated);
	}
}
