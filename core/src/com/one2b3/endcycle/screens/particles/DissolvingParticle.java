package com.one2b3.endcycle.screens.particles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.graphics.Drawable;
import com.one2b3.endcycle.engine.screens.ScreenObject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class DissolvingParticle implements ScreenObject {

	static final Color color = new Color(Color.WHITE);

	Drawable drawable;
	float x, y;
	float speed;
	float speedX, speedY;
	float fade;
	float state;

	@Getter
	byte layer;
	@Getter
	float comparisonKey;
	boolean gravity;

	public DissolvingParticle init(Drawable drawable, float speed, float speedX, float speedY) {
		this.state = 0.0F;
		this.x = 0.0F;
		this.y = 0.0F;
		this.drawable = drawable;
		this.speed = speed;
		this.speedX = speedX;
		this.speedY = speedY;
		fade = MathUtils.random(2.0F, 4.0F);
		return this;
	}

	public DissolvingParticle setPosition(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	@Override
	public void update(float delta) {
		update(delta, gravity);
	}

	public void update(float delta, boolean gravity) {
		fade -= speed * delta;
		state += delta;
		x += speedX * delta;
		y += speedY * delta;
		if (gravity) {
			speedY -= 150.0F * delta;
		}
	}

	@Override
	public boolean remove() {
		return fade < 0.0F || (speed == 0.0F && state > drawable.getAnimationDuration());
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		draw((Batch) batch, xOfs, yOfs);
	}

	public void draw(Batch batch, float xOfs, float yOfs) {
		draw(batch, xOfs, yOfs, 1, 1);
	}

	public void draw(Batch batch, float xOfs, float yOfs, float xScale, float yScale) {
		color.a = Math.min(fade, 1.0F);
		drawable.draw(batch, xOfs + x, yOfs + y, xScale, yScale, state, 0, 0, color);
	}
}