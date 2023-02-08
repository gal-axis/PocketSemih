package com.one2b3.endcycle.screens.menus.elements.table;

import com.one2b3.endcycle.screens.menus.elements.table.painter.MenuTablePaintParams;
import com.one2b3.endcycle.screens.menus.elements.table.painter.MenuTablePainter;

public class DefaultMenuTablePainter<T> implements MenuTablePainter<T> {

	@Override
	public void paint(MenuTablePaintParams<T> params) {
		getPainter(params, -1, 0).moveX(3).paint(params.object);
	}

}
