package com.one2b3.endcycle.engine.input.binders.bindings.images;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ButtonImageType {

	KEYBOARD("buttons/keyboard.png"), //
	XBOX("buttons/xone.png"), //
	PLAYSTATION("buttons/ps4.png"), //
	STEAM("buttons/steam.png"), //
	SWITCH("buttons/switch.png"), //
	;

	String path;
}