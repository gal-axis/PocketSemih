package com.one2b3.endcycle.engine.graphics.data.info;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.one2b3.endcycle.engine.proguard.KeepClass;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@KeepClass
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class TextureInfo {

	boolean pack = true;
	boolean packFonts = false;
	String atlas;
	TextureWrap wrap = TextureWrap.ClampToEdge;
	TextureFilter filter = TextureFilter.Nearest;
}
