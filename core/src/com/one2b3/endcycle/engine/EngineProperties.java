package com.one2b3.endcycle.engine;

import com.badlogic.gdx.files.FileHandle;
import com.one2b3.endcycle.core.GameProperties;
import com.one2b3.endcycle.engine.assets.Assets;
import com.one2b3.endcycle.engine.files.Data;

public class EngineProperties {

	public static final Platform PLATFORM = Platform.valueOf(GameProperties.getStringProperty("Game.Platform", Platform.PROD.name()));

	public static final boolean USE_ATLAS = GameProperties.getBooleanProperty("Game.UseAtlas", true);

	public static final int CLIENT_VERSION;
	public static final String VERSION_NAME;
	public static String CAPTURE_FOLDER = GameProperties.getStringProperty("Game.Capture");

	public enum Platform {
		DEV, PROD;
	}

	static {
		FileHandle handle = Assets.findHandle("version.txt");
		if (handle.exists()) {
			String[] version = Data.readLines(handle);
			VERSION_NAME = version[0];
			CLIENT_VERSION = Integer.parseInt(version[1]);
		} else {
			VERSION_NAME = "Unknown";
			CLIENT_VERSION = -1;
		}
	}
}
