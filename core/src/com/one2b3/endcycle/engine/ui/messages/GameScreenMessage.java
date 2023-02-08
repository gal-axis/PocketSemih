package com.one2b3.endcycle.engine.ui.messages;

import com.one2b3.endcycle.engine.events.EventType;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.endcycle.engine.screens.ListenableGameScreenObject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC)
public abstract class GameScreenMessage extends ListenableGameScreenObject {

	static float current;

	public static float nextComparisonKey() {
		return current -= 0.1F;
	}

	public static float getCurrentComparisonKey() {
		return current;
	}

	GameScreenMessage next;
	@Getter
	byte layer = Layers.LAYER_MESSAGES;
	@Getter
	float comparisonKey;

	@Override
	public void init(GameScreen screen) {
		super.init(screen);
		comparisonKey = nextComparisonKey();
		triggerEvent(EventType.MESSAGE_START, this);
	}

	public void scheduleMessage(GameScreenMessage message) {
		if (next != null) {
			next.scheduleMessage(message);
		} else {
			next = message;
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		triggerEvent(EventType.MESSAGE_END, this);
		if (next != null) {
			screen.addObject(next);
			next = null;
		}
	}
}