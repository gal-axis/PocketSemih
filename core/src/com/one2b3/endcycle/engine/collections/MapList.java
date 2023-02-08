package com.one2b3.endcycle.engine.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapList<K, V> {

	public final List<V> list = new ArrayList<>();
	public final Map<K, V> map = new HashMap<>();

	public void add(K key, V value) {
		list.add(value);
		map.put(key, value);
	}

	public void remove(K key) {
		V value = map.remove(key);
		list.remove(value);
	}

	public V get(K key) {
		return map.get(key);
	}
}
