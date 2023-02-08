package com.one2b3.endcycle.screens.menus.elements.changer;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.ObjectPainter;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.FontCache;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.graphics.Drawable;
import com.one2b3.endcycle.engine.graphics.Drawables;
import com.one2b3.endcycle.engine.input.InputListener;
import com.one2b3.endcycle.engine.input.KeyCode;
import com.one2b3.endcycle.engine.input.events.ButtonEvent;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.engine.language.LocalizedMessage;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.features.theme.ActiveTheme;
import com.one2b3.endcycle.features.theme.MenuThemeDrawable;
import com.one2b3.endcycle.screens.menus.elements.MenuElement;
import com.one2b3.endcycle.screens.menus.elements.MenuElementAction.MenuElementActionType;
import com.one2b3.endcycle.screens.menus.elements.MenuElementShowAnim;
import com.one2b3.endcycle.utils.Modulator;
import com.one2b3.utils.CColor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Accessors(chain = true)
@Setter
@Getter
public class MenuChanger<T> extends MenuElement implements InputListener {

	static final Painter params = new Painter();

	final MenuElementShowAnim showAnimation = new MenuElementShowAnim(this);

	Drawable arrowLeft = Drawables.arrow_left.get();
	Drawable arrowRight = Drawables.arrow_right.get();
	Drawable arrowUp = Drawables.arrow_up.get();
	Drawable arrowDown = Drawables.arrow_down.get();
	MenuThemeDrawable background = ActiveTheme.panel;

	FontCache label;

	int selection;
	List<T> elements;

	MenuChangerSelectAction<T> selectAction;
	ObjectPainter<T> painter = new MenuChangerDefaultPainter<>();

	boolean selected;
	boolean horizontal = true, autoFocus;

