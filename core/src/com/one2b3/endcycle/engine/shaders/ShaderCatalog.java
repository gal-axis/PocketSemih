package com.one2b3.endcycle.engine.shaders;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.one2b3.endcycle.engine.assets.GameLoader;

public final class ShaderCatalog {

	public static final String PATH = "shaders/";

	public static void load(GameLoader loader) {
		ShaderProgram.pedantic = false;
		for (ShaderType type : ShaderType.values()) {
			loader.loadAsync(type);
		}
	}
}
