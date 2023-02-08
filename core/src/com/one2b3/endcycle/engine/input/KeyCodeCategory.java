package com.one2b3.endcycle.engine.input;

import com.one2b3.endcycle.engine.language.LocalizedMessage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum KeyCodeCategory {

	MENU(null), //
	BATTLE(null), //
	MISC(null);

	@Getter
	final LocalizedMessage categoryKey;

}
