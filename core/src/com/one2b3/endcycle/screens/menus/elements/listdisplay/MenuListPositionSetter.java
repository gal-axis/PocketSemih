package com.one2b3.endcycle.screens.menus.elements.listdisplay;

import com.badlogic.gdx.math.Vector2;

public interface MenuListPositionSetter<T> {

	default Vector2 start(MenuListDisplay<T> listDisplay) {
		return Vector2.Zero;
	}

	public void setPosition(MenuListDisplay<T> listDisplay, MenuListObjectPosition<T> position);

	default void end(MenuListDisplay<T> listDisplay) {
	}
}
