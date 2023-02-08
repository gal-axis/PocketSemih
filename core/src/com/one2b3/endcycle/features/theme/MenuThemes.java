package com.one2b3.endcycle.features.theme;

import com.one2b3.endcycle.utils.ID;

public enum MenuThemes {
	Default(new ID(0, 0)),

	Azure(new ID(0, 1)),

	Tri_Wing(new ID(0, 2)),

	Crimson(new ID(0, 3)),

	Ultimate(new ID(0, 4)),

	Halloween(new ID(0, 5)),

	LunarLux(new ID(0, 7)),

	Legacy(new ID(0, 6));

	private final ID id;

	private MenuThemes(ID id) {
		this.id = id;
	}

	public ID getId() {
		return this.id;
	}

	public MenuTheme get() {
		return MenuThemeCatalog.get(this.id);
	}
}
