package com.one2b3.endcycle.engine.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.utils.pools.DynamicObjectPool;

public class ScreenObjectUpdater implements AutoCloseable {

	static final DynamicObjectPool<ScreenObjectUpdater> UPDATE_POOL = new DynamicObjectPool<>(ScreenObjectUpdater::new, 5);

	Array<Throwable> exceptions = new Array<>();
	ScreenObjectHandler<?> handler;
	Array<ScreenObject> updateList = new Array<>(10);

	public static ScreenObjectUpdater get(ScreenObjectHandler<?> handler) {
		ScreenObjectUpdater next = UPDATE_POOL.getNext();
		next.handler = handler;
		next.updateList.addAll(handler.getObjects());
		return next;
	}

	public void init(ScreenObjectHandler<?> handler) {
		this.handler = handler;
		this.updateList.addAll(handler.getObjects());
	}

	public void update(float delta) {
		for (int i = 0; i < updateList.size; i++) {
			update(delta, updateList.get(i), handler);
		}
		printExceptions();
	}

	@SuppressWarnings("unchecked")
	public <T extends ScreenObject> void update(float delta, ScreenObject object, ScreenObjectHandler<T> handler) {
		if (!handler.contains(object)) {
			return;
		}
		if (!object.remove() && handler.isUpdatable(object)) {
			updateObject(object, delta);
		}
		if (object.remove()) {
			handler.removeObject((T) object);
		}
	}

	private void updateObject(ScreenObject object, float delta) {
		try {
			object.update(delta);
		} catch (Throwable t) {
			exceptions.add(t);
		}
	}

	public void printExceptions() {
		while (exceptions.notEmpty()) {
			Gdx.app.error("Layers", "Exception occurred updating objects!", exceptions.pop());
		}
	}

	@Override
	public void close() {
		updateList.clear();
		UPDATE_POOL.free(this);
	}
}
