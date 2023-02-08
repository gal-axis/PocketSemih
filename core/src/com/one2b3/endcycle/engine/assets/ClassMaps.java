package com.one2b3.endcycle.engine.assets;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.one2b3.endcycle.utils.StringUtil;

public class ClassMaps {

	private static final Map<Type, ClassMap<?>> maps;

	static {
		maps = new HashMap<>();
	}

	public static void add(Type type, ClassMap<?> map) {
		maps.put(type, map);
	}

	public static Set<Type> getTypes() {
		return maps.keySet();
	}

	@SuppressWarnings("unchecked")
	public static <T> ClassMap<T> get(Class<T> type) {
		return (ClassMap<T>) maps.get(type);
	}

	public static ClassMap<?> get(Object type) {
		return maps.get(type);
	}

	public static String toString(Class<?> type, Object object) {
		if (object == null) {
			return null;
		}
		String name = object.getClass().getSimpleName();
		ClassMap<?> map = get(type);
		return StringUtil.prettyName(map == null ? name : map.shortenName(name));
	}
}
