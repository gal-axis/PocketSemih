package com.one2b3.endcycle.screens.menus.elements.text;

import com.badlogic.gdx.math.Matrix4;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.endcycle.engine.screens.ScreenObject;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MenuTextBoxMobilePreview implements ScreenObject {

	static final Matrix4 TRANSFORM = new Matrix4();
	final MenuTextBox box;

	@Override
	public byte getLayer() {
		return Layers.LAYER_INTERFACE_FILTER;
	}

	@Override
	public boolean isHidden() {
		return !Cardinal.isPortable();
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		batch.drawScreenTint(0.4F);
		TRANSFORM.set(batch.getTransformMatrix());
		batch.setScale(batch.getScaleX() * 1.5F, batch.getScaleY() * 1.5F);
		box.draw(batch, (Cardinal.getWidth() - box.getWidth()) * 0.25F - box.calcX(), Cardinal.getHeight() * 0.4F - box.calcY());
		batch.setTransformMatrix(TRANSFORM);
	}

	public boolean touch(int x, int y) {
		x /= 1.5F;
		y /= 1.5F;
		x -= (Cardinal.getWidth() - box.getWidth()) * 0.25F - box.getAbsoluteX();
		y -= Cardinal.getHeight() * 0.4F - box.getAbsoluteY();
		return box.clickText(x, y);
	}

}
