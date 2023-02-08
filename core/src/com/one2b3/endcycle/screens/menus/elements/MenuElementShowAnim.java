package com.one2b3.endcycle.screens.menus.elements;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Interpolation.SwingOut;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public final class MenuElementShowAnim {

	static Interpolation animation = new SwingOut(1.2F);

	final MenuElement element;
	float state, start;
	float speed;
	boolean visible;

	float focusState;

	Matrix4 oldMatrix = new Matrix4(), newMatrix = new Matrix4();

	public void start() {
		start = (state == 0.0F ? MathUtils.random(-0.6F, 0.0F) : start);
		state = start;
		focusState = 1.0F;
		speed = MathUtils.random(5.0F, 6.0F);
		visible = true;
	}

	public void focus(boolean focused) {
		if (focused && visible && state == 1.0F) {
			focusState = 0.0F;
		}
	}

	public void stop() {
		visible = false;
		speed = MathUtils.random(7.0F, 8.0F);
	}

	public void update(float delta) {
		if (visible && (element == null || !element.isAbsoluteHidden())) {
			state = Math.min(state + speed * delta, 1.0F);
		} else {
			state = Math.max(state - speed * delta, Math.min(start, 0.0F));
		}
		if (focusState < 1.0F) {
			focusState += delta * 10.0F;
		}
	}

	public void setState(float state) {
		this.state = state;
	}

	public boolean isElementVisible() {
		return visible || state > 0.0F;
	}

	public boolean isFinished() {
		return visible ? state == 1.0F : state <= 0.0F;
	}

	public void begin(CustomSpriteBatch batch, float xOfs, float yOfs) {
		begin(batch, element.calcX() + xOfs, element.calcY() + yOfs, element.getWidth(), element.getHeight());
	}

	public void begin(CustomSpriteBatch batch, float elementX, float elementY, float elementWidth, float elementHeight) {
		if (visible ? state < 1.0F || focusState < 1.0F : state > 0.0F) {
			oldMatrix.set(batch.getTransformMatrix());
			float stateX = MathUtils.clamp(this.state * 2.0F, 0.0F, 1.0F);
			float stateY = Math.max(this.state - 0.5F, 0.0F) * 1.8F + 0.1F;
			stateY = animation.apply(stateY);
			Matrix4 newMatrix2 = newMatrix;
			newMatrix2.set(oldMatrix);
			float x = 0.0F, y = 0.0F;
			if (stateX > 0.0F) {
				x = elementX + elementWidth * 0.5F;
				x = x / stateX - x;
			}
			if (stateY > 0.0F) {
				y = elementY + elementHeight * 0.5F;
				y = y / stateY - y;
			}
			newMatrix2.scale(stateX, stateY, 1.0F);
			newMatrix2.translate(x, y, 0.0F);
			if (focusState < 1.0F) {
				float scale = 0.5F - Math.abs(0.5F - focusState);
				newMatrix2.translate(0.0F, scale * -2, 0.0F);
			}
			batch.setTransformMatrix(newMatrix2);
		}
	}

	public void end(CustomSpriteBatch batch) {
		if (visible ? state < 1.0F || focusState < 1.0F : state > 0.0F) {
			batch.setTransformMatrix(oldMatrix);
		}
	}
}
