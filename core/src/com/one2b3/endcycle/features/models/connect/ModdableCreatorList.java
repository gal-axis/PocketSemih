package com.one2b3.endcycle.features.models.connect;

import java.util.function.Supplier;

import com.badlogic.gdx.utils.Array;
import com.one2b3.modding.diff.Moddable;

public class ModdableCreatorList<T extends Moddable> extends Array<ModdableCreator<T>> {

	final ModdableConnector<T> connector;

	public ModdableCreatorList() {
		this.connector = null;
	}

	public ModdableCreatorList(ModdableConnector<T> list) {
		this.connector = list;
	}

	public ModdableCreatorList(ModdableCreator<T> creator) {
		this.connector = creator.connector;
		add(creator);
	}

	public ModdableCreatorList(String name, ModdableConnector<T> list, Supplier<? extends T> supplier) {
		this.connector = list;
		add(name, supplier);
	}

	@SuppressWarnings("unchecked")
	public ModdableCreatorList<T> add(ModdableCreatorList<? extends T> list) {
		for (int i = 0; i < list.size; i++) {
			add((ModdableCreator<T>) list.get(i));
		}
		return this;
	}

	public ModdableCreatorList<T> add(String name, Supplier<? extends T> supplier) {
		if (connector != null) {
			add(new ModdableCreator<>(name, connector, supplier));
		}
		return this;
	}

}