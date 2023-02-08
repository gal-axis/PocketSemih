package com.one2b3.endcycle.screens.menus.elements.scroller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.graphics.Drawable;
import com.one2b3.endcycle.engine.graphics.Drawables;
import com.one2b3.endcycle.engine.input.InputListener;
import com.one2b3.endcycle.engine.input.events.ButtonEvent;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.features.theme.ActiveTheme;
import com.one2b3.endcycle.screens.menus.elements.scroller.Dragger.DragListener;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class Scroller implements InputListener, DragListener {

	static final float SCROLL_SPEED = 15.0F;

	final Drawable scrollerX = Drawables.Scroller_X.get();
	final Drawable scrollerY = Drawables.Scroller_Y.get();

	final Scrollable scrollable;
	final Dragger dragger = new Dragger(this);
	Vector2 oldScroll;

	boolean scrollLockX, scrollLockY;

	@Getter
	boolean scrollingX, scrollingY;

	@Getter
	float scrollX, scrollY;

	public void setScrollX(float scrollX) {
		float limit = (scrollable.canScrollX() ? scrollable.getScrollWidth() : 0.0F);
		this.scrollX = MathUtils.clamp(scrollX, 0.0F, limit);
		scrollLockX = (this.scrollX == limit);
	}

	public void setScrollY(float scrollY) {
		float limit = (scrollable.canScrollY() ? scrollable.getScrollHeight() : 0.0F);
		this.scrollY = MathUtils.clamp(scrollY, 0.0F, limit);
		scrollLockY = (this.scrollY == limit);
	}

	public void updateScroll(boolean clamp) {
		setScrollX(scrollLockX && !clamp ? scrollable.getScrollWidth() : getScrollX());
		setScrollY(scrollLockY && !clamp ? scrollable.getScrollHeight() : getScrollY());
	}

	public void moveScroll(float offset) {
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
			setScrollX(getScrollX() + offset);
		} else {
			setScrollY(getScrollY() + offset);
		}
	}

	public void moveScroll(float x, float y) {
		if (scrollingY) {
			float scrollerHeight = scrollerX.getHeight() * 0.5F;
			y -= scrollable.getScrollAreaY() + scrollerHeight + scrollerY.getHeight() * 0.5F;
			float areaHeight = scrollable.getScrollAreaHeight() - scrollerHeight - scrollerY.getHeight();
			setScrollY((1.0F - y / areaHeight) * scrollable.getScrollHeight());
		}
		if (scrollingX) {
			float scrollerWidth = scrollerY.getWidth() * 0.5F;
			x -= scrollable.getScrollAreaX() + scrollerWidth + scrollerX.getWidth() * 0.5F;
			float areaWidth = scrollable.getScrollAreaWidth() - scrollerWidth - scrollerX.getWidth();
			setScrollX((x / areaWidth) * scrollable.getScrollWidth());
		}
	}

	public boolean touch(TouchEvent trigger, boolean touching) {
		if (trigger.isScroll()) {
			moveScroll(SCROLL_SPEED * trigger.positionY);
			return true;
		} else {
			boolean atBorderX = atBorderX(trigger.positionX);
			boolean atBorderY = atBorderY(trigger.positionY);
			if (trigger.isPressed() && trigger.isLeftMouse()) {
				if (atBorderX || atBorderY) {
					scroll(atBorderY, atBorderX);
					moveScroll(trigger.positionX, trigger.positionY);
				} else if (touching) {
					dragger.start(trigger);
					oldScroll = new Vector2(scrollX, scrollY);
					Cardinal.getInput().addPermanentListener(this);
				}
			}
			return atBorderX || atBorderY;
		}
	}

	@Override
	public void drag(float x, float y) {
		setScrollX(oldScroll.x - x);
		setScrollY(oldScroll.y + y);
	}

	public boolean isDragging() {
		return dragger.dragging;
	}

	public boolean isClickScrolled() {
		return dragger.clickScrolled;
	}

	private boolean atBorderX(float x) {
		x -= scrollable.getScrollAreaX();
		float width = scrollable.getScrollAreaWidth();
		float scrollerWidth = scrollerY.getWidth() * 0.5F;
		return (scrollable.canScrollY() && x > width - scrollerWidth && x < width + scrollerWidth);
	}

	private boolean atBorderY(float y) {
		y -= scrollable.getScrollAreaY();
		float scrollerHeight = scrollerX.getHeight() * 0.5F;
		return (scrollable.canScrollX() && y > -scrollerHeight && y < scrollerHeight);
	}

	private boolean scroll(boolean x, boolean y) {
		scrollingX = x;
		scrollingY = y;
		Cardinal.getInput().addPermanentListener(this);
		return true;
	}

	public boolean isTouching(float x, float y) {
		return atBorderX(x) || atBorderY(y);
	}

	public void draw(CustomSpriteBatch batch, float x, float y) {
		float scrollHeight = scrollable.getScrollHeight();
		float width = scrollable.getScrollAreaWidth();
		float height = scrollable.getScrollAreaHeight();
		if (scrollable.canScrollY()) {
			float scrollerHeight = scrollerX.getHeight() * 0.5F;
			float scrollerPosY = (height - scrollerY.getHeight() - scrollerHeight) * (1.0F - scrollY / scrollHeight);
			Painter.on(batch).at(x + width, y + scrollerHeight + scrollerPosY)
					.color(scrollingY ? ActiveTheme.selectColor : ActiveTheme.defaultColor).hAlign(0).paint(scrollerY);
		}
		float scrollWidth = scrollable.getScrollWidth();
		if (scrollable.canScrollX()) {
			float scrollerWidth = scrollerY.getWidth() * 0.5F;
			float scrollerPosX = (width - scrollerX.getWidth() - scrollerWidth) * (scrollX / scrollWidth);
			Painter.on(batch).at(x + scrollerPosX, y)
					.color(scrollingX ? ActiveTheme.selectColor : ActiveTheme.defaultColor).vAlign(0).paint(scrollerX);
		}
	}

	@Override
	public boolean triggerButton(ButtonEvent event) {
		return scrollingX || scrollingY;
	}

	@Override
	public boolean triggerTouch(TouchEvent event) {
		if (event.isReleased()) {
			boolean scrolled = scrollingX || scrollingY;
			boolean stop = dragger.stop(event);
			stop();
			if (stop) {
				return dragger.clickScrolled;
			} else {
				return scrolled;
			}
		} else if (event.isMoved()) {
			if (dragger.move(event)) {
				return true;
			} else {
				moveScroll(event.positionX, event.positionY);
			}
		}
		return scrollingX || scrollingY;
	}

	public void stop() {
		scrollingX = false;
		scrollingY = false;
		Cardinal.getInput().removePermanentListener(this);
		dragger.stop();
	}
}
