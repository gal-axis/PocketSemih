package com.one2b3.endcycle.engine.files.serializers;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializer;
import com.badlogic.gdx.utils.JsonValue;
import com.one2b3.endcycle.engine.language.UnlocalizedMessage;

@SuppressWarnings("rawtypes")
public class UnlocalizedMessageSerializer implements Serializer<UnlocalizedMessage> {

	@Override
	public void write(Json json, UnlocalizedMessage object, Class knownType) {
		json.writeValue(object.text == null || object.text.length() == 0 ? "" : '|' + object.text);
	}

	@Override
	public UnlocalizedMessage read(Json json, JsonValue jsonData, Class type) {
		UnlocalizedMessage message = new UnlocalizedMessage();
		json.readFields(message, jsonData);
		return message;
	}
}
