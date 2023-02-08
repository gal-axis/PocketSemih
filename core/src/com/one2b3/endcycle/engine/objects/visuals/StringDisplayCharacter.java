package com.one2b3.endcycle.engine.objects.visuals;

import com.one2b3.endcycle.utils.bounded.BoundedFloat;

public final class StringDisplayCharacter {

	final char displaying;
	final BoundedFloat stay;
	final BoundedFloat anim = new BoundedFloat(1.0F);
	final BoundedFloat alpha = new BoundedFloat(0.0F, 1.0F, 4.0F);

	public StringDisplayCharacter(char displaying, float stayTime) {
		this.displaying = displaying;
		this.stay = new BoundedFloat(stayTime);
	}

	public void init() {
		anim.toMin();
		alpha.toMin();
		stay.toMin();
	}

	public void update(float delta, StringDisplayAnimation animation) {
		anim.increase(delta * animation.speed);
		if (alpha.move(!stay.atMax(), delta) && alpha.atMax()) {
			animation.finish(this);
		}
		if (alpha.atMax()) {
			stay.increase(delta);
		}
	}

	public char getDisplaying() {
		return displaying;
	}

	public float getAnim() {
		return anim.getVal();
	}

	public float getAlpha() {
		return alpha.getVal();
	}

	public boolean movingBack() {
		return stay.atMax();
	}

	public boolean canAdvance(float percentage) {
		return !stay.atMin() || alpha.getPercentage() >= percentage;
	}
}