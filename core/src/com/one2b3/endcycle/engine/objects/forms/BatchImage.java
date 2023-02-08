package com.one2b3.endcycle.engine.objects.forms;

import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.graphics.Drawable;
import com.one2b3.endcycle.engine.graphics.DrawableState;
import com.one2b3.endcycle.engine.screens.ScreenObject;

public class BatchImage extends DrawableState implements ScreenObject {
	static final int SQ_WIDTH = 32, SQ_HEIGHT = 24;

	byte layer;
	boolean hide;

	public BatchImage(Drawable d, byte layer) {
		this(d, layer, 0, 0);
	}

	public BatchImage(Drawable d, byte layer, float x, float y, int hAlign, int vAlign) {
		this(d, layer, x, y);
		this.hAlign = hAlign;
		this.vAlign = vAlign;
	}

	public BatchImage(Drawable d, byte layer, float x, float y) {
		super(d, x, y);
		setLayer(layer);
	}

	public void setLayer(byte layer) {
		this.layer = layer;
	}

	@Override
	public byte getLayer() {
		return layer;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

	@Override
	public boolean isHidden() {
		return hide;
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		super.draw(batch, xOfs, yOfs);
	}

	@Override
	public void dispose() {
	}
}
