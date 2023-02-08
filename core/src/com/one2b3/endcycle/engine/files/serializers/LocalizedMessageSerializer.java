package com.one2b3.endcycle.engine.files.serializers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializer;
import com.badlogic.gdx.utils.JsonValue;
import com.one2b3.endcycle.engine.language.FreeLocalizedMessage;
import com.one2b3.endcycle.engine.language.LocalizedMessage;
import com.one2b3.endcycle.engine.language.UnlocalizedMessage;
import com.one2b3.revo.Revo;

@SuppressWarnings("rawtypes")
public class LocalizedMessageSerializer implements Serializer<LocalizedMessage> {

	@Override
	public void write(Json json, LocalizedMessage object, Class knownType) {
		// Can't really happen since its an interface
		throw new GdxRuntimeException("Unable to write localized message!");
	}

	@SuppressWarnings("unchecked")
	@Override
	public LocalizedMessage read(Json json, JsonValue jsonData, Class type) {
		if (jsonData.isNull()) {
			return null;
		} else if (jsonData.isString()) {
			String value = jsonData.asString();
			int localized = value.indexOf('|');
			if (localized == 0) {
				String unlocalized = value.substring(1);
				warn(unlocalized);
				return new UnlocalizedMessage(unlocalized);
			} else if (localized == -1) {
				warn(value);
				return new UnlocalizedMessage(value);
			} else {
				return new FreeLocalizedMessage(value.substring(0, localized), value.substring(localized + 1));
			}
		} else {
			return Revo.cast(json.readValue(type, jsonData), LocalizedMessage.class);
		}
	}

	public void warn(String value) {
		if (value.length() > 0) {
			Gdx.app.error("Localization", "Unlocalized string detected: " + value);
		}
	}
}
