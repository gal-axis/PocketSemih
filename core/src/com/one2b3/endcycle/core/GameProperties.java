package com.one2b3.endcycle.core;

import java.io.IOException;
import java.util.Properties;

import com.badlogic.gdx.files.FileHandle;
import com.one2b3.endcycle.engine.assets.Assets;

public class GameProperties {

	static final Properties PROPERTIES = new Properties();

	public static void load() {
		loadProperties("game.properties");
		loadProperties("server.properties");
		loadProperties("beta.properties");
	}

	public static void loadProd() {
		loadProperties("prod.properties");
	}

	public static void loadDatabase() {
		loadProperties("database.properties");
	}

	private static void loadProperties(String path) {
		FileHandle handle = Assets.findHandle(path);
		if (handle.exists()) {
			try {
				PROPERTIES.load(handle.read());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static int getIntProperty(String key) {
		return getIntProperty(key, -1);
	}

	public static int getIntProperty(String key, int defaultValue) {
		String property = PROPERTIES.getProperty(key);
		return (property == null || property.length() == 0 ? defaultValue : Integer.parseInt(property));
	}

	public static long getLongProperty(String key) {
		String property = PROPERTIES.getProperty(key);
		return (property == null || property.length() == 0 ? -1L : Long.parseLong(property));
	}

	public static boolean getBooleanProperty(String key) {
		return getBooleanProperty(key, false);
	}

	public static boolean getBooleanProperty(String key, boolean defaultValue) {
		String property = PROPERTIES.getProperty(key);
		return (property == null || property.length() == 0 ? defaultValue : Boolean.parseBoolean(property));
	}

	public static String getStringProperty(String key) {
		return PROPERTIES.getProperty(key);
	}

	public static String getStringProperty(String key, String defaultValue) {
		return PROPERTIES.getProperty(key, defaultValue);
	}
}
