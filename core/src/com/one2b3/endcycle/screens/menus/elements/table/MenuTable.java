package com.one2b3.endcycle.screens.menus.elements.table;

import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.GameFont;
import com.one2b3.endcycle.engine.input.InputListener;
import com.one2b3.endcycle.engine.input.InputRepeater;
import com.one2b3.endcycle.engine.input.KeyCode;
import com.one2b3.endcycle.engine.input.events.ButtonEvent;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.engine.language.LocalizedMessage;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.features.theme.ActiveTheme;
import com.one2b3.endcycle.features.theme.MenuThemeDrawable;
import com.one2b3.endcycle.screens.menus.Colors;
import com.one2b3.endcycle.screens.menus.elements.MenuElement;
import com.one2b3.endcycle.screens.menus.elements.MenuElementShowAnim;
import com.one2b3.endcycle.screens.menus.elements.buttons.MenuButtonClickedAnimation;
import com.one2b3.endcycle.screens.menus.elements.scroller.Scrollable;
import com.one2b3.endcycle.screens.menus.elements.scroller.Scroller;
import com.one2b3.endcycle.screens.menus.elements.table.drag.MenuTableDragListener;
import com.one2b3.endcycle.screens.menus.elements.table.drag.MenuTableItemDrag;
import com.one2b3.endcycle.screens.menus.elements.table.painter.MenuTablePaintParams;
import com.one2b3.endcycle.screens.menus.elements.table.painter.MenuTablePainter;
import com.one2b3.endcycle.utils.Modulator;
import com.one2b3.endcycle.utils.NumDisplay;
import com.one2b3.endcycle.utils.bounded.BoundedFloat;
import com.one2b3.utils.ArrayWrapperList;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Getter
@Setter
public class MenuTable<T> extends MenuElement implements InputListener, Scrollable {

	final BoundedFloat highlightFade = new BoundedFloat(0.0F, 1.0F, 6.0F);
	final MenuElementShowAnim showAnimation = new MenuElementShowAnim(this);
	final MenuButtonClickedAnimation selectAnimation = new MenuButtonClickedAnimation(),
			specialAnimation = new MenuButtonClickedAnimation();
	final MenuTablePaintParams<T> paintParameters = new MenuTablePaintParams<>(this);

	MenuThemeDrawable containerTitle = ActiveTheme.containerTitle;
	MenuThemeDrawable container = ActiveTheme.container;

	final Scroller scroller = new Scroller(this);
	final MenuTableItemDrag<T> drag = new MenuTableItemDrag<>(this);
	final NumDisplay visualScrollX = new NumDisplay(300.0F), visualScrollY = new NumDisplay(300.0F);
	final InputRepeater inputRepeater = new InputRepeater(this, KeyCode.MENU_UP, KeyCode.MENU_DOWN, KeyCode.MENU_LEFT,
			KeyCode.MENU_RIGHT);

	MenuTableTitleData titleData = ActiveTheme.titleData;
	String name;

	boolean selectEnabled = true, specialEnabled = true;
	LocalizedMessage selectString, specialString;
	LocalizedMessage selectButtonTip, headerButtonTip, specialButtonTip;
	float touchButtonSize = 20.0F;
	boolean touchButtonOutside;

	@NonNull
	List<T> objects;

	MenuTablePainter<T> objectPainter = new DefaultMenuTablePainter<>();

	@Accessors(chain = true)
	MenuTableAction<T> action;

	boolean overviewSelectable = true, seamlessX = true, seamlessY = true;

	Color backgroundColor = ActiveTheme.menuColor, disabledColor = ActiveTheme.disabledColor;
	GameFont nameFont;
	@Accessors(chain = true)
	Color nameColor = null;

	int selection = -1;
	int lastSelection = 0;

	float padLeft = container.getLeft(), padRight = container.getRight(), padTop = container.getTop(),
			padBottom = container.getBottom();
	@Accessors(chain = true)
	float elementWidth, elementHeight, elementOffsetX = 3, elementOffsetY = 3;

	boolean itemDrag, onlyDrag;

	boolean highlight, highlightExclude;
	int highlightIndex = -1;