	public MenuChanger(List<T> elements) {
		this.elements = elements;
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

	public MenuChanger<T> setLabel(LocalizedMessage label) {
		return setLabel(label == null ? "" : label.format());
	}

	public MenuChanger<T> setLabel(String label) {
		this.label = GameFonts.SmallBorder.getCache(label);
		return this;
	}

	public void setSelectedElement(Object element) {
		setSelection(elements.indexOf(element));
	}

	public void setSelection(int selection) {
		this.selection = MathUtils.clamp(selection, 0, elements.size());
	}

	public boolean moveSelection(int offset) {
		setSelection((this.selection + offset + elements.size()) % elements.size());
		if (selectAction != null) {
			selectAction.changedSelection(selection, elements.get(selection));
		}
		triggerAction(MenuElementActionType.CHANGED_VALUE, getSelected());
		playSound(ActiveTheme.navigate);
		return true;
	}

	public T getSelected() {
		return elements == null ? null : elements.get(selection);
	}

	public MenuChanger<T> horizontal() {
		horizontal = true;
		return this;
	}

	public MenuChanger<T> vertical() {
		horizontal = false;
		return this;
	}

	@Override
	public MenuElement setFocused(boolean focused) {
		if (isAutoFocus() && isEnabled()) {
			setSelected(focused);
		}
		showAnimation.focus(focused);
		return super.setFocused(focused);
	}

	@Override
	public boolean select() {
		if (isEnabled()) {
			setSelected(true);
			playSound(ActiveTheme.select);
			super.select();
		} else {
			playSound(ActiveTheme.error);
		}
		return true;
	}

	@Override
	public boolean touch(TouchEvent trigger) {
		if (!isEnabled()) {
			return isTouching(trigger.positionX, trigger.positionY);
		}
		if (isTouching(trigger.positionX, trigger.positionY)) {
			setSelected(true);
			return true;
		} else {
			setSelected(false);
			return false;
		}
	}

	public MenuElement setSelected(boolean selected) {
		if (this.selected != selected) {
			this.selected = selected;
			triggerAction(selected ? MenuElementActionType.SELECTED : MenuElementActionType.DESELECTED);

			if (selected) {
				screen.input.add(this);
			} else {
				screen.input.remove(this);
			}
		}
		return this;
	}

	public boolean isSelected() {
		return selected;
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
		float width = getWidth();
		float height = getHeight();
		drawChanger(batch, x, y, width, height);
		showAnimation.end(batch);
	}

	public void drawChanger(CustomSpriteBatch batch, float x, float y, float width, float height) {
		if (background != null) {
			batch.setColor(getBackgroundColor());
			background.drawNinePatch(batch, x, y, width, height);
		}
		if (canDrawArrows()) {
			float offset = getArrowOffset();
			CColor color = (isSelected() && !isMobile() ? ActiveTheme.selectColor : ActiveTheme.defaultColor);
			if (isHorizontal()) {
				arrowLeft.setColor(color);
				arrowLeft.draw(batch, x + offset, y + height * 0.5F, -1, 0);
				arrowRight.setColor(color);
				arrowRight.draw(batch, x + width - offset, y + height * 0.5F, 1, 0);
			} else {
				arrowDown.setColor(color);
				arrowDown.draw(batch, x + width * 0.5F, y + offset, 0, -1);
				arrowUp.setColor(color);
				arrowUp.draw(batch, x + width * 0.5F, y + height - offset, 0, 1);
			}
		}
		params.reset(batch).at(x, y).width(width).height(label == null ? height : height - 2);
		if (elements != null && elements.size() > 0) {
			painter.paint(elements.get(selection), params);
		}
		drawLabel(batch, x, y, height);
	}

	public void drawLabel(CustomSpriteBatch batch, float x, float y, float height) {
		if (label != null) {
			params.reset(batch).at(x + getWidth() * 0.5F, y + height).align(0, 1).paint(label);
		}
	}

	public Color getBackgroundColor() {
		return isEnabled() ? ActiveTheme.defaultColor : Color.GRAY;
	}

	public boolean canDrawArrows() {
		return isEnabled();
	}

	public float getArrowOffset() {
		if (isMobile()) {
			return Modulator.getLinear(3.0F, 6.0F, 2.0F);
		}
		return isSelected() ? Modulator.getLinear(3.0F, 6.0F, 4.0F) : 3.0F;
	}

	public boolean isMobile() {
		return Cardinal.isPortable() && screen.input.lastTouch;
	}

	@Override
	public boolean canDrawCursor() {
		return !isSelected() || isAutoFocus();
	}

	@Override
	public boolean triggerButton(ButtonEvent event) {
		if (event.isPressed()) {
			if (event.isKey(isHorizontal() ? KeyCode.MENU_RIGHT : KeyCode.MENU_DOWN)) {
				return moveSelection(1);
			} else if (event.isKey(isHorizontal() ? KeyCode.MENU_LEFT : KeyCode.MENU_UP)) {
				return moveSelection(-1);
			} else if (event.isKey(KeyCode.MENU_SELECT) || event.isKey(KeyCode.MENU_CANCEL)) {
				if (!isAutoFocus()) {
					setSelected(false);
					playSound(ActiveTheme.select);
				}
			}
		}
		return !isAutoFocus();
	}

	@Override
	public boolean triggerTouch(TouchEvent event) {
		float x = getAbsoluteX(), y = getAbsoluteY();
		float width = getWidth(), height = getHeight();
		if (event.positionX >= x && event.positionY >= y && event.positionX <= x + width
				&& event.positionY <= y + height) {
			boolean pressed = event.isLeftMouse() && event.isReleased();
			if (pressed) {
				if (horizontal) {
					x = event.positionX - x;
					float w = Math.max(25.0F, width * 0.2F);
					if (x < w) {
						return moveSelection(-1);
					} else if (x > width - w) {
						return moveSelection(1);
					}
				} else {
					y = event.positionY - y;
					float h = Math.max(25.0F, height * 0.2F);
					if (y < h) {
						return moveSelection(1);
					} else if (y > height - h) {
						return moveSelection(-1);
					}
				}
			}
		} else {
			setSelected(false);
		}
		return false;
	}
}
