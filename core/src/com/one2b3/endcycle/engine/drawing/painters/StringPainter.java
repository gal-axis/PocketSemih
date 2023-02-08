package com.one2b3.endcycle.engine.drawing.painters;

import com.one2b3.endcycle.engine.drawing.ObjectPainter;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.language.Localizer;

public class StringPainter implements ObjectPainter<String> {

	@Override
	public void paint(String object, Painter painter) {
		Object[] args = painter.arguments();
		if (args != null && args.length > 0) {
			object = Localizer.format(object, args);
		}
		get(painter.font, GameFonts.Text).paint(object, painter);
	}
}
