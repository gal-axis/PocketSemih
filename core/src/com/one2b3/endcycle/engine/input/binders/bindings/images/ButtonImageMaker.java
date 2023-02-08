package com.one2b3.endcycle.engine.input.binders.bindings.images;

import com.badlogic.gdx.controllers.Controller;

public abstract class ButtonImageMaker {

	ButtonImageList collection;

	public abstract void init(Controller controller);

	private void reset() {
		collection = createList();
	}

	public ButtonImageList createList() {
		return new ButtonImageList();
	}

	public void add(ButtonImages image, int... buttons) {
		collection.add(image, buttons);
	}

	public ButtonImageList get(Controller controller) {
		reset();
		init(controller);
		collection.update();
		return collection;
	}

}
