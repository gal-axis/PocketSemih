package com.one2b3.endcycle.features.models.connect;

import java.util.List;

import com.one2b3.endcycle.engine.assets.GameAsset;
import com.one2b3.endcycle.engine.assets.GameLoader;
import com.one2b3.endcycle.utils.ID;
import com.one2b3.modding.diff.Moddable;
import com.one2b3.modding.diff.ModdedList;

public interface ModdableConnector<T extends Moddable> extends GameAsset, Connector<T, ID> {

	ModdedList<T> getList();

	default String getToolboxGroup() {
		return getName() + "s";
	}

	@Override
	default String getName() {
		String name = getList().getListType().getSimpleName();
		int index = name.lastIndexOf("Data");
		return (index == -1 ? name : name.substring(0, index));
	}

	default String getLocalizeKey() {
		return null;
	}

	@Override
	default void load(GameLoader loader) {
		getList().load();
		Connectors.put(getClass(), this);
	}

	default void save() {
		getList().save();
	}

	@Override
	default List<T> getValues() {
		return getList().objects;
	}

	default ModdableCreatorList<T> getCreators() {
		return new ModdableCreatorList<>(getName(), this, getList()::create);
	}

	@Override
	default ID getValue(T object) {
		return object == null ? null : object.getId();
	}

	default void update(T object) {
	}

	default void delete(T object) {
	}

}
