package com.one2b3.endcycle.engine.files.serializers;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializer;
import com.badlogic.gdx.utils.JsonValue;
import com.one2b3.endcycle.engine.audio.sound.SoundInfo;

@SuppressWarnings("rawtypes")
public class SoundInfoSerializer implements Serializer<SoundInfo> {

	@Override
	public void write(Json json, SoundInfo object, Class knownType) {
		json.writeObjectStart(object.getClass(), knownType);
		json.writeFields(object);
		json.writeObjectEnd();
	}

	@Override
	public SoundInfo read(Json json, JsonValue jsonData, Class type) {
		if (jsonData.isString()) {
			return new SoundInfo(jsonData.asString());
		} else if (jsonData.isObject()) {
			SoundInfo soundInfo = new SoundInfo();
			json.readFields(soundInfo, jsonData);
			return soundInfo;
		} else {
			return null;
		}
	}
}
