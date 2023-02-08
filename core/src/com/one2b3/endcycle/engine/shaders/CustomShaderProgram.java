package com.one2b3.endcycle.engine.shaders;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class CustomShaderProgram extends ShaderProgram {

	public CustomShaderProgram(String vertexShader, String fragmentShader) {
		super(vertexShader, fragmentShader);
	}

	public CustomShaderProgram(FileHandle vertexShader, FileHandle fragmentShader) {
		super(vertexShader, fragmentShader);
	}

}
