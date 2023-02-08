package com.one2b3.endcycle.screens.menus.elements.buttons;

import com.badlogic.gdx.graphics.Color;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.FontCache;
import com.one2b3.endcycle.engine.fonts.GameFont;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.graphics.Drawable;
import com.one2b3.endcycle.engine.language.LocalizedMessage;
import com.one2b3.endcycle.engine.language.Unlocalize;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.features.theme.ActiveTheme;
import com.one2b3.endcycle.features.theme.MenuThemeDrawable;
import com.one2b3.endcycle.screens.menus.elements.MenuElement;
import com.one2b3.endcycle.screens.menus.elements.MenuElementAction.MenuElementActionType;
import com.one2b3.endcycle.screens.menus.elements.MenuElementShowAnim;
import com.one2b3.endcycle.utils.Modulator;
import com.one2b3.endcycle.utils.bounded.BoundedFloat;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@Getter
public class MenuButton extends MenuElement {

	static final int Y_OFFSET = 2;

	final MenuElementShowAnim showAnimation = new MenuElementShowAnim(this);
	final BoundedFloat taint = new BoundedFloat(0.0F, 1.0F, 6.0F);
	final BoundedFloat taintDisabled = new BoundedFloat(0.0F, 1.0F, 6.0F);
	final Color color = new Color(ActiveTheme.defaultColor);
	final MenuButtonClickedAnimation animation = new MenuButtonClickedAnimation();

	MenuThemeDrawable background = ActiveTheme.button;

	Color disabledColor = Color.GRAY, defaultColor = ActiveTheme.defaultColor, selectedColor = ActiveTheme.selectColor;

	MenuButtonAction action;

	Drawable drawable;
	boolean fitDrawable;

	LocalizedMessage text;
	int textHAlign, textVAlign;
	GameFont font;

	boolean disableBackground;
	boolean selected;
	boolean singleClick;

	public MenuButton() {
	}

	public MenuButton(String text) {
		this.text = Unlocalize.get(text);
	}

	public MenuButton(LocalizedMessage message) {
		this.text = message;
	}

	@Override
	public void show(GameScreen screen) {
		super.show(screen);
		showAnimation.start();
		color.set(isEnabled() ? defaultColor : disabledColor);
	}

	@Override
	public void hide(GameScreen screen) {
		super.hide(screen);
		showAnimation.stop();
	}

	public MenuButton setSelected(boolean selected) {
		if (this.selected != selected) {
			this.selected = selected;
			triggerAction(selected ? MenuElementActionType.SELECTED : MenuElementActionType.DESELECTED);
		}
		return this;
	}

	public MenuButton setText(LocalizedMessage message) {
		this.text = message;
		return this;
	}

	public MenuButton clearText() {
		setText((LocalizedMessage) null);
		return this;
	}

	public MenuButton setText(String text) {
		return setText(text == null ? null : Unlocalize.get(text));
	}

	@Override
	public boolean select() {
		if (isEnabled() && !isSelected()) {
			setSelected(true);
			taint.toMax();
			animation.start();
			click();
			return true;
		} else if (action != null && action.onDisabledClick()) {
			return true;
		}
		return false;
	}

	public void click() {
		if (action != null) {
			action.onClick();
		}
	}

	@Override
	public MenuButton setFocused(boolean focused) {
		showAnimation.focus(focused);
		super.setFocused(focused);
		return this;
	}

	@Override
	public void update(float delta) {
		if (!singleClick && isSelected()) {
			if (taint.atMax()) {
				setSelected(false);
			}
		}
		showAnimation.update(delta);
		animation.update(delta);
		taint.move(isSelected(), delta);
		taintDisabled.move(!isEnabled(), delta);
		color.set(defaultColor);
		color.lerp(selectedColor, taint.getVal());
		color.lerp(disabledColor, taintDisabled.getVal());
	}

	public void updateText() {
	}

	@Override
	public boolean isHidden() {
		return super.isHidden() || !showAnimation.isElementVisible();
	}

	@Override
	public void draw(CustomSpriteBatch batch, float x, float y) {
		if (showAnimation.isElementVisible()) {
			showAnimation.begin(batch, x, y);
			x += calcX();
			y += calcY();
			float width = getWidth();
			float height = getHeight();
			drawButton(batch, x, y, width, height);
			showAnimation.end(batch);
		}
	}

	public void drawButton(CustomSpriteBatch batch, float x, float y, float width, float height) {
		height = animation.getHeight(height);
		float textOffset = 0.0F;
		if (drawBG(width, height)) {
			setColor(batch);
			drawBackground(batch, x, y, width, height);
			textOffset = Y_OFFSET;
		}
		Painter painter = Painter.on(batch) //
				.at(x + width * 0.5F, y + textOffset + (height - textOffset) * 0.5F) //
				.hAlign(0).vAlign(0);
		if (!isEnabled()) {
			painter.color(color);
		}
		if (fitDrawable) {
			float scale = Math.min(width / drawable.getWidth(), (height - textOffset) / drawable.getHeight());
			painter.scale(scale);
		}
		drawDrawable(painter);
		painter.hAlign(textHAlign).vAlign(textVAlign).moveX(textHAlign * 0.5F * (width - 4)).moveY(textVAlign * 0.5F * (height - Y_OFFSET));
		drawText(painter.color(null).alpha(isEnabled() ? 1.0F : 0.5F).scale(1.0F).font(getFont()), getText());

		if (drawBG(width, height)) {
			setColor(batch);
			drawOverlay(batch, x, y, width, height);
		}
	}

	public void setColor(CustomSpriteBatch batch) {
		batch.setColor(color);
		if (isFocused()) {
			float multiplier = Modulator.getLinear(0.9F, 1.0F, 2.0F);
			batch.getColor().mul(multiplier, multiplier, multiplier, 1.0F);
		}
	}

	public void drawBackground(CustomSpriteBatch batch, float x, float y, float width, float height) {
		background.draw(batch, x, y, width, height);
	}

	public void drawOverlay(CustomSpriteBatch batch, float x, float y, float width, float height) {
		background.drawOverlay(batch, x, y, width, height);
	}

	public boolean drawBG(float width, float height) {
		return !disableBackground && background != null
				&& (fitDrawable || drawable == null || drawable.getWidth() < width || drawable.getHeight() < height);
	}

	public void drawDrawable(Painter painter) {
		if (drawable != null) {
			painter.paint(drawable);
		}
	}

	public void drawText(Painter painter, LocalizedMessage message) {
		if (text != null) {
			FontCache cache = (painter.font == null ? GameFonts.Text : painter.font).getCache(message.format(), painter.width);
			float textWidth = getTextWidth(painter);
			painter.xScale(Math.min(textWidth / cache.getWidth(), 1.0F));
			painter.paint(cache);
		}
	}

	public float getTextWidth(Painter painter) {
		return (getWidth() - (background == null ? 0 : background.getLeft() + background.getRight()))
				* (painter.hAlign == -1 ? 0.45F : 1.0F);
	}

	@Override
	public String toString() {
		return "[MenuButton, Text:" + text + ", X (Absolute): " + getAbsoluteX() + ", Y (Absolute): " + getAbsoluteY() + "]";
	}
}
