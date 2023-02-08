package com.one2b3.endcycle.screens.menus.elements.checkbox;

import com.badlogic.gdx.graphics.Color;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.FontCache;
import com.one2b3.endcycle.engine.fonts.GameFont;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.graphics.Drawable;
import com.one2b3.endcycle.engine.graphics.Drawables;
import com.one2b3.endcycle.engine.language.LocalizedMessage;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.features.theme.ActiveTheme;
import com.one2b3.endcycle.features.theme.MenuThemeDrawable;
import com.one2b3.endcycle.screens.menus.elements.MenuElement;
import com.one2b3.endcycle.screens.menus.elements.MenuElementAction.MenuElementActionType;
import com.one2b3.endcycle.screens.menus.elements.MenuElementShowAnim;
import com.one2b3.endcycle.screens.menus.elements.buttons.MenuButtonClickedAnimation;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@Getter
public class MenuCheckBox extends MenuElement {

	final MenuElementShowAnim showAnimation = new MenuElementShowAnim(this);
	final MenuButtonClickedAnimation animation = new MenuButtonClickedAnimation();

	MenuThemeDrawable checkedBackground = ActiveTheme.button;
	MenuThemeDrawable uncheckedBackground = ActiveTheme.button;
	MenuThemeDrawable disabledBackground = ActiveTheme.button;

	Drawable drawableChecked = Drawables.checkmark_checked.get();
	Drawable drawableUnchecked = Drawables.checkmark_unchecked.get();

	Color checkedColor = ActiveTheme.selectColor;
	Color uncheckedColor = ActiveTheme.defaultColor;
	Color disabledColor = ActiveTheme.disabledColor;

	String text;
	GameFont font;
	boolean checked;
	MenuCheckBoxAction action;

	public MenuCheckBox() {
	}

	public MenuCheckBox(String text) {
		setText(text);
	}

	public MenuCheckBox setText(LocalizedMessage text) {
		return setText(text == null ? "" : text.format());
	}

	public MenuCheckBox setText(String text) {
		this.text = text;
		return this;
	}

	@Override
	public boolean select() {
		if (isEnabled()) {
			setChecked(!isChecked());
			animation.start();
			if (action != null) {
				action.onClick(isChecked());
			}
			triggerAction(MenuElementActionType.CHANGED_VALUE);
			triggerAction(MenuElementActionType.SELECTED);
			playSound(ActiveTheme.select);
			return true;
		} else if (action != null && action.onDisabledClick()) {
			return true;
		}
		playSound(ActiveTheme.error);
		return false;
	}

	@Override
	public MenuElement setFocused(boolean focused) {
		showAnimation.focus(focused);
		return super.setFocused(focused);
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
		animation.update(delta);
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
		float height = animation.getHeight(getHeight());
		MenuThemeDrawable background = (isEnabled() ? (checked ? checkedBackground : uncheckedBackground) : disabledBackground);
		batch.setColor(isEnabled() ? (checked ? checkedColor : uncheckedColor) : disabledColor);
		background.drawNinePatch(batch, x, y, getWidth(), height);
		float textY = y + height * 0.5F + 1;
		FontCache text = (font == null ? GameFonts.Text : font).getCache(this.text);
		Painter.on(batch).at(x + 5, textY).align(-1, 0)
				.xScale(Math.min(1.0F, (getWidth() - drawableChecked.getWidth() - 12) / text.getWidth())).paint(text);
		Painter.on(batch).at(x + getWidth() - 4, textY).align(1, 0).paint(checked ? drawableChecked : drawableUnchecked);
		showAnimation.end(batch);

	}
}
