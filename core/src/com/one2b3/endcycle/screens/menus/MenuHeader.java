package com.one2b3.endcycle.screens.menus;

import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.graphics.Drawable;
import com.one2b3.endcycle.engine.graphics.DrawableImage;
import com.one2b3.endcycle.engine.graphics.Drawables;
import com.one2b3.endcycle.engine.input.InputListener;
import com.one2b3.endcycle.engine.input.KeyCode;
import com.one2b3.endcycle.engine.input.events.ButtonEvent;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.engine.language.LocalizedMessage;
import com.one2b3.endcycle.engine.language.Unlocalize;
import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.endcycle.engine.screens.ScreenObject;
import com.one2b3.endcycle.features.theme.ActiveTheme;
import com.one2b3.endcycle.utils.bounded.BoundedFloat;

import lombok.Getter;
import lombok.Setter;

public class MenuHeader implements ScreenObject, InputListener {

	public static final String ONLY_SHOW_BACK = ";-+!AAA;";

	@Getter
	final DrawableImage header = ActiveTheme.header, headerBar = ActiveTheme.headerBar,
			headerBack = ActiveTheme.headerBack;
	final Drawable headerBackArrow = Drawables.Menu_Header_Back_Arrow.get();

	public String text;
	public Runnable back;

	@Setter
	LocalizedMessage backText = Unlocalize.get("Back");

	BoundedFloat animation = new BoundedFloat(0.0F, 1.0F, 8.0F);
	boolean selected;

	public float textOffset = 100.0F;

	public MenuHeader(String text, Runnable back) {
		this.text = text;
		this.back = back;
	}

	@Override
	public float getComparisonKey() {
		return 1.0F;
	}

	@Override
	public byte getLayer() {
		return Layers.LAYER_HUD;
	}

	@Override
	public void update(float delta) {
		animation.move(selected, delta);
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		xOfs = yOfs = 0;
		float y = yOfs + Cardinal.getHeight();
		float yScale = 1.0F;
		Painter painter = Painter.on(batch).color(ActiveTheme.tintHeaderBar ? ActiveTheme.selectColor : null).x(xOfs)
				.yScale(yScale);

		if (text != ONLY_SHOW_BACK) {
			painter.y(y).hAlign(-1).vAlign(1).paint(header);
			painter.moveX(header.getWidth(Cardinal.TIME_ACTIVE));
			int w = headerBar.getWidth(Cardinal.TIME_ACTIVE);
			while (w > 0.0F && painter.x < Cardinal.getWidth()) {
				painter.paint(headerBar).moveX(w);
			}
			painter.x(xOfs + textOffset).y(y - 13 * painter.yScale).hAlign(0).vAlign(0).color(ActiveTheme.titleColor)
					.font(GameFonts.Heading);
			painter.yScale(1.0F);
			painter.alpha(0.5F).moveX(1.0F).moveY(-1.0F).paint(text);
			painter.alpha(1.0F).moveX(-1.0F).moveY(1.0F).paint(text);
		}
		painter.yScale(yScale);
		float offset = (1.0F - animation.getVal()) * -30.0F;
		painter.x(xOfs + offset).y(y).align(-1, 1).color(ActiveTheme.tintHeaderBack ? ActiveTheme.selectColor : null)
				.paint(headerBack);
		painter.vAlign(0).moveY(-13 * painter.yScale).yScale(1.0F);
		if (animation.getPercentage() > 0.0F) {
			painter.moveX(22).hAlign(0).color(null).font(GameFonts.TextBorder).alpha(animation.getPercentage())
					.paint(backText);
		}
		painter.x(xOfs + 1).color(ActiveTheme.selectColor).alpha(1.0F - animation.getPercentage()).hAlign(-1)
				.paint(headerBackArrow);
	}

	@Override
	public boolean triggerButton(ButtonEvent event) {
		if (event.isPressed() && event.isKey(KeyCode.MENU_CANCEL)) {
			back.run();
			return true;
		}
		return false;
	}

	@Override
	public boolean triggerTouch(TouchEvent event) {
		if (!event.isScroll()) {
			float width = headerBack.getWidth(Cardinal.TIME_ACTIVE) - 20.0F;
			float height = headerBack.getHeight(Cardinal.TIME_ACTIVE) - 4;
			float y = Cardinal.getHeight() - height;
			boolean touching = event.isIn(0, y, width, height);
			selected = touching && !Cardinal.isPortable();
			if (touching && event.isLeftMouse()) {
				if (event.isReleased()) {
					back.run();
					selected = false;
				}
				return true;
			}
		}
		return false;
	}
}
