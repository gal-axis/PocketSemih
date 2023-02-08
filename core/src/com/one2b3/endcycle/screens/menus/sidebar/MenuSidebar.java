package com.one2b3.endcycle.screens.menus.sidebar;

import com.badlogic.gdx.graphics.Color;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.graphics.Drawable;
import com.one2b3.endcycle.engine.graphics.Drawables;
import com.one2b3.endcycle.engine.input.InputListener;
import com.one2b3.endcycle.engine.language.LocalizedMessage;
import com.one2b3.endcycle.engine.objects.Direction;
import com.one2b3.endcycle.features.theme.ActiveTheme;
import com.one2b3.endcycle.screens.menus.elements.MenuElement;
import com.one2b3.endcycle.screens.menus.elements.MenuElementController;
import com.one2b3.endcycle.screens.menus.elements.MenuLayout;
import com.one2b3.endcycle.screens.menus.elements.anchors.MenuAnchors;
import com.one2b3.endcycle.screens.menus.elements.group.MenuElementGroup;
import com.one2b3.endcycle.utils.bounded.BoundedFloat;

public final class MenuSidebar extends MenuElementGroup implements InputListener {

	final Drawable sideBar = Drawables.menu_sidebar.get();

	final Color color;
	final BoundedFloat offset = new BoundedFloat(0.0F, 1.0F, 10.0F);

	MenuElement currentOpen;

	public MenuSidebar(final Color color) {
		this.color = color;
		setBackground(ActiveTheme.panel);
		setBgPad(3.0F);
	}

	public MenuSidebarButton addButton(LocalizedMessage name, Drawable drawable, MenuElement element) {
		MenuSidebarButton sidebarButton = new MenuSidebarButton(this, name, drawable, element);
		addObject(sidebarButton);
		return sidebarButton;
	}

	public void select(MenuElement element, boolean sound) {
		if (element != this.currentOpen) {
			if (sound) {
				playSound(ActiveTheme.open);
			}
			set(element);
		} else {
			MenuElementController controller = findController();
			if (controller != null) {
				controller.move(Direction.RIGHT);
			}
		}
	}

	public void set(MenuElement current) {
		if (this.currentOpen != null) {
			getParent().removeObject(currentOpen);
		}
		this.currentOpen = current;
		if (current != null) {
			setFocused(false);
			getParent().addObject(current);
			MenuElementController controller = findController();
			if (controller != null) {
				controller.buildNeighbors();
				controller.move(Direction.RIGHT);
				controller.setSelected(current instanceof MenuElementGroup ? ((MenuElementGroup) current).getDefaultElement() : current);
			}
		}
	}

	@Override
	public float calcX() {
		return super.calcX() - getParent().getAbsoluteX();
	}

	@Override
	public float calcY() {
		return super.calcY() - getParent().getAbsoluteY();
	}

	@Override
	public float getAbsoluteX() {
		return super.getAbsoluteX() + getOffset();
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		super.draw(batch, xOfs + getOffset(), yOfs);
	}

	private float getOffset() {
		return -30.0F * (1.0F - offset.getVal());
	}

	public boolean isTouchingSidebar(float x, float y) {
		return x - 3 <= getAbsoluteX() + getWidth();
	}

	@Override
	public void resize(int width, int height) {
		for (int i = 0; i < getObjects().size(); i++) {
			MenuElement element = getObjects().get(i);
			element.setY((element.getHeight() + 5) * (getObjects().size() - i));
		}
		offset.toLimit(true);
		MenuLayout.layout(this).rightOf(MenuAnchors.LEFT, 0.0F).y(-15.0F);
		calculateSize();
		super.resize(width, height);
	}
}
