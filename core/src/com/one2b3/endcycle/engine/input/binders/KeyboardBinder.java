package com.one2b3.endcycle.engine.input.binders;

import java.util.ArrayList;

import com.badlogic.gdx.controllers.Controller;
import com.one2b3.endcycle.engine.graphics.DrawableImage;
import com.one2b3.endcycle.engine.input.binders.bindings.images.ButtonImageList;
import com.one2b3.endcycle.engine.input.binders.bindings.images.makers.KeyboardImageMaker;
import com.one2b3.endcycle.engine.proguard.KeepClass;

@KeepClass
public class KeyboardBinder extends DefaultKeyBinder {

	transient ButtonImageList realImages;

	public KeyboardBinder() {
		controller = "Keyboard";
		buttonImages = new ButtonImageList();
		bindings = new ArrayList<>();
	}

	@Override
	public void load(Controller controller) {
		super.load(controller);
	}

	@Override
	public ButtonImageList getImages(Controller controller) {
		return new ButtonImageList();
	}

	@Override
	public void load() {
		super.load();
		buttonImages.clear();
	}

	@Override
	public DrawableImage getButtonImage(int key) {
		if (realImages == null) {
			realImages = new KeyboardImageMaker().get(null);
		}
		return realImages.get(key);
	}
}
