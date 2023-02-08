package com.one2b3.endcycle.engine.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.one2b3.endcycle.engine.proguard.KeepClass;
import com.one2b3.endcycle.features.revo.GameData;
import com.one2b3.utils.java.Objects;

@KeepClass
public class FixedList<E> implements List<E>, Json.Serializable {

	Object[] elements;

	FixedList() {
	}

	public FixedList(int size) {
		elements = new Object[size];
	}

	public FixedList(Object[] elements) {
		this.elements = elements;
	}

	public void resize(int size) {
		if (size != elements.length) {
			Object[] newElements = new Object[size];
			System.arraycopy(elements, 0, newElements, 0, Math.min(elements.length, size));
			this.elements = newElements;
		}
	}

	@Override
	public boolean add(E object) {
		return addGet(object) != -1;
	}

	public int addGet(E object) {
		for (int i = 0; i < size(); i++) {
			if (get(i) == null) {
				set(i, object);
				return i;
			}
		}
		return -1;
	}

	@Override
	@SuppressWarnings("unchecked")
	public E get(int index) {
		return (E) elements[index];
	}

	@Override
	public Iterator<E> iterator() {
		return new FixedListIterator<>(this);
	}

	@Override
	public E remove(int index) {
		return set(index, null);
	}

	@Override
	public E set(int index, E object) {
		if (index < 0 || index > size()) {
			return null;
		}
		@SuppressWarnings("unchecked")
		E returned = (E) elements[index];
		elements[index] = object;
		return returned;
	}

	public E pop() {
		for (int i = elements.length - 1; i >= 0; i--) {
			if (elements[i] != null) {
				@SuppressWarnings("unchecked")
				E element = (E) elements[i];
				elements[i] = null;
				return element;
			}
		}
		return null;
	}

	public void collect() {
		int lastElement = 0;
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] != null) {
				if (lastElement != i) {
					elements[lastElement] = elements[i];
					elements[i] = null;
				}
				lastElement++;
			}
		}
	}

	public int count() {
		int amount = 0;
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] != null) {
				amount++;
			}
		}
		return amount;
	}

	@Override
	public int size() {
		return elements.length;
	}

	@Override
	public boolean isEmpty() {
		for (int i = elements.length - 1; i >= 0; i--) {
			if (elements[i] != null) {
				return false;
			}
		}
		return true;
	}

	public void copy(FixedList<E> list) {
		resize(list.size());
		for (int i = 0; i < elements.length; i++) {
			elements[i] = list.get(i);
		}
	}

	@Override
	public boolean contains(Object o) {
		return indexOf(o, false) != -1;
	}

	@Override
	public Object[] toArray() {
		return elements;
	}

	@Override
	public boolean remove(Object o) {
		return remove(o, false) != -1;
	}

	public int remove(Object o, boolean identity) {
		for (int i = 0; i < elements.length; i++) {
			if (identity ? elements[i] == o : Objects.equals(elements[i], o)) {
				elements[i] = null;
				return i;
			}
		}
		return -1;
	}

	@Override
	public void clear() {
		for (int i = 0; i < elements.length; i++) {
			elements[i] = null;
		}
	}

	@Override
	public int indexOf(Object o) {
		return indexOf(o, false);
	}

	public int indexOf(Object o, boolean identity) {
		for (int i = 0; i < elements.length; i++) {
			if (identity ? elements[i] == o : Objects.equals(elements[i], o)) {
				return i;
			}
		}
		return -1;
	}

	public int lastIndexOf(Object o, boolean identity) {
		for (int i = elements.length - 1; i >= 0; i--) {
			if (identity ? elements[i] == o : Objects.equals(elements[i], o)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		return lastIndexOf(o, false);
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<E> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return GameData.toString(elements);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != getClass()) {
			return false;
		}
		FixedList<?> list = (FixedList<?>) obj;
		if (list.elements.length != elements.length) {
			return false;
		}
		for (int i = 0; i < elements.length; i++) {
			if (!Objects.equals(elements[i], list.elements[i])) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void write(Json json) {
		json.writeArrayStart("items");
		for (int i = 0; i < elements.length; i++) {
			json.writeValue(elements[i], null);
		}
		json.writeArrayEnd();
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		this.elements = new Object[jsonData.size];
		for (JsonValue entry = jsonData.child; entry != null; entry = entry.next) {
			add(json.readValue(null, entry));
		}
	}
}
