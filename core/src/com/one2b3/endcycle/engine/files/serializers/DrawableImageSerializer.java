package com.one2b3.endcycle.engine.files.serializers;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializer;
import com.badlogic.gdx.utils.JsonValue;
import com.one2b3.endcycle.engine.graphics.DrawableImage;
import com.one2b3.endcycle.engine.graphics.data.DrawableLoader;
import com.one2b3.endcycle.utils.ID;

@SuppressWarnings("rawtypes")
public class DrawableImageSerializer implements Serializer<DrawableImage> {

	@Override
	public void write(Json json, DrawableImage object, Class knownType) {
		json.writeValue(object == null ? null : object.id);
	}

	@Override
	public DrawableImage read(Json json, JsonValue jsonData, Class type) {
		ID id = json.readValue(ID.class, jsonData);
		return id == null ? null : DrawableLoader.get().getImage(id);
	}
}
