package com.one2b3.utils.conditions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.features.revo.GameData;

public final class Conditions<T> {

	public T entity;
	Array<Condition<? super T>> conditions = new Array<>(3), temp = new Array<>();

	public Conditions() {
	}

	public Conditions(T entity) {
		this.entity = entity;
	}

	@SafeVarargs
	public Conditions(T entity, Condition<T>... condition) {
		this.entity = entity;
		if (condition != null) {
			conditions.ensureCapacity(condition.length);
			for (int i = 0; i < condition.length; i++) {
				add(condition[i]);
			}
		}
	}

	public void clear() {
		conditions.clear();
		temp.clear();
	}

	public void set(boolean value) {
		clear();
		add(value ? Condition.TRUE : Condition.FALSE);
	}

	public void add(Condition<? super T> condition) {
		if (condition != null) {
			conditions.add(condition);
		}
	}

	public void addTemp(Condition<? super T> condition) {
		if (condition != null) {
			temp.add(condition);
		}
	}

	public boolean contains(Condition<? super T> condition) {
		return temp.contains(condition, true) || conditions.contains(condition, true);
	}

	public boolean remove(Condition<? super T> condition) {
		return temp.removeValue(condition, true) || conditions.removeValue(condition, true);
	}

	public int size() {
		return conditions.size + temp.size;
	}

	public boolean get(Object... params) {
		boolean finalResult = false;
		for (int i = temp.size - 1; i >= 0; i--) {
			boolean result = test(temp.get(i), params);
			if (!result) {
				temp.removeIndex(i);
			}
			finalResult |= result;
		}
		for (int i = conditions.size - 1; i >= 0; i--) {
			finalResult |= test(conditions.get(i), params);
		}
		return finalResult;
	}

	private boolean test(Condition<? super T> condition, Object... params) {
		if (params != null && condition instanceof ParamCondition) {
			return ((ParamCondition<? super T>) condition).test(entity, params);
		}
		return condition.test(entity);
	}

	public boolean debug(Object... params) {
		boolean finalResult = false;
		for (int i = temp.size - 1; i >= 0; i--) {
			Condition<? super T> condition = temp.get(i);
			boolean result = test(condition, params);
			if (!result) {
				temp.removeIndex(i);
			}
			finalResult |= result;
			Gdx.app.debug("Condition", GameData.toString(condition) + " (Temp) is " + result);
		}
		for (int i = conditions.size - 1; i >= 0; i--) {
			Condition<? super T> condition = conditions.get(i);
			boolean result = test(condition, params);
			finalResult |= result;
			Gdx.app.debug("Condition", GameData.toString(condition) + " is " + result);
		}
		return finalResult;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != getClass()) {
			return false;
		}
		Conditions<?> other = (Conditions<?>) obj;
		return other.conditions.equalsIdentity(conditions) && other.temp.equalsIdentity(temp);
	}

}
