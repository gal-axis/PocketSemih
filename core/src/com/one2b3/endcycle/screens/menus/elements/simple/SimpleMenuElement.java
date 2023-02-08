package com.one2b3.endcycle.screens.menus.elements.simple;

import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.screens.menus.elements.MenuElement;
import com.one2b3.endcycle.screens.menus.elements.MenuElementController;
import com.one2b3.endcycle.screens.menus.elements.MenuElementShowAnim;

public abstract class SimpleMenuElement extends MenuElement {

	public final MenuElementShowAnim showAnimation = new MenuElementShowAnim(this);

	public boolean selectable;

	public SimpleMenuElement() {
	}

	@Override
	public void buildNeighbors(MenuElementController controller, Array<MenuElement> elements) {
		if (selectable) {
			super.buildNeighbors(controller, elements);
		}
	}

	@Override
	public MenuElement setFocused(boolean focused) {
		showAnimation.focus(focused);
		return super.setFocused(focused);
	}

	@Override
	public boolean isTouching(float x, float y) {
		return selectable && super.isTouching(x, y);
	}

	@Override
	public void show(GameScreen screen) {
		super.show(screen);
		showAnimation.start();
	}

	@Override
	public void hide(GameScreen screen) {
		super.hide(screen);
		showAnimation.stop();
	}

	@Override
	public void update(float delta) {
		showAnimation.update(delta);
	}

	@Override
	public boolean isHidden() {
		return super.isHidden() || !showAnimation.isElementVisible();
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		showAnimation.begin(batch, xOfs, yOfs);
		drawElement(batch, xOfs, yOfs);
		showAnimation.end(batch);
	}

	public abstract void drawElement(CustomSpriteBatch batch, float xOfs, float yOfs);
}
