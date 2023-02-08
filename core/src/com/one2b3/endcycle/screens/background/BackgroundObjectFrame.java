package com.one2b3.endcycle.screens.background;

import com.badlogic.gdx.graphics.Color;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.graphics.Drawable;
import com.one2b3.endcycle.engine.graphics.DrawableId;
import com.one2b3.endcycle.features.background.BackgroundFrame;
import com.one2b3.endcycle.utils.ID;

public class BackgroundObjectFrame {

	BackgroundFrame data;
	float x, y;
	Drawable drawable;
	float animState;

	public BackgroundObjectFrame(BackgroundFrame data) {
		this.data = data;
		drawable = data.drawable.create();
	}

	public void update(float delta) {
		animState += delta;
		x += data.xSpeed * delta;
		y += data.ySpeed * delta;
		DrawableId id = data.drawable;
		if (drawable == null || !ID.equals(drawable.getId(), id.drawable)) {
			drawable = id.create();
		}
	}

	public void draw(CustomSpriteBatch batch, Color color, float x, float y, float xOfs, float yOfs, float width, float height) {
		if (drawable == null) {
			return;
		}
		xOfs *= data.offsetXFactor;
		yOfs *= data.offsetYFactor;
		xOfs += this.x + data.x + (width * 0.5F * (data.screenHAlign + 1));
		yOfs += this.y + data.y + (height * 0.5F * (data.screenVAlign + 1));
		float repeatX = data.xRepeat, repeatY = data.yRepeat;
		boolean isRepeatX = (repeatX != 0.0F), isRepeatY = (repeatY != 0.0F);
		boolean flipX = repeatX < 0, flipY = repeatY < 0;
		if (flipX) {
			repeatX = -repeatX;
		}
		if (flipY) {
			repeatY = -repeatY;
		}

		Painter painter = Painter.on(batch).color(color).align(data.hAlign, data.vAlign);
		painter.animState(animState + data.animationOffset);
		painter.modulateColor(data.tintModulate, data.tintSpeed);
		float limitX = width + repeatX * 2;
		float limitY = height + repeatY * 2;
		if (isRepeatX && isRepeatY) {
			xOfs %= repeatX;
			yOfs %= repeatY;
			for (float xo = -repeatX * 2; xo <= limitX; xo += repeatX) {
				for (float yo = -repeatY * 2; yo <= limitY; yo += repeatY) {
					painter.at(x + xOfs + (flipX ? limitX - xo : xo), y + yOfs + (flipY ? limitY - yo : yo)).paint(drawable);
				}
			}
		} else if (isRepeatY) {
			yOfs %= repeatY;
			for (float yo = -repeatY * 2; yo <= limitY; yo += repeatY) {
				painter.at(x + xOfs, y + yOfs + (flipY ? limitY - yo : yo)).paint(drawable);
			}
		} else if (isRepeatX) {
			xOfs %= repeatX;
			for (float xo = -repeatX * 2; xo <= limitX; xo += repeatX) {
				painter.at(x + xOfs + (flipX ? limitX - xo : xo), y + yOfs).paint(drawable);
			}
		} else {
			painter.at(x + xOfs, y + yOfs).paint(drawable);
		}
	}
}