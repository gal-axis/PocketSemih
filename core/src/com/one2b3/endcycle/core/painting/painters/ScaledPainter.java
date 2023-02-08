package com.one2b3.endcycle.core.painting.painters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.MathUtils;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.core.painting.GamePainter;
import com.one2b3.endcycle.engine.graphics.DrawableImage;
import com.one2b3.endcycle.engine.screens.GameScreen;

import lombok.Getter;

public class ScaledPainter implements GamePainter {

	final CustomSpriteBatch batch;

	int preferredWidth, preferredHeight;
	int maxWidth, maxHeight;

	@Getter
	FrameBuffer frameBuffer;

	@Getter
	int scale = 4;
	@Getter
	float marginX, marginY, realScale;

	public ScaledPainter(CustomSpriteBatch batch, int width, int height, int maxWidth, int maxHeight) {
		this.batch = batch;
		setPreferredSize(width, height, maxWidth, maxHeight);
	}

	public void setPreferredSize(int width, int height, int maxWidth, int maxHeight) {
		this.preferredWidth = width;
		this.preferredHeight = height;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void setScale(int scale) {
		this.scale = scale;
		updateFrameBuffer();
	}

	public void updateFrameBuffer() {
		if (frameBuffer != null) {
			frameBuffer.dispose();
			frameBuffer = null;
		}
		if (scale > 1) {
			scale = MathUtils.ceil(Math.max(Gdx.graphics.getHeight() / (float) Cardinal.getHeight(),
					Gdx.graphics.getWidth() / (float) Cardinal.getWidth()));
			// We need to only go for 2x, 4x, 6x or 8x
			scale = MathUtils.ceil(MathUtils.clamp(scale, 2, 8) * 0.5F) * 2;
			frameBuffer = new FrameBuffer(Format.RGBA8888, Cardinal.getWidth() * scale, Cardinal.getHeight() * scale, false);
			frameBuffer.getColorBufferTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		} else {
			scale = 1;
		}
		DrawableImage.useDirect = (scale == 1);
	}

	@Override
	public void draw(GameScreen screen) {
		try {
			start();
			if (screen != null) {
				screen.draw(batch);
			}
			stop();
		} catch (Throwable throwable) {
			batch.reset();
			throw throwable;
		}
	}

	public void start() {
		batch.resetBlendFunction();
		batch.enableBlending();
		batch.setShader(null);
		batch.begin();
		if (frameBuffer != null) {
			batch.setScale(scale, scale);
			batch.bind(frameBuffer);
		} else {
			float w = Gdx.graphics.getWidth() - marginX * 2, h = Gdx.graphics.getHeight() - marginY * 2;
			batch.updateView(null);
			batch.setScaleTranslation(marginX / realScale, marginY / realScale, realScale, realScale);
			batch.startMasking(0, 0, w / realScale, h / realScale);
		}
	}

	public void stop() {
		if (frameBuffer != null) {
			batch.unbind();
			batch.setColor(Color.WHITE);
			batch.setScale(1.0F, 1.0F);
			batch.setShader(null);
			batch.draw(frameBuffer, marginX, marginY, Gdx.graphics.getWidth() - marginX * 2, Gdx.graphics.getHeight() - marginY * 2);
		} else {
			batch.stopMasking();
		}
		batch.reset();
	}

	@Override
	public void resize(int width, int height) {
		int localWidth, localHeight;
		marginX = marginY = 0.0F;
		float hScale = (float) height / preferredHeight;
		float wScale = (float) width / preferredWidth;
		if (hScale >= wScale) {
			localHeight = Math.min(maxHeight, MathUtils.round(height / wScale));
			localWidth = preferredWidth;
			realScale = wScale;
			marginY = (height - localHeight * realScale) * 0.5F;
		} else {
			localWidth = Math.min(maxWidth, MathUtils.round(width / hScale));
			localHeight = preferredHeight;
			realScale = hScale;
			marginX = (width - localWidth * realScale) * 0.5F;
		}
		Cardinal.setSize(localWidth, localHeight);
		updateFrameBuffer();
	}

	@Override
	public void changeOrientation(boolean landscape) {
	}

	@Override
	public int toLocalX(int x) {
		return MathUtils.ceil((x - marginX) / realScale);
	}

	@Override
	public int toLocalY(int y) {
		return MathUtils.ceil((y - marginY) / realScale);
	}

}
