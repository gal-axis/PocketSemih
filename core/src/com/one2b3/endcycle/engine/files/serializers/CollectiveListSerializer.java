package com.one2b3.endcycle.engine.files.serializers;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializer;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SerializationException;
import com.one2b3.endcycle.engine.collections.CollectiveList;

@SuppressWarnings("rawtypes")
public class CollectiveListSerializer implements Serializer<CollectiveList> {

	@Override
	public void write(Json json, CollectiveList object, Class knownType) {
		json.writeObjectStart(object.getClass(), knownType);
		json.writeValue("duplicates", object.duplicates);
		json.writeArrayStart("uniques");
		for (Object o : object.uniques) {
			json.writeObjectStart();
			json.writeValue("amount", object.amountOf(o));
			if (o != null) {
				json.writeValue("class", o.getClass().getName());
			}
			json.writeValue("item", o);
			json.writeObjectEnd();
		}
		json.writeArrayEnd();
		json.writeObjectEnd();
	}

	@Override
	public CollectiveList read(Json json, JsonValue jsonData, Class type) {
		CollectiveList<Object> list = new CollectiveList<>(jsonData.getInt("duplicates"));
		for (JsonValue entry = jsonData.get("uniques").child; entry != null; entry = entry.next) {
			int amount = entry.getInt("amount");
			if (amount > 0) {
				JsonValue item = entry.get("item");
				if (item == null || item.isNull()) {
					list.add(null, amount);
				} else {
					String className = entry.getString("class", null);
					try {
						Class<?> clazz = Class.forName(className);
						Object value = json.readValue(clazz, item);
						list.add(value, amount);
					} catch (ClassNotFoundException | SerializationException e) {
					}
				}
			}
		}
		return list;
	}
}
