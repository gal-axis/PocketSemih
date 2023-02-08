package com.one2b3.endcycle.engine.drawing.painters;

import com.one2b3.endcycle.engine.drawing.ObjectPainter;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.features.revo.GameData;

public class DefaultObjectPainter implements ObjectPainter<Object> {

	@Override
	public void paint(Object object, Painter parameters) {
		parameters.paint(GameData.toString(object));
	}

}
