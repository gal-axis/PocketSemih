package com.one2b3.endcycle.engine.drawing.painters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.ObjectPainter;
import com.one2b3.endcycle.engine.drawing.Painter;

public class TextureRegionPainter implements ObjectPainter<TextureRegion> {

	@Override
	public void paint(TextureRegion object, Painter parameters) {
		CustomSpriteBatch batch = parameters.batch;
		float width = object.getRegionWidth() * parameters.xScale;
		float height = object.getRegionHeight() * parameters.yScale;
		float x = parameters.x - width * (parameters.hAlign + 1) * 0.5F;
		float y = parameters.y - height * (parameters.vAlign + 1) * 0.5F;
		batch.setColor(parameters.color);
		batch.draw(object, x, y, width, height);
	}

}
