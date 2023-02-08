package com.one2b3.endcycle.engine.assets;

import com.one2b3.endcycle.features.models.Description;
import com.one2b3.endcycle.features.models.Name;
import com.one2b3.endcycle.utils.StringUtil;
import com.one2b3.endcycle.utils.objects.Describable;
import com.one2b3.endcycle.utils.objects.Named;
import com.one2b3.endcycle.utils.reflect.ClassUtils;
import com.one2b3.utils.java.Supplier;

import lombok.Getter;

public class ClassIdentifier<T> implements Named, Describable, Comparable<Named> {

	@Getter
	final String id, name, description;
	final Supplier<? extends T> create;

	public ClassIdentifier(Class<? extends T> clazz, String suffix) {
		this.id = clazz.getName();
		Name nameValue = clazz.getAnnotation(Name.class);
		this.name = (nameValue == null ? StringUtil.prettyName(shortenName(clazz.getSimpleName(), suffix))
				: nameValue.value());
		Description description = clazz.getAnnotation(Description.class);
		this.description = (description == null ? null : description.value());
		this.create = ClassUtils.supplier(clazz);
	}

	private String shortenName(String name, String suffix) {
		if (suffix == null || !name.endsWith(suffix)) {
			return name;
		}
		return name.substring(0, name.length() - suffix.length());
	}

	public T create() {
		return create.get();
	}

	@Override
	public int compareTo(Named o) {
		return getName().compareTo(o.getName());
	}

}
