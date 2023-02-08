package com.one2b3.endcycle.engine.input.binders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.one2b3.endcycle.engine.input.KeyCode;

public class BindingMap {

	List<KeyBinding> binding = new ArrayList<>(KeyCode.values().length);

	public BindingMap with(KeyCode code, Integer... button) {
		binding.add(new KeyBinding(code, new ArrayList<>(Arrays.asList(button))));
		return this;
	}

	public List<KeyBinding> build() {
		return binding;
	}
}
