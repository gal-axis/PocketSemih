package com.bikinger.semih.clicker;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.graphics.data.DrawableLoader;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.ScreenObject;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SemihBanana implements ScreenObject {
	final Vector2 position;
	float speed, angle, alpha, rotation;
	TextureRegion texture;

	@Override
	public void init(GameScreen screen) {
		texture = DrawableLoader.get().loadTexture("banana" + MathUtils.random(1, 4) + ".png");
		speed = MathUtils.random(100f, 200f);
		angle = MathUtils.random(0f, 360f);
		rotation = MathUtils.random(0f, 360f);
		alpha = 1.0f;
	}

	@Override
	public void update(float delta) {
		alpha -= delta;
		Vector2 offset = new Vector2(speed * delta, 0);
		offset.rotateDeg(angle);
		position.add(offset);
	}

	@Override
	public boolean remove() {
		return alpha < 0;
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		Painter.on(batch).at(position).align(0).alpha(alpha).scale(0.25f).paint(texture);
	}
}
