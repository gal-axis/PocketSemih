package com.one2b3.endcycle.engine.ui.messages;

import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.FontCache;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.language.LocalizedMessage;
import com.one2b3.endcycle.engine.language.Unlocalize;

public class ConfirmMessage extends ContentMessage {

	public FontCache cache;

	public ConfirmMessage(final LocalizedMessage text, final MessageAction action) {
		this(text.format(), action);
	}

	public ConfirmMessage(final String text, final MessageAction action) {
		cache = GameFonts.Text.getCache(text, 165.0F);
		width = Math.max(100, cache.getWidth() + message.getLeft() + message.getRight() + 4);
		height = Math.max(40, cache.getHeight() + message.getTop() + message.getBottom() + 4);
		setChoices(1, Unlocalize.get("Yes"), Unlocalize.get("No"));
		setActions(action, null);
		selection = 1;
	}

	@Override
	public void drawContent(CustomSpriteBatch batch, float x, float y, float width, float height) {
		Painter.on(batch).at(x + width * 0.5F, y + height * 0.5F).align(0).paint(cache);
	}
}
