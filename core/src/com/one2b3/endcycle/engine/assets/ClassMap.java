package com.one2b3.endcycle.engine.assets;

import java.util.*;

import com.one2b3.endcycle.features.models.connect.ObjectCreateProvider;
import com.one2b3.endcycle.utils.StringUtil;

import lombok.Setter;

public class ClassMap<T> implements ObjectCreateProvider {

	@Setter
	String suffix;
	List<ClassIdentifier<T>> types;
	Map<String, ClassIdentifier<T>> typeMap;

	public ClassMap() {
		types = new ArrayList<>();
		typeMap = new HashMap<>();
	}

	public void add(Class<? extends T> type) {
		if (type != null) {
			add(new ClassIdentifier<>(type, suffix));
		}
	}

	public void sort() {
		Collections.sort(types);
	}

	public void add(ClassIdentifier<T> identifier) {
		types.add(identifier);
		typeMap.put(identifier.getId(), identifier);
	}

	public void remove(ClassIdentifier<T> identifier) {
		types.remove(identifier);
		typeMap.remove(identifier.getId());
	}

	public ClassIdentifier<T> getIdentifier(String name) {
		return typeMap.get(name);
	}

	public T get(String name) {
		ClassIdentifier<T> identifier = getIdentifier(name);
		return identifier == null ? null : identifier.create();
	}

	@Override
	public String shortenName(String name) {
		return suffix == null ? name : StringUtil.cut(name, suffix);
	}

	@Override
	public List<ClassIdentifier<T>> getIdentifiers() {
		return types;
	}

}
