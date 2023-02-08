package com.one2b3.endcycle.engine.input.binders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.controllers.Controller;
import com.one2b3.endcycle.engine.graphics.DrawableImage;
import com.one2b3.endcycle.engine.input.KeyCode;
import com.one2b3.endcycle.engine.input.KeyCodeCategory;
import com.one2b3.endcycle.engine.input.binders.bindings.images.ButtonImages;
import com.one2b3.endcycle.engine.proguard.KeepClass;

@KeepClass
public class EmptyKeybinder implements KeyBinder {

	static final List<DrawableImage> EMPTY = new ArrayList<>(0);

	public EmptyKeybinder() {
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
	public KeyCode getCode(KeyCodeCategory category, int button) {
		return null;
	}

	@Override
	public void setKey(KeyCode code, int key) {
	}

	@Override
	public boolean isKey(KeyCode code, int button) {
		return false;
	}

	@Override
	public Integer getKey(KeyCode code) {
		return null;
	}

	@Override
	public List<Integer> getKeys(KeyCode code) {
		return Collections.emptyList();
	}

	@Override
	public DrawableImage getButtonImage(KeyCode code) {
		return null;
	}

	@Override
	public List<DrawableImage> getButtonImages(KeyCode code) {
		return EMPTY;
	}

	@Override
	public boolean isChanged() {
		return false;
	}

	@Override
	public void removeKey(KeyCode code, int key) {
	}

	@Override
	public void clearImages() {
	}

	@Override
	public void setButtonImage(int button, ButtonImages image) {
	}

	@Override
	public void load(Controller controller) {
	}

}
