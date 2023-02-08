package com.one2b3.endcycle.utils.reflect;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Set;

import com.one2b3.utils.java.Supplier;

public class ClassUtils {

	static final Set<Class<?>> wrappers = new HashSet<>();
	static {
		wrappers.add(Integer.class);
		wrappers.add(Long.class);
		wrappers.add(Boolean.class);
		wrappers.add(Float.class);
		wrappers.add(Short.class);
		wrappers.add(Byte.class);
		wrappers.add(Double.class);
	}

	@SuppressWarnings("unchecked")
	public static <T> T newGenericInstance(Class<?> type) {
		try {
			Object newInstance = newInstance(type);
			return (T) newInstance;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<T> type) throws InstantiationException, IllegalAccessException {
		Object primitive = getDefaultPrimitive(type);
		if (primitive != null) {
			return (T) primitive;
		} else if (type.isEnum()) {
			return type.getEnumConstants()[0];
		} else if (type.isArray()) {
			return (T) Array.newInstance(type.getComponentType(), 0);
		} else {
			return type.newInstance();
		}
	}

	public static Object getDefaultPrimitive(Class<?> type) {
		if (type == int.class || type == Integer.class) {
			return 0;
		} else if (type == long.class || type == Long.class) {
			return 0L;
		} else if (type == boolean.class || type == Boolean.class) {
			return false;
		} else if (type == float.class || type == Float.class) {
			return 0.0F;
		} else if (type == short.class || type == Short.class) {
			return (short) 0;
		} else if (type == byte.class || type == Byte.class) {
			return (byte) 0;
		} else if (type == double.class || type == Double.class) {
			return 0.0;
		} else {
			return null;
		}
	}

	public static boolean isPrimitiveOrWrapper(Class<?> type) {
		return type.isPrimitive() || wrappers.contains(type);
	}

	public static boolean isPrimitiveMatch(Object object, Object type) {
		if (object == null) {
			return false;
		}
		if (type == int.class || type == Integer.class) {
			return object instanceof Integer;
		} else if (type == long.class || type == Long.class) {
			return object instanceof Long;
		} else if (type == boolean.class || type == Boolean.class) {
			return object instanceof Boolean;
		} else if (type == float.class || type == Float.class) {
			return object instanceof Float;
		} else if (type == short.class || type == Short.class) {
			return object instanceof Short;
		} else if (type == byte.class || type == Byte.class) {
			return object instanceof Byte;
		} else if (type == double.class || type == Double.class) {
			return object instanceof Double;
		}
		return false;
	}

	public static Class<?> getBoxedType(Class<?> clazz) {
		if (!clazz.isPrimitive()) {
			return clazz;
		}
		if (clazz == Integer.TYPE) {
			return Integer.class;
		}
		if (clazz == Long.TYPE) {
			return Long.class;
		}
		if (clazz == Boolean.TYPE) {
			return Boolean.class;
		}
		if (clazz == Byte.TYPE) {
			return Byte.class;
		}
		if (clazz == Character.TYPE) {
			return Character.class;
		}
		if (clazz == Float.TYPE) {
			return Float.class;
		}
		if (clazz == Double.TYPE) {
			return Double.class;
		}
		if (clazz == Short.TYPE) {
			return Short.class;
		}
		if (clazz == Void.TYPE) {
			return Void.class;
		}
		return clazz;
	}

	public static <T> Supplier<T> supplier(Class<T> clazz) {
		return () -> newGenericInstance(clazz);
	}
}
