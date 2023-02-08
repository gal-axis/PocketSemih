package com.bikinger.semih;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		DisplayMode display = Lwjgl3ApplicationConfiguration.getDisplayMode();
		int scale = Math.max(1, display.height / 234);
		config.setWindowedMode(135 * scale, 234 * scale);
		config.setTitle("Pocket Semih");
		new Lwjgl3Application(new SemihCardinal(), config);
	}
}
