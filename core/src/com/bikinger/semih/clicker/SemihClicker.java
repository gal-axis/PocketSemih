package com.bikinger.semih.clicker;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.audio.sound.SoundInfo;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.graphics.data.DrawableLoader;
import com.one2b3.endcycle.engine.input.InputListener;
import com.one2b3.endcycle.engine.input.events.ButtonEvent;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.GameScreenObject;
import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.endcycle.screens.menus.Colors;
import com.one2b3.endcycle.utils.Modulator;
import com.one2b3.endcycle.utils.bounded.BoundedFloat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SemihClicker extends GameScreenObject implements InputListener {

	final SemihPoints points;
	public int clickBananas = 1, surfBananas = 10, autoBananas = 100;

	public int surfCounter;

	Vector2 autoClicking;
	int autoPointer;
	float autoClick, autoSpeed;

	BoundedFloat autoMaker;

	@Getter
	boolean enabled = true;

	TextureRegion texture, choked1, choked2;
	BoundedFloat clickAnimation = new BoundedFloat(0.0F, 1.0F, 4.0F);

	BoundedFloat choked = new BoundedFloat(), chokeSound = new BoundedFloat(0.4F);
	int chokeCounter;
	public int chokeWin = 0, stealWin = 0;

	BoundedFloat stealTimer = new BoundedFloat();
	DuendeSemihEvent duende;

	@Override
	public void init(GameScreen screen) {
		super.init(screen);
		screen.input.add(this);
		texture = DrawableLoader.get().loadTexture("semih1.png");
		choked1 = DrawableLoader.get().loadTexture("chokeEvent1.png");
		choked2 = DrawableLoader.get().loadTexture("chokeEvent2.png");
		resetChoked();
	}

	public void resetChoked() {
		choked.toMin();
		choked.setMax(MathUtils.random(20.0F, 25.0F));
		chokeCounter = 20;
	}

	public void resetDuende() {
		stealTimer.toMin();
		stealTimer.setMax(MathUtils.random(20.0F, 25.0F));
		if (duende != null) {
			screen.removeObject(duende);
		}
		duende = null;
	}

	public void decreaseChoked() {
		chokeCounter--;
		if (chokeCounter <= 0) {
			resetChoked();
			if (chokeWin > 0) {
				addBanana(chokeWin, Cardinal.getWidth() * 0.5F, Cardinal.getHeight() * 0.5F, false);
			}
		}
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		autoClicking = null;
	}

	public void upgradeAutoMaker() {
		if (autoMaker == null) {
			autoMaker = new BoundedFloat(5.0F);
		} else {
			autoBananas += 100;
		}
	}

	public void upgradeAutoClick() {
		if (autoSpeed == 0.0F) {
			autoSpeed = 4.0F;
		} else {
			autoSpeed += 1.0F;
		}
	}

	@Override
	public void update(float delta) {
		clickAnimation.decrease(delta);
		if (!enabled) {
			return;
		}
		updateChoked(delta);
		updateDuende(delta);
		if (autoClicking != null) {
			autoClick += delta * autoSpeed;
			while (autoClick > 1.0F) {
				autoClick--;
				addBanana(clickBananas, autoClicking.x, autoClicking.y);
				playSound(new SoundInfo("boom.wav", 0.2F, 1.6F));
			}
		}
		if (autoMaker != null && !autoMaker.increase(delta)) {
			autoMaker.toMin();
			float breadX = Cardinal.getWidth() * 0.5F;
			float breadY = 100.0F;
			addBanana(autoBananas, breadX, 100, false);
			screen.addObject(new SemihBananaBread(new Vector2(breadX, breadY)));
		}
	}

	private void updateChoked(float delta) {
		if (points.getPoints() < 1000) {
			choked.toMin();
			return;
		}
		if (points.isFever()) {
			return;
		}
		choked.increase(delta);
		if (isChoked()) {
			autoClicking = null;
			if (!chokeSound.increase(delta)) {
				chokeSound.setMax(MathUtils.random(0.4F, 0.6F));
				playSound(new SoundInfo("surfCatch.wav", 1.0F, 0.6F + MathUtils.random(0.0F, 0.2F)));
				chokeSound.toMin();
				points.decrease(10);
			}
		}
	}

	private void updateDuende(float delta) {
		if (duende != null && duende.remove()) {
			resetDuende();
		}
		if (points.getPoints() < 5000) {
			stealTimer.toMin();
			return;
		}
		if (points.isFever() || isChoked()) {
			return;
		}
		if (stealTimer.increase(delta) && stealTimer.atMax()) {
			if (duende == null) {
				duende = new DuendeSemihEvent(this);
			}
			addObject(duende);
		}
	}

	public boolean isChoked() {
		return choked.atMax();
	}

	@Override
	public byte getLayer() {
		return Layers.LAYER_2;
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		TextureRegion texture = this.texture;
		if (isChoked()) {
			texture = Cardinal.getTime() % 1.0F > 0.5F ? choked1 : choked2;
		}
		float scale = (float) (Cardinal.getHeight() - 10) / texture.getRegionHeight();
		float scaleChange = -Interpolation.swingIn.apply(clickAnimation.getVal()) * 0.1F;
		Affine2 transform = new Affine2();
		transform.translate(Cardinal.getWidth() * 0.5F, 0);
		if (points.isFever()) {
			transform.shear(Modulator.getCosine(-0.2F, 0.2F, 3), 0);
			scaleChange += Math.abs(Modulator.getLinear(-0.06F, 0.06F, 3)) - 0.06F;
			batch.setColor(Colors.temp.set(Color.WHITE).lerp(Colors.rainbow, 0.7F));
		} else if (isChoked()) {
			float val = Cardinal.getTime() % 0.2F > 0.1F ? 0.6F : 1.0F;
			batch.setColor(1.0F, val, val, 1.0F);
		} else {
			batch.setColor(null);
		}
		transform.scale(scale, scale + scaleChange);
		transform.translate(-texture.getRegionWidth() * 0.5F, 0);
		batch.draw(texture, texture.getRegionWidth(), texture.getRegionHeight(), transform);
		if (isChoked()) {
			Painter.on(batch)
					.at(Cardinal.getWidth() * 0.5F,
							Cardinal.getHeight() * 0.5F + Modulator.getLinear(0.0F, 6.0F, 10.0F))
					.align(0).font(GameFonts.HeadingBorder).paint("Du wirst gechoked!!!\nBefrei dich!!!");
		}
	}

	@Override
	public boolean triggerTouch(TouchEvent event) {
		if (event.isPressed() && enabled) {
			clickAnimation.toMax();
			int positionX = event.positionX;
			int positionY = event.positionY;
			click(event, positionX, positionY);
		} else if (event.isMoved()) {
			if (autoClicking != null && autoPointer == event.pointer) {
				autoClicking.set(event.positionX, event.positionY);
			}
		} else if (event.isReleased()) {
			if (autoPointer == event.pointer)
				autoClicking = null;
		}
		return InputListener.super.triggerTouch(event);
	}

	private void click(TouchEvent event, int positionX, int positionY) {
		if (isChoked()) {
			playSound(new SoundInfo("boom.wav", 0.4F, 1.0F));
			decreaseChoked();
		} else {
			addBanana(clickBananas, positionX, positionY);
			playSound(new SoundInfo("boom.wav", 0.3F, 1.0F));
			startAutoClick(event.pointer, positionX, positionY);
		}
	}

	private void startAutoClick(int pointer, float x, float y) {
		if (autoSpeed > 0.0F && autoPointer != -1) {
			autoPointer = pointer;
			autoClicking = new Vector2(x, y);
			autoClick = 0.0F;
		}
	}

	public void addBanana(int bananaCount, float positionX, float positionY) {
		addBanana(bananaCount, positionX, positionY, true);
	}

	public void addBanana(int bananaCount, float positionX, float positionY, boolean bonus) {
		if (bonus) {
			bananaCount = points.calcPoints(bananaCount);
		}
		points.showText("+" + bananaCount + " Banana", positionX, positionY);

		points.increase(bananaCount);

		for (int i = 0, max = Math.min(bananaCount, 100); i < max; ++i) {
			screen.addObject(new SemihBanana(new Vector2(positionX, positionY)));
		}
		if (bonus) {
			int lastPoints = surfCounter;
			surfCounter += bananaCount;
			int surfFrequency = clickBananas * 100;
			if (surfCounter / surfFrequency > lastPoints / surfFrequency) {
				screen.addObject(new SurfSemihEvent(this));
			}
		}
	}

	@Override
	public boolean triggerButton(ButtonEvent event) {
		if (event.isPressed() && event.buttonId == Keys.SPACE) {
			points.increase(1000000);
		}
		return false;
	}
}
