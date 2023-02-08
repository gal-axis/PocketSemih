package com.one2b3.utils.conditions;

public interface Condition<T> {

	Condition<Object> TRUE = e -> true;
	Condition<Object> FALSE = e -> false;

	static Condition<Object> get(boolean isTrue) {
		return (isTrue ? TRUE : FALSE);
	}

	boolean test(T entity);

}
