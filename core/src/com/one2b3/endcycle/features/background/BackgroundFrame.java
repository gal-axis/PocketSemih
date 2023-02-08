package com.one2b3.endcycle.features.background;

import com.one2b3.endcycle.engine.graphics.DrawableId;
import com.one2b3.endcycle.engine.proguard.KeepClass;
import com.one2b3.endcycle.features.models.AlignHorizontal;
import com.one2b3.endcycle.features.models.AlignVertical;
import com.one2b3.endcycle.features.models.Description;
import com.one2b3.endcycle.features.models.primitives.DoubleRange;
import com.one2b3.endcycle.features.models.primitives.FloatRange;
import com.one2b3.endcycle.features.models.primitives.Percent;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@KeepClass
@FieldDefaults(level = AccessLevel.PUBLIC)
public final class BackgroundFrame {

	@Description("The drawable that this frame should draw")
	DrawableId drawable = new DrawableId();
	@Description("By how many seconds the animation should be offset")
	@DoubleRange(min = 0.0)
	double animationOffset;
	@Description("By how many percent the tint should modulate between its full color and black")
	@FloatRange(min = 0.0F, max = 1.0F)
	@Percent
	float tintModulate = 0.0F;
	@Description("How fast tint should modulate")
	float tintSpeed = 0.0F;
	@Description("Where the drawable is positioned")
	float x, y;
	@Description("How much percent this frame is affected by the camera's movements")
	@Percent
	float offsetXFactor = 0.5F, offsetYFactor = 0.5F;
	@Description("When set this frame is repeatedly drawn after every amount specified here")
	float xRepeat, yRepeat;
	@Description("When set, this frame slowly moves towards this direction")
	float xSpeed, ySpeed;
	@Description("How to align the drawable based on the frame's position")
	@AlignHorizontal
	int hAlign;
	@Description("How to align the drawable based on the frame's position")
	@AlignVertical
	int vAlign;
	@Description("How to align the drawable based on the screen")
	@AlignHorizontal
	int screenHAlign = -1;
	@Description("How to align the drawable based on the screen")
	@AlignVertical
	int screenVAlign = -1;
}
