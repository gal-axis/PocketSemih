package com.one2b3.endcycle.engine.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.MathUtils;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.engine.assets.Assets;
import com.one2b3.endcycle.screens.menus.Colors;

public final class InputUtils {

	private InputUtils() {
	}

	public static boolean vibrationsEnabled;

	public static boolean isVibrating(Controller controller) {
		return controller != null && controller.isVibrating();
	}

	public static void vibrateUI() {
		vibrateLast(0.1F, 0.5F);
	}

	public static void vibrateLast(float duration, float strength) {
		vibrate(Cardinal.getInput().getLast(), duration, strength);
	}

	public static void vibrate(Controller controller, float duration, float strength) {
		if (vibrationsEnabled && controller != null) {
			controller.cancelVibration();
			controller.startVibration((int) (duration * 1000), MathUtils.clamp(strength, 0.0F, 1.0F));
		}
	}

	public static void cancelVibrate(Controller controller) {
		if (controller != null) {
			controller.cancelVibration();
		}
	}

	public static void loadCursor(Color color) {
		if (!Cardinal.isPhone()) {
			Pixmap pixmap = new Pixmap(Assets.findHandle("images/cursor.png"));
			for (int x = 0; x < pixmap.getWidth(); x++) {
				for (int y = 0; y < pixmap.getHeight(); y++) {
					pixmap.drawPixel(x, y, Color.rgba8888(Colors.temp(pixmap.getPixel(x, y)).mul(color)));
				}
			}
			Cursor cursor = Gdx.graphics.newCursor(pixmap, 0, 0);
			pixmap.dispose();
			Gdx.graphics.setCursor(cursor);
		}
	}

}
