package com.one2b3.modding.diff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.one2b3.endcycle.engine.assets.Assets;
import com.one2b3.endcycle.engine.files.Data;
import com.one2b3.endcycle.utils.ID;

public class ModdedList<T extends Moddable> {

	final String path, patchPath;
	final Class<T> type;

	final boolean visual;
	List<T> originalObjects;

	public final List<T> objects = new ArrayList<>();

	final List<T> savedObjects = new ArrayList<>();
	final Map<ID, Integer> idPositions = new HashMap<>();

	public ModdedList(String path, String patchPath, Class<T> type) {
		this(path, patchPath, type, false);
	}

	public ModdedList(String path, String patchPath, Class<T> type, boolean visual) {
		this.path = path;
		this.patchPath = patchPath;
		this.type = type;
		this.visual = visual;
	}

	public Class<T> getListType() {
		return type;
	}

	public void load() {
		debug("Loading " + path + "...");
		loadOriginal();
		objects.clear();
		objects.addAll(loadPatched());
		if (visual || !Assets.visualOnly) {
			refreshObjectMap(objects);
		}
		savedObjects.clear();
		savedObjects.addAll(objects);
	}

	private void debug(String str) {
		if (Gdx.app == null) {
			System.out.print("[Loader] ");
			System.out.println(str);
		} else {
			Gdx.app.debug("Loader", str);
		}
	}

	public void add(T object) {
		if (object != null) {
			Integer index = idPositions.get(object.id);
			if (index == null) {
				idPositions.put(object.id, objects.size());
				objects.add(object);
			} else {
				objects.set(index, object);
			}
		}
	}

	public T open(T object) {
		if (object != null) {
			Integer index = idPositions.get(object.id);
			if (index != null) {
				if (index < savedObjects.size()) {
					Json json = getJson();
					T copy = json.fromJson(type, json.toJson(object, type));
					if (copy != null) {
						savedObjects.set(index, copy);
						if (patchPath == null) {
							object = json.fromJson(type, json.toJson(object, type));
						}
					}
				}
			}
		}
		return object;
	}

	public boolean isDirty(T object) {
		if (object == null) {
			return false;
		}
		Integer index = idPositions.get(object.id);
		if (index == null || index >= savedObjects.size()) {
			return true;
		}
		Json json = getJson();
		T saved = savedObjects.get(index);
		return saved == null || !json.toJson(object, type).equals(json.toJson(saved, type));
	}

	public void save(T object) {
		save(object, true);
	}

	public void save(T object, boolean copy) {
		if (object != null) {
			Integer index = idPositions.get(object.id);
			if (index == null) {
				index = objects.size();
				idPositions.put(object.id, index);
				objects.add(object);
			}
			while (savedObjects.size() <= index) {
				savedObjects.add(null);
			}
			if (copy) {
				Json json = getJson();
				object = json.fromJson(type, json.toJson(object, type));
			}
			savedObjects.set(index, object);
		}
	}

	public static Json getJson() {
		Json json = Data.getJson();
		json.setSerializer(Moddable.class, null);
		json.setUsePrototypes(true);
		return json;
	}

	public void remove(T object) {
		Integer index = idPositions.remove(object.id);
		if (index != null) {
			int i = index;
			objects.remove(i);
			if (i < savedObjects.size()) {
				savedObjects.remove(i);
				if (i > 0 && i == savedObjects.size()) {
					while (savedObjects.size() > 0 && savedObjects.get(--i) == null) {
						savedObjects.remove(i);
					}
				}
			}
			// Reconstruct map as we need to move indexes!
			refreshObjectMap(objects);
		}
	}

	@SuppressWarnings("unchecked")
	private List<T> loadPatched() {
		if (patchPath == null) {
			return originalObjects;
		}
		FileHandle file = Assets.getHandle(path);
		Json json = getJson();
		JsonReader reader = getJsonReader();
		ArrayList<T> list = (file.exists() ? json.readValue(ArrayList.class, type, reader.parse(file))
				: new ArrayList<>());
		return fixList(list);
	}

	public JsonReader getJsonReader() {
		return new JsonReader();
	}

	private void loadOriginal() {
		originalObjects = loadList(Assets.getHandle(path));
	}

	private void refreshObjectMap(List<T> objects) {
		idPositions.clear();
		for (int i = 0; i < objects.size(); i++) {
			idPositions.put(objects.get(i).id, i);
		}
	}

	@SuppressWarnings("unchecked")
	private List<T> loadList(FileHandle handle) {
		if (!handle.exists()) {
			return new ArrayList<>();
		}
		ArrayList<T> list = getJson().fromJson(ArrayList.class, type, handle);
		return fixList(list);
	}

	public List<T> fixList(ArrayList<T> list) {
		if (list == null) {
			return new ArrayList<>();
		}
		boolean idLess = false;
		for (int i = 0; i < list.size(); i++) {
			T object = list.get(i);
			if (object == null) {
				list.remove(i);
				i--;
			} else if (object.id == null || object.id.equals(0, 0)) {
				if (idLess) {
					object.id = new ID(1);
					ID id = object.id;
					while (id != null) {
						id = null;
						for (int j = 0; j < i; j++) {
							if (object.id.equals(list.get(j).id)) {
								id = object.id;
								id.increment();
								break;
							}
						}
					}
				} else {
					idLess = true;
				}
			}
		}
		return list;
	}

	public void save() {
		FileHandle handle = Assets.getHandle(path);
		save(handle, getJson().prettyPrint(savedObjects, Integer.MAX_VALUE));
	}

	private boolean save(FileHandle handle, String object) {
		return Data.save(handle, object);
	}

	public boolean move(int position, int newPosition) {
		if (position == newPosition || newPosition < 0 || newPosition > objects.size()) {
			return false;
		}
		T object = objects.remove(position);
		objects.add(position < newPosition ? newPosition - 1 : newPosition, object);

		object = (position >= savedObjects.size() ? null : savedObjects.remove(position));
		savedObjects.add(position < newPosition ? newPosition - 1 : newPosition, object);

		refreshObjectMap(objects);
		return true;
	}

	public void revert(T object) {
		T original = ID.findObject(isVanilla(object) ? savedObjects : originalObjects, object.id);
		if (original != null) {
			Json json = getJson();
			json.setUsePrototypes(false);
			String jsonObject = json.toJson(original);
			json.readFields(object, getJsonReader().parse(jsonObject));
		}
	}

	public boolean isVanilla(T selectedObject) {
		return ID.findObject(originalObjects, selectedObject.id) != null;
	}

	public T get(ID id) {
		Integer position = idPositions.get(id);
		return position == null ? null : objects.get(position);
	}

	public ID nextId() {
		ID id = new ID(0);
		while (get(id) != null) {
			id.increment();
		}
		return id;
	}

	public T create() {
		try {
			T instance = type.newInstance();
			instance.id = nextId();
			return instance;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int indexOf(ID id) {
		Integer position = idPositions.get(id);
		return position == null ? -1 : position;
	}
}
