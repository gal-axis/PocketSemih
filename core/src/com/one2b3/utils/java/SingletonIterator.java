package com.one2b3.utils.java;

import java.util.Iterator;
import java.util.NoSuchElementException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class SingletonIterator<E> implements Iterator<E> {

	final E e;
	boolean hasNext = true;

	@Override
	public boolean hasNext() {
		return hasNext;
	}

	@Override
	public E next() {
		if (hasNext) {
			hasNext = false;
			return e;
		}
		throw new NoSuchElementException();
	}

}