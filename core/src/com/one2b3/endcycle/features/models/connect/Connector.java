package com.one2b3.endcycle.features.models.connect;

import java.util.List;

import com.one2b3.utils.java.Objects;

public interface Connector<T, R> {

	default T find(Object currentValue) {
		List<T> list = getValues();
		for (int i = 0; i < list.size(); i++) {
			T val = list.get(i);
			if (Objects.equals(getValue(val), currentValue)) {
				return val;
			}
		}
		return null;
	}

	default String getName() {
		return null;
	}

	default String toString(T object) {
		return null;
	}

	List<T> getValues();

	R getValue(T object);
}
