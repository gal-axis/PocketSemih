package com.one2b3.endcycle.screens.menus.elements.anchors;

import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.screens.menus.elements.MenuElement;

public final class MenuAnchors extends MenuElement {

	public static final MenuAnchors TOP = new MenuAnchors(-1, 1, 1.0F, 0.0F), //
			LEFT = new MenuAnchors(-1, -1, 0.0F, 1.0F), //
			BOTTOM = new MenuAnchors(-1, -1, 1.0F, 0.0F), //
			RIGHT = new MenuAnchors(1, -1, 0.0F, 1.0F), //
			CENTER = new MenuAnchors(-1, -1, 1.0F, 1.0F);

	private MenuAnchors(int hAlign, int vAlign, float width, float height) {
		setHAlign(hAlign).setVAlign(vAlign);
		setWidth(width);
		setHeight(height);
	}

	@Override
	public float calcX() {
		return Cardinal.getWidth() * 0.5F * (getHAlign() + 1);
	}

	@Override
	public float calcY() {
		return Cardinal.getHeight() * 0.5F * (getVAlign() + 1);
	}

	@Override
	public float getWidth() {
		return super.getWidth() * Cardinal.getWidth();
	}

	@Override
	public float getHeight() {
		return super.getHeight() * Cardinal.getHeight();
	}
}
