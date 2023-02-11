package com.bikinger.semih.clicker;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.graphics.data.DrawableLoader;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.endcycle.engine.screens.ScreenObject;

public class StrawHat implements ScreenObject {

	static final Affine2 affine = new Affine2();

	Vector2 position;
	float rotation, speed;
	TextureRegion texture;

	float scale = 0.2F;

	@Override
	public void init(GameScreen screen) {
		texture = DrawableLoader.get().loadTexture("straw_hat.png");
		position = new Vector2(MathUtils.random(0.0F, Cardinal.getWidth()), Cardinal.getHeight() + 100);
		rotation = MathUtils.random(0.0F, 360.0F);
		speed = MathUtils.random(300f, 350f);
	}

	@Override
	public void update(float delta) {
		position.y -= delta * speed;
	}

	@Override
	public boolean remove() {
		return position.y < -100;
	}

	@Override
	public byte getLayer() {
		return Layers.LAYER_1;
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		affine.setToTranslation(position);
		affine.scale(scale, scale);
		affine.rotate(rotation);
		affine.translate(texture.getRegionWidth() * -0.5F, texture.getRegionHeight() * -0.5F);
		batch.setColor(null);
		batch.draw(texture, texture.getRegionWidth(), texture.getRegionHeight(), affine);
	}

}
