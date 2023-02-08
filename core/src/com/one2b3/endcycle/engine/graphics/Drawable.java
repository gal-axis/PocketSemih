package com.one2b3.endcycle.engine.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.engine.drawing.Paintable;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.graphics.data.DrawableLoader;
import com.one2b3.endcycle.engine.proguard.KeepClass;
import com.one2b3.endcycle.utils.ID;
import com.one2b3.utils.DrawableAnimation;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@KeepClass
@FieldDefaults(level = AccessLevel.PUBLIC)
@EqualsAndHashCode
public class Drawable implements Paintable {

	DrawableImage toDraw;

	Color color = new Color(1.0F, 1.0F, 1.0F, 1.0F);
	float scaleX = 1.0F, scaleY = 1.0F;
	float rotation;

	public Drawable() {
	}

	public Drawable(long group, long id) {
		this(DrawableLoader.getImage(group, id));
	}

	public Drawable(Drawable drawable) {
		set(drawable);
	}

	public Drawable(TextureRegion region) {
		setToDraw(new DrawableAnimation(0.0, new DrawableImageFrame(region, null, 0.0F, 0.0F, null, null)));
	}

	public Drawable(DrawableImage image) {
		this.toDraw = image;
	}

	public void set(Drawable drawable) {
		if (drawable != null) {
			toDraw = drawable.toDraw;
			setColor(drawable.color);
			rotation = drawable.rotation;
			scaleX = drawable.scaleX;
			scaleY = drawable.scaleY;
		}
	}

	public void set(DrawableImage image) {
		this.toDraw = image;
	}

	public ID getId() {
		return toDraw == null ? null : toDraw.id;
	}

	private void setToDraw(DrawableAnimation animation) {
		if (animation != null) {
			toDraw = new DrawableImage(animation);
		}
	}

	public Drawable setScaleFromSize(float width, float height, boolean aspect) {
		if (toDraw == null) {
			return this;
		}
		float imageWidth = toDraw.getWidth();
		float imageHeight = toDraw.getHeight();
		if (aspect) {
			float scale = height / imageHeight;
			if (scale * imageWidth > width) {
				scale = width / imageWidth;
			}
			scaleX = scaleY = scale;
		} else {
			scaleX = width / imageWidth;
			scaleY = height / imageHeight;
		}
		return this;
	}

	public Drawable setScaleFromWidth(float width) {
		scaleX = scaleY = width / toDraw.getWidth();
		return this;
	}

	public Drawable setScaleFromHeight(float height) {
		scaleX = scaleY = height / toDraw.getHeight();
		return this;
	}

	public Drawable setScale(float scale) {
		scaleX = scaleY = scale;
		return this;
	}

	public Drawable setColor(Color color) {
		if (color == null) {
			setTint(1.0F, 1.0F, 1.0F, 1.0F);
		} else {
			setTint(color.r, color.g, color.b, color.a);
		}
		return this;
	}

	public Drawable setTint(float r, float g, float b, float a) {
		if (color == null) {
			color = new Color(r, g, b, a);
		} else {
			color.r = r;
			color.g = g;
			color.b = b;
			color.a = a;
		}
		return this;
	}

	public double getFrameDuration() {
		return toDraw.getFrameDuration();
	}

	public int getFrames() {
		return toDraw.getFrames();
	}

	public double getAnimationDuration() {
		if (toDraw == null) {
			return 0;
		}
		return toDraw.getAnimationDuration();
	}

	public TextureRegion getRegion(double animState, float x, float y, float width, float height) {
		return toDraw.getRegion(animState, x, y, width, height);
	}

	public TextureRegion getRegion(TextureRegion region, double animState, float x, float y, float width, float height) {
		return toDraw.getRegion(region, animState, x, y, width, height);
	}

	public TextureRegion getRegion(TextureRegion region, double animState) {
		return toDraw.getRegion(region, animState, 0, 0, toDraw.getWidth(animState), toDraw.getHeight(animState));
	}

	public Vector2 getSize() {
		return new Vector2(getWidth(), getHeight());
	}

	@Override
	public float getWidth() {
		return toDraw == null ? 0 : toDraw.getWidth() * scaleX;
	}

	@Override
	public float getHeight() {
		return toDraw == null ? 0 : toDraw.getHeight() * scaleY;
	}

	public float getWidth(double animState) {
		return toDraw == null ? 0 : toDraw.getWidth(animState) * scaleX;
	}

	public float getHeight(double animState) {
		return toDraw == null ? 0 : toDraw.getHeight(animState) * scaleY;
	}

	public double getAnimState() {
		return Cardinal.TIME_ACTIVE;
	}

	// Drawing methods

	public void draw(Batch batch, float posX, float posY) {
		draw(batch, posX, posY, 1.0F, 1.0F, getAnimState(), -1, -1, null);
	}

	public void draw(Batch batch, float posX, float posY, Color tint) {
		draw(batch, posX, posY, 1.0F, 1.0F, getAnimState(), -1, -1, tint);
	}

	public void draw(Batch batch, float posX, float posY, float scaleX, float scaleY) {
		draw(batch, posX, posY, scaleX, scaleY, getAnimState(), -1, -1, null);
	}

	public void draw(Batch batch, float posX, float posY, double animState) {
		draw(batch, posX, posY, 1.0F, 1.0F, animState, -1, -1, null);
	}

	public void draw(Batch batch, float posX, float posY, int hAlign, int vAlign) {
		draw(batch, posX, posY, 1.0F, 1.0F, getAnimState(), hAlign, vAlign, null);
	}

	public void draw(Batch batch, float posX, float posY, float scaleX, float scaleY, double animState, int hAlign, int vAlign,
			Color tint) {
		if (batch != null && batch.isDrawing() && toDraw != null) {
			if (tint == null) {
				batch.setColor(this.color);
			} else {
				batch.setColor(tint.r * this.color.r, tint.g * this.color.g, tint.b * this.color.b, tint.a * this.color.a);
			}
			posX += getXPosition(scaleX, animState, hAlign);
			posY += getYPosition(scaleY, animState, vAlign);
			toDraw.draw(batch, posX, posY, scaleX * this.scaleX, scaleY * this.scaleY, animState, rotation);
		}
	}

	public float getXPosition(float scaleX, double animState, int hAlign) {
		return (hAlign + 1) * -0.5F * getWidth() * scaleX;
	}

	public float getYPosition(float scaleY, double animState, int vAlign) {
		return (vAlign + 1) * -0.5F * getHeight() * scaleY;
	}

	@Override
	public void paint(Painter painter) {
		draw(painter.batch, painter.x, painter.y, painter.xScale, painter.yScale, Paintable.get(painter.animState, Cardinal.TIME_ACTIVE),
				painter.hAlign, painter.vAlign, painter.color);
	}

}
