package com.one2b3.endcycle.screens.menus.elements.listdisplay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.features.theme.ActiveTheme;
import com.one2b3.endcycle.features.theme.MenuThemeDrawable;
import com.one2b3.endcycle.screens.menus.elements.MenuElement;
import com.one2b3.endcycle.screens.menus.elements.MenuElementShowAnim;
import com.one2b3.endcycle.screens.menus.elements.scroller.Scrollable;
import com.one2b3.endcycle.screens.menus.elements.scroller.Scroller;
import com.one2b3.utils.CColor;
import com.one2b3.utils.java.ObjectSupplier;
import com.one2b3.utils.java.Supplier;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Getter
@Setter
public class MenuListDisplay<T> extends MenuElement implements Scrollable {

	final MenuThemeDrawable pack = ActiveTheme.containerTitle;
	final MenuThemeDrawable message = ActiveTheme.container;
	final MenuElementShowAnim showAnimation = new MenuElementShowAnim(this);
	final Painter parameters = new Painter();

	CColor backgroundColor = ActiveTheme.menuColor;

	@NonNull
	Supplier<String> listName;

	@NonNull
	List<T> objects;

	@NonNull
	MenuListPositionSetter<T> positionSetter;

	List<MenuListObjectPosition<T>> objectPositions = new ArrayList<>();

	final Rectangle drawRectangle = new Rectangle();

	MenuObjectPainter<T> painter;

	@Accessors(chain = true)
	Color nameColor = ActiveTheme.titleColor;

	final Scroller scroller = new Scroller(this);

	@Accessors(chain = true)
	// TODO Set these according to the drawable
	int paddingX = 3, paddingY = 3, elementOffsetX = 3, elementOffsetY = 3;

	public MenuListDisplay() {
		this(Collections.emptyList(), null);
	}

	public MenuListDisplay(String name, List<T> objects, MenuObjectPainter<T> painter) {
		this(new ObjectSupplier<>(name), objects, painter);
	}

	public MenuListDisplay(List<T> objects, MenuObjectPainter<T> painter) {
		this(new ObjectSupplier<>(null), objects, painter);
	}

	public MenuListDisplay(Supplier<String> name, List<T> objects, MenuObjectPainter<T> painter) {
		this.listName = name;
		this.objects = objects;
		this.painter = painter;
	}

	public MenuListDisplay<T> setName(String name) {
		return setListName(new ObjectSupplier<>(name));
	}

	public MenuListDisplay<T> setListName(Supplier<String> name) {
		this.listName = name;
		return this;
	}

	@Override
	public boolean touch(TouchEvent trigger) {
		return scroller.touch(trigger, isTouching(trigger.positionX, trigger.positionY));
	}

	@Override
	public float getScrollAreaX() {
		return getAbsoluteX();
	}

	@Override
	public float getScrollAreaWidth() {
		return getWidth();
	}

	@Override
	public float getScrollWidth() {
		return Math.max(0.0F, drawRectangle.getWidth() - getScrollAreaWidth());
	}

	@Override
	public float getScrollAreaHeight() {
		return getHeight() - getNameOffset();
	}

	@Override
	public float getScrollAreaY() {
		return getAbsoluteY();
	}

	@Override
	public float getScrollHeight() {
		return Math.max(0.0F, drawRectangle.getHeight() - getHeight() + getPaddingY() * 2.0F);
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
	public void resize(int width, int height) {
		super.resize(width, height);
		refreshPositions(true);
	}

	@Override
	public boolean isHidden() {
		return super.isHidden() || !showAnimation.isElementVisible();
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		showAnimation.begin(batch, xOfs, yOfs);
		float x = calcX() + xOfs;
		float y = calcY() + yOfs;
		refreshPositions(false);
		drawContainer(batch, x, y);
		scroller.draw(batch, x, y);
		batch.startMasking(x + getPaddingX(), y + getPaddingY(), getWidth() - getPaddingX() * 2.0F, getHeight() - getPaddingY() * 2.0F);
		drawObjects(batch, x + scroller.getScrollX(), y + scroller.getScrollY());
		batch.stopMasking();
		showAnimation.end(batch);
	}

	public void drawContainer(CustomSpriteBatch batch, float x, float y) {
		batch.setColor(backgroundColor);
		if (hasName()) {
			pack.drawNinePatch(batch, x, y, getWidth(), getHeight());
			Painter.on(batch).x(x + getWidth() * 0.5F).y(y + getHeight() - 8.5F).align(0).font(GameFonts.TextBorder).color(nameColor)
					.paint(listName.get());
		} else {
			message.drawNinePatch(batch, x, y, getWidth(), getHeight());
		}
	}

	public void refreshPositions(boolean force) {
		if (objects.size() != objectPositions.size() || force) {
			updatePositions();
		}
	}

	public void updatePositions() {
		scroller.setScrollX(scroller.getScrollX());
		scroller.setScrollY(scroller.getScrollY());
		objectPositions.clear();
		if (positionSetter != null) {
			drawRectangle.set(Float.MAX_VALUE, Float.MAX_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);
			float x = getPaddingX(), y = getPaddingY();
			Vector2 position = positionSetter.start(this);
			x += position.x;
			y += position.y;
			for (T object : objects) {
				MenuListObjectPosition<T> objectPosition = new MenuListObjectPosition<>(object);
				objectPosition.x = x;
				objectPosition.y = y;
				positionSetter.setPosition(this, objectPosition);
				x = objectPosition.x;
				y = objectPosition.y;
				// Update the rectangle in which we draw:
				drawRectangle.setX(Math.min(drawRectangle.getX(), x));
				drawRectangle.setY(Math.min(drawRectangle.getY(), y));
				drawRectangle.setWidth(Math.max(drawRectangle.getWidth(), x + objectPosition.width));
				drawRectangle.setHeight(Math.max(drawRectangle.getHeight(), y + objectPosition.height));
				objectPositions.add(objectPosition);
			}
			positionSetter.end(this);
			drawRectangle.setWidth(drawRectangle.getWidth() - drawRectangle.getX());
			drawRectangle.setHeight(drawRectangle.getHeight() - drawRectangle.getY());
		}
		scroller.updateScroll(false);
	}

	public void drawObjects(CustomSpriteBatch batch, float x, float y) {
		painter.start();
		for (MenuListObjectPosition<T> objectPosition : objectPositions) {
			parameters.reset(batch);
			parameters.x(x + objectPosition.x).y(y + objectPosition.y);
			parameters.width(objectPosition.width).height(objectPosition.height);
			painter.paint(objectPosition.object, parameters);
		}
		painter.end();
	}

	public boolean hasName() {
		return listName != null && listName.get() != null;
	}

	public float getNameOffset() {
		return (hasName() ? 15 : 0);
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean remove() {
		return false;
	}
}
