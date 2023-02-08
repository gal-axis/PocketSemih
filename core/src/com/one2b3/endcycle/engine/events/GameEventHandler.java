package com.one2b3.endcycle.engine.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.utils.pools.DynamicObjectPool;

public final class GameEventHandler implements GameEventListener {
	static DynamicObjectPool<Array<GameEventListener>> dispatchers = new DynamicObjectPool<>(() -> new Array<>(), 2);
	static final GameEventHandler staticListener = new GameEventHandler(null);

	public static void addStaticListener(GameEventListener listener) {
		staticListener.add(listener);
	}

	public static void triggerStatic(Object type, Object trigger, Object... parameters) {
		staticListener.trigger(type, trigger, parameters);
	}

	public static void removeStaticListener(GameEventListener listener) {
		staticListener.remove(listener);
	}

	public static void clearStaticListeners() {
		staticListener.clear();
	}

	public GameScreen screen;
	public Array<GameEventListener> listeners;

	public GameEventHandler(GameScreen screen) {
		this.screen = screen;
		listeners = new Array<>(GameEventListener.class);
	}

	public void clear() {
		listeners.clear();
	}

	public void add(GameEventListener listener) {
		if (listener != null && !listeners.contains(listener, true)) {
			listeners.add(listener);
			trigger(EventType.EVENT_ADD, listener);
		}
	}

	public boolean contains(GameEventListener listener) {
		return listeners.contains(listener, true);
	}

	public void remove(GameEventListener listener) {
		if (listeners.removeValue(listener, true)) {
			trigger(EventType.EVENT_REMOVE, listener);
		}
	}

	public int trigger(Object type, Object trigger, Object... parameters) {
		try (GameEvent event = GameEvent.get(this, screen, type, trigger, parameters)) {
			handle(event);
			// return event.flags;
			return 0;
		}
	}

	@Override
	public void handle(final GameEvent event) {
		if (this != staticListener) {
			// Gdx.app.debug("GameEvent", "Triggered event: " + event);
			staticListener.handle(event);
		}
		handle(listeners, event);
	}

	public static void handle(Array<? extends GameEventListener> listeners, GameEvent event) {
		if (listeners.size > 0) {
			Array<GameEventListener> dispatcher = dispatchers.getNext();
			dispatcher.addAll(listeners);
			for (int i = 0; i < dispatcher.size; i++) {
				GameEventListener listener = dispatcher.get(i);
				try {
					listener.handle(event);
				} catch (Throwable t) {
					Gdx.app.error("Events", "Error while handling event on " + listener, t);
				}
			}
			dispatcher.clear();
			dispatchers.free(dispatcher);
		}
	}
}
