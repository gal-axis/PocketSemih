package com.one2b3.endcycle.engine.events;

import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.utils.pools.DynamicObjectPool;

public final class GameEvent implements AutoCloseable {

	private static final DynamicObjectPool<GameEvent> POOL = new DynamicObjectPool<>(GameEvent::new, 5);

	public static final int FLAG_CANCELLED = 0b1;

	// public static boolean isCancelled(int flags) {
	// return (flags & FLAG_CANCELLED) != 0;
	// }

	protected static GameEvent get(GameEventHandler firedBy, GameScreen screen, Object type, Object trigger,
			Object... parameters) {
		GameEvent event = POOL.getNext();
		event.firedBy = firedBy;
		event.screen = screen;
		event.type = type;
		event.trigger = trigger;
		event.parameters = parameters;
		return event;
	}

	// public int flags;
	public GameEventHandler firedBy;
	public GameScreen screen;
	public Object type;
	public Object trigger;
	Object[] parameters;

	private GameEvent() {
	}

	// public void cancel() {
	// flags |= FLAG_CANCELLED;
	// }

	@SuppressWarnings("unchecked")
	public <T> T getParameter(int parameter) {
		if (parameter >= 0 && parameter < parameters.length) {
			return (T) parameters[parameter];
		}
		return null;
	}

	public int getParameters() {
		return parameters == null ? 0 : parameters.length;
	}

	@Override
	public String toString() {
		return "Event: Type=" + type + ", Trigger=" + trigger;
	}

	@Override
	public void close() {
		POOL.free(this);
		// flags = 0;
		firedBy = null;
		screen = null;
		type = null;
		trigger = null;
		parameters = null;
	}
}