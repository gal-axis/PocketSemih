package com.one2b3.modding.diff;

import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.utils.ID;

public class ModdableArray<T extends Moddable> extends Array<T> {

	@Override
	public void add(T value) {
		if (value == null) {
			return;
		}
		if (value.id == null || value.id.equals(0, 0)) {
			value.id = nextId();
		}
		super.add(value);
	}

	@Override
	public void set(int index, T value) {
		if (value == null) {
			return;
		}
		if (value.id == null || value.id.equals(0, 0)) {
			value.id = nextId();
		}
		super.set(index, value);
	}

	public T get(ID id) {
		if (id == null) {
			return null;
		}
		for (int i = 0; i < size; i++) {
			if (id.equals(get(i).id)) {
				return get(i);
			}
		}
		return null;
	}

	public ID nextId() {
		ID id = new ID(0);
		while (get(id) != null) {
			id.increment();
		}
		return id;
	}
}
