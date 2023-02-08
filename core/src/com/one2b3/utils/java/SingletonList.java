package com.one2b3.utils.java;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.RandomAccess;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public final class SingletonList<E> extends AbstractList<E> implements RandomAccess, Serializable {

	private static final long serialVersionUID = 3093736618740652951L;

	private E element;

	public SingletonList(E obj) {
		element = obj;
	}

	@Override
	public Iterator<E> iterator() {
		return new SingletonIterator<>(element);
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public boolean contains(Object obj) {
		return Objects.equals(obj, element);
	}

	@Override
	public E set(int index, E element) {
		if (index != 0) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: 1");
		}
		E old = this.element;
		this.element = element;
		return old;
	}

	@Override
	public E get(int index) {
		if (index != 0) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: 1");
		}
		return element;
	}

	@Override
	public void forEach(Consumer<? super E> action) {
		action.accept(element);
	}

	@Override
	public boolean removeIf(Predicate<? super E> filter) {
		return false;
	}

	@Override
	public void replaceAll(UnaryOperator<E> operator) {
		element = operator.apply(element);
	}

	@Override
	public void sort(Comparator<? super E> c) {
	}

}