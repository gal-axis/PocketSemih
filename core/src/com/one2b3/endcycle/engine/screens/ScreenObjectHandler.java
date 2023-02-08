package com.one2b3.endcycle.engine.screens;

import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.engine.events.EventType;
import com.one2b3.endcycle.engine.events.GameEventHandler;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public abstract class ScreenObjectHandler<E extends ScreenObject> implements UpdateCondition {

	public GameScreen parent;

	protected abstract Array<E> getObjects();

	public <T extends E> T addObject(T object) {
		if (object != null) {
			getObjects().add(object);
			initObject(object);
		}
		return object;
	}

	public <T extends E> void initObject(T object) {
		object.init(parent);
		object.resize(Cardinal.getWidth(), Cardinal.getHeight());
		if (parent == null) {
			GameEventHandler.triggerStatic(EventType.OBJECT_ADDED, object);
		} else {
			parent.events.trigger(EventType.OBJECT_ADDED, object);
		}
	}

	public void show() {
		Array<E> objects = getObjects();
		for (int i = objects.size - 1; i >= 0; i = Math.min(objects.size - 1, i - 1)) {
			objects.get(i).show(parent);
		}
	}

	public void hide() {
		Array<E> objects = getObjects();
		for (int i = objects.size - 1; i >= 0; i = Math.min(objects.size - 1, i - 1)) {
			objects.get(i).hide(parent);
		}
	}

	public boolean removeObject(E object) {
		if (getObjects().removeValue(object, true)) {
			disposeObject(object);
			return true;
		}
		return false;
	}

	public void disposeObject(E object) {
		object.dispose();
		if (parent == null) {
			GameEventHandler.triggerStatic(EventType.OBJECT_REMOVED, object);
		} else {
			parent.events.trigger(EventType.OBJECT_REMOVED, object);
		}
	}

	public <T extends E> boolean removeObject(Class<T> object) {
		return removeObject(getObject(object));
	}

	public void clearObjects() {
		Array<E> objects = getObjects();
		while (objects.size > 0) {
			removeObject(objects.get(0));
		}
	}

	public void updateObjects(float delta) {
		try (ScreenObjectUpdater updater = ScreenObjectUpdater.get(this)) {
			updater.update(delta);
		}
	}

	@Override
	public boolean isUpdatable(ScreenObject object) {
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean contains(ScreenObject object) {
		return getObjects().contains((E) object, true);
	}

	public ScreenObject getObject(String name) {
		if (name == null) {
			return null;
		}
		Array<E> objects = getObjects();
		for (int i = objects.size - 1; i >= 0; i--) {
			ScreenObject object = objects.get(i);
			if (name.equals(object.getName())) {
				return object;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T extends ScreenObject> T getObject(Class<T> objectClass) {
		if (objectClass == null) {
			return null;
		}
		Array<E> objects = getObjects();
		for (int i = objects.size - 1; i >= 0; i--) {
			ScreenObject object = objects.get(i);
			if (objectClass.isAssignableFrom(object.getClass())) {
				return (T) object;
			}
		}
		return null;
	}

	public void resize(int width, int height) {
		Array<E> objects = getObjects();
		for (int i = objects.size - 1; i >= 0; i--) {
			objects.get(i).resize(width, height);
		}
	}

}
