package com.one2b3.endcycle.engine.graphics.data.info;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.files.FileHandle;
import com.one2b3.endcycle.engine.assets.Assets;
import com.one2b3.endcycle.engine.files.Data;
import com.one2b3.endcycle.engine.graphics.data.DrawableLoader;

public class TextureInfoLoader {

	static final TextureInfo defaultInfo = new TextureInfo();
	static Map<String, TextureInfo> infos = new HashMap<>();

	public static void clear() {
		infos.clear();
	}

	public static TextureInfo getInfo(String texture) {
		return getInfo(texture, true);
	}

	private static TextureInfo getInfo(String texture, boolean file) {
		TextureInfo info = infos.get(texture);
		if (info == null) {
			FileHandle handle = Assets.findHandle(DrawableLoader.IMAGE_PATH, texture + ".info");
			if (handle.exists()) {
				info = Data.load(handle, TextureInfo.class);
			}
			if (info == null) {
				if (file) {
					info = getInfo(texture.substring(0, texture.lastIndexOf("/") + 1) + "folder", false);
				} else {
					info = defaultInfo;
				}
			}
			infos.put(texture, info);
		}
		return info;
	}
}
