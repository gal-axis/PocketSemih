package com.one2b3.endcycle.engine.objects.visuals;

import com.one2b3.endcycle.engine.drawing.Painter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class StringDisplayAnimation implements CharacterAnim {

	public static final StringDisplayAnimation SCALE_UP = new StringDisplayAnimation(CharacterAnim.SCALE_UP_ANIM, 6.0F, 0.2F), //
			JUMP = new StringDisplayAnimation(CharacterAnim.JUMP_ANIM, 6.0F, 0.0F), //
			BOUNCE = new StringDisplayAnimation(CharacterAnim.BOUNCE_ANIM, 3.0F, 0.2F), //
			FALL = new StringDisplayAnimation(CharacterAnim.FALL, 3.0F, 0.4F) //
	;

	public CharacterAnim anim;
	public float speed;
	public float percentage;

	@Override
	public void draw(StringDisplayCharacter prop, Painter painter) {
		anim.draw(prop, painter);
	}

	@Override
	public void finish(StringDisplayCharacter prop) {
		anim.finish(prop);
	}
}
