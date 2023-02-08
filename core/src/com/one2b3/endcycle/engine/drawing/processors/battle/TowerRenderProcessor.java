package com.one2b3.endcycle.engine.drawing.processors.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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

public class TowerRenderProcessor implements RenderProcessor {

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
		if (layer == Layers.LAYER_7) {
			TEMP.set(batch.getTransformMatrix());
			batch.bind(buffer);
			batch.setScaleTranslation(batch.getTranslationX() * 0.5F, batch.getTranslationY() * 0.5F, 2.0F, 2.0F);
			batch.clearTransparent();
		}
	}

	@Override
	public void stopRendering(CustomSpriteBatch batch, byte layer) {
		if (layer == Layers.LAYER_7) {
			batch.unbind();
			batch.setColor(Color.WHITE);
			batch.setTransformMatrix(TEMP);
			batch.setShaderType(ShaderType.TOWER);
			batch.setBlendFunction(GL20.GL_SRC_ALPHA_SATURATE, GL20.GL_ONE, GL20.GL_ONE, GL20.GL_ONE);
			batch.draw(buffer, -batch.getTranslationX(), -batch.getTranslationY(), Cardinal.getWidth(), Cardinal.getHeight());
			batch.setShader(null);
			batch.resetBlendFunction();
		}
	}

}
