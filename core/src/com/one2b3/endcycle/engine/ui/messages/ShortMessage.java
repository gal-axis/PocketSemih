package com.one2b3.endcycle.engine.ui.messages;

import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.FontCache;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.input.InputListener;
import com.one2b3.endcycle.engine.input.KeyCode;
import com.one2b3.endcycle.engine.input.events.ButtonEvent;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.engine.language.LocalizedMessage;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.endcycle.features.theme.ActiveTheme;
import com.one2b3.endcycle.features.theme.MenuThemeDrawable;
import com.one2b3.endcycle.screens.menus.elements.MenuElementShowAnim;
import com.one2b3.endcycle.utils.bounded.BoundedFloat;

import lombok.Getter;

public class ShortMessage extends GameScreenMessage implements InputListener {
	protected static final int STATE_OPEN = 0, STATE_STAY = 1, STATE_FINISH = 2, STATE_OVER = 3;

	final MenuThemeDrawable message = ActiveTheme.smallMessage;

	String display;
	FontCache displayCache;
	BoundedFloat stayTime = new BoundedFloat(2.0F);

	MenuElementShowAnim animation = new MenuElementShowAnim(null);

	@Getter
	int state;

	public ShortMessage(LocalizedMessage display, Object... args) {
		this(true, display, args);
	}

	public ShortMessage(boolean toUppercase, LocalizedMessage display, Object... args) {
		setDisplay(toUppercase, display, args);
	}

	public ShortMessage(String display) {
		setDisplay(display);
	}

	private synchronized FontCache updateCache() {
		if (displayCache == null) {
			displayCache = GameFonts.SmallBorder.getCache(display, 180.0F);
		}
		return displayCache;
	}

	public synchronized void refreshCache() {
		this.displayCache = null;
	}

	public void setDisplay(String display) {
		if (display != null && !display.equals(this.display)) {
			this.display = display;
			refreshCache();
		}
	}

	public void setUnskippable() {
		setStayTime(0.0F);
	}

	public boolean isUnskippable() {
		return stayTime.getMax() == 0.0F;
	}

	public float getScale() {
		return animation.state;
	}

	public void setStayTime(float time) {
		stayTime.setMax(time);
	}

	public void setDisplay(boolean toUppercase, LocalizedMessage display, Object... args) {
		if (display != null) {
			String str = display.format(args);
			if (str != null && toUppercase) {
				str = str.toUpperCase();
			}
			setDisplay(str);
		}
	}

	@Override
	public void init(GameScreen screen) {
		super.init(screen);
		animation.start();
		stayTime.toMin();
		setState(STATE_OPEN);
		screen.input.add(this);
	}

	@Override
	public void show(GameScreen screen) {
		super.show(screen);
		if (state == STATE_OPEN || state == STATE_STAY) {
			screen.input.add(this);
		}
	}

	@Override
	public void hide(GameScreen screen) {
		super.hide(screen);
		screen.input.remove(this);
	}

	/**
	 * Is called when the message should switch states naturally. If the message is
	 * disposed of, do not call this method!
	 *
	 * @param state
	 */
	public void setState(int state) {
		this.state = state;
	}

	@Override
	public byte getLayer() {
		return Layers.LAYER_MESSAGES;
	}

	@Override
	public void update(float delta) {
		animation.visible = (state == STATE_OPEN || state == STATE_STAY);
		animation.update(delta);
		if (state == STATE_OPEN) {
			if (animation.isFinished()) {
				setState(STATE_STAY);
			}
		} else if (state == STATE_STAY) {
			if (stayTime.increase(delta) && stayTime.atMax()) {
				finish();
			}
		} else if (state == STATE_FINISH) {
			if (animation.isFinished()) {
				end();
			}
		}
	}

	public void end() {
		setState(STATE_OVER);
	}

	@Override
	public boolean isHidden() {
		return state == STATE_OVER || !animation.isElementVisible();
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		FontCache cache = updateCache();
		float width = cache.getWidth() + 40, height = cache.getHeight() + 14;
		float x = Cardinal.getWidth() * 0.5F - width * 0.5F, y = Cardinal.getHeight() * 0.5F - height * 0.5F;
		animation.begin(batch, x, y, width, height);
		drawMessage(batch, cache, width, height, x, y);
		animation.end(batch);
	}

	public void drawMessage(CustomSpriteBatch batch, FontCache cache, float width, float height, float x, float y) {
		batch.setColor(ActiveTheme.selectColor);
		message.draw(batch, x, y, width, height);
		Painter.on(batch).at(x + width * 0.5F, y + height * 0.5F).align(0).paint(cache);
		batch.setColor(ActiveTheme.defaultColor);
		message.drawOverlay(batch, x, y, width, height);
	}

	@Override
	public boolean remove() {
		return state == STATE_OVER;
	}

	public void skip() {
		if (state == STATE_STAY && !stayTime.atMin()) {
			finish();
		}
	}

	public void finish() {
		setState(STATE_FINISH);
	}

	@Override
	public boolean triggerButton(ButtonEvent event) {
		if (!isUnskippable() && event.isPressed()) {
			if (event.isKey(KeyCode.MENU_SELECT) || event.isKey(KeyCode.MENU_CANCEL)) {
				skip();
			}
		}
		return true;
	}

	@Override
	public boolean triggerTouch(TouchEvent event) {
		if (!isUnskippable() && event.isReleased() && event.isLeftMouse()) {
			skip();
		}
		return true;
	}

	@Override
	public void dispose() {
		super.dispose();
		screen.input.remove(this);
		state = STATE_OVER;
	}
}
