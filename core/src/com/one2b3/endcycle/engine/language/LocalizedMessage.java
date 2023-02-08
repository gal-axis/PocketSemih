package com.one2b3.endcycle.engine.language;

import com.one2b3.endcycle.engine.drawing.Paintable;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.proguard.KeepClass;

@KeepClass
public interface LocalizedMessage extends Paintable {

	String getKey();

	String getGroup();

	default String format(Object... args) {
		return Localizer.format(this, args);
	}

	default LocalizedMessage localize(Object... args) {
		return new LocalizedCacheMessage(this, args);
	}

	static String createKeyFromValue(String value) {
		return value.replace('_', '.');
	}

	static String createValueFromKey(String value) {
		return value.replace('.', '_');
	}

	@Override
	default void paint(Painter painter) {
		String value = format(painter.arguments());
		painter.clearArguments().paint(value);
	}
}
