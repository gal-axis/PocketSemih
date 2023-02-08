package com.one2b3.endcycle.screens.menus.elements.table.painter;

import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.screens.menus.elements.table.MenuTable;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
@Accessors(chain = true)
public class MenuTablePaintParams<T> {

	MenuTable<T> table;

	CustomSpriteBatch batch;

	int index;

	T object;

	float x, y, width, height;

	public MenuTablePaintParams(MenuTable<T> table) {
		this.table = table;
	}

	public final void reset() {
		x = -1;
		y = -1;
		index = -1;
		object = null;
	}

}
