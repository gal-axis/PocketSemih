package com.one2b3.endcycle.screens.particles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.graphics.Drawable;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.ScreenObject;
import com.one2b3.endcycle.utils.pools.DynamicObjectPool;
import com.one2b3.utils.CColor;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC)
public class DissolvingParticleManager implements ScreenObject {

	static final DynamicObjectPool<DissolvingParticle> particlePool = new DynamicObjectPool<>(DissolvingParticle::new,
			128);

	@Getter
	byte layer;
	@Getter
	float comparisonKey;

	final Array<DissolvingParticle> particles = new Array<>();
	boolean gravity;

	@Override
	public void init(GameScreen screen) {
	}

	public DissolvingParticle[] burst(int amount, float speed, float speedRangeX, float speedRangeY, float spreadX,
			float spreadY, Drawable... drawables) {
		if (drawables == null) {
			return null;
		}
		int d = 0;
		DissolvingParticle[] created = new DissolvingParticle[amount];
		while (d < amount) {
			float speedX = MathUtils.random(-speedRangeX, speedRangeX);
			float speedY = gravity ? MathUtils.random(speedRangeY * 0.3F, speedRangeY)
					: MathUtils.random(-speedRangeY, speedRangeY);
			Drawable drawable = drawables[d % drawables.length];
			DissolvingParticle particle = add(drawable, speed, speedX, speedY);
			float offsetX = (speedRangeX == 0 ? MathUtils.random(-1.0F, 1.0F) : speedX / speedRangeX) * spreadX * 0.5F;
			float offsetY = (speedRangeY == 0 ? MathUtils.random(-1.0F, 1.0F) : speedY / speedRangeY) * spreadY * 0.5F;
			particle.setPosition(offsetX, offsetY);
			created[d] = particle;
			d++;
		}
		return created;
	}

	public DissolvingParticle add(Drawable drawable, float speed, float speedX, float speedY) {
		DissolvingParticle particle = particlePool.getNext();
		particle.init(drawable, speed, speedX, speedY);
		particles.add(particle);
		return particle;
	}

	@Override
	public void update(float delta) {
		for (int i = particles.size - 1; i >= 0; i--) {
			DissolvingParticle particle = particles.get(i);
			particle.update(delta, gravity);
			if (particle.remove()) {
				particles.removeIndex(i);
				particlePool.free(particle);
			}
		}
	}

	@Override
	public boolean remove() {
		return particles.isEmpty();
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		batch.startOverrideShader();
		draw((Batch) batch, xOfs, yOfs);
		batch.stopOverrideShader();
	}

	public void draw(Batch batch, float xOfs, float yOfs) {
		for (int i = 0; i < particles.size; i++) {
			particles.get(i).draw(batch, xOfs, yOfs);
		}
	}

	@Override
	public void dispose() {
		while (particles.size > 0) {
			particlePool.free(particles.pop());
		}
	}

	public void draw(CustomSpriteBatch batch, float x, float y, float scaleX, float scaleY, CColor color) {
		for (int i = 0; i < particles.size; i++) {
			particles.get(i).draw(batch, x, y, scaleX, scaleY);
		}
	}
}
