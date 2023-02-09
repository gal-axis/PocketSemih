package com.bikinger.semih.clicker;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.graphics.data.DrawableLoader;
import com.one2b3.endcycle.engine.input.InputListener;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.GameScreenObject;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SurfSemihEvent extends GameScreenObject implements InputListener {
	Vector2 position;
	float speed;
	TextureRegion texture;
	final SemihClicker clicker;

	float scale = 0.3F;

	boolean caught;
	float alpha;

	@Override
	public void init(GameScreen screen) {
		super.init(screen);
		screen.input.add(this);
		texture = DrawableLoader.get().loadTexture("surfih1.png");
		position = new Vector2(Cardinal.getWidth() + 100, MathUtils.random(0, Cardinal.getHeight() - 60) + 30);
		speed = MathUtils.random(300f, 350f);
		alpha = 1.0F;
	}

	public void catchSemih() {
		if (caught) {
			return;
		}
		screen.input.remove(this);
		texture = DrawableLoader.get().loadTexture("surfih2.png");
		clicker.addBanana(clicker.surfBananas, position.x, position.y);
		caught = true;
	}

	@Override
	public void dispose() {
		screen.input.remove(this);
	}

	@Override
	public void update(float delta) {
		if (caught) {
			alpha -= delta;
		} else {
			position.x -= delta * speed;
		}
	}

	@Override
	public boolean remove() {
		return position.x < -100 || alpha < 0;
	}

	@Override
	public byte getLayer() {
		return (byte) (clicker.getLayer() + 1);
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		Painter.on(batch).at(position).align(0).scale(scale).alpha(alpha).paint(texture);
	}

	@Override
	public boolean triggerTouch(TouchEvent event) {
		if (!clicker.isEnabled()) {
			return false;
		}
		float width = texture.getRegionWidth() * scale;
		float height = texture.getRegionHeight() * scale;
		if (event.isPressed() && event.isIn(position.x - width * 0.5F, position.y - height * 0.5F, width, height)) {
			catchSemih();
			return true;
		}
		return false;
	}
}
