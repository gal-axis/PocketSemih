package com.one2b3.endcycle.features.models.connect;

import java.util.List;

import com.one2b3.utils.java.Supplier;

public class ValueConnector<T> implements Connector<T, T> {

	final Supplier<List<T>> objects;

	public ValueConnector(Supplier<List<T>> objects) {
		this.objects = objects;
	}

	@Override
	public List<T> getValues() {
		return objects.get();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T find(Object currentValue) {
		return (T) currentValue;
	}

	@Override
	public T getValue(T object) {
		return object;
	}
}
