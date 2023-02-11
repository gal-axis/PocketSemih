package com.bikinger.semih.clicker;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.graphics.data.DrawableLoader;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.endcycle.engine.screens.ScreenObject;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SemihBanana implements ScreenObject {

	static final Affine2 affine = new Affine2();

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
	public byte getLayer() {
		return Layers.LAYER_4;
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		affine.setToTranslation(position);
		affine.scale(0.15F, 0.15F);
		affine.rotate(rotation);
		affine.translate(texture.getRegionWidth() * -0.5F, texture.getRegionHeight() * -0.5F);
		batch.setColor(1, 1, 1, alpha);
		batch.draw(texture, texture.getRegionWidth(), texture.getRegionHeight(), affine);
	}
}
