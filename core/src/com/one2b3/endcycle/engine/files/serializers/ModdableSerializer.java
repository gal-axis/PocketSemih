package com.one2b3.endcycle.engine.files.serializers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializer;
import com.badlogic.gdx.utils.JsonValue;
import com.one2b3.endcycle.features.models.connect.ModdableConnector;
import com.one2b3.endcycle.utils.ID;
import com.one2b3.endcycle.utils.reflect.ClassUtils;
import com.one2b3.modding.diff.Catalog;
import com.one2b3.modding.diff.Moddable;

@SuppressWarnings("rawtypes")
public class ModdableSerializer implements Serializer<Moddable> {

	private static final String CUSTOM_TAG = "_custom_";

	@Override
	public void write(Json json, Moddable object, Class knownType) {
		if (object != null && object.id == null) {
			json.writeObjectStart();
			json.writeValue(CUSTOM_TAG, true);
			json.writeFields(object);
			json.writeObjectEnd();
		} else if (hasCatalog(knownType)) {
			json.writeValue(object == null ? null : object.getId());
		} else {
			Gdx.app.error("Json", "Moddables cannot be written to Json! Please serialize by ID!");
			json.writeValue(null);
		}
	}

	private boolean hasCatalog(Class<?> knownType) {
		if (knownType == null) {
			return false;
		}
		return knownType != null && knownType.isAnnotationPresent(Catalog.class);
	}

	private ModdableConnector<Moddable> getCatalog(Class<?> knownType) {
		if (knownType == null) {
			return null;
		}
		Catalog catalog = knownType.getAnnotation(Catalog.class);
		if (catalog == null) {
			return null;
		}
		return ClassUtils.newGenericInstance(catalog.value());
	}

	@Override
	public Moddable read(Json json, JsonValue jsonData, Class type) {
		if (jsonData.has(CUSTOM_TAG)) {
			Moddable instance = ClassUtils.newGenericInstance(type);
			json.readFields(instance, jsonData);
			return instance;
		} else {
			ModdableConnector<Moddable> connector = getCatalog(type);
			if (connector == null) {
				Gdx.app.error("Json", "Moddables cannot be read Json! Please serialize by ID!");
				return null;
			}
			ID id = json.readValue(ID.class, jsonData);
			return id == null ? null : connector.find(id);
		}
	}
}
