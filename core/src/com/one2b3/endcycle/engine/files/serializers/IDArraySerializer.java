package com.one2b3.endcycle.engine.files.serializers;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializer;
import com.badlogic.gdx.utils.JsonValue;
import com.one2b3.endcycle.utils.ID;

@SuppressWarnings("rawtypes")
public class IDArraySerializer implements Serializer<ID[]> {

	@Override
	public void write(Json json, ID[] object, Class knownType) {
		json.writeArrayStart();
		for (int i = 0; i < object.length; i++) {
			if (object[i] == null) {
				json.writeValue(null);
			} else {
				json.writeValue(object[i].group);
				json.writeValue(object[i].id);
			}
		}
		json.writeArrayEnd();
	}

	@Override
	public ID[] read(Json json, JsonValue jsonData, Class type) {
		int size = 0;
		ID[] ids = new ID[jsonData.size];
		for (JsonValue entry = jsonData.child; entry != null; entry = entry.next, size++) {
			if (entry.isNull()) {
				continue;
			}
			if (entry.isObject()) {
				ids[size] = json.readValue(ID.class, entry);
			} else {
				ids[size] = new ID(entry.asLong(), entry.next.asLong());
				entry = entry.next;
			}
		}
		ID[] realIds = new ID[size];
		System.arraycopy(ids, 0, realIds, 0, size);
		return realIds;
	}

}
