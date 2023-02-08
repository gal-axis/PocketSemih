package com.one2b3.endcycle.engine.input.events;

import com.one2b3.endcycle.engine.input.InputType;
import com.one2b3.endcycle.engine.input.KeyCode;
import com.one2b3.endcycle.engine.input.KeyCodeCategory;

public class KeyCodeEvent extends ButtonEvent {

	public KeyCode code;

	public KeyCodeEvent(KeyCode code, InputType type) {
		super(code.ordinal(), type);
		this.code = code;
	}

	@Override
	public boolean isKey(KeyCode code) {
		return this.code == code;
	}

	@Override
	public KeyCode getCode(KeyCodeCategory category) {
		return this.code.getCategory() == category ? this.code : null;
	}
}
