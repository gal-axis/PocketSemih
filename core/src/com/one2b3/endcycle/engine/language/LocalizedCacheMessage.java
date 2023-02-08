package com.one2b3.endcycle.engine.language;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class LocalizedCacheMessage implements LocalizedMessage {

	LocalizedMessage message;
	Object[] args;

	@Override
	public String getKey() {
		return message.getKey();
	}

	@Override
	public String getGroup() {
		return message.getGroup();
	}

	@Override
	public String format(Object... args) {
		return LocalizedMessage.super.format(args == null || args.length == 0 ? this.args : args);
	}

	@Override
	public LocalizedMessage localize(Object... args) {
		return this;
	}

}