	public MenuTable() {
		this.objects = Collections.emptyList();
	}

	public MenuTable(String name) {
		this();
		this.name = name;
	}

	public MenuTable(String name, List<T> objects) {
		this.name = name;
		this.objects = objects;
	}

	public MenuTable(List<T> objects, MenuTablePainter<T> drawFunction) {
		this.objectPainter = drawFunction;
		this.objects = objects;
	}

	public MenuTable(String name, List<T> objects, MenuTablePainter<T> drawFunction) {
		this.name = name;
		this.objectPainter = drawFunction;
		this.objects = objects;
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
	public void resize(int width, int height) {
		super.resize(width, height);
		if (isSelected() && getColumns() != 0) {
			scrollTo(getSelection());
			scroller.updateScroll(true);
			updateScroll();
		}
	}

	public void setObjects(Array<T> array) {
		setObjects(new ArrayWrapperList<>(array));
	}

	public void setObjects(List<T> list) {
		this.objects = list;
	}

	public void setDragListener(MenuTableDragListener<T> dragListener) {
		drag.setDragListener(dragListener);
	}

	public void highlight(int index, boolean exclude) {
		this.highlightIndex = index;
		this.highlightExclude = exclude;
		highlight = true;
	}

	public void stopHighlight() {
		highlight = false;
	}

	public MenuTable<T> setElementSize(float size) {
		return setElementWidth(size).setElementHeight(size);
	}

	public int getColumns() {
		return Math.max(1,
				(int) ((getScrollAreaWidth() - padLeft - padRight + elementOffsetX) / (elementWidth + elementOffsetX)));
	}

	public MenuTable<T> setFromAmount(int sizeX, int sizeY) {
		if (sizeX != 0) {
			setWidth(sizeX * (elementWidth + elementOffsetX) - elementOffsetX + padLeft + padRight);
		}
		if (sizeY != 0) {
			setHeight(sizeY * (elementHeight + elementOffsetY) - elementOffsetY + padBottom + padTop + getNameOffset());
		}
		return this;
	}

	public MenuTable<T> setFromSize(float width, float height, boolean directWidth, boolean directHeight) {
		if (directWidth) {
			setWidth(width);
		} else {
			int sizeX = (int) ((width - padLeft - padRight + elementOffsetX) / (elementWidth + elementOffsetX));
			sizeX = Math.max(1, sizeX);
			setWidth(sizeX * elementWidth + (sizeX - 1) * elementOffsetX + padLeft + padRight);
		}
		if (directHeight) {
			setHeight(height);
		} else {
			float offset = getNameOffset();
			int sizeY = (int) ((height - padBottom - padTop + elementOffsetY - offset)
					/ (elementHeight + elementOffsetY));
			sizeY = Math.max(1, sizeY);
			setHeight(sizeY * elementHeight + (sizeY - 1) * elementOffsetY + padBottom + padTop + offset);
		}
		return this;
	}

	public void setSelection(T object) {
		setSelection(objects.indexOf(object));
	}

	public void clampSelection() {
		setSelection(MathUtils.clamp(selection, -1, getObjectAmount() - 1));
	}

	public void setSelection(int selection) {
		setSelection(selection, selection != -1);
	}

	public void setSelection(int selection, boolean updateScroll) {
		this.selection = selection;
		if (selection == -1) {
			inputRepeater.stop();
		} else {
			if (updateScroll) {
				scrollTo(selection);
			}
		}
		if (action != null) {
			action.updateTableSelection(selection, getSelected());
		}
	}

	public T getMobileSelected() {
		if (Cardinal.isPortable()) {
			if (isSelected()) {
				return getSelected();
			} else {
				return (getLastSelection() < 0 || getLastSelection() >= objects.size()) ? null
						: objects.get(getLastSelection());
			}
		}
		return getSelected();
	}

	public int getMobileSelection() {
		if (Cardinal.isPortable()) {
			return isSelected() ? getSelection() : getLastSelection();
		}
		return getSelection();
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
	public float getScrollAreaY() {
		return getAbsoluteY() + calcTouchButtonSize();
	}

	@Override
	public float getScrollAreaHeight() {
		return getHeight() - getNameOffset() - calcTouchButtonSize();
	}

	@Override
	public float getScrollWidth() {
		return getTotalElementWidth() + padLeft + padRight - getWidth();
	}

	public float getTotalElementWidth() {
		int cols = Math.min(getObjectAmount(), getColumns());
		return cols * (elementWidth + elementOffsetX) - elementOffsetX;
	}

	@Override
	public float getScrollHeight() {
		float touchButton = calcTouchButtonSize();
		float height = getHeight() - getNameOffset() - touchButton;
		return getTotalElementHeight() + padBottom + padTop - height;
	}

	public float getTotalElementHeight() {
		int rows = MathUtils.ceil(getObjectAmount() / (float) getColumns());
		return rows * (elementHeight + elementOffsetY) - elementOffsetY;
	}

	public void scrollTo(int selection) {
		int sizeX = getColumns();
		if (getWidth() > 0.0F) {
			float elementDistanceX = getElementWidth() + getElementOffsetX();
			int col = selection % sizeX;
			float newScrollX = elementDistanceX * col;
			if (newScrollX < scroller.getScrollX()) {
				scroller.setScrollX(newScrollX - elementDistanceX);
			} else if (newScrollX + elementDistanceX > scroller.getScrollX() + getScrollAreaWidth()) {
				scroller.setScrollX(newScrollX - getScrollAreaWidth() + elementDistanceX + padLeft + padRight);
			}
		}
		if (getHeight() > 0.0F) {
			float elementDistanceY = getElementHeight() + getElementOffsetY();
			int row = selection / sizeX;
			float newScrollY = elementDistanceY * row;
			if (newScrollY < scroller.getScrollY()) {
				scroller.setScrollY(newScrollY);
			} else if (newScrollY + elementDistanceY + padBottom + padTop > scroller.getScrollY()
					+ getScrollAreaHeight()) {
				scroller.setScrollY(newScrollY - getScrollAreaHeight() + elementDistanceY + padBottom + 1);
			}
		}
	}

	public void updateScroll() {
		visualScrollX.real = scroller.getScrollX();
		visualScrollX.sync();
		visualScrollY.real = scroller.getScrollY();
		visualScrollY.sync();
	}

	public T getSelected() {
		if (selection >= 0 && selection < getObjectAmount()) {
			return objects.get(selection);
		}
		return null;
	}

	@Override
	public boolean touch(TouchEvent trigger) {
		if (!isEnabled()) {
			return false;
		}
		boolean touching = isTouching(trigger.positionX, trigger.positionY);
		if (scroller.touch(trigger, touching) && selection != -1) {
			drag.stop(trigger.pointer);
			return true;
		} else if (touching) {
			int selection = getItemAt(trigger.positionX, trigger.positionY);
			if (selection >= 0 || (selection == -1 && isOverviewSelectable())) {
				if (getSelection() != -1) {
					setLastSelection(getSelection());
				}
				if (getSelection() != selection) {
					drag.stop(trigger.pointer);
					if (selection >= 0 && selection < getObjectAmount()) {
						screen.input.add(this);
					} else {
						screen.input.remove(this);
					}
					setSelection(selection);
				}
				if (trigger.isPressed()) {
					if (itemDrag && trigger.isLeftMouse()) {
						drag.start(selection, getSelected(), trigger.pointer, trigger.positionX, trigger.positionY);
					}
				}
			}
			if (trigger.isReleased()) {
				drag.stop(trigger.pointer);
				if (trigger.positionY > getAbsoluteY() + getHeight() - getNameOffset()) {
					selectHeader();
				} else if (getSelection() != -1 && selection == getSelection()) {
					if ((!itemDrag || !onlyDrag) && trigger.isLeftMouse()) {
						clicked(trigger.positionX, trigger.positionY);
					} else if (trigger.isRightMouse()) {
						if (specialEnabled) {
							specialSelect();
						} else {
							select();
						}
					}
				} else if (selection == -2) {
					select();
					selectAnimation.start();
				} else if (selection == -3) {
					specialSelect();
					specialAnimation.start();
				}
			}
			return true;
		} else {
			drag.stop(trigger.pointer);
		}
		return false;
	}

	@Override
	public boolean isTouching(float x, float y) {
		if (scroller.isTouching(x, y)) {
			return true;
		}
		float absY = getAbsoluteY(), height = getHeight();
		if (touchButtonOutside) {
			absY -= touchButtonSize;
			height += touchButtonSize;
		}
		return x >= getAbsoluteX() && y >= absY && x <= getAbsoluteX() + getWidth() && y <= absY + height;
	}

	public boolean moveX(int x) {
		int newSelection = getMovedX(getSelection(), x);
		if (newSelection != -1) {
			moveSelection(newSelection);
		} else if (!isOverviewSelectable()) {
			return !isSeamlessX() && getObjectAmount() != 0;
		}
		return true;
	}

	public int getMovedX(int selection, int offset) {
		int row = selection / getColumns() * getColumns();
		selection += offset;
		if (selection >= row && selection <= Math.min(getObjectAmount() - 1, row + getColumns() - 1)) {
			return selection;
		}
		return -1;
	}

	public boolean moveY(int y) {
		int newSelection = getMovedY(getSelection(), y);
		if (newSelection != -1) {
			moveSelection(newSelection);
		} else if (!isOverviewSelectable()) {
			return !isSeamlessY() && getObjectAmount() != 0;
		}
		return true;
	}

	private void moveSelection(int newSelection) {
		setSelection(newSelection);
		playSound(ActiveTheme.navigate);
		if (action != null) {
			action.moveTableSelection(newSelection, getSelected());
		}
	}

	public int getMovedY(int selection, int offset) {
		if (getObjectAmount() < getColumns()) {
			return -1;
		}
		selection += offset * getColumns();
		int sizeY = MathUtils.ceil((float) getObjectAmount() / getColumns());
		if (selection >= 0 && selection / getColumns() < sizeY) {
			return Math.min(selection, getObjectAmount() - 1);
		}
		return -1;
	}

	@Override
	public MenuElement setFocused(boolean focused) {
		if (focused) {
			if (!overviewSelectable) {
				if (selection == -1) {
					setSelection(MathUtils.clamp(getLastSelection(), 0, objects.size() - 1));
				}
			}
			if (showing) {
				screen.input.add(this);
			}
		} else {
			deselect();
			screen.input.remove(this);
		}
		return super.setFocused(focused);
	}

	public boolean deselect() {
		if (getSelection() != -1) {
			setLastSelection(getSelection());
			setSelection(-1);
			return true;
		}
		return false;
	}

	public int getItemAt(float x, float y) {
		x -= this.getAbsoluteX() - scroller.getScrollX() + padLeft;
		y -= this.getAbsoluteY();
		float touchY = (touchButtonOutside ? -touchButtonSize : 0);
		if (isTouchButton() && y >= touchY && y < touchY + touchButtonSize) {
			return (specialString == null || x + padLeft < getWidth() * 0.5F ? -2 : -3);
		}
		float realHeight = getHeight() - getNameOffset();
		y = realHeight + scroller.getScrollY() - y - padBottom;
		float elementDistanceX = getElementWidth() + getElementOffsetX();
		float elementDistanceY = getElementHeight() + getElementOffsetY();
		int xPos = (int) (x / elementDistanceX);
		int yPos = (int) (y / elementDistanceY);
		int sizeX = getColumns();
		int rows = MathUtils.ceil(getObjectAmount() / (float) sizeX);
		int index = xPos + yPos * sizeX;
		if (index < 0 || index >= getObjects().size()) {
			return -1;
		} else if (x >= scroller.getScrollX() && x <= scroller.getScrollX() + getWidth() - getTotalPaddingX()
				&& xPos < sizeX && y >= scroller.getScrollY()
				&& y <= scroller.getScrollY() + realHeight - padBottom - padTop && yPos < rows) {
			return index;
		} else {
			return -1;
		}
	}

	public MenuTable<T> setPaddingX(float paddingX) {
		padLeft = padRight = paddingX;
		return this;
	}

	public float getTotalPaddingX() {
		return padLeft + padRight;
	}

	public MenuTable<T> setPaddingY(float paddingY) {
		padBottom = padTop = paddingY;
		return this;
	}

	public float getTotalPaddingY() {
		return padBottom + padTop;
	}

	@Override
	public boolean select() {
		if (objects.size() == 0) {
			return false;
		}
		if (isSelected()) {
			select(getSelection(), getSelected());
		} else {
			screen.input.add(this);
			playSound(ActiveTheme.select);
			setSelection(MathUtils.clamp(getLastSelection(), 0, getObjectAmount() - 1));
			if (action != null) {
				action.moveTableSelection(getSelection(), getSelected());
			}
		}
		return true;
	}

	public void select(int selection, T selected) {
		inputRepeater.stop();
		if (action != null) {
			scrollTo(selection);
			action.selectTable(selection, selected);
			playSound(ActiveTheme.select);
		}
	}

	public boolean specialSelect() {
		if (isSelected()) {
			inputRepeater.stop();
			if (action != null) {
				scrollTo(getSelection());
				action.specialTableSelect(getSelection(), getSelected());
				playSound(ActiveTheme.select);
			}
			return true;
		}
		return false;
	}

	public void clicked(float x, float y) {
		inputRepeater.stop();
		if (action != null) {
			scrollTo(selection);
			action.clickedTable(selection, getSelected(), x, y);
			playSound(ActiveTheme.select);
		}
	}

	public boolean selectHeader() {
		return action != null && action.selectTableHeader();
	}

	public boolean isSelected() {
		return getSelection() >= 0 && getSelection() < getObjectAmount();
	}

	@Override
	public void update(float delta) {
		if (scroller.isClickScrolled()) {
			drag.stop();
		}
		drag.update(delta);
		if (drag.getDragging() != null) {
			scroller.stop();
		}

		highlightFade.move(highlight, delta);
		showAnimation.update(delta);
		selectAnimation.update(delta);
		specialAnimation.update(delta);
		if (isSelected()) {
			inputRepeater.update(delta);
		}
		scroller.updateScroll(true);
		visualScrollX.real = scroller.getScrollX();
		if (scroller.isScrollingX() || scroller.isDragging()) {
			visualScrollX.sync();
		} else {
			visualScrollX.speed = 6.0F * Math.max(50.0F, Math.abs(visualScrollX.getDelta()));
			visualScrollX.update(delta);
		}
		visualScrollY.real = scroller.getScrollY();
		if (scroller.isScrollingY() || scroller.isDragging()) {
			visualScrollY.sync();
		} else {
			visualScrollY.speed = 6.0F * Math.max(50.0F, Math.abs(visualScrollY.getDelta()));
			visualScrollY.update(delta);
		}
	}

	@Override
	public boolean isHidden() {
		return super.isHidden() || !showAnimation.isElementVisible();
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		showAnimation.begin(batch, xOfs, yOfs);
		drawElements(batch, xOfs, yOfs);
		showAnimation.end(batch);
	}

	public void drawElements(CustomSpriteBatch batch, float xOfs, float yOfs) {
		float x = calcX() + xOfs;
		float y = calcY() + yOfs;
		float h = getHeight();
		if (isTouchButton()) {
			if (touchButtonOutside) {
				y -= touchButtonSize;
			} else {
				h -= touchButtonSize;
			}
			float w = (specialString == null ? getWidth() : getWidth() * 0.5F);
			drawTouchButton(batch, x, y, selectString, selectAnimation, w, selectEnabled);
			if (specialString != null) {
				drawTouchButton(batch, x + w, y, specialString, specialAnimation, w, specialEnabled);
			}
			y += touchButtonSize;
		}
		drawContainer(batch, x, y, h);
		drawObjects(batch, x, y, getWidth(), h);
		scroller.draw(batch, x, y);
	}

	public void drawTouchButton(CustomSpriteBatch batch, float x, float y, LocalizedMessage str,
			MenuButtonClickedAnimation anim, float w, boolean enabled) {
		batch.setColor(enabled ? ActiveTheme.defaultColor : ActiveTheme.disabledColor);
		float ht = anim.getHeight(touchButtonSize);
		ActiveTheme.button.drawNinePatch(batch, x, y, w, ht);
		Painter.on(batch).at(x + w * 0.5F, y + ht * 0.5F).align(0).paint(str);
	}

	public boolean isTouchButton() {
		return Cardinal.isPortable() && (screen == null || screen.input.lastTouch) && selectString != null;
	}

	public float calcTouchButtonSize() {
		return isTouchButton() && !touchButtonOutside ? touchButtonSize : 0.0F;
	}

	public void drawContainer(CustomSpriteBatch batch, float x, float y, float h) {
		if (backgroundColor != null) {
			batch.setColor(isEnabled() ? backgroundColor : disabledColor);
			if (hasName()) {
				containerTitle.drawNinePatch(batch, x, y, getWidth(), h);
				drawName(batch, x, y, h);
			} else {
				container.drawNinePatch(batch, x, y, getWidth(), h);
			}
		}
	}

	public void drawName(CustomSpriteBatch batch, float x, float y, float h) {
		titleData.getPainter(batch, x, y, getWidth(), h, padTop).color(nameColor).font(nameFont).paint(name);
	}

	public void drawObjects(CustomSpriteBatch batch, float x, float y, float w, float h) {
		paintParameters.batch = batch;
		paintParameters.width = elementWidth;
		paintParameters.height = elementHeight;
		if (objects.size() == 0) {
			drawEmptyObjects(batch, x, y, h);
		} else if (objectPainter != null) {
			float nameOffset = getNameOffset();
			batch.startMasking(x + padLeft, y + padBottom, getWidth() - getTotalPaddingX(),
					h - padBottom - padTop - nameOffset);
			paintParameters.reset();
			objectPainter.startPainting(paintParameters);

			float elementDstX = getElementWidth() + getElementOffsetX();
			float elementDstY = getElementHeight() + getElementOffsetY();
			int ox = (int) (visualScrollX.val / elementDstX);
			int ex = MathUtils.ceil(ox + (getWidth() - getTotalPaddingX()) / elementDstX) + 1;
			int oy = (int) (visualScrollY.val / elementDstY);
			int ey = MathUtils.ceil(oy + (h - getNameOffset() - padBottom - padTop) / elementDstY) + 1;
			int sizeX = getColumns();
			drawElements(x, y, ox, ex, oy, ey, sizeX, true);
			drawElements(x, y, ox, ex, oy, ey, sizeX, false);
			paintParameters.reset();
			objectPainter.stopPainting(paintParameters);
			batch.stopMasking();
		}
		if (highlightIndex == -2 && !highlightFade.atMin()) {
			batch.drawRectangle(x, y, getWidth(), getHeight(), getHighlightColor());
		}
	}

	public void drawEmptyObjects(CustomSpriteBatch batch, float x, float y, float h) {
		if (objectPainter != null) {
			paintParameters.x = x + getX(0);
			paintParameters.y = y + getY(0);
			objectPainter.paintEmpty(paintParameters);
		}
	}

	private void drawElements(float x, float y, int ox, int ex, int oy, int ey, int sizeX, boolean bg) {
		T dragging = drag.getDragging();
		if (ex > sizeX) {
			ex = sizeX;
		}
		for (int ix = ox; ix < ex; ix++) {
			for (int iy = oy; iy < ey; iy++) {
				int i = ix + iy * sizeX;
				if (i >= 0 && i < getObjectAmount()) {
					T object = objects.get(i);
					if (dragging == null || object != dragging) {
						if (bg) {
							drawObjectBG(object, i, x + getX(i), y + getY(i));
						} else {
							drawObject(object, i, x + getX(i), y + getY(i));
						}
					}
				}
			}
		}
	}

	public void drawObjectBG(T object, int index, float x, float y) {
		paintParameters.x = x;
		paintParameters.y = y;
		paintParameters.index = index;
		paintParameters.object = object;
		objectPainter.paintBG(paintParameters);
	}

	public void drawObject(T object, int index, float x, float y) {
		paintParameters.x = x;
		paintParameters.y = y;
		paintParameters.index = index;
		paintParameters.object = object;
		objectPainter.paint(paintParameters);
		if (highlight) {
			if ((highlightIndex == index) ^ highlightExclude) {
				paintParameters.batch.drawRectangle(x, y, getElementWidth(), getElementHeight(), getHighlightColor());
			}
		}
	}

	private Color getHighlightColor() {
		Color c = Colors.temp(ActiveTheme.selectColor);
		c.a = Modulator.getLinear(0.2F, 0.3F, 2.0F);
		return c;
	}

	@Override
	public boolean canDrawCursor() {
		return drag.getDragging() == null;
	}

	@Override
	public void setCursor(Rectangle rectangle) {
		int selection = getSelection();
		if (selection < 0 || selection >= getObjects().size()) {
			super.setCursor(rectangle);
		} else {
			setCursor(rectangle, selection);
		}
	}

	public void setCursor(Rectangle rectangle, int selection) {
		float x = getAbsoluteX(), y = getAbsoluteY(), touch = calcTouchButtonSize();
		float itemX = getX(selection), itemY = getY(selection) + touch;
		float itemWidth = getElementWidth(), itemHeight = getElementHeight();
		if (itemY < touch) {
			itemHeight += itemY - touch;
			itemY = touch;
		}
		float maxY = getHeight() - getNameOffset();
		if (itemY + itemHeight > maxY) {
			itemHeight = maxY - itemY;
		}
		if (itemX < 0.0F) {
			itemWidth += itemX;
			itemX = 0.0F;
		}
		float maxX = getWidth();
		if (itemX + itemWidth > maxX) {
			itemWidth = maxX - itemX;
		}
		if (itemWidth < 5.0F || itemHeight < 5.0F) {
			rectangle.setSize(0.0F);
		} else {
			rectangle.set(x + itemX - 2, y + itemY - 2, itemWidth + 4, itemHeight + 4);
		}
	}

	public float getAbsoluteX(int index) {
		return getAbsoluteX() + getX(index);
	}

	public float getAbsoluteY(int index) {
		return getAbsoluteY() + calcTouchButtonSize() + getY(index);
	}

	public float getX(int pos) {
		return padLeft + (pos % getColumns()) * (getElementWidth() + getElementOffsetX()) - visualScrollX.val;
	}

	public float getY(int pos) {
		int row = 1 + pos / getColumns();
		float touchButton = calcTouchButtonSize();
		float h = getHeight() - touchButton - getNameOffset();
		return h + getElementOffsetY() - padTop - row * (getElementHeight() + getElementOffsetY()) + visualScrollY.val;
	}

	private boolean hasName() {
		return name != null;
	}

	public float getNameOffset() {
		return (hasName() ? containerTitle.getTop() - container.getTop() : 0);
	}

	public int getObjectAmount() {
		return objects == null ? 0 : objects.size();
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean remove() {
		return false;
	}

	@Override
	public boolean triggerButton(ButtonEvent event) {
		if (!isEnabled()) {
			return false;
		}
		inputRepeater.triggerButton(event);
		if (event.isPressed()) {
			if (getSelection() != -1) {
				if (event.isKey(KeyCode.MENU_DOWN)) {
					return moveY(1);
				} else if (event.isKey(KeyCode.MENU_UP)) {
					return moveY(-1);
				} else if (event.isKey(KeyCode.MENU_LEFT)) {
					return moveX(-1);
				} else if (event.isKey(KeyCode.MENU_RIGHT)) {
					return moveX(1);
				} else if (event.isKey(KeyCode.MENU_CANCEL)) {
					if (isOverviewSelectable() && deselect()) {
						playSound(ActiveTheme.cancel);
						return true;
					}
				} else if (event.isKey(KeyCode.MENU_SPECIAL_2)) {
					return specialSelect();
				} else if (event.isKey(KeyCode.MENU_SELECT)) {
					return select();
				}
			}
			if (event.isKey(KeyCode.MENU_SPECIAL)) {
				return selectHeader();
			}
		}
		return false;
	}

	@Override
	public boolean triggerTouch(TouchEvent event) {
		return false;
	}

	@Override
	public String toString() {
		return "Menu Table" + (name != null && name.length() > 0 ? (": " + name) : "");
	}
}
