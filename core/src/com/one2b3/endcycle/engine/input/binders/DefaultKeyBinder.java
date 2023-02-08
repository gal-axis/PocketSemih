package com.one2b3.endcycle.engine.input.binders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.files.FileHandle;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.engine.files.Data;
import com.one2b3.endcycle.engine.graphics.DrawableImage;
import com.one2b3.endcycle.engine.input.KeyCode;
import com.one2b3.endcycle.engine.input.KeyCodeCategory;
import com.one2b3.endcycle.engine.input.binders.bindings.images.ButtonImageList;
import com.one2b3.endcycle.engine.input.binders.bindings.images.ButtonImageListFactory;
import com.one2b3.endcycle.engine.input.binders.bindings.images.ButtonImages;
import com.one2b3.endcycle.engine.proguard.KeepClass;
import com.one2b3.endcycle.screens.menus.elements.text.TextAcceptors;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@KeepClass
public class DefaultKeyBinder implements KeyBinder {

	transient List<KeyBinding> defaultBindings;
	transient Map<KeyCode, KeyBinding> bindingMap = new HashMap<>();
	@Getter
	transient boolean changed;

	public String controller;
	protected ButtonImageList buttonImages;
	public List<KeyBinding> bindings;

	public DefaultKeyBinder(Controller controller, String name) {
		this.controller = name;
		bindings = new ArrayList<>();
	}

	@Override
	public void load(Controller controller) {
		if (controller != null) {
			DefaultBinding binding = DefaultBindingFactory.getDefaultBinding(this.controller);
			defaultBindings = binding.getDefaults(controller);
			buttonImages = getImages(controller);
			load();
		}
	}

	public ButtonImageList getImages(Controller controller) {
		return ButtonImageListFactory.getButtonImages(this.controller, controller);
	}

	@Override
	public void reset() {
		changed = true;
		bindings.clear();
		bindings.addAll(defaultBindings);
		generateCodes();
		addMissing();
	}

	@Override
	public void clear() {
		bindingMap.clear();
		bindings.clear();
		buttonImages.clear();
		for (KeyCode code : KeyCode.values()) {
			bindings.add(new KeyBinding(code, new ArrayList<>()));
		}
		generateCodes();
	}

	private void addMissing() {
		for (KeyCode code : KeyCode.values()) {
			if (!bindingMap.containsKey(code)) {
				KeyBinding binding = null;
				for (KeyBinding b : defaultBindings) {
					if (b.getKeyCode() == code) {
						binding = b;
						break;
					}
				}
				if (binding == null) {
					binding = new KeyBinding(code, new ArrayList<>());
				}
				bindings.add(binding);
				bindingMap.put(code, binding);
			}
		}
	}

	private void generateCodes() {
		bindingMap.clear();
		for (KeyBinding binding : bindings) {
			KeyCode code = binding.getKeyCode();
			if (code != null) {
				if (binding.getCodes() == null) {
					for (int i = 0; i < defaultBindings.size(); i++) {
						KeyBinding def = defaultBindings.get(i);
						if (def.getKeyCode() == code) {
							binding.codes = new ArrayList<>(def.getCodes());
							break;
						}
					}
				}
				bindingMap.put(code, binding);
			}
		}
	}

	public FileHandle getHandle() {
		String path = TextAcceptors.FILENAME.filter(controller);
		return Data.getHandle("controllers/" + path + "-map.json");
	}

	@Override
	public void load() {
		reset();
		changed = false;
		if (Data.load(getHandle(), this)) {
			buttonImages.update();
			generateCodes();
			addMissing();
		} else {
			save();
		}
	}

	@Override
	public void save() {
		Data.save(getHandle(), this);
		changed = false;
	}

	@Override
	public void setKey(KeyCode code, int key) {
		List<Integer> keys = bindingMap.get(code).getCodes();
		if (keys.contains(key)) {
			keys.remove((Integer) key);
		} else {
			removeKey(code, key);
			if (keys.size() > 5) {
				keys.remove(0);
			}
			keys.add(key);
		}
		changed = true;
	}

	@Override
	public void removeKey(KeyCode code, int key) {
		for (KeyBinding binding : bindings) {
			KeyCode keyCode = binding.getKeyCode();
			List<Integer> codes = binding.getCodes();
			if (keyCode != null && keyCode != code && keyCode.getCategory() == code.getCategory() && codes.contains(key)) {
				codes.remove((Integer) key);
			}
		}
		changed = true;
	}

	@Override
	public void setButtonImage(int button, ButtonImages image) {
		buttonImages.set(button, image);
		changed = true;
	}

	@Override
	public Integer getKey(KeyCode code) {
		List<Integer> keys = getKeys(code);
		return (keys != null && keys.size() > 0 ? keys.get((int) ((Cardinal.TIME_ACTIVE * 0.5F) % keys.size())) : null);
	}

	@Override
	public boolean isKey(KeyCode code, int button) {
		List<Integer> keys = getKeys(code);
		return keys != null && keys.contains(button);
	}

	@Override
	public List<Integer> getKeys(KeyCode code) {
		KeyBinding binding = bindingMap.get(code);
		return binding == null ? null : binding.getCodes();
	}

	@Override
	public KeyCode getCode(KeyCodeCategory category, int button) {
		for (KeyBinding binding : bindings) {
			KeyCode keyCode = binding.getKeyCode();
			if (keyCode != null && keyCode.getCategory() == category) {
				if (binding.getCodes() != null && binding.getCodes().contains(button)) {
					return keyCode;
				}
			}
		}
		return null;
	}

	@Override
	public void clearImages() {
		buttonImages.update();
	}

	@Override
	public DrawableImage getButtonImage(KeyCode code) {
		Integer key = getKey(code);
		return key == null ? null : getButtonImage(key);
	}

	@Override
	public List<DrawableImage> getButtonImages(KeyCode code) {
		List<DrawableImage> drawables = new ArrayList<>();
		List<Integer> keys = getKeys(code);
		if (keys != null) {
			for (Integer key : keys) {
				drawables.add(getButtonImage(key));
			}
		}
		return drawables;
	}

	public DrawableImage getButtonImage(int key) {
		return buttonImages.get(key);
	}
}
