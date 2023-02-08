package com.one2b3.endcycle.engine.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.one2b3.endcycle.engine.files.Data;
import com.one2b3.endcycle.engine.proguard.KeepClass;

@KeepClass
public class Assets implements FileHandleResolver {

	private static FileHandleResolver RESOLVER = new Assets();
	public static boolean visualOnly = false;

	private Assets() {
	}

	@Override
	public FileHandle resolve(String fileName) {
		if (Gdx.files == null) {
			return new FileHandle("assets/" + fileName);
		}
		return getHandle(fileName);
	}

	public static FileHandle getHandle(String fileName) {
		String root = Data.isMobile() ? "" : "assets/";
		return Gdx.files.internal(root + fileName);
	}

	public static FileHandleResolver getFileHandleResolver() {
		return RESOLVER;
	}

	public static FileHandle findHandle(String fileName) {
		return RESOLVER.resolve(fileName);
	}

	public static FileHandle findHandle(String parent, String fileName) {
		int mod = (fileName == null ? -1 : fileName.indexOf(':'));
		return RESOLVER.resolve(mod == -1 ? parent + "/" + fileName
				: fileName.substring(0, mod + 1) + parent + "/" + fileName.substring(mod + 1));
	}
}
