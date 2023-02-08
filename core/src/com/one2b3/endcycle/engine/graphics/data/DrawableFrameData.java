package com.one2b3.endcycle.engine.graphics.data;

import com.one2b3.endcycle.engine.proguard.KeepClass;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@KeepClass
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class DrawableFrameData {

	String texturePath;
	int x;
	int y;
	int width;
	int height;

	float xOffset;
	float yOffset;

	public DrawableFrameData copy() {
		return new DrawableFrameData(texturePath, x, y, width, height, xOffset, yOffset);
	}

}
