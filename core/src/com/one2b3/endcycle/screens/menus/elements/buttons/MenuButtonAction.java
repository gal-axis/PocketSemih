package com.one2b3.endcycle.screens.menus.elements.buttons;

public interface MenuButtonAction {

	void onClick();

	default boolean onDisabledClick() {
		return false;
	}

	static Runnable toRunnable(MenuButtonAction action) {
		return action::onClick;
	}

}
