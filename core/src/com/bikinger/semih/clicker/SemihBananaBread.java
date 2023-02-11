package com.bikinger.semih.clicker;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.graphics.data.DrawableLoader;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.Layers;

public class SemihBananaBread extends SemihBanana {

	Vector2 original;
	TextureRegion oma;

	public SemihBananaBread(Vector2 position) {
		super(position);
		original = new Vector2(position);
	}

	@Override
	public void init(GameScreen screen) {
		super.init(screen);
		texture = DrawableLoader.get().loadTexture("bananabread.png");
		oma = DrawableLoader.get().loadTexture("grandmih_empty.png");
	}

	@Override
	public void update(float delta) {
		alpha -= delta;
		position.add(0.0F, 180.0F * delta);
		rotation += delta * 360.0F * 2;
	}

	@Override
	public byte getLayer() {
		return Layers.LAYER_5;
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		Painter.on(batch).at(original).scale(0.1F).alpha(alpha).align(1, 0).paint(oma);
		super.draw(batch, xOfs, yOfs);
	}
}
