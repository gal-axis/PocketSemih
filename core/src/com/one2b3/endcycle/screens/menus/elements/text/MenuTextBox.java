package com.one2b3.endcycle.screens.menus.elements.text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Clipboard;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.FontCache;
import com.one2b3.endcycle.engine.fonts.GameFont;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.engine.language.LocalizedMessage;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.features.theme.ActiveTheme;
import com.one2b3.endcycle.features.theme.MenuThemeDrawable;
import com.one2b3.endcycle.screens.menus.elements.MenuElement;
import com.one2b3.endcycle.screens.menus.elements.MenuElementAction.MenuElementActionType;
import com.one2b3.endcycle.screens.menus.elements.MenuElementShowAnim;
import com.one2b3.endcycle.screens.menus.elements.scroller.Scrollable;
import com.one2b3.endcycle.screens.menus.elements.scroller.Scroller;
import com.one2b3.endcycle.utils.Modulator;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class MenuTextBox extends MenuElement implements TextAcceptor, Scrollable {

	static final float EXTRA_SPACE = 6.0F;

	final MenuElementShowAnim showAnimation = new MenuElementShowAnim(this);
	final MenuThemeDrawable background = ActiveTheme.panel;
	final Scroller scroller = new Scroller(this);

	FontCache textCache = FontCache.Empty;

	Color backgroundColor = ActiveTheme.defaultColor;

	GameFont font;
	Color emptyTextColor = ActiveTheme.disabledColor;
	TextAcceptor inputAcceptor = this;

	float border = 4;
	float scale = 1.0F;

	String label, emptyText = "", regex = null;

	String text = "";
	int limit = 10;
	int cursor = -1;

	int textHAlign = -1, textVAlign = 0;

	boolean hideText;
	boolean selected;

	TextBoxInputProcessor inputProcessor = new TextBoxInputProcessor(this);

	public MenuElement setSelected(boolean selected) {
		if (this.selected != selected) {
			this.selected = selected;
			triggerAction(selected ? MenuElementActionType.SELECTED : MenuElementActionType.DESELECTED);
		}
		return this;
	}

	public MenuTextBox setLabelAndEmptyText(String text) {
		return setLabel(text).setEmptyText(text);
	}

	public MenuTextBox setLabel(LocalizedMessage text) {
		if (text == null) {
			this.label = null;
			return this;
		}
		return setLabel(text.format());
	}

	public MenuTextBox setLabel(String text) {
		this.label = text + ":";
		return this;
	}

	private FontCache updateCache() {
		FontCache cache = textCache;
		if (cache == FontCache.Empty && text.length() > 0) {
			textCache = cache = (font == null ? GameFonts.Text : font).getCache(createDisplayingText());
		}
		return cache;
	}

	private void invalidateCache() {
		this.textCache = FontCache.Empty;
	}

	protected String createDisplayingText() {
		return (hideText ? text.replaceAll(".", "*") : text);
	}

	public void moveCursor(int offset) {
		cursor = MathUtils.clamp(cursor + offset, 0, text.length());
		clampScroll();
	}

	private void clampScroll() {
		float width = getScrollWidth();
		if (width > 0.0F) {
			Rectangle character = updateCache().getPosition(cursor);
			if (character != null) {
				float w = getWidth() - border * 2.0F;
				if (character.x <= scroller.getScrollX() + border + 1) {
					scroller.setScrollX(character.x - 1 - border);
				} else if (character.x + character.width >= scroller.getScrollX() + w - 1) {
					scroller.setScrollX(character.x + character.width + 1.0F - w);
				}
			} else if (cursor == 0) {
				scroller.setScrollX(0);
			} else if (cursor == text.length()) {
				scroller.setScrollX(width);
			}
		} else {
			scroller.setScrollX(0);
		}
	}

	@Override
	public boolean touch(TouchEvent trigger) {
		boolean touching = isTouching(trigger.positionX, trigger.positionY);
		if (!isEnabled()) {
			if (touching && trigger.isPressed() && trigger.isLeftMouse()) {
				return copy();
			}
			return false;
		}
		if (scroller.touch(trigger, touching)) {
			return true;
		} else if (super.touch(trigger)) {
			clickText(trigger.positionX, trigger.positionY);
			return true;
		}
		return false;
	}

	@Override
	public MenuElement setFocused(boolean focused) {
		showAnimation.focus(focused);
		return super.setFocused(focused);
	}

	@Override
	public boolean select() {
		if (!isEnabled()) {
			return copy();
		}
		inputProcessor.select();
		if (cursor == -1 || cursor > text.length()) {
			cursor = text.length();
		}
		clampScroll();
		return true;
	}

	public boolean copy() {
		Clipboard clipboard = Gdx.app.getClipboard();
		if (clipboard != null) {
			clipboard.setContents(getText());
			return true;
		}
		return false;
	}

	public void clearText() {
		setText("");
	}

	public MenuTextBox setText(String text) {
		this.text = (text == null ? "" : text);
		invalidateCache();
		return this;
	}

	public boolean addCharacter(char c) {
		if (text.length() < limit) {
			c = (inputAcceptor == null ? this : inputAcceptor).getChar(text, c);
			if (c != 0) {
				if (cursor == text.length()) {
					text += c;
				} else {
					text = text.substring(0, cursor) + c + text.substring(cursor);
				}
				cursor++;
				float w = updateCache().getWidth();
				invalidateCache();
				FontCache textCache = updateCache();
				Rectangle character = textCache.getPosition(cursor - 1);
				if (character == null || scroller.getScrollX() + getScrollAreaWidth() < character.x + character.width
						+ border * 2 + EXTRA_SPACE) {
					scroller.setScrollX(scroller.getScrollX() + textCache.getWidth() - w);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public char getChar(String text, char c) {
		if (regex != null && !Character.toString(c).matches(regex)) {
			return 0;
		}
		return (font == null ? GameFonts.Text : font).hasCharacter(c) ? c : 0;
	}

	public void deleteCharacter() {
		if (cursor < text.length()) {
			cursor++;
			removeCharacter();
		}
	}

	public void removeCharacter() {
		if (text.length() > 0 && cursor > 0) {
			if (cursor == text.length()) {
				text = text.substring(0, text.length() - 1);
			} else {
				text = text.substring(0, cursor - 1) + text.substring(cursor);
			}
			cursor--;
			invalidateCache();
			scroller.updateScroll(true);
		}
	}

	public boolean clickText(int positionX, int positionY) {
		positionX -= getAbsoluteX() + border;
		positionY -= getAbsoluteY() + border;
		if (positionX >= 0 && positionX <= getWidth() - border * 2 && positionY >= 0
				&& positionY <= getHeight() - border * 2) {
			FontCache cache = updateCache();
			positionX += scroller.getScrollX() + cache.getWidth() * 0.5F * (textHAlign + 1) + textHAlign;
			positionY += scroller.getScrollY();
			cursor = cache.getIndex(positionX, 0);
			return true;
		}
		return false;
	}

	@Override
	public void show(GameScreen screen) {
		super.show(screen);
		showAnimation.start();
	}

	@Override
	public void hide(GameScreen screen) {
		inputProcessor.deselect();
		super.hide(screen);
		showAnimation.stop();
	}

	@Override
	public void update(float delta) {
		showAnimation.update(delta);
	}

	@Override
	public boolean isHidden() {
		return super.isHidden() || !showAnimation.isElementVisible();
	}

	@Override
	public void draw(CustomSpriteBatch batch, float x, float y) {
		showAnimation.begin(batch, x, y);
		draw(batch, x + calcX(), y + calcY(), getWidth(), getHeight());
		showAnimation.end(batch);
	}

	public void draw(CustomSpriteBatch batch, float x, float y, float width, float height) {
		drawBackground(batch, x, y, width, height);
		batch.startMasking(x + border, y + border, width - border * 2, height - border * 2);
		float textX = x + getPosition(textHAlign, width) - scroller.getScrollX();
		float textY = y + getPosition(textVAlign, height - (label == null ? 0 : 3)) - scroller.getScrollY();
		drawString(batch, updateCache(), textX, textY);
		batch.stopMasking();
		batch.setColor(isEnabled() ? backgroundColor : Color.GRAY);
		background.drawOverlay(batch, x, y, width, height);
		drawLabel(batch, x, y, width, height);
		scroller.draw(batch, x, y);
	}

	public void drawLabel(CustomSpriteBatch batch, float x, float y, float width, float height) {
		if (label != null) {
			Painter.on(batch).x(x + width * 0.5F).y(y + height).hAlign(0).vAlign(1).font(GameFonts.SmallBorder)
					.paint(label.toUpperCase());
		}
	}

	public void drawBackground(CustomSpriteBatch batch, float x, float y, float width, float height) {
		batch.setColor(isEnabled() ? backgroundColor : Color.GRAY);
		background.drawNinePatch(batch, x, y, width, height);
	}

	@Override
	public boolean isTouching(float x, float y) {
		return scroller.isTouching(x, y) || super.isTouching(x, y);
	}

	public void drawString(CustomSpriteBatch batch, FontCache text, float x, float y) {
		Painter params = Painter.on(batch).at(x, y).scale(scale).align(textHAlign, textVAlign).font(font);
		if (text == null || text.length() == 0) {
			if (emptyText != null) {
				params.color(emptyTextColor).paint(emptyText);
			}
		} else {
			params.paint(text);
		}
		if (isSelected() && Modulator.getLinear(5.0F) > 0.5F) {
			if (textHAlign == 1) {
				if (cursor == text.length()) {
					x--;
				}
			} else if (textHAlign == -1) {
				if (cursor == 0) {
					x++;
				}
			}
			Rectangle cache = text.getPosition(cursor - 1);
			params.color(1.0F)
					.x(x + (cache == null ? -1 : cache.x + cache.width - 1) - text.getWidth() * 0.5F * (textHAlign + 1))
					.y(y + (cache == null ? 0.0F : cache.y)).scale(scale).color(Color.GRAY).paint("|");
		}
	}

	@Override
	public boolean canDrawCursor() {
		return !isSelected();
	}

	private float getPosition(int align, float size) {
		if (align == -1) {
			return border + 1;
		} else if (align == 0) {
			return size * 0.5F;
		} else if (align == 1) {
			return size - border - 1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return "[MenuTextBox, Text:" + text + ", X (Absolute): " + getAbsoluteX() + ", Y (Absolute): " + getAbsoluteY()
				+ "]";
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
		return getAbsoluteY();
	}

	@Override
	public float getScrollAreaHeight() {
		return getHeight();
	}

	@Override
	public float getScrollWidth() {
		return updateCache().getWidth() + border * 2.0F - getWidth() + EXTRA_SPACE;
	}

	@Override
	public float getScrollHeight() {
		return updateCache().getHeight() + border * 2.0F - getHeight();
	}
}
