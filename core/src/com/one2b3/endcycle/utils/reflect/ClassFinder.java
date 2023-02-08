package com.one2b3.endcycle.utils.reflect;

import java.util.Set;

public class ClassFinder {

	public static Class<?> findClass(Set<?> classes, Class<?> objectClass) {
		if (classes.contains(objectClass)) {
			return objectClass;
		}
		Class<?>[] interfaces = objectClass.getInterfaces();
		if (interfaces.length > 0) {
			for (int i = 0; i < interfaces.length; i++) {
				Class<?> interfaceClass = findClass(classes, interfaces[i]);
				if (interfaceClass != null) {
					return interfaceClass;
				}
			}
		}
		Class<?> superClass = objectClass.getSuperclass();
		if (superClass != null) {
			return findClass(classes, superClass);
		}
		return null;
	}
}
