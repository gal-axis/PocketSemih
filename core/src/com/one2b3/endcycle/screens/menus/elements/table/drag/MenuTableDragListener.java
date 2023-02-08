package com.one2b3.endcycle.screens.menus.elements.table.drag;

import com.one2b3.endcycle.screens.menus.elements.table.MenuTable;

public interface MenuTableDragListener<T> {

	default void startDrag(MenuTable<T> container, T element, int index) {
	}

	default void drag(MenuTable<T> container, T element, int index, int x, int y) {
	}

	default void stopDrag(MenuTable<T> container, T element, int index, int x, int y) {
	}

	default void draw(MenuTable<T> container, T element, int index, float x, float y) {
		container.drawObject(element, index, x, y);
	}
}
