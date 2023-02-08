package com.one2b3.endcycle.screens.menus.elements;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.engine.screens.GameScreen;

public class MenuContainer<T extends MenuElement> extends MenuElement {

	T element;

	public void set(T element) {
		if (this.element != null) {
			this.element.actionListeners.remove(this);
			if (showing) {
				this.element.hide(screen);
				this.element.dispose();
			}
		}
		this.element = element;
		if (element != null && showing) {
			element.actionListeners.add(this);
			element.init(screen);
			element.show(screen);
		}
	}

	public T get() {
		return element;
	}

	@Override
	public void show(GameScreen screen) {
		super.show(screen);
		if (element != null) {
			element.show(screen);
		}
	}

	@Override
	public void init(GameScreen screen) {
		super.init(screen);
		if (element != null) {
			element.init(screen);
		}
	}

	@Override
	public void hide(GameScreen screen) {
		super.hide(screen);
		if (element != null) {
			element.hide(screen);
		}
	}

	@Override
	public void update(float delta) {
		if (element != null) {
			element.update(delta);
		}
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		if (element != null) {
			element.draw(batch, xOfs + getX(), yOfs + getY());
		}
	}

	@Override
	public boolean isFocusable() {
		return super.isFocusable() && element != null && element.isFocusable();
	}

	@Override
	public boolean isEnabled() {
		return super.isEnabled() && element != null && element.isEnabled();
	}

	@Override
	public boolean isTouching(float x, float y) {
		return element != null && element.isTouching(x, y);
	}

	@Override
	public boolean touch(TouchEvent trigger) {
		return element != null && element.touch(trigger);
	}

	@Override
	public boolean canDrawCursor() {
		return element != null && element.canDrawCursor();
	}

	@Override
	public void buildNeighbors(MenuElementController controller, Array<MenuElement> elements) {
		super.buildNeighbors(controller, elements);
		if (element != null) {
			element.buildNeighbors(null, elements);
		}
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		if (element != null) {
			element.resize(width, height);
		}
	}

	@Override
	public void setCursor(Rectangle rectangle) {
		if (element != null) {
			element.setCursor(rectangle);
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		if (element != null) {
			element.dispose();
		}
	}

	@Override
	public boolean select() {
		return element != null && element.select();
	}

}
