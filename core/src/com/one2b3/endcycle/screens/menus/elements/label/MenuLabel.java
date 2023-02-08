package com.one2b3.endcycle.screens.menus.elements.label;

import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.FontCache;
import com.one2b3.endcycle.engine.fonts.GameFont;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.language.LocalizedMessage;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.features.theme.ActiveTheme;
import com.one2b3.endcycle.features.theme.MenuThemeDrawable;
import com.one2b3.endcycle.screens.menus.elements.MenuElement;
import com.one2b3.endcycle.screens.menus.elements.MenuElementController;
import com.one2b3.endcycle.screens.menus.elements.MenuElementShowAnim;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
public class MenuLabel extends MenuElement {

	final MenuElementShowAnim showAnimation = new MenuElementShowAnim(this);

	MenuThemeDrawable background = ActiveTheme.panel;

	boolean wrapText;
	String label, text;
	GameFont font;
	FontCache cache;

	float border = 4.0F;

	int textHAlign = -1, textVAlign = 0;

	public MenuLabel(LocalizedMessage message) {
		setText(message);
	}

	public MenuLabel(String text) {
		setText(text);
	}

	public MenuLabel setText(LocalizedMessage message) {
		return setText(message == null ? null : message.format());
	}

	public MenuLabel setText(String text) {
		if (this.text == null || !this.text.equals(text)) {
			this.text = text;
			updateCache();
		}
		return this;
	}

	public MenuLabel setFont(GameFont font) {
		if (this.font != font) {
			this.font = font;
			updateCache();
		}
		return this;
	}

	public MenuLabel setBorder(float border) {
		this.border = border;
		updateCache();
		return this;
	}

	@Override
	public MenuLabel setWidth(float width) {
		super.setWidth(width);
		updateCache();
		return this;
	}

	@Override
	public MenuLabel setHeight(float height) {
		super.setHeight(height);
		return this;
	}

	public void updateCache() {
		float width = getWidth() - getBorder() * 2.0F;
		if (text != null && (!wrapText || width > 0.0F)) {
			cache = (font == null ? GameFonts.Text : font).getCache(text, wrapText ? width : 0.0F);
		}
	}

	public float getTextWidth() {
		return cache == null ? 0 : cache.getWidth();
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
	public boolean select() {
		return true;
	}

	@Override
	public boolean isTouching(float x, float y) {
		return super.isTouching(x, y);
	}

	@Override
	public void buildNeighbors(MenuElementController controller, Array<MenuElement> elements) {
	}

	@Override
	public boolean isHidden() {
		return super.isHidden() || !showAnimation.isElementVisible();
	}

	@Override
	public void draw(CustomSpriteBatch batch, float x, float y) {
		showAnimation.begin(batch, x, y);
		x += calcX();
		y += calcY();
		drawBackground(batch, x, y, getWidth(), getHeight());
		drawLabel(batch, x, y, getHeight());
		x += getPosition(textHAlign, getWidth());
		y += getPosition(textVAlign, getHeight());
		drawString(batch, x, y);
		showAnimation.end(batch);
	}

	public void drawBackground(CustomSpriteBatch batch, float x, float y, float width, float height) {
		if (background != null && width > 0.0F && height > 0.0F) {
			batch.setColor(isEnabled() ? ActiveTheme.menuColor : ActiveTheme.disabledColor);
			background.drawNinePatch(batch, x, y, width, height);
		}
	}

	public void drawLabel(CustomSpriteBatch batch, float x, float y, float height) {
		if (label != null) {
			Painter.on(batch).at(x, y + height).align(-1, 1).font(GameFonts.SmallBorder).paint(label);
		}
	}

	public void drawString(CustomSpriteBatch batch, float x, float y) {
		if (cache != null) {
			Painter.on(batch).at(x, y).align(textHAlign, textVAlign)
					.xScale(wrapText || getWidth() == 0.0F ? 1.0F : Math.min((getWidth() - getBorder() * 2) / cache.getWidth(), 1.0F))
					.paint(cache);
		}
	}

	public float getPosition(int align, float size) {
		if (align == -1) {
			return border;
		} else if (align == 0) {
			return size * 0.5F;
		} else if (align == 1) {
			return size - border;
		}
		return 0;
	}
}
