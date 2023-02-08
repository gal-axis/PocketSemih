package com.one2b3.endcycle.engine.input.binders;

import java.util.List;

import com.badlogic.gdx.controllers.Controller;

public interface DefaultBinding {

	public List<KeyBinding> getDefaults(Controller controller);

	public default BindingMap map() {
		return new BindingMap();
	}
}
