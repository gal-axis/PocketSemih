package com.one2b3.endcycle.screens.menus.elements.scroller;

public interface Scrollable {

	float getScrollAreaX();

	float getScrollAreaY();

	float getScrollAreaWidth();

	float getScrollAreaHeight();

	float getScrollWidth();

	float getScrollHeight();

	default boolean canScrollX() {
		return getScrollWidth() > 0.0F;
	}

	default boolean canScrollY() {
		return getScrollHeight() > 0.0F;
	}
}