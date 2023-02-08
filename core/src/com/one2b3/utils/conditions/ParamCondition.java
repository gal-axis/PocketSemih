package com.one2b3.utils.conditions;

public interface ParamCondition<T> extends Condition<T> {

	@Override
	default boolean test(T entity) {
		return test(entity, null);
	}

	boolean test(T entity, Object[] param);
}
