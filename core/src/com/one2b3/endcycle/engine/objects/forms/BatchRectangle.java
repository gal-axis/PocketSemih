package com.one2b3.endcycle.engine.objects.forms;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.graphics.data.DrawableLoader;
import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.endcycle.engine.screens.ScreenObject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC)
@Accessors(chain = true)
public class BatchRectangle implements ScreenObject {

	float x, y;
	float width, height;
	Color tint = new Color(Color.WHITE);

	@Getter
	byte layer;

	public BatchRectangle(int x, int y, byte layer, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.layer = layer;
	}

	public BatchRectangle(int x, int y, byte layer, float width, float height, float r, float g, float b, float a) {
		this(x, y, layer, width, height);
		setTint(r, g, b, a);
	}

	public BatchRectangle() {
		this(Layers.LAYER_0);
	}

	public BatchRectangle(float width, float height) {
		this(0, 0, Layers.LAYER_0, width, height);
	}

	public BatchRectangle(byte layer) {
		this(0, 0, layer, 1, 1);
	}

	public BatchRectangle(float r, float g, float b, float a) {
		this(0, 0, Layers.LAYER_0, 0, 0, r, g, b, a);
	}

	public void setTint(Color color) {
		this.tint.set(color);
	}

	public void setTint(float r, float g, float b, float a) {
		this.tint.set(r, g, b, a);
	}

	public float getTintR() {
		return tint.r;
	}

	public float getTintG() {
		return tint.g;
	}

	public float getTintB() {
		return tint.b;
	}

	public float getTintA() {
		return tint.a;
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		draw(batch, xOfs, yOfs, 1.0F, 1.0F);
	}

	public void draw(Batch batch, float xOfs, float yOfs, float scaleX, float scaleY) {
		TextureRegion region = DrawableLoader.get().loadTexture("white.png");
		if (region != null && batch.isDrawing()) {
			batch.setColor(tint);
			batch.draw(region, x + xOfs, y + yOfs, scaleX * width, scaleY * height);
		}
	}

	@Override
	public boolean remove() {
		return false;
	}

	@Override
	public void dispose() {
	}
}
