package com.one2b3.endcycle.features.background;

import java.util.ArrayList;
import java.util.List;

import com.one2b3.endcycle.engine.assets.GameLoader;
import com.one2b3.endcycle.features.models.connect.ModdableConnector;
import com.one2b3.endcycle.utils.ID;
import com.one2b3.endcycle.utils.Randomizer;
import com.one2b3.modding.diff.ModdedList;

import lombok.Getter;

public final class BackgroundCatalog implements ModdableConnector<BackgroundData> {

	public static final ModdedList<BackgroundData> backgrounds = new ModdedList<>("data/backgrounds.json", "data/backgrounds_patch.json",
			BackgroundData.class);
	@Getter
	static final List<BackgroundData> visible = new ArrayList<>();

	@Override
	public void load(GameLoader loader) {
		ModdableConnector.super.load(loader);
		generateIndexes();
	}

	@Override
	public void save() {
		backgrounds.save();
		generateIndexes();
	}

	private static void generateIndexes() {
		visible.clear();
		for (BackgroundData data : backgrounds.objects) {
			if (data.visible) {
				visible.add(data);
			}
		}
	}

	public static boolean exists(ID id) {
		return get(id) != null;
	}

	public static BackgroundData get(ID id) {
		if (id == null) {
			return getRandom();
		}
		return backgrounds.get(id);
	}

	public static BackgroundData getRandom() {
		return Randomizer.get(visible);
	}

	public static List<BackgroundData> getAll() {
		return backgrounds.objects;
	}

	@Override
	public List<BackgroundData> getValues() {
		return BackgroundCatalog.getAll();
	}

	@Override
	public ModdedList<BackgroundData> getList() {
		return backgrounds;
	}

	@Override
	public String getLocalizeKey() {
		return "BG";
	}
}
