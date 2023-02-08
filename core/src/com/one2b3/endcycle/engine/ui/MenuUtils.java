package com.one2b3.endcycle.engine.ui;

import com.badlogic.gdx.graphics.Color;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.graphics.Drawable;
import com.one2b3.endcycle.features.theme.ActiveTheme;

public final class MenuUtils {

	private MenuUtils() {
	}

	public static void drawCursor(CustomSpriteBatch batch, Drawable cursor, float x, float y, float width, float height) {
		drawCursor(batch, cursor, x, y, width, height, Cardinal.TIME_ACTIVE);
	}

	public static void drawCursor(CustomSpriteBatch batch, Drawable cursor, float x, float y, float width, float height, double animState) {
		drawCursor(batch, cursor, ActiveTheme.selectColor, x, y, width, height, animState);
	}

	public static void drawCursor(CustomSpriteBatch batch, Drawable cursor, Color color, float x, float y, float width, float height,
			double animState) {
		cursor.draw(batch, x, y + height, 1.0F, 1.0F, animState, -1, 1, color);
		cursor.draw(batch, x + width, y + height, -1.0F, 1.0F, animState, -1, 1, color);
		cursor.draw(batch, x, y, 1.0F, -1.0F, animState, -1, 1, color);
		cursor.draw(batch, x + width, y, -1.0F, -1.0F, animState, -1, 1, color);
	}
}
