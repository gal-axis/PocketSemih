package com.one2b3.endcycle.features.models.connect;

import java.util.List;

import com.one2b3.endcycle.engine.assets.ClassIdentifier;

public interface ObjectCreateProvider {

	List<? extends ClassIdentifier<?>> getIdentifiers();

	default String shortenName(String name) {
		return name;
	}

	default boolean isSingle() {
		return getIdentifiers().size() == 1;
	}

	default ClassIdentifier<?> find(String name) {
		for (ClassIdentifier<?> identifier : getIdentifiers()) {
			if (identifier.getId().equals(name)) {
				return identifier;
			}
		}
		return null;
	}

	default Object create() {
		return isSingle() ? getIdentifiers().get(0).create() : null;
	}
}
