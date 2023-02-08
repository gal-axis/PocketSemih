package com.one2b3.endcycle.engine.shaders;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class ShaderManager {

	public static ShaderProgram defaultOverride;

	public ShaderType type;
	public transient ShaderProgram shaderBefore;

	public void begin(Batch batch) {
		ShaderProgram program = (type == null || (type == ShaderType.DEFAULT && defaultOverride != null) ? defaultOverride : type.program);
		if (program != null) {
			if (batch.getShader() != program) {
				shaderBefore = batch.getShader();
				batch.setShader(program);
			} else {
				shaderBefore = null;
			}
		} else {
			shaderBefore = null;
		}
	}

	public void end(Batch batch) {
		if (shaderBefore != null) {
			batch.setShader(shaderBefore);
			shaderBefore = null;
		}
	}
}
