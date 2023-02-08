package com.one2b3.utils;

import java.util.IdentityHashMap;

import com.badlogic.gdx.utils.Array;

public class ArraySet<T> extends Array<T> {

	IdentityHashMap<T, Integer> map = new IdentityHashMap<>();

	public ArraySet() {
	}

	public ArraySet(int capacity) {
		super(capacity);
	}

	public ArraySet(boolean ordered, int capacity) {
		super(ordered, capacity);
	}

	public ArraySet(boolean ordered, int capacity, Class<?> arrayType) {
		super(ordered, capacity, arrayType);
	}

	public ArraySet(Class<?> arrayType) {
		super(arrayType);
	}

	private void removeSet(int index) {
		T old = items[index];
		if (old != null) {
			Integer count = map.get(old);
			count--;
			if (count <= 0) {
				map.remove(old);
			} else {
				map.put(old, count);
			}
		}
	}

	private void addSet(T value) {
		if (value != null) {
			Integer count = map.get(value);
			if (count == null) {
				map.put(value, 1);
			} else {
				map.put(value, count + 1);
			}
		}
	}

	@Override
	public void clear() {
		super.clear();
		map.clear();
	}

	@Override
	public boolean contains(T value, boolean identity) {
		if (identity) {
			return map.containsKey(value);
		}
		return super.contains(value, identity);
	}

	@Override
	public void set(int index, T value) {
		removeSet(index);
		addSet(value);
		super.set(index, value);
	}

	@Override
	public void add(T value) {
		super.add(value);
		addSet(value);
	}

	@Override
	public void add(T value1, T value2) {
		super.add(value1, value2);
		addSet(value1);
		addSet(value2);
	}

	@Override
	public void add(T value1, T value2, T value3) {
		super.add(value1, value2, value3);
		addSet(value1);
		addSet(value2);
		addSet(value3);
	}

	@Override
	public void add(T value1, T value2, T value3, T value4) {
		super.add(value1, value2, value3, value4);
		addSet(value1);
		addSet(value2);
		addSet(value3);
		addSet(value4);
	}

	@Override
	public void addAll(T[] array, int start, int count) {
		for (int i = 0; i < count; i++) {
			addSet(array[start + count]);
		}
		super.addAll(array, start, count);
	}

	@Override
	public void insert(int index, T value) {
		addSet(value);
		super.insert(index, value);
	}

	@Override
	public void truncate(int newSize) {
		if (size <= newSize) {
			return;
		}
		for (int i = newSize; i < size; i++) {
			removeSet(i);
		}
		super.truncate(newSize);
	}

	@Override
	public T removeIndex(int index) {
		removeSet(index);
		return super.removeIndex(index);
	}

	@Override
	public T pop() {
		if (size > 0) {
			removeSet(size - 1);
		}
		return super.pop();
	}

	@Override
	public void removeRange(int start, int end) {
		if (start < end && end < size) {
			for (int i = start; i < end; i++) {
				removeSet(i);
			}
		}
		super.removeRange(start, end);
	}

}
