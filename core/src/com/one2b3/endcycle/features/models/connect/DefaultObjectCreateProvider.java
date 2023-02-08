package com.one2b3.endcycle.features.models.connect;

import java.util.ArrayList;
import java.util.List;

import com.one2b3.endcycle.engine.assets.ClassIdentifier;

import lombok.Getter;

public class DefaultObjectCreateProvider implements ObjectCreateProvider {

	@Getter
	List<ClassIdentifier<?>> identifiers;

	public DefaultObjectCreateProvider(Object... objects) {
		identifiers = new ArrayList<>();
		for (int i = 0; i < objects.length; i++) {
			Object obj = objects[i];
			if (obj instanceof Class) {
				identifiers.add(new ClassIdentifier<>((Class<?>) obj, (String) null));
			}
		}
	}

}
