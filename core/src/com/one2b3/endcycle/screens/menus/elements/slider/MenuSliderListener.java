package com.one2b3.endcycle.screens.menus.elements.slider;

public interface MenuSliderListener {

	void dragSlider(float value);

	default void startSliderDrag() {
	}

	default void stopSliderDrag() {
	}
}
