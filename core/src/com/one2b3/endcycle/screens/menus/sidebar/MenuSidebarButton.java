package com.one2b3.endcycle.screens.menus.sidebar;

import com.badlogic.gdx.math.Rectangle;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.FontCache;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.graphics.Drawable;
import com.one2b3.endcycle.engine.graphics.Drawables;
import com.one2b3.endcycle.engine.language.LocalizedMessage;
import com.one2b3.endcycle.screens.menus.elements.MenuElement;
import com.one2b3.endcycle.screens.menus.elements.buttons.MenuButton;
import com.one2b3.endcycle.screens.menus.elements.buttons.MenuButtonAction;
import com.one2b3.endcycle.utils.bounded.BoundedFloat;

public class MenuSidebarButton extends MenuButton implements MenuButtonAction {

	final BoundedFloat position = new BoundedFloat(-6.0F, 0.0F, 100.0F);

	final MenuSidebar sidebar;
	final Drawable foreground;
	final Drawable image;

	final MenuElement element;

	public MenuSidebarButton(MenuSidebar sidebar, LocalizedMessage title, Drawable image, MenuElement element) {
		this.sidebar = sidebar;
		setText(title);
		setTextVAlign(-1);
		setFont(GameFonts.SmallBorder);
		this.element = element;

		this.image = image;
		Drawable button = Drawables.menu_button_bg.get();
		setDrawable(button).setWidth(button.getWidth()).setHeight(button.getHeight());
		foreground = Drawables.menu_button_fg.get();

		setAction(this);
	}

	@Override
	public void onClick() {
		sidebar.select(element, true);
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		boolean isCurrent = element != null && sidebar.currentOpen == element;
		position.move(isFocused() || isCurrent, delta);
		if (!isSelected() && isCurrent) {
			getTaint().increase(delta * 2.0F);
		}
	}

	@Override
	public void draw(CustomSpriteBatch batch, float x, float y) {
		super.draw(batch, x + position.getVal(), y);
	}

	@Override
	public void drawText(Painter painter, LocalizedMessage text) {
		if (text != null) {
			FontCache cache = (painter.font == null ? GameFonts.Text : painter.font).getCache(text.format().toUpperCase(), painter.width);
			painter.xScale(Math.min((getWidth() - 10) / cache.getWidth(), 1.0F));
			painter.moveY(-1 - (getHeight() - getAnimation().getHeight(getHeight())) * 0.5F).paint(cache);
		}
	}

	@Override
	public void drawDrawable(Painter painter) {
		painter.color(getColor());
		super.drawDrawable(painter);
		painter.color(null).paint(foreground);
		painter.align(0).moveX(2).moveY(4).paint(image);
	}

	@Override
	public void setCursor(Rectangle rectangle) {
		rectangle.set(getAbsoluteX() + 4 + position.getVal(), getAbsoluteY() + 2, getWidth() - 4, getHeight());
	}
}
