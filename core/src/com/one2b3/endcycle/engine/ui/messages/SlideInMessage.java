package com.one2b3.endcycle.engine.ui.messages;

import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.features.theme.ActiveTheme;
import com.one2b3.endcycle.utils.bounded.BoundedFloat;

public abstract class SlideInMessage extends GameScreenMessage {

	public State state;
	BoundedFloat position;

	public SlideInMessage() {
		position = new BoundedFloat(0.0F, 1.0F, 8.0F);
	}

	public void setSpeed(float speed) {
		position.setSpeed(speed);
	}

	@Override
	public void init(GameScreen screen) {
		super.init(screen);
		state = State.SLIDING_IN;
		playSound(ActiveTheme.open);
	}

	@Override
	public void update(float delta) {
		updatePosition(delta);
	}

	public void updatePosition(float delta) {
		if (state == State.SLIDING_IN) {
			if (!position.increase(delta)) {
				setStay();
			}
		} else if (state == State.SLIDING_OUT) {
			if (!position.decrease(delta)) {
				state = State.DONE;
			}
		}
	}

	public void setStay() {
		state = State.STAY;
	}

	public boolean isStaying() {
		return state == State.STAY;
	}

	public float getPosition() {
		return position.getVal();
	}

	public void finish() {
		state = State.SLIDING_OUT;
		playSound(ActiveTheme.close);
	}

	@Override
	public boolean remove() {
		return state == State.DONE;
	}

	public enum State {
		SLIDING_IN, SLIDING_OUT, STAY, DONE;
	}
}
