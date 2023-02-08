package com.one2b3.endcycle.screens.screenshot;

import com.badlogic.gdx.graphics.Color;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.FontCache;
import com.one2b3.endcycle.engine.fonts.GameFont;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.endcycle.engine.screens.ScreenObject;
import com.one2b3.endcycle.utils.bounded.BoundedFloat;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC)
public class NotificationDisplayer implements ScreenObject {

	final FontCache cache;
	Color tint = Color.WHITE;

	BoundedFloat timer = new BoundedFloat(1.0F);
	BoundedFloat pathFadeIn = new BoundedFloat(0.0F, 1.0F, 5.0F);

	public NotificationDisplayer(String text, GameFont font) {
		cache = font.getCache(text);
	}

	@Override
	public void init(GameScreen screen) {
		pathFadeIn.toMin();
		timer.toMax();
	}

	@Override
	public void update(float delta) {
		timer.increase(delta);
		pathFadeIn.move(!timer.atMax(), delta);
	}

	@Override
	public boolean remove() {
		return false;
	}

	@Override
	public boolean isHidden() {
		return pathFadeIn.atMax();
	}

	@Override
	public byte getLayer() {
		return Layers.LAYER_INTERFACE_FILTER;
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		float x = (cache.getWidth() + 10.0F) * pathFadeIn.getVal(), y = Cardinal.getHeight() - 5.0F;
		Painter.on(batch).at(x, y).align(1).color(tint).paint(cache);
	}

}
