package com.one2b3.endcycle.engine.collections;

import java.util.Iterator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FixedListIterator<E> implements Iterator<E> {

	final FixedList<E> list;
	int index;

	@Override
	public boolean hasNext() {
		return index < list.size();
	}

	@Override
	public E next() {
		return list.get(index++);
	}

}
