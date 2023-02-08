package com.one2b3.endcycle.screens.utils;

import com.badlogic.gdx.graphics.Color;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.GameScreenObject;
import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.endcycle.utils.bounded.BoundedFloat;

public class ScreenFlash extends GameScreenObject {

	public Color color = new Color(Color.WHITE);
	public BoundedFloat stay = new BoundedFloat(0.0F);
	public BoundedFloat flash = new BoundedFloat(0.0F, 1.0F, 1.0F);
	public byte layer = Layers.LAYER_MASTER_FILTER;

	public ScreenFlash() {
	}

	public ScreenFlash(float speed) {
		this(0.0F, speed);
	}

	public ScreenFlash(float stay, float speed) {
		this();
		this.stay.setMax(stay);
		if (speed <= 0.0F) {
			flash.setMin(1.0F);
			flash.setSpeed(0.0F);
		} else {
			flash.setSpeed(speed);
		}
	}

	@Override
	public void init(GameScreen screen) {
		super.init(screen);
		flash.toMax();
		stay.toMin();
	}

	@Override
	public void update(float delta) {
		if (stay.increase(delta)) {
			if (stay.atMax()) {
				finishStay();
			}
		} else {
			flash.decrease(delta);
		}
	}

	public void finishStay() {
	}

	@Override
	public boolean remove() {
		return stay.atMax() && flash.atMin();
	}

	@Override
	public byte getLayer() {
		return layer;
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		batch.drawScreenTint(color.r, color.g, color.b, color.a * flash.getVal());
	}

}
