package com.one2b3.endcycle.screens.menus.elements.checkbox;

public interface MenuCheckBoxAction {
	void onClick(boolean checked);

	default boolean onDisabledClick() {
		return false;
	}
}