package com.one2b3.endcycle.screens.particles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.graphics.Drawable;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.utils.CColor;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC)
public class DissolvingParticleSpawner extends DissolvingParticleManager {

	Drawable[] drawables;
	int amount;
	float originX, originY;

	float spreadX, spreadY;
	float speed = 7.0F;
	float speedRangeX = 70.0F, speedRangeY = 70.0F;

	public DissolvingParticleSpawner(Drawable[] drawables, int amount, float originX, float originY) {
		this.drawables = drawables;
		this.amount = amount;
		this.originX = originX;
		this.originY = originY;
	}

	@Override
	public void init(GameScreen screen) {
		super.init(screen);
		super.burst(amount, speed, speedRangeX, speedRangeY, spreadX, spreadY, drawables);
	}

	public void setColor(Color color) {
		for (int i = 0; i < drawables.length; i++) {
			drawables[i].color.mul(color);
		}
	}

	@Override
	public void draw(Batch batch, float xOfs, float yOfs) {
		super.draw(batch, xOfs + originX, yOfs + originY);
	}

	@Override
	public void draw(CustomSpriteBatch batch, float x, float y, float scaleX, float scaleY, CColor color) {
		super.draw(batch, x + originX, y + originY, scaleX, scaleY, color);
	}
}
