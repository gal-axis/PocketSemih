package com.one2b3.endcycle.features.revo;

import java.lang.reflect.Array;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.StringBuilder;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.engine.files.Data;
import com.one2b3.endcycle.engine.input.InputManager;
import com.one2b3.endcycle.utils.objects.DataName;
import com.one2b3.endcycle.utils.objects.Named;
import com.one2b3.revo.Revo;

public final class GameData {

	private GameData() {
	}

	public static long getSystemTime() {
		return System.currentTimeMillis();
	}

	public static float getTime() {
		return Cardinal.getTime();
	}

	public static double getTimePrecise() {
		return Cardinal.TIME_ACTIVE;
	}

	public static boolean isPortable() {
		return Cardinal.isPortable();
	}

	public static Object load(String path) {
		if (path.startsWith("./") || path.contains("..")) {
			Gdx.app.error("Data", "Cannot load object from path: " + path);
			return null;
		}
		FileHandle handle = Data.getHandle("mods/" + path);
		return handle.exists() ? Data.load(handle, null) : null;
	}

	public static boolean save(String path, Object object) {
		if (path.startsWith("./") || path.contains("..")) {
			Gdx.app.error("Data", "Cannot save object to path: " + path);
			return false;
		}
		FileHandle handle = Data.getHandle("mods/" + path);
		return Data.save(handle, object);
	}

	public static InputManager getInput() {
		return Cardinal.getInput();
	}

	public static String toString(Object object) {
		if (object == null) {
			return "null";
		} else if (object.getClass().isArray()) {
			return toArrayString(object);
		} else if (object.getClass() == com.badlogic.gdx.utils.Array.class) {
			return toArrayString((com.badlogic.gdx.utils.Array<?>) object);
		} else if (object.getClass() == String.class) {
			return object.toString();
		} else if (object.getClass() == Class.class) {
			return ((Class<?>) object).getSimpleName();
		}
		Named named = Revo.cast(object, Named.class);
		if (object instanceof Named) {
			String dataName = named.getName();
			if (dataName != null && dataName.length() > 0) {
				return dataName;
			}
		}
		DataName name = Revo.cast(object, DataName.class);
		if (name != null) {
			String dataName = name.getDataName();
			if (dataName != null && dataName.length() > 0) {
				return dataName;
			}
		}
		String str = object.toString();
		String hashCode = Integer.toHexString(object.hashCode());
		if (isDefault(str, object.getClass().getName(), hashCode)) {
			String className;
			className = object.getClass().getSimpleName();
			return className + "#" + hashCode;
		} else {
			return str;
		}
	}

	private static boolean isDefault(String string, String name, String hashCode) {
		return string.length() == name.length() + hashCode.length() + 1 && string.startsWith(name)
				&& string.charAt(name.length()) == '@' && string.endsWith(hashCode);
	}

	private static String toArrayString(com.badlogic.gdx.utils.Array<?> array) {
		if (array.size == 0) {
			return "[]";
		}
		StringBuilder buffer = new StringBuilder(32);
		buffer.append('[');
		buffer.append(toString(array.items[0]));
		for (int i = 1; i < array.size; i++) {
			buffer.append(", ");
			buffer.append(toString(array.items[i]));
		}
		buffer.append(']');
		return buffer.toString();
	}

	private static String toArrayString(Object object) {
		int length = Array.getLength(object);
		if (length == 0) {
			return "[]";
		}
		StringBuilder builder = new StringBuilder();
		builder.append('[');
		builder.append(toString(Array.get(object, 0)));
		for (int i = 1; i < length; i++) {
			builder.append(", ");
			builder.append(toString(Array.get(object, i)));
		}
		builder.append(']');
		return builder.toString();
	}

	public static String getDataName(DataName name) {
		return name == null ? null : name.getDataName();
	}
}
