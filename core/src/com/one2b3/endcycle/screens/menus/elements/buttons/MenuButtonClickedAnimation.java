package com.one2b3.endcycle.screens.menus.elements.buttons;

import com.one2b3.endcycle.utils.bounded.BoundedFloat;

public final class MenuButtonClickedAnimation {

	BoundedFloat animation = new BoundedFloat(0.0F, 1.0F, 10.0F);

	public void start() {
		animation.toMax();
	}

	public void update(float delta) {
		animation.decrease(delta);
	}

	public float getVal() {
		return animation.getVal();
	}

	public float getHeight(float height) {
		return getHeight(height, 1.0F);
	}

	public float getHeight(float height, float scale) {
		float offset = 3 * scale;
		return height - offset + Math.abs((0.5F - animation.getVal()) * offset * 2);
	}

}
