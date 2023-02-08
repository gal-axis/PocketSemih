package com.one2b3.endcycle.utils.bounded;

import com.badlogic.gdx.math.MathUtils;
import com.one2b3.endcycle.engine.proguard.KeepClass;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

@KeepClass
@Getter
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
public final class BoundedDouble {

	double min = 0.0F, max = 1.0F, speed = 1.0F;
	double val;

	public BoundedDouble() {
	}

	public BoundedDouble(double min, double max, double speed, double start) {
		setFields(min, max, speed, start);
	}

	public BoundedDouble(double min, double max, double speed, boolean startAtMin) {
		this(min, max, speed, (startAtMin ? Math.min(min, max) : Math.max(min, max)));
	}

	public BoundedDouble(double min, double max, double speed) {
		this(min, max, speed, Math.min(min, max));
	}

	public BoundedDouble(double min, double max) {
		this(min, max, 1.0F);
	}

	public BoundedDouble(double max) {
		this(0, max);
	}

	public BoundedDouble setFields(double min, double max, double speed, double start) {
		this.min = Math.min(min, max);
		this.max = Math.max(min, max);
		setSpeed(speed);
		setVal(start);
		return this;
	}

	public BoundedDouble setSpeed(double speed) {
		this.speed = Math.abs(speed);
		return this;
	}

	public BoundedDouble setMin(double min) {
		this.max = Math.max(min, max);
		this.min = Math.min(min, max);
		return this;
	}

	public BoundedDouble setMax(double max) {
		this.max = Math.max(min, max);
		this.min = Math.min(min, max);
		return this;
	}

	public BoundedDouble setVal(double val) {
		this.val = MathUtils.clamp(val, min, max);
		return this;
	}

	public void setPercentage(double percentage) {
		setVal((max - min) * percentage + min);
	}

	public double getPercentage() {
		if (max - min == 0.0F) {
			return 1.0F;
		}
		return (val - min) / (max - min);
	}

	public boolean increase(double delta) {
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

	public BoundedDouble toMax() {
		setVal(max);
		return this;
	}

	public boolean atMax() {
		return val >= max;
	}

	public boolean decrease(double delta) {
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

	public BoundedDouble toMin() {
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

	public boolean move(boolean increase, double delta) {
		return (increase ? increase(delta) : decrease(delta));
	}

}
