package com.bikinger.semih.clicker;

import java.text.NumberFormat;

import com.badlogic.gdx.graphics.Color;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.audio.music.MusicData;
import com.one2b3.endcycle.engine.audio.music.MusicHandler;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.objects.visuals.StringDisplay;
import com.one2b3.endcycle.engine.objects.visuals.StringDisplayAnimation;
import com.one2b3.endcycle.engine.objects.visuals.StringDisplayFactory;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.GameScreenObject;
import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.endcycle.screens.menus.Colors;
import com.one2b3.endcycle.utils.Modulator;
import com.one2b3.endcycle.utils.NumDisplay;
import com.one2b3.endcycle.utils.bounded.BoundedFloat;

public class SemihPoints extends GameScreenObject {

	public int getPoints() {
		return (int) display.real;
	}

	public int multiplier = 1, maxMultiplier = 3, combo, comboRequired = 60;
	public BoundedFloat multiplierTimer = new BoundedFloat(0.0F, 1.0F, 1.0F);
	public BoundedFloat fever = new BoundedFloat(6.0F);
	public float experience;
	NumDisplay display = new NumDisplay(100.0F, 0);

	MusicData normalSong = new MusicData("music/normal.ogg", 0.0F);
	MusicData feverSong = new MusicData("music/fever.ogg", 0.0F);
	MusicData finalSong = new MusicData("music/we_are.ogg", -1.0F);

	BoundedFloat finale;

	@Override
	public void init(GameScreen screen) {
		normalSong.id.id = 1;
		finalSong.id.id = 2;
		finalSong.next = normalSong.id;
		super.init(screen);
		playNormal();
	}

	public void buyFinal() {
		if (isFinal()) {
			return;
		}
		finale = new BoundedFloat(0.1F);
		fever.setMax(4 * 60);
		startFever();
	}

	private boolean isFinal() {
		return finale != null;
	}

	private void playNormal() {
		MusicHandler.instance.play(normalSong);
	}

	public void increase(int points) {
		display.real += points;
		if (display.real - display.val < 4.0F) {
			display.speed = 1000.0F;
		} else {
			display.speed = (display.real - display.val) * 3.0F;
		}
		increaseMultiplier();
		increaseCombo();
	}

	public boolean decrease(int points) {
		if (points > getPoints()) {
			return false;
		}
		display.real -= points;
		display.sync();
		return true;
	}

	private void increaseMultiplier() {
		multiplierTimer.toMax();
		if (multiplier < maxMultiplier) {
			multiplier += 1;
			multiplierTimer.setSpeed(Math.min(2.0F, multiplier * 0.4F));
		}
	}

	private void increaseCombo() {
		if (!isFever()) {
			combo++;
			if (combo >= comboRequired) {
				startFever();
			}
		}
	}

	private void startFever() {
		fever.toMax();
		combo = 0;
		MusicHandler.instance.play(isFinal() ? finalSong : feverSong, true);
	}

	public boolean isFever() {
		return !fever.atMin();
	}

	public int calcPoints(int points) {
		return points * multiplier * (isFever() ? 3 : 1);
	}

	@Override
	public void update(float delta) {
		display.update(delta);
		if (!multiplierTimer.decrease(delta)) {
			multiplier = 1;
		}
		if (fever.decrease(delta) && fever.atMin()) {
			fever.setMax(6.0F);
			finale = null;
			playNormal();
		}
		if (finale != null && !finale.decrease(delta)) {
			screen.addObject(new StrawHat());
			finale.toMax();
		}
	}

	@Override
	public byte getLayer() {
		return Layers.LAYER_HUD;
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		// Multiplier
		float multiplierX = 5, multiplierY = 5;
		float multiplierWidth = Cardinal.getWidth() - multiplierX * 2, multiplierHeight = 20;
		batch.drawRectangle(multiplierX, multiplierY, multiplierWidth, multiplierHeight, Color.BLACK);
		batch.drawRectangle(multiplierX, multiplierY, multiplierWidth * multiplierTimer.getVal(), multiplierHeight,
				Color.RED);
		Painter.on(batch).at(multiplierX + multiplierWidth * 0.5F, multiplierY + multiplierHeight * 0.5F).align(0)
				.font(GameFonts.MonospaceBorder).paint(multiplier + "x Bananaplier");

		// Bananas
		int bananas = (int) display.val;
		String bananaStr;
//		if (bananas > 1000000) {
//			bananaStr = String.format("%.2fm", bananas / 1000000.0F);
//		} else if (bananas > 1000) {
//			bananaStr = String.format("%.2fk", bananas / 1000.0F);
//		} else {
		bananaStr = NumberFormat.getNumberInstance().format(bananas);
//		}
		Painter.on(batch).at(Cardinal.getWidth() - multiplierX, Cardinal.getHeight() - 5).align(1)
				.font(GameFonts.MonospaceBorder).fontScale(1.5F).paint("Bananas: " + bananaStr);

		// Fever
		float feverY = 30.0F;
		batch.drawRectangle(multiplierX, feverY, multiplierWidth, multiplierHeight, Color.BLACK);
		batch.drawRectangle(multiplierX, feverY,
				multiplierWidth * (isFever() ? fever.getPercentage() : (float) combo / comboRequired), multiplierHeight,
				(isFever() ? Colors.rainbow : Color.SKY));
		if (isFever()) {
			String finalString = "FEVER! 3x POINTS!";
			Painter.on(batch).at(Cardinal.getWidth() * 0.5F, feverY + multiplierHeight * 0.5F)
					.moveY(Modulator.getLinear(0, 5, 10)).align(0).font(GameFonts.MonospaceBorder).fontScale(2.0F)
					.color(Colors.rainbow).paint(finalString);
		} else {
			Painter.on(batch).at(multiplierX + multiplierWidth * 0.5F, feverY + multiplierHeight * 0.5F).align(0)
					.font(GameFonts.MonospaceBorder).paint("Banana Fever");
		}
		if (isFinal()) {
			Painter.on(batch).at(Cardinal.getWidth() * 0.5F, Cardinal.getHeight() - 65).align(0)
					.font(GameFonts.MonospaceBorder).fontScale(2.0F).paint("Happy Birthday\nSemih!!!");
		}
	}

	public void showText(String text, float positionX, float positionY) {
		StringDisplay spawn = StringDisplayFactory.spawn(text, positionX, positionY);
		spawn.font = GameFonts.TitleBorder;
		spawn.animation = StringDisplayAnimation.BOUNCE;
		spawn.layer = Layers.LAYER_MESSAGES;
		spawn.setCharacterSpeed(10.0F);
		screen.addObject(spawn);
	}
}
