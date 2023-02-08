package com.one2b3.endcycle.engine.language;

import com.one2b3.endcycle.engine.proguard.KeepClass;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@KeepClass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public final class FreeLocalizedMessage implements LocalizedMessage {

	@Getter
	public String group;
	@Getter
	public String key;

	public FreeLocalizedMessage(Class<? extends LocalizedMessage> messageClass) {
		setMessageClass(messageClass);
	}

	public FreeLocalizedMessage(Class<? extends LocalizedMessage> messageClass, String key) {
		setMessageClass(messageClass);
		this.key = key;
	}

	public void setMessageClass(Class<? extends LocalizedMessage> messageClass) {
		this.group = findGroup(messageClass);
	}

	public String findGroup(Class<?> messageClass) {
		BundleInfo bundle = Localizer.getBundle(messageClass);
		return (bundle == null ? null : bundle.group);
	}

	@Override
	public String format(Object... args) {
		return key == null || key.length() == 0 ? "" : LocalizedMessage.super.format(args);
	}

	@Override
	public String toString() {
		return group + '/' + key + '|' + format();
	}
}
