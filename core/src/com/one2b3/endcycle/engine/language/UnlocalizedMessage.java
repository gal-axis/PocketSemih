package com.one2b3.endcycle.engine.language;

import com.one2b3.endcycle.engine.proguard.KeepClass;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@KeepClass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public final class UnlocalizedMessage implements LocalizedMessage {

	public String text = "";

	@Override
	public String getGroup() {
		return null;
	}

	@Override
	public String getKey() {
		return null;
	}

	@Override
	public String format(Object... args) {
		return text;
	}

}
