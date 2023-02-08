package com.one2b3.endcycle.features.models.connect;

import java.util.List;

import com.one2b3.endcycle.engine.assets.ClassIdentifier;
import com.one2b3.endcycle.engine.assets.ClassMap;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClassMapConnector<T> implements Connector<ClassIdentifier<T>, String> {

	final ClassMap<T> map;

	@Override
	public List<ClassIdentifier<T>> getValues() {
		return map.getIdentifiers();
	}

	@Override
	public String getValue(ClassIdentifier<T> object) {
		return object.getId();
	}

}
