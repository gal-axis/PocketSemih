package com.one2b3.endcycle.screens.menus.elements.group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.features.theme.ActiveTheme;
import com.one2b3.endcycle.features.theme.MenuThemeDrawable;
import com.one2b3.endcycle.screens.menus.elements.MenuElement;
import com.one2b3.endcycle.screens.menus.elements.MenuElementController;
import com.one2b3.endcycle.screens.menus.elements.MenuElementShowAnim;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuElementGroup extends MenuElement implements Comparator<MenuElement> {

	final MenuElementShowAnim showAnimation = new MenuElementShowAnim(this);

	MenuThemeDrawable background;
	float bgPad = Float.NaN;
	Color backgroundColor = ActiveTheme.menuColor;

	float offsetX, offsetY;

	List<MenuElement> objects = new ArrayList<>(), countSize = new ArrayList<>();

	MenuElementController controller;
	MenuElement defaultElement;

	public MenuElementGroup() {
		setFocusable(false);
	}

	@Override
	public boolean select() {
		if (getObjects().size() > 0) {
			MenuElementController controller = findController();
			if (controller != null) {
				controller.setSelected(defaultElement == null ? getObjects().get(0) : defaultElement);
			}
		}
		return super.select();
	}

	@Override
	public MenuElementController findController() {
		if (controller != null) {
			return controller;
		}
		return super.findController();
	}

	@Override
	public boolean touch(TouchEvent trigger) {
		return false;
	}

	public void setBackground(MenuThemeDrawable background) {
		this.background = background;
		if (Float.isNaN(bgPad)) {
			bgPad = background.getTop();
		}
	}

	public <T extends MenuElement> T addObject(T object) {
		if (object != null && !objects.contains(object)) {
			object.setParent(this);
			objects.add(object);
			countSize.add(object);
			if (showing) {
				object.init(screen);
				object.resize(Cardinal.getWidth(), Cardinal.getHeight());
				object.show(screen);
			}
		}
		return object;
	}

	public void clearObjects() {
		for (int i = 0; i < objects.size(); i++) {
			MenuElement object = objects.get(i);
			if (object.getParent() == this) {
				object.setParent(null);
			}
		}
		objects.clear();
		countSize.clear();
	}

	public void removeObject(MenuElement object) {
		if (object != null) {
			objects.remove(object);
			countSize.remove(object);
			if (object.getParent() == this) {
				object.setParent(null);
			}
		}
	}

	public void ignoreSize(MenuElement object) {
		countSize.remove(object);
	}

	public void countSize(MenuElement object) {
		if (!countSize.contains(object)) {
			countSize.add(object);
		}
	}

	@Override
	public void calculateSize() {
		offsetX = Float.MAX_VALUE;
		offsetY = Float.MAX_VALUE;
		float maxX = Float.MIN_VALUE, maxY = Float.MIN_VALUE;
		for (int i = 0; i < countSize.size(); i++) {
			countSize.get(i).calculateSize();
		}
		for (int i = 0; i < countSize.size(); i++) {
			MenuElement object = countSize.get(i);
			float objX = object.calcX(), objY = object.calcY();
			if (objX < offsetX) {
				offsetX = objX;
			}
			if (objY < offsetY) {
				offsetY = objY;
			}
			if (objX + object.getWidth() > maxX) {
				maxX = objX + object.getWidth();
			}
			if (objY + object.getHeight() > maxY) {
				maxY = objY + object.getHeight();
			}
		}
		setWidth(maxX - offsetX);
		setHeight(maxY - offsetY);
	}

	@Override
	public void buildNeighbors(MenuElementController controller, Array<MenuElement> elements) {
		if (isFocusable()) {
			super.buildNeighbors(controller, elements);
			Array<MenuElement> e = new Array<>();
			for (int i = 0; i < objects.size(); i++) {
				buildNeighbor(controller, e, objects.get(i));
			}
			controller.buildNeighbors(e);
			return;
		}
		if (!isEnabled()) {
			return;
		}
		for (int i = 0; i < objects.size(); i++) {
			buildNeighbor(controller, elements, objects.get(i));
		}
	}

	public void buildNeighbor(MenuElementController controller, Array<MenuElement> elements, MenuElement object) {
		object.buildNeighbors(controller, elements);
	}

	@Override
	public void init(GameScreen screen) {
		super.init(screen);
		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).init(screen);
		}
	}

	@Override
	public void show(GameScreen screen) {
		super.show(screen);
		showAnimation.start();
		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).show(screen);
		}
	}

	@Override
	public void hide(GameScreen screen) {
		super.hide(screen);
		showAnimation.stop();
		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).hide(screen);
		}
	}

	@Override
	public void update(float delta) {
		showAnimation.update(delta);
		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).update(delta);
		}
	}

	@Override
	public boolean isTouching(float x, float y) {
		if (isAbsoluteHidden() || isFocusable()) {
			return false;
		}
		for (int i = 0; i < objects.size(); i++) {
			MenuElement element = objects.get(i);
			if (!element.isHidden() && element.isTouching(x, y)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public float getAbsoluteX() {
		return super.getAbsoluteX() - offsetX;
	}

	@Override
	public float getAbsoluteY() {
		return super.getAbsoluteY() - offsetY;
	}

	@Override
	public Rectangle getPosition() {
		Rectangle rectangle = super.getPosition();
		rectangle.x += offsetX;
		rectangle.y += offsetY;
		return rectangle;
	}

	@Override
	public void setCursor(Rectangle rectangle) {
		super.setCursor(rectangle);
		rectangle.x += offsetX;
		rectangle.y += offsetY;
	}

	@Override
	public boolean isHidden() {
		return super.isHidden() || !showAnimation.isElementVisible();
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		synchronized (objects) {
			Collections.sort(objects, this);
		}
		float x = calcX() - offsetX + xOfs, y = calcY() - offsetY + yOfs;
		if (background != null && showAnimation.isElementVisible()) {
			showAnimation.begin(batch, xOfs, yOfs);
			drawBackground(batch, x, y);
			showAnimation.end(batch);
		}
		drawElements(batch, x, y);
	}

	public void drawElements(CustomSpriteBatch batch, float x, float y) {
		for (int i = 0; i < objects.size(); i++) {
			MenuElement element = objects.get(i);
			if (!element.isHidden()) {
				if (element.getParent() == this) {
					drawElement(batch, x, y, element);
				} else {
					drawElement(batch, 0.0F, 0.0F, element);
				}
			}
		}
	}

	public void drawElement(CustomSpriteBatch batch, float x, float y, MenuElement element) {
		element.draw(batch, x, y);
	}

	public void drawBackground(CustomSpriteBatch batch, float x, float y) {
		batch.setColor(backgroundColor);
		float padding = getBgPad();
		background.drawNinePatch(batch, x + offsetX - padding, y + offsetY - padding, getWidth() + padding * 2, getHeight() + padding * 2);
	}

	@Override
	public void resize(int width, int height) {
		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).resize(width, height);
		}
	}

	@Override
	public void dispose() {
		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).dispose();
		}
	}

	@Override
	public int compare(MenuElement o1, MenuElement o2) {
		return Integer.compare(o1.getLayer(), o2.getLayer());
	}
}
