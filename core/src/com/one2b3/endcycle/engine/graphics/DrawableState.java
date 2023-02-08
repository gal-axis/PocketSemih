package com.one2b3.endcycle.engine.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.one2b3.endcycle.engine.drawing.Paintable;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.proguard.KeepClass;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@KeepClass
@FieldDefaults(level = AccessLevel.PUBLIC)
@EqualsAndHashCode(exclude = { "animState" }, callSuper = true)
public class DrawableState extends Drawable {

	float xPos, yPos;

	double animState;

	boolean ignoreSize = false, shadow = true;

	int hAlign = -1, vAlign = -1;

	public DrawableState() {
	}

	public DrawableState(Drawable drawable) {
		set(drawable);
	}

	public DrawableState(Drawable d, float posX, float posY) {
		super(d);
		setPositions(posX, posY);
	}

	public DrawableState(DrawableImage image) {
		super(image);
	}

	@Override
	public void set(Drawable drawable) {
		super.set(drawable);
		if (drawable instanceof DrawableState) {
			DrawableState state = (DrawableState) drawable;
			xPos = state.xPos;
			yPos = state.yPos;
			ignoreSize = state.ignoreSize;
			animState = state.animState;
			shadow = state.shadow;
			hAlign = state.hAlign;
			vAlign = state.vAlign;
		}
	}

	public void setPositions(float posX, float posY) {
		this.xPos = posX;
		this.yPos = posY;
	}

	public void update(float delta) {
		animState += delta;
	}

	public boolean isAnimationDone() {
		return getAnimationDuration() <= getAnimState();
	}

	public void resetAnimation() {
		animState = 0.0F;
	}

	@Override
	public void draw(Batch batch, float posX, float posY) {
		draw(batch, posX, posY, 1.0F, 1.0F, getAnimState(), hAlign, vAlign, color);
	}

	@Override
	public void draw(Batch batch, float posX, float posY, Color tint) {
		draw(batch, posX, posY, 1.0F, 1.0F, getAnimState(), hAlign, vAlign, tint);
	}

	@Override
	public void draw(Batch batch, float posX, float posY, float scaleX, float scaleY) {
		draw(batch, posX, posY, scaleX, scaleY, getAnimState(), hAlign, vAlign, null);
	}

	@Override
	public void draw(Batch batch, float posX, float posY, double animState) {
		draw(batch, posX, posY, 1.0F, 1.0F, animState, hAlign, vAlign, null);
	}

	@Override
	public void draw(Batch batch, float posX, float posY, int hAlign, int vAlign) {
		draw(batch, posX, posY, 1.0F, 1.0F, getAnimState(), hAlign, vAlign, null);
	}

	public void draw(Batch batch, float posX, float posY, float scaleX, float scaleY, Color tint) {
		draw(batch, posX, posY, scaleX, scaleY, getAnimState(), hAlign, vAlign, tint);
	}

	@Override
	public double getAnimState() {
		return animState;
	}

	@Override
	public void draw(Batch batch, float posX, float posY, float scaleX, float scaleY, double animState, int hAlign, int vAlign,
			Color tint) {
		posX += xPos * scaleX;
		posY += yPos * scaleY;
		super.draw(batch, posX, posY, scaleX, scaleY, animState, hAlign, vAlign, tint);
	}

	@Override
	public void paint(Painter painter) {
		draw(painter.batch, painter.x, painter.y, painter.xScale, painter.yScale, Paintable.get(painter.animState, getAnimState()),
				Paintable.get(painter.hAlign, hAlign), Paintable.get(painter.vAlign, vAlign), painter.color);
	}
}
