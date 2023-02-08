package com.one2b3.endcycle.engine.drawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.fonts.GameFont;
import com.one2b3.endcycle.engine.graphics.Drawable;
import com.one2b3.endcycle.utils.Modulator;
import com.one2b3.revo.Revo;
import com.one2b3.utils.CColor;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC)
@Setter
@Accessors(fluent = true, chain = true)
public final class Painter {

	private static final Painter GLOBAL = new Painter();

	final Object[] singleArg = new Object[1];
	final CColor color = new CColor();

	CustomSpriteBatch batch;
	GameFont font;
	float x, y, width, height, xScale = 1.0F, yScale = 1.0F;
	float fontScale;
	int hAlign, vAlign;
	double animState;

	@Setter(AccessLevel.NONE)
	Object[] arguments;

	public Painter() {
	}

	public static Painter on(CustomSpriteBatch batch) {
		return GLOBAL.reset(batch);
	}

	public Painter reset(CustomSpriteBatch batch) {
		this.batch = batch;
		x = y = 0.0F;
		width = height = 0.0F;
		fontScale = xScale = yScale = 1.0F;
		hAlign = vAlign = -1;
		color.r = color.g = color.b = color.a = 1.0F;
		animState = Float.NaN;
		arguments = null;
		font = null;
		return this;
	}

	public Painter at(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Painter at(Vector2 vector) {
		if (vector != null) {
			this.x = vector.x;
			this.y = vector.y;
		}
		return this;
	}

	public Painter size(Drawable drawable) {
		this.width = drawable.getWidth();
		this.height = drawable.getHeight();
		return this;
	}

	public Painter size(float width, float height) {
		this.width = width;
		this.height = height;
		return this;
	}

	public Painter moveX(float x) {
		this.x += x;
		return this;
	}

	public Painter moveY(float y) {
		this.y += y;
		return this;
	}

	public Painter align(int hAlign, int vAlign) {
		this.hAlign = hAlign;
		this.vAlign = vAlign;
		return this;
	}

	public Painter align(int align) {
		hAlign = vAlign = align;
		return this;
	}

	public Painter scale(float xScale, float yScale) {
		this.xScale = xScale;
		this.yScale = yScale;
		return this;
	}

	public Painter scale(float scale) {
		xScale = yScale = scale;
		return this;
	}

	public Painter color(Color color) {
		this.color.set(color == null ? Color.WHITE : color);
		return this;
	}

	public Painter alpha(float a) {
		this.color.a = a;
		return this;
	}

	public Painter mulColor(float color) {
		this.color.mul(color);
		return this;
	}

	public Painter addColor(Color color) {
		if (color != null) {
			this.color.add(color);
		}
		return this;
	}

	public Painter mulColor(Color color) {
		if (color != null) {
			this.color.mul(color);
		}
		return this;
	}

	public Painter color(float color) {
		this.color.r = this.color.g = this.color.b = this.color.a = color;
		return this;
	}

	public Painter color(float r, float g, float b) {
		this.color.r = r;
		this.color.g = g;
		this.color.b = b;
		return this;
	}

	public Painter invertColor() {
		float r = color.r, b = color.b;
		color.r = color.g;
		color.b = r;
		color.g = b;
		return this;
	}

	public Painter color(float r, float g, float b, float a) {
		this.color.r = r;
		this.color.g = g;
		this.color.b = b;
		this.color.a = a;
		return this;
	}

	public Painter lerpColor(Color color, float lerp) {
		this.color.lerp(color, lerp);
		return this;
	}

	public Painter modulateColor(float amount, float speed) {
		color.r = modulateColor(color.r, amount, speed);
		color.g = modulateColor(color.g, amount, speed);
		color.b = modulateColor(color.b, amount, speed);
		return this;
	}

	private static float modulateColor(float color, float amount, float speed) {
		return color * (1.0F - amount) + color * amount * Modulator.getCosine(speed);
	}

	public Painter arguments(Object... args) {
		this.arguments = args;
		return this;
	}

	public Painter argument(Object arg) {
		this.arguments = singleArg;
		this.arguments[0] = arg;
		return this;
	}

	public Painter clearArguments() {
		this.arguments = null;
		return this;
	}

	public Object[] arguments() {
		return arguments;
	}

	public Painter center() {
		hAlign = vAlign = 0;
		x += width * 0.5F;
		y += height * 0.5F;
		width = height = 0.0F;
		return this;
	}

	public Painter centerX() {
		hAlign = 0;
		x += width * 0.5F;
		width = 0.0F;
		return this;
	}

	public Painter centerY() {
		vAlign = 0;
		y += height * 0.5F;
		height = 0.0F;
		return this;
	}

	public <T> Painter paint(T object) {
		if (object != null) {
			Paintable paintable = Revo.cast(object, Paintable.class);
			if (paintable != null) {
				paintable.paint(this);
				return this;
			}
			ObjectPainter<T> painter = getDefaultPainter(object);
			if (painter != null) {
				painter.paint(object, this);
			}
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	private <T> ObjectPainter<T> getDefaultPainter(T object) {
		return (ObjectPainter<T>) ObjectPainterFactory.getPainter(object.getClass());
	}

	@SuppressWarnings("unchecked")
	public <T> Painter paint(T object, ObjectPainter<?> painter) {
		if (painter != null) {
			((ObjectPainter<T>) painter).paint(object, this);
		}
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> Painter paint(T object, Class<?> paintingClass) {
		ObjectPainter<T> painter = (ObjectPainter<T>) ObjectPainterFactory.getPainter(paintingClass);
		if (painter != null) {
			painter.paint(object, this);
		}
		return this;
	}

	public void mask() {
		batch.startMasking(x, y, width, height, true);
	}

	public void unmask() {
		batch.stopMasking();
	}

	public Painter paintRectangle() {
		return paintRectangle(width, height, color.r, color.g, color.b, color.a);
	}

	public Painter paintRectangle(float width, float height) {
		return paintRectangle(width, height, color.r, color.g, color.b, color.a);
	}

	public Painter paintRectangle(float width, float height, float r, float g, float b, float a) {
		batch.drawRectangle(x - (hAlign + 1) * 0.5F * width, y - (vAlign + 1) * 0.5F * height, width, height, r, g, b,
				a);
		return this;
	}

	public Painter paintRectangle(float r, float g, float b, float a) {
		return paintRectangle(width, height, r, g, b, a);
	}

	public Painter paintRectangle(float width, float height, Color color) {
		return paintRectangle(width, height, color.r, color.g, color.b, color.a);
	}

	public Painter paintRectangle(Color color) {
		return paintRectangle(width, height, color.r, color.g, color.b, color.a);
	}

}
