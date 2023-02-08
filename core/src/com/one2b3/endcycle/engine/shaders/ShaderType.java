package com.one2b3.endcycle.engine.shaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.one2b3.endcycle.engine.assets.GameAsset;
import com.one2b3.endcycle.engine.assets.GameLoader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ShaderType implements GameAsset {

	DEFAULT("default"), //
	RECOLOR("recolor"), //
	GRAYSCALE("grayscale"), //
	INVERT("invert"), //
	TOWER("tower"), //
	EXPERIMENTAL("experimental"), //
	;

	public final String filename;

	public ShaderProgram program;

	@Override
	public void load(GameLoader loader) {
		dispose();
		FileHandle vertexFile = loader.resolve(ShaderCatalog.PATH + filename + ".vert");
		FileHandle fragmentFile = loader.resolve(ShaderCatalog.PATH + filename + ".frag");
		program = new CustomShaderProgram(vertexFile, fragmentFile);
		if (!program.isCompiled()) {
			Gdx.app.error("Shader", "ShaderProgram " + filename + " failed to compile:\n" + program.getLog());
			dispose();
		}
	}

	@Override
	public void dispose() {
		if (program != null) {
			program.dispose();
			program = null;
		}
	}

}
