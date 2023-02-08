package com.one2b3.endcycle.utils.reflect;

import java.lang.reflect.Field;

public class ClassFields {

	final Field[] fields;

	public ClassFields(Class<?> clazz, String... names) {
		this.fields = new Field[names.length];
		for (int i = 0; i < names.length; i++) {
			try {
				Field field = getField(clazz, names[i]);
				if (field != null) {
					field.setAccessible(true);
					this.fields[i] = field;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Field getField(Class<?> clazz, String name) {
		try {
			return clazz.getDeclaredField(name);
		} catch (NoSuchFieldException e) {
			if (!clazz.equals(Object.class)) {
				return getField(clazz.getSuperclass(), name);
			}
		}
		return null;
	}

	public Field[] getFields() {
		return fields;
	}
}
