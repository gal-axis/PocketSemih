package com.bikinger.semih.clicker;

import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.endcycle.engine.screens.ScreenObject;
import com.one2b3.endcycle.utils.NumDisplay;

public class SemihPoints implements ScreenObject {

	NumDisplay display = new NumDisplay(100.0F, 0);

	public void increase(int points) {
		display.real += points;
		display.speed = points * 3;
	}

	@Override
	public void update(float delta) {
		display.update(delta);
	}

	@Override
	public byte getLayer() {
		return Layers.LAYER_HUD;
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		Painter.on(batch).at(Cardinal.getWidth() - 5, Cardinal.getHeight() - 5).align(1).font(GameFonts.MonospaceBorder).fontScale(2.0F)
				.paint("Bananas: " + (int) display.val);
	}
}
