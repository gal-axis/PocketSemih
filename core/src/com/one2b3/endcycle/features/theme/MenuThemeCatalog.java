package com.one2b3.endcycle.features.theme;

import java.util.ArrayList;
import java.util.List;

import com.one2b3.endcycle.engine.assets.GameLoader;
import com.one2b3.endcycle.features.models.connect.ModdableConnector;
import com.one2b3.endcycle.utils.ID;
import com.one2b3.modding.diff.ModdedList;

public final class MenuThemeCatalog implements ModdableConnector<MenuTheme> {

	public static final ModdedList<MenuTheme> themes = new ModdedList<>("data/themes.json", "data/themes_patch.json", MenuTheme.class,
			true);

	public static MenuTheme get(ID id) {
		return themes.get(id);
	}

	public static List<MenuTheme> getAll() {
		return themes.objects;
	}

	public static List<MenuTheme> getVisible() {
		List<MenuTheme> list = new ArrayList<>();
		for (MenuTheme data : themes.objects) {
			if (data.visible) {
				list.add(data);
			}
		}
		return list;
	}

	@Override
	public void load(GameLoader loader) {
		ModdableConnector.super.load(loader);
		ActiveTheme.reload();
	}

	@Override
	public ModdedList<MenuTheme> getList() {
		return themes;
	}

	@Override
	public String getLocalizeKey() {
		return "Theme";
	}

	@Override
	public String getToolboxGroup() {
		return "Vault/Menu Themes";
	}
}