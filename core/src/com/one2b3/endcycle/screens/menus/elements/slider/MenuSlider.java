package com.one2b3.endcycle.screens.menus.elements.slider;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.graphics.Drawable;
import com.one2b3.endcycle.engine.graphics.Drawables;
import com.one2b3.endcycle.engine.input.InputListener;
import com.one2b3.endcycle.engine.input.InputType;
import com.one2b3.endcycle.engine.input.KeyCode;
import com.one2b3.endcycle.engine.input.events.ButtonEvent;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.engine.language.LocalizedMessage;
import com.one2b3.endcycle.engine.language.Localizer;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.features.theme.ActiveTheme;
import com.one2b3.endcycle.screens.menus.elements.MenuElement;
import com.one2b3.endcycle.screens.menus.elements.MenuElementAction.MenuElementActionType;
import com.one2b3.endcycle.screens.menus.elements.MenuElementShowAnim;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class MenuSlider extends MenuElement implements InputListener {

	final Drawable[] sliderDrawable = Drawables.array(Drawables.Slider_0, Drawables.Slider_1, Drawables.Slider_2);
	final Drawable sliderKnob = Drawables.Scroller_Y.get();

	final MenuElementShowAnim showAnimation = new MenuElementShowAnim(this);

	LocalizedMessage label;
	float steps;
	float value, minimum, maximum;
	float dragSpeed = 150.0F;

	boolean percent;

	boolean dragging, mouseDrag, displayValue = true;
	boolean autoSelect;

	MenuSliderListener listener;

	public MenuSlider() {
		steps = 0.0F;
		minimum = 0.0F;
		maximum = 0.0F;
		value = 0.5F;
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
	public MenuElement setFocused(boolean focused) {
		if (autoSelect) {
			if (focused) {
				if (showing) {
					startDragging();
				}
			} else {
				stopDragging();
			}
		}
		showAnimation.focus(focused);
		return super.setFocused(focused);
	}

	public float translateToValue(float slider) {
		float w = getWidth() - 2 - sliderKnob.getWidth();
		slider -= sliderKnob.getWidth() * 0.5F + 1;
		return MathUtils.clamp(minimum + slider / w * (maximum - minimum), minimum, maximum);
	}

	public float translateToSlider(float value) {
		float w = getWidth() - 2 - sliderKnob.getWidth();
		return sliderKnob.getWidth() * 0.5F + 1 + ((value - minimum) / (maximum - minimum)) * w;
	}

	public void changeValue(float value) {
		float steppedValue = steps > 0.0F ? MathUtils.round(value / steps) * steps : value;
		setValue(MathUtils.clamp(steppedValue, minimum, maximum));
		triggerAction(MenuElementActionType.CHANGED_VALUE);
		if (listener != null) {
			listener.dragSlider(getValue());
		}
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		showAnimation.update(delta);
		if (!mouseDrag && dragging) {
			if (steps <= 0.0F) {
				boolean right = KeyCode.MENU_RIGHT.isPressed();
				boolean left = KeyCode.MENU_LEFT.isPressed();
				if (right != left) {
					if (right) {
						changeValue(getValue() + delta * dragSpeed);
					} else if (left) {
						changeValue(getValue() - delta * dragSpeed);
					}
				}
			}
		}
	}

	@Override
	public boolean select() {
		startDragging();
		triggerAction(MenuElementActionType.SELECTED);
		return true;
	}

	@Override
	public boolean touch(TouchEvent trigger) {
		if (isTouching(trigger.positionX, trigger.positionY)) {
			if (trigger.type == InputType.PRESSED) {
				startDragging();
				mouseDrag = true;
				touchDrag(trigger);
				return true;
			} else if (autoSelect) {
				if (isFocused() && showing) {
					startDragging();
				}
			}
		}
		return false;
	}

	public void startDragging() {
		dragging = true;
		if (listener != null) {
			listener.startSliderDrag();
		}
		screen.input.add(this);
	}

	public void stopDragging() {
		dragging = false;
		if (listener != null) {
			listener.stopSliderDrag();
		}
		screen.input.remove(this);
	}

	@Override
	public boolean isHidden() {
		return super.isHidden() || !showAnimation.isElementVisible();
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		showAnimation.begin(batch, xOfs, yOfs);
		float x = xOfs + calcX();
		float y = yOfs + calcY() + getHeight() * 0.5F;
		Color color = (isEnabled() ? ActiveTheme.defaultColor : ActiveTheme.disabledColor);
		Painter params = Painter.on(batch).at(x, y).align(-1, 0).color(color);
		params.paint(sliderDrawable[0]);
		params.xScale((getWidth() - 6) / 3.0F).x(x + 3).paint(sliderDrawable[1]);
		params.xScale(1.0F).x(x + getWidth()).hAlign(1).paint(sliderDrawable[2]);
		float xKnob = x + translateToSlider(value);
		params.x(xKnob).align(0).color(isDragging() ? ActiveTheme.selectColor : ActiveTheme.disabledColor).paint(sliderKnob);
		if (isDisplayValue()) {
			Painter.on(batch).at(xKnob, y).align(0).font(GameFonts.TextBorder).paint(getValueString());
		}
		if (label != null) {
			Painter.on(batch).at(x, y + getHeight() * 0.5F).align(-1, 1).font(GameFonts.TinyBorder).paint(label);
		}
		showAnimation.end(batch);
	}

	@Override
	public void setCursor(Rectangle rectangle) {
		if (dragging && !mouseDrag) {
			float w = sliderKnob.getWidth() + 8;
			rectangle.set(getAbsoluteX() + translateToSlider(value) - w * 0.5F, getAbsoluteY(), w, getHeight());
		} else {
			super.setCursor(rectangle);
		}
	}

	protected String getValueString() {
		return steps == 1.0F ? Integer.toString((int) value) : (percent ? Localizer.percent(value, 0, false) : Localizer.comma(value, 2));
	}

	@Override
	public boolean triggerButton(ButtonEvent event) {
		if (event.isPressed()) {
			mouseDrag = false;
			float move = (this.steps > 0.0F ? this.steps : 0.0F);
			if (event.isKey(KeyCode.MENU_LEFT)) {
				changeValue(this.value - move);
				return true;
			} else if (event.isKey(KeyCode.MENU_RIGHT)) {
				changeValue(this.value + move);
				return true;
			} else if (event.isKey(KeyCode.MENU_CANCEL) || event.isKey(KeyCode.MENU_SELECT)) {
				if (!autoSelect) {
					stopDragging();
				}
			}
		}
		return !autoSelect;
	}

	@Override
	public boolean triggerTouch(TouchEvent event) {
		if (event.type == InputType.RELEASED) {
			if (autoSelect) {
				mouseDrag = false;
				return true;
			} else {
				stopDragging();
			}
		} else {
			if (event.type == InputType.PRESSED && isTouching(event.positionX, event.positionY)) {
				mouseDrag = true;
			}
			touchDrag(event);
		}
		if (!mouseDrag && event.type == InputType.MOVED && !isTouching(event.positionX, event.positionY)) {
			stopDragging();
		}
		return !autoSelect || (dragging && mouseDrag);
	}

	private void touchDrag(TouchEvent trigger) {
		if (mouseDrag) {
			float current = translateToValue(trigger.positionX - getAbsoluteX());
			changeValue(current);
		}
	}
}
