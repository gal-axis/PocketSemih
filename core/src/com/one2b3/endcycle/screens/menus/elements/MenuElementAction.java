package com.one2b3.endcycle.screens.menus.elements;

import com.badlogic.gdx.utils.Pools;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class MenuElementAction implements AutoCloseable {

	public static enum MenuElementActionType {
		CHANGED_VALUE, SELECTED, FOCUSED, UNFOCUSED, DESELECTED, ENABLED, DISABLED, HIDDEN, SHOWN;
	}

	MenuElementActionType type;
	MenuElement source;
	Object value;

	@Override
	public void close() {
		Pools.free(this);
	}
}
