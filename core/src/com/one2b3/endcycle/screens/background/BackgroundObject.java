package com.one2b3.endcycle.screens.background;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.screens.GameScreenObject;
import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.endcycle.features.background.BackgroundData;
import com.one2b3.endcycle.features.background.BackgroundFrame;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC)
public class BackgroundObject extends GameScreenObject implements Background {

	final Matrix4 old = new Matrix4();

	int width = Cardinal.getWidth(), height = Cardinal.getHeight();

	BackgroundData data;

	Array<BackgroundObjectFrame> frames = new Array<>();

	Color color = new Color(Color.WHITE);

	float xOffset, yOffset;

	public BackgroundObject(BackgroundData data) {
		createFrames(data);
	}

	public void createFrames(BackgroundData data) {
		if (data != null) {
			this.data = data;
			frames.clear();
			for (BackgroundFrame frame : data.frames) {
				frames.add(new BackgroundObjectFrame(frame));
			}
		}
	}

	@Override
	public byte getLayer() {
		return Layers.LAYER_0;
	}

	@Override
	public void update(float delta) {
		if (data == null) {
			return;
		}
		int size = data.frames.size();
		while (frames.size < size) {
			BackgroundFrame data = this.data.frames.get(frames.size);
			frames.add(new BackgroundObjectFrame(data));
		}
		while (frames.size > size) {
			frames.removeIndex(frames.size - 1);
		}
		for (BackgroundObjectFrame frame : frames) {
			frame.update(delta);
		}
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		if (data == null) {
			return;
		}
		xOfs += xOffset;
		yOfs += yOffset;
		batch.startOverrideShader();
		drawBackground(batch, xOfs, yOfs);
		batch.stopOverrideShader();
	}

	public void drawBackground(CustomSpriteBatch batch, float xOfs, float yOfs) {
		drawBackground(batch, 0, 0, xOfs, yOfs, width, height);
	}

	public void drawBackground(CustomSpriteBatch batch, float x, float y, float xOfs, float yOfs, float width, float height) {
		Color color = getColor();
		Color bg = data.background;
		if (bg != null && bg.a > 0.0F) {
			batch.drawRectangle(x - 10, y - 10, width + 20, height + 20, bg.r, bg.g, bg.b, bg.a * Math.min(1.0F, color.a));
		}
		for (BackgroundObjectFrame frame : frames) {
			frame.draw(batch, color, x, y, xOfs, yOfs, width, height);
		}
	}

	public Color getColor() {
		return color;
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
	}
}
