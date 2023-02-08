package com.one2b3.endcycle.utils.pools;

import java.util.ArrayList;

import com.one2b3.utils.java.Supplier;

public final class SizedObjectPool<T> {

	private int current = 0;
	private final Supplier<T> objectSupplier;
	private ArrayList<T> infos;

	public SizedObjectPool(Supplier<T> objectSupplier, int startSize) {
		this.objectSupplier = objectSupplier;
		infos = new ArrayList<>(startSize);
		ensureCapacity(startSize);
		current = 0;
	}

	private void ensureCapacity(int capacity) {
		infos.ensureCapacity(capacity);
		while (infos.size() < capacity) {
			infos.add(objectSupplier.get());
		}
	}

	public void reset() {
		current = 0;
	}

	public int getCurrent() {
		return current;
	}

	public ArrayList<T> getInfos() {
		return infos;
	}

	public T getNext() {
		if (current >= infos.size()) {
			ensureCapacity(infos.size() + 10);
		}
		return infos.get(current++);
	}
}