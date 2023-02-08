package com.one2b3.endcycle.engine.drawing.painters;

import com.one2b3.endcycle.engine.drawing.ObjectPainter;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.graphics.DrawableImage;
import com.one2b3.endcycle.engine.graphics.Drawables;

public class DrawablesPainter implements ObjectPainter<Drawables> {

	@Override
	public void paint(Drawables object, Painter painter) {
		DrawableImage image = object.getImage();
		if (image != null) {
			image.paint(painter);
		}
	}

}
