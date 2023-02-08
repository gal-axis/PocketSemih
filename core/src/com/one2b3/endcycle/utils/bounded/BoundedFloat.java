package com.one2b3.endcycle.utils.bounded;

import com.badlogic.gdx.math.MathUtils;
import com.one2b3.endcycle.engine.proguard.KeepClass;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@KeepClass
@Accessors(chain = true)
@Getter
@EqualsAndHashCode
@ToString
public final class BoundedFloat implements Cloneable {

	float min = 0.0F, max = 1.0F, speed = 1.0F;
	float val;

	public BoundedFloat() {
	}

	public BoundedFloat(float min, float max, float speed, float start) {
		setFields(min, max, speed, start);
	}

	public BoundedFloat(float min, float max, float speed, boolean startAtMin) {
		this(min, max, speed, (startAtMin ? Math.min(min, max) : Math.max(min, max)));
	}

	public BoundedFloat(float min, float max, float speed) {
		this(min, max, speed, Math.min(min, max));
	}

	public BoundedFloat(float min, float max) {
		this(min, max, 1.0F);
	}

	public BoundedFloat(float max) {
		this(0, max);
	}

	public BoundedFloat setFields(float min, float max, float speed, float start) {
		this.min = Math.min(min, max);
		this.max = Math.max(min, max);
		setSpeed(speed);
		setVal(start);
		return this;
	}

	public BoundedFloat setMin(float min) {
		this.max = Math.max(min, max);
		this.min = Math.min(min, max);
		return this;
	}

	public BoundedFloat setMax(float max) {
		this.max = Math.max(min, max);
		this.min = Math.min(min, max);
		return this;
	}

	public BoundedFloat setSpeed(float speed) {
		this.speed = Math.abs(speed);
		return this;
	}

	public BoundedFloat setVal(float val) {
		this.val = MathUtils.clamp(val, min, max);
		return this;
	}

	public BoundedFloat setValDirect(float val) {
		this.val = val;
		return this;
	}

	public void setPercentage(float percentage) {
		setVal((max - min) * percentage + min);
	}

	public float getPercentage() {
		if (max - min == 0.0F) {
			return 1.0F;
		}
		return (val - min) / (max - min);
	}

	public boolean increase(float delta) {
		if (val < max) {
			val += speed * delta;
			if (val > max) {
				toMax();
			}
			return true;
		} else {
			return false;
		}
	}

	public BoundedFloat toMax() {
		setVal(max);
		return this;
	}

	public boolean atMax() {
		return val >= max;
	}

	public boolean decrease(float delta) {
		if (val > min) {
			val -= speed * delta;
			if (val < min) {
				toMin();
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean atMin() {
		return val <= min;
	}

	public BoundedFloat toMin() {
		setVal(min);
		return this;
	}

	public boolean atLimit() {
		return atMax() || atMin();
	}

	public void toLimit(boolean increase) {
		if (increase) {
			toMax();
		} else {
			toMin();
		}
	}

	public boolean move(boolean increase, float delta) {
		return (increase ? increase(delta) : decrease(delta));
	}

	@Override
	public BoundedFloat clone() {
		try {
			return (BoundedFloat) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

}
