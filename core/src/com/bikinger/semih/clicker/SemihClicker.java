package com.bikinger.semih.clicker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.audio.sound.SoundInfo;
import com.one2b3.endcycle.engine.graphics.data.DrawableLoader;
import com.one2b3.endcycle.engine.input.InputListener;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.GameScreenObject;
import com.one2b3.endcycle.screens.menus.Colors;
import com.one2b3.endcycle.utils.Modulator;
import com.one2b3.endcycle.utils.bounded.BoundedFloat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class SemihClicker extends GameScreenObject implements InputListener {

	final SemihPoints points;
	public int clickBananas = 1, surfBananas = 10;

	@Setter
	@Getter
	boolean enabled = true;

	TextureRegion texture;
	BoundedFloat clickAnimation = new BoundedFloat(0.0F, 1.0F, 4.0F);

	@Override
	public void init(GameScreen screen) {
		super.init(screen);
		screen.input.add(this);
		texture = DrawableLoader.get().loadTexture("semih1.png");
	}

	@Override
	public void update(float delta) {
		clickAnimation.decrease(delta);
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		batch.clear();
		float scale = (float) (Cardinal.getHeight() - 10) / texture.getRegionHeight();
		float scaleChange = -Interpolation.swingIn.apply(clickAnimation.getVal()) * 0.1F;
		Affine2 transform = new Affine2();
		transform.translate(Cardinal.getWidth() * 0.5F, 0);
		if (points.isFever()) {
			transform.shear(Modulator.getCosine(-0.2F, 0.2F, 3), 0);
			scaleChange += Math.abs(Modulator.getLinear(-0.06F, 0.06F, 3)) - 0.06F;
			batch.setColor(Colors.temp.set(Color.WHITE).lerp(Colors.rainbow, 0.7F));
		} else {
			batch.setColor(null);
		}
		transform.scale(scale, scale + scaleChange);
		transform.translate(-texture.getRegionWidth() * 0.5F, 0);
		batch.draw(texture, texture.getRegionWidth(), texture.getRegionHeight(), transform);
	}

	@Override
	public boolean triggerTouch(TouchEvent event) {
		if (event.isPressed() && enabled) {
			clickAnimation.toMax();
			int positionX = event.positionX;
			int positionY = event.positionY;
			addBanana(clickBananas, positionX, positionY);
			playSound(new SoundInfo("boom.wav"));
		}
		return InputListener.super.triggerTouch(event);
	}

	public void addBanana(int bananaCount, float positionX, float positionY) {
		bananaCount = points.calcPoints(bananaCount);
		points.showText("+" + bananaCount + " Banana", positionX, positionY);
		int lastPoints = points.getPoints();

		points.increase(bananaCount);

		for (int i = 0, max = Math.min(bananaCount, 100); i < max; ++i) {
			screen.addObject(new SemihBanana(new Vector2(positionX, positionY)));
		}
		int surfFrequency = 40;
		if (points.getPoints() / surfFrequency > lastPoints / surfFrequency) {
			screen.addObject(new SurfSemihEvent(this));
		}
	}

}
