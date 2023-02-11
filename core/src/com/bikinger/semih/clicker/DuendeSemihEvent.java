package com.bikinger.semih.clicker;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.audio.sound.SoundInfo;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.graphics.data.DrawableLoader;
import com.one2b3.endcycle.engine.input.InputListener;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.GameScreenObject;
import com.one2b3.endcycle.utils.bounded.BoundedFloat;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DuendeSemihEvent extends GameScreenObject implements InputListener {
	Vector2 position;
	TextureRegion texture;
	final SemihClicker clicker;

	int life;
	float scale = 0.15F, stealTimer = 0.0F, stealSpeed = 10.0F;
	BoundedFloat clickAnimation = new BoundedFloat(0.0F, 1.0F, 6.0F);

	float alpha;

	@Override
	public void init(GameScreen screen) {
		super.init(screen);
		screen.input.add(this);
		texture = DrawableLoader.get().loadTexture("duendih.png");
		position = new Vector2(Cardinal.getWidth() + 100, MathUtils.random(0, Cardinal.getHeight() - 60) + 30);
		alpha = 1.0F;
		life = 14;
	}

	public void catchSemih() {
		if (life < 0) {
			return;
		}
		life--;
		playSound(new SoundInfo("surfCatch.wav", 1.0F, 2.0F));
		if (life < 0) {
			clickAnimation.toMin();
			screen.input.remove(this);
			if (clicker.stealWin > 0) {
				clicker.addBanana(clicker.stealWin, position.x, position.y);
			}
		} else {
			clickAnimation.toMax();
		}
	}

	@Override
	public void dispose() {
		screen.input.remove(this);
	}

	@Override
	public void update(float delta) {
		if (life < 0) {
			alpha -= delta;
		} else {
			clickAnimation.decrease(delta);
			stealTimer += delta * stealSpeed;
			while (stealTimer > 1.0F) {
				stealTimer -= 1.0F;
				clicker.points.decrease(4);
			}
			position.x = Cardinal.getWidth() * 0.5F + MathUtils.sin(Cardinal.getTime() * 4) * 70.0F;
			position.y = Cardinal.getHeight() * 0.5F + MathUtils.cos(Cardinal.getTime() * 4) * 70.0F;
		}
	}

	@Override
	public boolean remove() {
		return alpha < 0;
	}

	@Override
	public byte getLayer() {
		return (byte) (clicker.getLayer() + 2);
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		Painter.on(batch).at(position).align(0).scale(scale - clickAnimation.getVal() * 0.4F * scale).alpha(alpha)
				.paint(texture);
		if (life > 0)
			Painter.on(batch).at(position).at(Cardinal.getWidth() * 0.5F, Cardinal.getHeight() * 0.5F - 30).align(0)
					.font(GameFonts.HeadingBorder).paint("Duendih stiehlt\ndeine Bananen!\nTÖTE IHN!!!");
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
