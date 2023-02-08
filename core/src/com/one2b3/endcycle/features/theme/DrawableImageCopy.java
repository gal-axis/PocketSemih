package com.one2b3.endcycle.features.theme;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.one2b3.endcycle.engine.graphics.DrawableImage;
import com.one2b3.endcycle.engine.graphics.DrawableImageFrame;
import com.one2b3.utils.DrawableAnimation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class DrawableImageCopy extends DrawableImage {

	static final Color temp = new Color();
	public DrawableImage image;
	public Color color;

	@Override
	public NinePatch getNinePatch() {
		return image.getNinePatch();
	}

	@Override
	public NinePatch getColorblindPatch() {
		return image.getColorblindPatch();
	}

	@Override
	public DrawableAnimation getAnimation() {
		return image.getAnimation();
	}

	@Override
	public void setAnimation(DrawableAnimation animation) {
		image.setAnimation(animation);
	}

	@Override
	public float getWidth() {
		return image.getWidth();
	}

	@Override
	public float getHeight() {
		return image.getHeight();
	}

	@Override
	public TextureRegion getRegion(double animState) {
		return image.getRegion(animState);
	}

	@Override
	public int getFrameIndex(double animState) {
		return image.getFrameIndex(animState);
	}

	@Override
	public DrawableImageFrame getFrame(int index) {
		return image.getFrame(index);
	}

	@Override
	public Texture getTexture(int index) {
		return image.getTexture(index);
	}

	@Override
	public int getFrames() {
		return image == null ? 0 : image.getFrames();
	}

	@Override
	public double getFrameDuration() {
		return image.getFrameDuration();
	}

	@Override
	public double getAnimationDuration() {
		return image.getAnimationDuration();
	}

	@Override
	public int getWidth(double animState) {
		return image.getWidth(animState);
	}

	@Override
	public int getHeight(double animState) {
		return image.getHeight(animState);
	}

	@Override
	public Texture getTexture(double animState) {
		return image.getTexture(animState);
	}

	@Override
	public TextureRegion getRegion(double animState, float x, float y, float width, float height) {
		return image.getRegion(animState, x, y, width, height);
	}

	@Override
	public TextureRegion getRegion(TextureRegion region, double animState, float x, float y, float width, float height) {
		return image.getRegion(region, animState, x, y, width, height);
	}

	@Override
	public void draw(Batch batch, float posX, float posY, float scaleX, float scaleY, double animState) {
		setColor(batch);
		image.draw(batch, posX, posY, scaleX, scaleY, animState);
		batch.setColor(temp);
	}

	@Override
	public void draw(Batch batch, float posX, float posY, float scaleX, float scaleY, double animState, float rotation) {
		setColor(batch);
		image.draw(batch, posX, posY, scaleX, scaleY, animState, rotation);
		batch.setColor(temp);
	}

	@Override
	public void drawNinePatch(Batch batch, float x, float y, float width, float height) {
		setColor(batch);
		image.drawNinePatch(batch, x, y, width, height);
		batch.setColor(temp);
	}

	private void setColor(Batch batch) {
		temp.set(batch.getColor());
		if (color != null) {
			batch.setColor(batch.getColor().mul(2.0F).mul(color));
		}
	}

}
