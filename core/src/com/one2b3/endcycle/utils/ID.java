package com.one2b3.endcycle.utils;

import java.util.List;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.one2b3.endcycle.engine.proguard.KeepClass;
import com.one2b3.endcycle.utils.objects.IdEntity;
import com.one2b3.utils.java.Objects;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@KeepClass
@NoArgsConstructor
@AllArgsConstructor
public final class ID implements Comparable<ID>, Json.Serializable {

	static final ID temp = new ID();

	public static ID temp(long id) {
		temp.group = 0;
		temp.id = id;
		return temp;
	}

	public static ID temp(long group, long id) {
		temp.group = group;
		temp.id = id;
		return temp;
	}

	public static ID get(IdEntity entity) {
		return entity == null ? null : entity.getId();
	}

	public static ID copy(ID id) {
		return (id == null ? null : new ID(id.group, id.id));
	}

	public static ID fromString(String str) {
		return new ID().parse(str);
	}

	public static <E extends IdEntity> E findObject(List<E> list, ID id) {
		int index = findObjectIndex(list, id);
		return index == -1 ? null : list.get(index);
	}

	public static <E extends IdEntity> int findObjectIndex(List<E> list, ID id) {
		for (int i = 0; i < list.size(); i++) {
			if (ID.equals(id, list.get(i).getId())) {
				return i;
			}
		}
		return -1;
	}

	public static <E extends IdEntity> E findObject(Array<E> list, ID id) {
		int index = findObjectIndex(list, id);
		return index == -1 ? null : list.get(index);
	}

	public static <E extends IdEntity> int findObjectIndex(Array<E> list, ID id) {
		for (int i = 0; i < list.size; i++) {
			if (ID.equals(id, list.get(i).getId())) {
				return i;
			}
		}
		return -1;
	}

	public static boolean equals(ID id1, ID id2) {
		return Objects.equals(id1, id2);
	}

	public static int compare(ID id1, ID id2) {
		if (id1 == null && id2 == null) {
			return 0;
		} else if (id1 != null && id2 == null) {
			return -1;
		} else if (id2 != null && id1 == null) {
			return 1;
		} else {
			return id1.compareTo(id2);
		}
	}

	public long group;
	public long id;

	public ID(long id) {
		this.group = 0;
		this.id = id;
	}

	private ID parse(String str) {
		if (str == null || str.isEmpty()) {
			return null;
		}
		int index = str.indexOf('/');
		if (str.charAt(0) != '#' || index == str.length() || index < 2) {
			return null;
		}
		group = Long.parseLong(str.substring(1, index));
		id = Long.parseLong(str.substring(index + 1));
		return this;
	}

	public void increment() {
		id++;
	}

	public void set(ID id) {
		this.group = id.group;
		this.id = id.id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (group ^ (group >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		ID other = (ID) obj;
		return id == other.id && group == other.group;
	}

	public boolean equals(long group, long id) {
		return this.group == group && this.id == id;
	}

	@Override
	public String toString() {
		return "#" + group + "/" + id;
	}

	@Override
	public int compareTo(ID id) {
		int compare = Long.compare(this.group, id.group);
		if (compare != 0) {
			return compare;
		}
		return Long.compare(this.id, id.id);
	}

	@Override
	public void write(Json json) {
		json.writeFields(this);
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		if (jsonData.isString()) {
			parse(jsonData.asString());
		} else if (jsonData.isNumber()) {
			group = 0;
			id = jsonData.asInt();
		} else if (jsonData.isObject()) {
			json.readFields(this, jsonData);
		} else {
			group = jsonData.parent.getLong(jsonData.name + "_group", 0);
			id = jsonData.asLong();
		}
	}

	public static String combine(ID id, String name) {
		return (id == null ? "Null" : id.toString()) + ": " + name;
	}

	public static boolean isVanilla(ID id) {
		return id != null && id.group == 0L;
	}
}
