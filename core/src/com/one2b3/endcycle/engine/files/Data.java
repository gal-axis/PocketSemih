package com.one2b3.endcycle.engine.files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.badlogic.gdx.utils.ObjectMap;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.engine.EngineProperties;
import com.one2b3.endcycle.engine.EngineProperties.Platform;
import com.one2b3.utils.I18NBundle;

public class Data {

	private static String getRoot() {
		return Cardinal.getPlatform().getDataRoot();
	}

	public static Json getJson() {
		Json json = new Json();
		json.setOutputType(OutputType.json);
		json.setIgnoreUnknownFields(true);
		Serializers.setSerializers(json);
		return json;
	}

	public static String toJson(Object object) {
		Json json = getJson();
		json.setUsePrototypes(false);
		return json.prettyPrint(object);
	}

	public static void parseInto(Object object, String text) {
		try {
			Json json = getJson();
			json.readFields(object, new JsonReader().parse(text));
		} catch (Throwable throwable) {
			error("Exception while parsing json into object: " + object + "\n" + text, throwable);
		}
	}

	public static void parseInto(Object object, FileHandle file) {
		try {
			Json json = getJson();
			json.readFields(object, new JsonReader().parse(file));
		} catch (Throwable throwable) {
			error("Exception while parsing json into object: " + object + "\n" + file, throwable);
		}
	}

	public static boolean save(FileHandle fileHandle, ObjectMap<String, String> properties) {
		try (Writer writer = open(fileHandle, false, I18NBundle.DEFAULT_ENCODING)) {
			PropertiesUtil.store(properties, writer, false);
			log("Saved properties " + fileHandle.file().getAbsolutePath());
			return true;
		} catch (Throwable throwable) {
			error("Error while saving properties: " + fileHandle, throwable);
			return false;
		}
	}

	public static boolean save(FileHandle path, Object object) {
		return save(path, object, true);
	}

	public static boolean save(FileHandle path, Object object, boolean usePrototypes) {
		String value;
		try {
			Json json = getJson();
			json.setUsePrototypes(usePrototypes);
			value = json.prettyPrint(object, Integer.MAX_VALUE);
		} catch (Throwable t) {
			error("Exception writing json!", t);
			return false;
		}
		return write(path, true, value);
	}

	public static boolean save(FileHandle path, String object) {
		return write(path, true, object);
	}

	public static <T> T load(FileHandle path, Class<T> type) {
		if (path.exists()) {
			try {
				return getJson().fromJson(type, path);
			} catch (Throwable throwable) {
				error("Exception while reading file " + path + " with type " + type, throwable);
				return null;
			}
		} else {
			return getNewInstance(type);
		}
	}

	public static <T> T load(FileHandle path, Class<T> type, Class<?> elementType) {
		if (path.exists()) {
			try {
				return getJson().fromJson(type, elementType, path);
			} catch (Throwable throwable) {
				error("Exception while reading file " + path + " with type " + type, throwable);
				return null;
			}
		} else {
			return getNewInstance(type);
		}
	}

	public static boolean load(FileHandle path, Object object) {
		if (path.exists()) {
			try {
				getJson().readFields(object, new JsonReader().parse(path));
				return true;
			} catch (Throwable throwable) {
				error("Exception while reading file " + path + " with object " + object, throwable);
			}
		}
		return false;
	}

	public static boolean write(FileHandle fileHandle, boolean rewrite, String save) {
		try (Writer writer = open(fileHandle, !rewrite, null)) {
			writer.write(save);
			log("Saved file " + fileHandle.file().getAbsolutePath());
			return true;
		} catch (Throwable throwable) {
			error("Error while saving to path: " + fileHandle, throwable);
			return false;
		}
	}

	private static void error(String message, Throwable throwable) {
		if (Gdx.app == null) {
			System.err.println(message);
			throwable.printStackTrace();
		} else {
			Gdx.app.error("Data", message, throwable);
		}
	}

	private static void log(String message) {
		if (Gdx.app == null) {
			System.out.println(message);
		} else {
			Gdx.app.debug("Data", message);
		}
	}

	public static Writer open(FileHandle fileHandle, boolean append, String charset) throws IOException {
		if (fileHandle.type() == FileType.Internal || fileHandle.type() == FileType.Classpath) {
			File file = fileHandle.file();
			file.getParentFile().mkdirs();
			OutputStream writer = new FileOutputStream(file, append);
			return charset == null ? new OutputStreamWriter(writer) : new OutputStreamWriter(writer, charset);
		} else {
			fileHandle.parent().mkdirs();
			return fileHandle.writer(append, charset);
		}
	}

	private static <T> T getNewInstance(Class<T> type) {
		try {
			return type.newInstance();
		} catch (Exception e) {
			throw new GdxRuntimeException("Error while instantiating type: " + type, e);
		}
	}

	public static FileHandle getHandle(String path) {
		return isMobile() ? Gdx.files.local("data/" + path) : new FileHandle(getHome() + path);
	}

	public static FileHandle getDocumentHandle(String path) {
		return isMobile() ? Gdx.files.local(path) : Gdx.files.absolute(getRoot() + path);
	}

	public static boolean isMobile() {
		return Gdx.app != null && //
				(Gdx.app.getType() == Application.ApplicationType.Android
						|| Gdx.app.getType() == Application.ApplicationType.iOS);
	}

	public static String getHome() {
		return EngineProperties.PLATFORM == Platform.DEV ? System.getProperty("user.home") + "/.12b3/endcycle/"
				: System.getProperty("user.dir") + "/data/";
	}

	public static String[] readLines(FileHandle handle) {
		return (handle.exists() ? handle.readString().replaceAll("\r", "").split("\n") : null);
	}
}
