package com.one2b3.utils;

import com.badlogic.gdx.math.MathUtils;
import com.one2b3.utils.ScalingArray.Clearable;
import com.one2b3.utils.java.Supplier;

public class ScalingArray<T extends Clearable> {

	public Supplier<T> supplier;

	Clearable[] items;

	public int size;

	public ScalingArray(Supplier<T> supplier, int startSize) {
		this.supplier = supplier;
		items = new Clearable[startSize];
		size = 0;
	}

	public void delete(int amount) {
		if (amount > size) {
			throw new ArrayIndexOutOfBoundsException("Cannot clear " + amount + " elements from array with size " + amount);
		} else if (amount <= 0) {
			return;
		} else if (amount == size) {
			clear();
			return;
		}
		Clearable[] newItems = new Clearable[size];
		for (int i = 0; i < size; i++) {
			int itemIndex = (i + amount);
			if (itemIndex >= size) {
				itemIndex %= size;
				items[itemIndex].clear();
			}
			newItems[i] = items[itemIndex];
		}
		items = newItems;
		size -= amount;
	}

	@SuppressWarnings("unchecked")
	public T next() {
		if (items.length == size) {
			Clearable[] old = items;
			items = new Clearable[MathUtils.ceil(items.length * 1.5F)];
			System.arraycopy(old, 0, items, 0, old.length);
		}
		int index = size;
		if (size < items.length) {
			size++;
		}
		if (items[index] == null) {
			items[index] = supplier.get();
		} else {
			items[index].clear();
		}
		return (T) items[index];
	}

	public void clear() {
		for (int i = 0; i < size; i++) {
			items[i].clear();
		}
		size = 0;
	}

	public void clear(int amount) {
		if (amount == 0) {
			return;
		} else if (amount > size) {
			throw new ArrayIndexOutOfBoundsException("Cannot clear " + amount + " elements from array with size " + amount);
		}
		for (int i = 0; i < amount; i++, size--) {
			items[size - 1].clear();
		}
	}

	@SuppressWarnings("unchecked")
	public T get(int index) {
		if (index >= size) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		return (T) items[size - index - 1];
	}

	public static interface Clearable {

		void clear();

	}
}
