package com.one2b3.endcycle.engine.input.binders;

import java.util.HashMap;
import java.util.Map;

import com.one2b3.endcycle.engine.input.InputManager;
import com.one2b3.endcycle.engine.input.binders.bindings.*;

public class DefaultBindingFactory {

	static final Map<String, DefaultBinding> BINDINGS;
	static final DefaultBinding FALLBACK_BINDING;

	static {
		FALLBACK_BINDING = new AdvancedDefaultBinding(new PlaystationDefaultBinding());
		BINDINGS = new HashMap<>();
		BINDINGS.put(InputManager.KEYBOARD.getName(), new KeyboardDefaultBinding());
		BINDINGS.put("Wireless Controller", new PlaystationDefaultBinding());
		BINDINGS.put("Xbox", new XboxDefaultBinding());
		BINDINGS.put("vJoy Device", new VJoyDefaultBinding());
		BINDINGS.put("GAMEPAD4", new PlaystationDefaultBinding());
		BINDINGS.put("Playstation", new PlaystationDefaultBinding());
		BINDINGS.put("XInput", new AdvancedDefaultBinding(new XboxDefaultBinding()));
	}

	public static DefaultBinding getDefaultBinding(String controller) {
		controller = controller.toLowerCase();
		for (String name : BINDINGS.keySet()) {
			if (controller.contains(name.toLowerCase())) {
				return BINDINGS.get(name);
			}
		}
		return FALLBACK_BINDING;
	}
}
