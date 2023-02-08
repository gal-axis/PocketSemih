package com.one2b3.endcycle.engine.ui.messages.select;

import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.graphics.Drawable;
import com.one2b3.endcycle.engine.language.LocalizedMessage;
import com.one2b3.endcycle.engine.ui.MenuUtils;
import com.one2b3.endcycle.features.theme.ActiveTheme;
import com.one2b3.endcycle.features.theme.MenuThemeDrawable;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class SelectMessageButton {

	final MenuThemeDrawable button = ActiveTheme.button;

	final float x, y, width, height;
	final LocalizedMessage text, description;
	final Runnable onSelect;

	public boolean isTouching(float x, float y) {
		return this.x <= x && x <= this.x + this.width && this.y <= y && y <= this.y + this.height;
	}

	public void draw(CustomSpriteBatch batch, Drawable cursor, float xOfs, float yOfs, boolean selected) {
		float x = this.x + xOfs;
		float y = this.y + yOfs;
		batch.setColor(selected ? ActiveTheme.defaultColor : ActiveTheme.disabledColor);
		button.draw(batch, x, y, width, height);
		Painter.on(batch).x(x + width * 0.5F).y(y + height * 0.5F).hAlign(0).vAlign(0).paint(text);
		if (selected) {
			MenuUtils.drawCursor(batch, cursor, x, y, width, height);
		}
		batch.setColor(selected ? ActiveTheme.selectColor : ActiveTheme.disabledColor);
		button.drawOverlay(batch, x, y, width, height);
	}
}