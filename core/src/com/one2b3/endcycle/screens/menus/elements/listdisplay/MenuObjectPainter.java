package com.one2b3.endcycle.screens.menus.elements.listdisplay;

import com.one2b3.endcycle.engine.drawing.ObjectPainter;

public interface MenuObjectPainter<T> extends ObjectPainter<T> {

	default void start() {
	}

	default void end() {
	}
}
