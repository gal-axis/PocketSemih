package com.one2b3.endcycle.engine.input.binders.bindings.images;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.FontCache;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.graphics.DrawableImage;
import com.one2b3.endcycle.engine.input.InputManager;

public class UnknownButtonImageDrawable extends DrawableImage {

	static final Painter PAINTER = new Painter();
	final FontCache cache;

	public UnknownButtonImageDrawable(int button) {
		String name = "";
		int btn = Math.abs(button);
		if (btn >= InputManager.AXIS_ADDER) {
			int axisNo = (btn - InputManager.AXIS_ADDER) / 2;
			name = "AXIS " + axisNo + "\n";
			if (btn % 2 == 0) {
				name += (button > 0 ? "LEFT" : "RIGHT");
			} else {
				name += (button > 0 ? "UP" : "DOWN");
			}
		} else {
			name = "BTN\n" + button;
		}
		cache = GameFonts.Heading.getCache(name);
	}

	public UnknownButtonImageDrawable(String button) {
		cache = GameFonts.Heading.getCache(button);
	}

	@Override
	public float getWidth() {
		TextureRegion region = ButtonImages.KEYBOARD_EMPTY.getRegion();
		return region.getRegionWidth();
	}

	@Override
	public float getHeight() {
		TextureRegion region = ButtonImages.KEYBOARD_EMPTY.getRegion();
		return region.getRegionHeight();
	}

	@Override
	public int getWidth(double animState) {
		return (int) getWidth();
	}

	@Override
	public int getHeight(double animState) {
		return (int) getHeight();
	}

	@Override
	public void draw(Batch batch, float posX, float posY, float scaleX, float scaleY, double animState, float rotation) {
		TextureRegion region = ButtonImages.KEYBOARD_EMPTY.getRegion();
		batch.setColor(Color.WHITE);
		float w = region.getRegionWidth() * scaleX;
		float h = region.getRegionHeight() * scaleY;
		Painter painter = PAINTER.reset((CustomSpriteBatch) batch).at(posX + w * 0.5F, posY + h * 0.5F).align(0);
		painter.scale(scaleX, scaleY).paint(region);
		float scale = 0.5F * Math.min(w / cache.getWidth(), h / cache.getHeight());
		painter.moveY(painter.yScale).scale(scale).paint(cache);
	}

}
