package com.one2b3.endcycle.engine.input.binders.bindings.images.makers;

import com.badlogic.gdx.controllers.Controller;
import com.one2b3.endcycle.engine.input.binders.bindings.images.ButtonImageMaker;

public class KeyboardImageMaker extends ButtonImageMaker {

	@Override
	public KeyboardImageList createList() {
		return new KeyboardImageList();
	}

	@Override
	public void init(Controller controller) {
	}
}
