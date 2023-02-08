package com.one2b3.endcycle.engine.drawing.processors.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.endcycle.engine.screens.RenderList;
import com.one2b3.endcycle.engine.screens.RenderProcessor;

public class DownscaleRenderProcessor implements RenderProcessor {

	static final float DOWNSCALE = 0.666F;
	final Matrix4 TEMP = new Matrix4();

	FrameBuffer buffer;

	@Override
	public void init(GameScreen screen) {
		resize(Cardinal.getWidth(), Cardinal.getHeight());
	}

	@Override
	public void resize(int width, int height) {
		dispose();
		buffer = new FrameBuffer(Format.RGBA8888, MathUtils.ceil(width * DOWNSCALE), MathUtils.ceil(height * DOWNSCALE), false);
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
		if (layer == Layers.LAYER_4) {
			TEMP.set(batch.getTransformMatrix());
			batch.bind(buffer);
			batch.setScale(DOWNSCALE, DOWNSCALE);
			batch.clearTransparent();
		}
	}

	@Override
	public void stopRendering(CustomSpriteBatch batch, byte layer) {
		if (layer == Layers.LAYER_5) {
			batch.unbind();
			batch.setTransformMatrix(TEMP);
			batch.setColor(Color.WHITE);
			batch.draw(buffer, 0, 0, Cardinal.getWidth(), Cardinal.getHeight());
		}
	}

}
