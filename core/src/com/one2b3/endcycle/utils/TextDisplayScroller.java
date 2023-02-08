package com.one2b3.endcycle.utils;

import com.badlogic.gdx.graphics.Color;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.GameFont;
import com.one2b3.endcycle.engine.language.LocalizedMessage;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public final class TextDisplayScroller {

	public float maxWidth;
	public float speed = 40.0F;

	float position;
	float repeatOffset;
	String text;
	GameFont font;
	Color color;

	public TextDisplayScroller(float width) {
		this.maxWidth = width;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setText(String text, GameFont font) {
		this.text = text;
		this.font = font;
		float width = font.getCache(text).getWidth();
		repeatOffset = (width > maxWidth ? width + 30.0F : 0.0F);
		position = -speed;
	}

	public void setText(LocalizedMessage message, GameFont font) {
		setText(message.format(), font);
	}

	public void update(float delta) {
		if (repeatOffset == 0.0F) {
			position = 0.0F;
		} else {
			position += speed * delta;
			if (position > repeatOffset) {
				position = -speed;
			}
		}
	}

	public void draw(Painter painter) {
		painter.moveX(-Math.max(0, position)).font(font).color(color).paint(text);
		if (repeatOffset > 0.0F) {
			painter.moveX(repeatOffset).paint(text);
		}
	}
}
