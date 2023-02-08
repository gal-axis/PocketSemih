package com.one2b3.endcycle.utils.pools;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.one2b3.utils.java.Supplier;

public final class DynamicObjectPool<T> {

	private final Supplier<T> objectSupplier;
	public final Array<T> objects;

	public DynamicObjectPool(Supplier<T> objectSupplier, int startSize) {
		this.objectSupplier = objectSupplier;
		objects = new Array<>(startSize);
		ensureCapacity(startSize);
	}

	private synchronized void ensureCapacity(int capacity) {
		objects.ensureCapacity(capacity);
		while (objects.size < capacity) {
			objects.add(objectSupplier.get());
		}
	}

	public synchronized void free(T element) {
		objects.add(element);
	}

	public synchronized T getNext() {
		if (objects.size == 0) {
			ensureCapacity(objects.size + 10);
		}
		return objects.pop();
	}

	public boolean hasFree() {
		return objects.notEmpty();
	}

	public void dispose() {
		for (int i = 0; i < objects.size; i++) {
			T obj = objects.get(i);
			if (obj instanceof Disposable) {
				((Disposable) obj).dispose();
			}
		}
		objects.clear();
	}
}