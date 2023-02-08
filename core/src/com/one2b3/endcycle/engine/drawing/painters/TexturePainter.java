package com.one2b3.endcycle.engine.drawing.painters;

import com.badlogic.gdx.graphics.Texture;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.ObjectPainter;
import com.one2b3.endcycle.engine.drawing.Painter;

public class TexturePainter implements ObjectPainter<Texture> {

	@Override
	public void paint(Texture object, Painter parameters) {
		CustomSpriteBatch batch = parameters.batch;
		float width = object.getWidth() * parameters.xScale;
		float height = object.getHeight() * parameters.yScale;
		float x = parameters.x - width * (parameters.hAlign + 1) * 0.5F;
		float y = parameters.y - height * (parameters.vAlign + 1) * 0.5F;
		batch.setColor(parameters.color);
		batch.draw(object, x, y, width, height);
	}

}
