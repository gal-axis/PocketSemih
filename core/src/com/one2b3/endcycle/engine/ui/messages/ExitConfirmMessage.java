package com.one2b3.endcycle.engine.ui.messages;

import com.one2b3.endcycle.engine.language.LocalizedMessage;

public class ExitConfirmMessage extends ConfirmMessage {

	public ExitConfirmMessage(final LocalizedMessage text) {
		super(text, action);
	}

	static final MessageAction action = m -> {
		m.screen.toPreviousScreen();
		return true;
	};
}
