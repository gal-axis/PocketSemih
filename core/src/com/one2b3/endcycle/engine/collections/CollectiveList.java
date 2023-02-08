package com.one2b3.endcycle.engine.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.one2b3.endcycle.engine.proguard.KeepClass;
import com.one2b3.endcycle.utils.ID;
import com.one2b3.endcycle.utils.objects.IdEntity;

@KeepClass
public class CollectiveList<E> {

	public final List<E> uniques = new ArrayList<>();
	final Map<E, Integer> elementAmount = new HashMap<>();
	public int duplicates;

	protected CollectiveList() {
	}

	public CollectiveList(int duplicates) {
		this();
		this.duplicates = duplicates;
	}

	public CollectiveList(CollectiveList<E> list) {
		duplicates = list.duplicates;
		addAll(list);
	}

	private void decrease(E arg) {
		int amount = amountOf(arg) - 1;
		if (amount <= 0) {
			uniques.remove(arg);
			elementAmount.remove(arg);
		} else {
			elementAmount.put(arg, amount);
		}
	}

	public void swap(E object1, E object2) {
		swap(indexOf(object1), indexOf(object2));
	}

	public void swap(int index1, int index2) {
		E object1 = get(index1);
		E object2 = uniques.set(index2, object1);
		uniques.set(index1, object2);
	}

	public E get(int index) {
		if (index < 0 || index >= uniques.size()) {
			return null;
		}
		return uniques.get(index);
	}

	public boolean add(E arg) {
		return add(arg, 1);
	}

	public boolean add(E arg, int amount) {
		if (amount > 0 && (duplicates <= 0 || amountOf(arg) + amount <= duplicates)) {
			elementAmount.put(arg, amountOf(arg) + amount);
			if (!uniques.contains(arg)) {
				uniques.add(arg);
			}
			return true;
		}
		return false;
	}

	public boolean remove(E arg) {
		if (uniques.contains(arg)) {
			decrease(arg);
			return true;
		}
		return false;
	}

	public E remove(int index) {
		if (index < 0 || index >= uniques.size()) {
			return null;
		}
		E unique = uniques.get(index);
		decrease(unique);
		return unique;
	}

	public boolean contains(E object) {
		return uniques.contains(object);
	}

	public boolean addAll(CollectiveList<E> list) {
		if (list == null || list.size() == 0) {
			return false;
		}
		for (int i = 0; i < list.size(); i++) {
			E object = list.get(i);
			int amount = list.amountOf(object);
			add(object, amount);
		}
		return true;
	}

	public void clear() {
		elementAmount.clear();
		uniques.clear();
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public int size() {
		return uniques.size();
	}

	public int amountOf(Object arg) {
		Integer amount = elementAmount.get(arg);
		return (amount == null || amount < 0 ? 0 : amount);
	}

	public int count(ID id) {
		int amount = 0;
		for (E element : uniques) {
			IdEntity entityId = (IdEntity) element;
			if (entityId != null && ID.equals(entityId.getId(), id)) {
				amount += amountOf(element);
			}
		}
		return amount;
	}

	public int indexOf(Object arg) {
		return uniques.indexOf(arg);
	}

	@Override
	public String toString() {
		return "CollectiveList[" + elementAmount.toString() + "]";
	}

	public void sort(Comparator<? super E> comparator) {
		Collections.sort(uniques, comparator);
	}
}
