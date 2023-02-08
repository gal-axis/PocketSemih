package com.one2b3.utils;

import java.lang.reflect.Array;

public class Arrays {

	private Arrays() {
	}

	public static int find(int[] array, int value) {
		if (array == null) {
			return -1;
		}
		for (int i = 0; i < array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}

	public static Object get(Object array, int index) {
		Class<?> type = array.getClass().getComponentType();
		if (type.isPrimitive()) {
			if (type == int.class) {
				return ((int[]) array)[index];
			} else if (type == byte.class) {
				return ((byte[]) array)[index];
			} else if (type == short.class) {
				return ((short[]) array)[index];
			} else if (type == long.class) {
				return ((long[]) array)[index];
			} else if (type == float.class) {
				return ((float[]) array)[index];
			} else if (type == double.class) {
				return ((double[]) array)[index];
			} else if (type == boolean.class) {
				return ((boolean[]) array)[index];
			} else if (type == char.class) {
				return ((char[]) array)[index];
			}
		} else if (type.isArray() && type.getComponentType().isPrimitive()) {
			return Array.get(array, index);
		}
		return ((Object[]) array)[index];
	}

	public static void set(Object array, int index, Object value) {
		Class<?> type = array.getClass().getComponentType();
		if (type.isPrimitive()) {
			if (type == int.class) {
				((int[]) array)[index] = (int) value;
			} else if (type == byte.class) {
				((byte[]) array)[index] = (byte) value;
			} else if (type == short.class) {
				((short[]) array)[index] = (short) value;
			} else if (type == long.class) {
				((long[]) array)[index] = (long) value;
			} else if (type == float.class) {
				((float[]) array)[index] = (float) value;
			} else if (type == double.class) {
				((double[]) array)[index] = (double) value;
			} else if (type == boolean.class) {
				((boolean[]) array)[index] = (boolean) value;
			} else if (type == char.class) {
				((char[]) array)[index] = (char) value;
			}
		} else if (type.isArray() && type.getComponentType().isPrimitive()) {
			Array.set(array, index, value);
		} else {
			((Object[]) array)[index] = value;
		}
	}

	public static boolean deepEquals(Object[] equals1, Object[] equals2) {
		return java.util.Arrays.deepEquals(equals1, equals2);
	}

	public static int getLength(Object value) {
		return Array.getLength(value);
	}

	public static Object newInstance(Class<?> componentType, int length) {
		return Array.newInstance(componentType, length);
	}
}
