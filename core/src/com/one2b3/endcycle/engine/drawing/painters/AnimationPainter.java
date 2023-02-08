package com.one2b3.endcycle.engine.drawing.painters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.engine.drawing.ObjectPainter;
import com.one2b3.endcycle.engine.drawing.Painter;

public class AnimationPainter implements ObjectPainter<Animation<?>> {

	final TextureRegionPainter textureRegionPainter = new TextureRegionPainter();

	@Override
	public void paint(Animation<?> object, Painter parameters) {
		float animState = (float) get(parameters.animState, Cardinal.TIME_ACTIVE);
		TextureRegion region = (TextureRegion) object.getKeyFrame(animState);
		parameters.paint(region, textureRegionPainter);
	}

}
