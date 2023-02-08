package com.one2b3.endcycle.engine.objects.visuals;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Interpolation.BounceOut;
import com.one2b3.endcycle.engine.drawing.Painter;

public interface CharacterAnim {

	void draw(StringDisplayCharacter prop, Painter painter);

	default void finish(StringDisplayCharacter prop) {
	}

	CharacterAnim SCALE_UP_ANIM = (prop, painter) -> {
		painter.xScale *= prop.getAlpha();
		if (prop.movingBack()) {
			painter.x += painter.xScale * (1.0F - prop.getAlpha());
		}
		painter.alpha(prop.getAlpha());
	};

	CharacterAnim JUMP_ANIM = (prop, painter) -> {
		float anim = prop.getAnim() * 8.0F;
		painter.y += anim < 4.0F ? anim : 8.0F - anim;
		painter.alpha(prop.getAlpha());
	};

	CharacterAnim BOUNCE_ANIM = (prop, painter) -> {
		painter.x += Interpolation.pow2Out.apply(-10.0F, 0.0F, prop.getAnim());
		float yCutoff = 0.3F;
		if (prop.getAnim() < yCutoff) {
			painter.y += Interpolation.pow3Out.apply(0.0F, 10.0F, prop.getAnim() * (1.0F / yCutoff));
		} else {
			painter.y += 10.0F
					- new BounceOut(2).apply(0.0F, 10.0F, (prop.getAnim() - yCutoff) * (1.0F / (1.0F - yCutoff)));
		}
		painter.alpha(prop.getAlpha());
	};

	CharacterAnim FALL = new CharacterAnim() {
		@Override
		public void draw(StringDisplayCharacter prop, Painter painter) {
			float scale = prop.movingBack() ? 1.0F : 2.0F - prop.getAlpha();
			painter.xScale *= scale;
			painter.yScale *= scale;
			painter.alpha(prop.getAlpha());
		}

		@Override
		public void finish(StringDisplayCharacter prop) {
		}
	};
}