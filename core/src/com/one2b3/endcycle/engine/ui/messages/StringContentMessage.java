package com.one2b3.endcycle.engine.ui.messages;

import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.FontCache;
import com.one2b3.endcycle.engine.fonts.GameFont;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.language.LocalizedMessage;

public class StringContentMessage extends ContentMessage {

	FontCache contentCache;
	String content;
	GameFont font;
	public int hAlign = 0;

	public StringContentMessage() {
	}

	public StringContentMessage(LocalizedMessage content) {
		this(content.format());
	}

	public StringContentMessage(String content) {
		setContent(content);
		setSizeFromCache(8.0F, 8.0F);
	}

	public void setContent(String content) {
		this.content = content;
		updateCache();
	}

	public void setFont(GameFont font) {
		this.font = font;
		updateCache();
	}

	private void updateCache() {
		contentCache = (font == null ? GameFonts.Text : font).getCache(content);
	}

	public void setSizeFromCache(float paddingX, float paddingY) {
		width = contentCache.getWidth() + paddingX * 2;
		height = contentCache.getHeight() + paddingY * 2;
	}

	@Override
	public void drawContent(CustomSpriteBatch batch, float x, float y, float width, float height) {
		if (content != null) {
			Painter.on(batch).at(x + width * 0.5F * (hAlign + 1), y + height * 0.5F).align(hAlign, 0).font(font).width(width)
					.paint(content);
		}
	}

}
