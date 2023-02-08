package com.one2b3.endcycle.screens.menus.elements;

import com.one2b3.endcycle.screens.menus.elements.anchors.MenuAnchors;

public final class MenuLayout {

	static final MenuElement empty = new MenuElement() {
	};

	public static MenuLayout layout(MenuElement element) {
		return new MenuLayout().setLayingOut(element);
	}

	MenuElement layingOut;

	private MenuLayout() {
	}

	private MenuLayout setLayingOut(MenuElement layingOut) {
		this.layingOut = (layingOut == null ? empty : layingOut);
		return this;
	}

	public MenuLayout resetAnchor() {
		this.layingOut.setAnchor(null).setAnchorHAlign(-1).setAnchorVAlign(-1).setHAlign(-1).setVAlign(-1);
		return this;
	}

	public MenuLayout at(float x, float y) {
		this.layingOut.setX(x);
		this.layingOut.setY(y);
		return this;
	}

	public MenuLayout x(float x) {
		this.layingOut.setX(x);
		return this;
	}

	public MenuLayout y(float y) {
		this.layingOut.setY(y);
		return this;
	}

	public MenuLayout width(float width) {
		this.layingOut.setWidth(width);
		return this;
	}

	public MenuLayout height(float height) {
		this.layingOut.setHeight(height);
		return this;
	}

	public MenuLayout size(float size) {
		this.layingOut.setWidth(size);
		this.layingOut.setHeight(size);
		return this;
	}

	public MenuLayout center() {
		return center(MenuAnchors.CENTER);
	}

	public MenuLayout center(MenuElement element) {
		this.layingOut.setAnchor(element);
		this.layingOut.setHAlign(0).setAnchorHAlign(0);
		this.layingOut.setVAlign(0).setAnchorVAlign(0);
		this.layingOut.setX(0).setY(0);
		return this;
	}

	public MenuLayout moveX(float x) {
		this.layingOut.setX(this.layingOut.getX() + x);
		return this;
	}

	public MenuLayout moveY(float y) {
		this.layingOut.setY(this.layingOut.getY() + y);
		return this;
	}

	public MenuLayout size(MenuElement other) {
		if (other == null) {
			other = empty;
		}
		this.layingOut.setWidth(other.getWidth());
		this.layingOut.setHeight(other.getHeight());
		return this;
	}

	public MenuLayout width(MenuElement other) {
		return width(other, 1.0F);
	}

	public MenuLayout width(MenuElement other, float percent) {
		if (other == null) {
			other = empty;
		}
		this.layingOut.setWidth(other.getWidth() * percent);
		return this;
	}

	public MenuLayout height(MenuElement other) {
		return height(other, 1.0F);
	}

	public MenuLayout height(MenuElement other, float percent) {
		if (other == null) {
			other = empty;
		}
		this.layingOut.setHeight(other.getHeight() * percent);
		return this;
	}

	public MenuLayout horizontally(MenuElement other) {
		this.layingOut.setAnchor(other);
		this.layingOut.setAnchorHAlign(0);
		this.layingOut.setHAlign(0);
		this.layingOut.setX(0);
		return this;
	}

	public MenuLayout vertically(MenuElement other) {
		this.layingOut.setAnchor(other);
		this.layingOut.setAnchorVAlign(0);
		this.layingOut.setVAlign(0);
		this.layingOut.setY(0);
		return this;
	}

	public MenuLayout rightOf(MenuElement other, float distance) {
		this.layingOut.setAnchor(other);
		this.layingOut.setX(distance);
		right();
		vertically(other);
		return this;
	}

	public MenuLayout leftOf(MenuElement other, float distance) {
		this.layingOut.setAnchor(other);
		this.layingOut.setX(-distance);
		left();
		vertically(other);
		return this;
	}

	public MenuLayout topOf(MenuElement other, float distance) {
		this.layingOut.setAnchor(other);
		this.layingOut.setY(distance);
		top();
		horizontally(other);
		return this;
	}

	public MenuLayout bottomOf(MenuElement other, float distance) {
		this.layingOut.setAnchor(other);
		this.layingOut.setY(-distance);
		bottom();
		horizontally(other);
		return this;
	}

	public MenuLayout topLine(MenuElement other, float distance) {
		this.layingOut.setAnchor(other);
		topInner();
		this.layingOut.setY(distance);
		return this;
	}

	public MenuLayout bottomLine(MenuElement other, float distance) {
		this.layingOut.setAnchor(other);
		bottomInner();
		this.layingOut.setY(-distance);
		return this;
	}

	public MenuLayout left() {
		this.layingOut.setHAlign(1);
		this.layingOut.setAnchorHAlign(-1);
		return this;
	}

	public MenuLayout right() {
		this.layingOut.setHAlign(-1);
		this.layingOut.setAnchorHAlign(1);
		return this;
	}

	public MenuLayout top() {
		this.layingOut.setVAlign(-1);
		this.layingOut.setAnchorVAlign(1);
		return this;
	}

	public MenuLayout bottom() {
		this.layingOut.setVAlign(1);
		this.layingOut.setAnchorVAlign(-1);
		return this;
	}

	public MenuLayout leftInner() {
		this.layingOut.setHAlign(-1);
		this.layingOut.setAnchorHAlign(-1);
		return this;
	}

	public MenuLayout rightInner() {
		this.layingOut.setHAlign(1);
		this.layingOut.setAnchorHAlign(1);
		return this;
	}

	public MenuLayout topInner() {
		this.layingOut.setVAlign(1);
		this.layingOut.setAnchorVAlign(1);
		return this;
	}

	public MenuLayout bottomInner() {
		this.layingOut.setVAlign(-1);
		this.layingOut.setAnchorVAlign(-1);
		return this;
	}
}
