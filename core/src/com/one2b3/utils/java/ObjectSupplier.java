package com.one2b3.utils.java;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ObjectSupplier<T> implements Supplier<T> {

	final T object;

	@Override
	public T get() {
		return object;
	}

	@Override
	public String toString() {
		return object == null ? null : object.toString();
	}

}
