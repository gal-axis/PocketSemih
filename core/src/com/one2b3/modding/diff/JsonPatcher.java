package com.one2b3.modding.diff;

import java.util.List;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.ValueType;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.one2b3.endcycle.engine.files.Data;
import com.one2b3.endcycle.utils.ID;
import com.one2b3.utils.java.Objects;

public class JsonPatcher {

	public static final String ARRAY_SET_NULL = "PATCH_SET_NULL", ARRAY_ORIGINAL = "original_array_";
	private static final String JSON_PATCH_ID = "json_patch_id";
	final Json json;
	final JsonReader reader;

	public JsonPatcher() {
		json = Data.getJson();
		json.setSerializer(Moddable.class, null);
		json.setIgnoreUnknownFields(true);
		json.setUsePrototypes(false);
		json.setOutputType(OutputType.minimal);

		reader = new JsonReader();
	}

	public <T extends Moddable> void apply(Json json, List<T> objects, Class<T> type, JsonValue patches) {
		for (JsonValue entry = patches.child; entry != null; entry = entry.next) {
			Moddable original = find(objects, getId(entry));
			if (original == null) {
				T value = json.readValue(type, null, entry);
				objects.add(value);
			}
		}
	}

	private ID getId(JsonValue entry) {
		JsonValue patch_id = entry.get(JSON_PATCH_ID);
		if (patch_id == null) {
			return null;
		}
		long id = patch_id.getLong("id", 0), group = patch_id.getLong("group", 0);
		return new ID(group, id);
	}

	public JsonValue createPatch(Moddable original, Moddable changed) {
		return createPatch(toValue(original), toValue(changed));
	}

	public JsonValue createPatch(JsonValue original, JsonValue changed) {
		JsonValue value = new JsonValue(ValueType.object);
		for (JsonValue entry = changed.child; entry != null; entry = entry.next) {
			JsonValue originalEntry = original.get(entry.name);
			if (originalEntry == null || originalEntry.type() != entry.type()) {
				addCopy(value, entry);
			} else if (originalEntry.type() == ValueType.array) {
				JsonValue patch = createArrayPatch(originalEntry, entry);
				if (patch != null) {
					value.addChild(entry.name, patch);
					value.size++;
				}
			} else if (originalEntry.type() == ValueType.object) {
				JsonValue patch = createPatch(originalEntry, entry);
				if (!patch.isEmpty()) {
					value.addChild(entry.name, patch);
					value.size++;
				}
			} else if (!Objects.equals(originalEntry.asString(), entry.asString())) {
				addCopy(value, entry);
			}
		}
		return value;
	}

	private JsonValue createArrayPatch(JsonValue original, JsonValue changed) {
		// If the new array is empty then return the new array immediately!
		// Or, if the old array used to be empty then return the new array!
		if (changed.size == 0 || original.size == 0) {
			return changed.size == original.size ? null : changed;
		}
		JsonValue patch = new JsonValue(ValueType.array);
		JsonValue originalEntry = original.child;
		for (JsonValue changedEntry = changed.child; changedEntry != null && originalEntry != null;) {
			if (originalEntry.isNull()) {
				if (changedEntry.isNull()) {
					addNull(patch);
				} else {
					addCopy(patch, changedEntry);
				}
			} else if (changedEntry.isNull()) {
				addChild(patch, new JsonValue(ARRAY_SET_NULL));
			} else if (originalEntry.type() == changedEntry.type()) {
				if (originalEntry.type() == ValueType.object) {
					JsonValue patchValue = createPatch(originalEntry, changedEntry);
					if (patchValue.isEmpty()) {
						addNull(patch);
					} else {
						addChild(patch, patchValue);
					}
				} else if (originalEntry.type() == ValueType.array) {
					JsonValue value = createArrayPatch(originalEntry, changedEntry);
					if (value == null) {
						addNull(patch);
					} else {
						addChild(patch, value);
					}
				} else if (Objects.equals(originalEntry.asString(), changedEntry.asString())) {
					addNull(patch);
				} else {
					addCopy(patch, changedEntry);
				}
			} else {
				addCopy(patch, changedEntry);
			}
			// Go to next changed json value and extend patch array if needed
			changedEntry = changedEntry.next;
			if (changedEntry != null) {
				if (originalEntry.next == null) {
					patch.addChild(changedEntry);
					break;
				}
				originalEntry = originalEntry.next;
			}
		}
		// If no elements in the array changed and the sizes are the same, dont
		// write the array
		if (original.size == changed.size) {
			boolean hasObject = false;
			for (JsonValue patchEntry = patch.child; patchEntry != null; patchEntry = patchEntry.next) {
				if (!patchEntry.isNull()) {
					hasObject = true;
				}
			}
			if (!hasObject) {
				return null;
			}
		}
		patch.size = changed.size;
		return patch;
	}

	private void addNull(JsonValue copy) {
		copy.addChild(new JsonValue(ValueType.nullValue));
	}

	private void addCopy(JsonValue value, JsonValue child) {
		addChild(value, copy(child));
	}

	private static void addChild(JsonValue value, JsonValue child) {
		value.addChild(child);
		value.size++;
	}

	private static JsonValue copy(JsonValue entry) {
		JsonValue value = new JsonValue(ValueType.nullValue);
		set(value, entry);
		return value;
	}

	private static void set(JsonValue jsonValue, JsonValue newValue) {
		jsonValue.setType(newValue.type());
		jsonValue.name = newValue.name;
		jsonValue.child = newValue.child;
		jsonValue.size = newValue.size;
		switch (jsonValue.type()) {
		case booleanValue:
			jsonValue.set(newValue.asBoolean());
			break;
		case doubleValue:
			jsonValue.set(newValue.asDouble(), newValue.asString());
			break;
		case longValue:
			jsonValue.set(newValue.asLong(), newValue.asString());
			break;
		case stringValue:
			jsonValue.set(newValue.asString());
			break;
		default:
			break;
		}
	}

	public static JsonValue createChanged(List<? extends Moddable> original, List<? extends Moddable> changed,
			boolean patch) {
		JsonPatcher patcher = new JsonPatcher();
		JsonValue patches = new JsonValue(ValueType.array);
		for (int i = 0; i < changed.size(); i++) {
			Moddable changedObject = changed.get(i);
			if (changedObject == null) {
				continue;
			}
			Moddable originalObject = find(original, changedObject.getId());
			if (originalObject != null) {
				if (patch) {
					JsonValue value = patcher.createPatch(originalObject, changedObject);
					if (!value.isEmpty()) {
						value.addChild(JSON_PATCH_ID, patcher.toValue(changedObject.getId()));
						addChild(patches, value);
					}
				} else {
					String json = patcher.toJson(originalObject);
					String newJson = patcher.toJson(changedObject);
					if (!json.equals(newJson)) {
						patches.addChild(patcher.reader.parse(newJson));
						patches.size++;
					}
				}
			} else {
				patches.addChild(patcher.toValue(changedObject));
				patches.size++;
			}
		}
		return patches;
	}

	private static Moddable find(List<? extends Moddable> original, ID modId) {
		if (modId == null) {
			return null;
		}
		for (int i = 0; i < original.size(); i++) {
			Moddable object = original.get(i);
			if (modId.equals(object.getId())) {
				return original.get(i);
			}
		}
		return null;
	}

	public JsonValue toValue(Object object) {
		return reader.parse(toJson(object));
	}

	public String toJson(Object object) {
		return json.toJson(object, (Class<?>) null);
	}
}
