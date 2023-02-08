package com.one2b3.endcycle.screens.menus.elements.table.painter;

import com.one2b3.endcycle.engine.drawing.Painter;

public interface MenuTablePainter<T> {

	default void startPainting(MenuTablePaintParams<T> params) {
	}

	default void paintBG(MenuTablePaintParams<T> params) {
	}

	void paint(MenuTablePaintParams<T> params);

	default void paintEmpty(MenuTablePaintParams<T> params) {
	}

	default void stopPainting(MenuTablePaintParams<T> params) {
	}

	default Painter getPainter(MenuTablePaintParams<?> params, int hAlign, int vAlign) {
		return getPainter(params).moveX(params.width * (hAlign + 1) * 0.5F).moveY(params.height * (vAlign + 1) * 0.5F).hAlign(hAlign)
				.vAlign(vAlign);
	}

	default Painter getPainter(MenuTablePaintParams<?> params) {
		return Painter.on(params.batch).x(params.x).y(params.y);
	}
}
