package com.one2b3.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.badlogic.gdx.utils.Array;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class ArrayWrapperList<T> implements List<T> {

	public final Array<T> array;

	@Override
	public int size() {
		return array.size;
	}

	@Override
	public boolean isEmpty() {
		return array.size == 0;
	}

	@Override
	public boolean contains(Object object) {
		return array.contains((T) object, false);
	}

	@Override
	public Iterator<T> iterator() {
		return array.iterator();
	}

	@Override
	public Object[] toArray() {
		return array.toArray();
	}

	@Override
	public <E> E[] toArray(E[] array) {
		return (E[]) this.array.toArray(array.getClass().getComponentType());
	}

	@Override
	public boolean add(T element) {
		array.add(element);
		return true;
	}

	@Override
	public boolean remove(Object object) {
		return array.removeValue((T) object, false);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object object : c) {
			if (!contains(object)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		for (T object : c) {
			add(object);
		}
		return true;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		for (T object : c) {
			add(index, object);
		}
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		for (Object object : c) {
			remove(object);
		}
		return true;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		for (int i = array.size - 1; i >= 0; i--) {
			if (!c.contains(array.get(i))) {
				array.removeIndex(i);
			}
		}
		return true;
	}

	@Override
	public void clear() {
		array.clear();
	}

	@Override
	public T get(int index) {
		return array.get(index);
	}

	@Override
	public T set(int index, T element) {
		T old = array.get(index);
		array.set(index, element);
		return old;
	}

	@Override
	public void add(int index, T element) {
		array.insert(index, element);
	}

	@Override
	public T remove(int index) {
		return array.removeIndex(index);
	}

	@Override
	public int indexOf(Object object) {
		return array.indexOf((T) object, false);
	}

	@Override
	public int lastIndexOf(Object object) {
		return array.lastIndexOf((T) object, false);
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException("Cannot create an array list iterator!");
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		throw new UnsupportedOperationException("Cannot create an array list iterator!");
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException("Cannot sublist an array wrapper!");
	}

}
