package com.one2b3.endcycle.engine.drawing.processors.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.endcycle.engine.screens.RenderList;
import com.one2b3.endcycle.engine.screens.RenderProcessor;
import com.one2b3.endcycle.engine.shaders.ShaderType;

public class ExperimentalRenderProcessor implements RenderProcessor {

	final Matrix4 TEMP = new Matrix4();

	FrameBuffer buffer;

	@Override
	public void init(GameScreen screen) {
		resize(Cardinal.getWidth(), Cardinal.getHeight());
	}

	@Override
	public void resize(int width, int height) {
		dispose();
		buffer = new FrameBuffer(Format.RGBA8888, width * 2, height * 2, false);
		buffer.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
	}

	@Override
	public void dispose() {
		if (buffer != null) {
			buffer.dispose();
			buffer = null;
		}
	}

	@Override
	public void startRendering(CustomSpriteBatch batch, RenderList objects, byte layer, float xOfs, float yOfs) {
		if (layer == Layers.LAYER_0) {
			TEMP.set(batch.getTransformMatrix());
			batch.bind(buffer);
			batch.setScale(2.0F, 2.0F);
			batch.clearTransparent();
		}
	}

	@Override
	public void stopRendering(CustomSpriteBatch batch, byte layer) {
		if (layer == Layers.LAYER_5) {
			batch.unbind();
			batch.setColor(Color.WHITE);
			batch.setTransformMatrix(TEMP);
			batch.setShaderType(ShaderType.EXPERIMENTAL);
			batch.draw(buffer, -batch.getTranslationX(), -batch.getTranslationY(), Cardinal.getWidth(), Cardinal.getHeight());
			batch.setShader(null);
			batch.resetBlendFunction();
		}
	}

}
