package com.one2b3.endcycle.engine.files.serializers;

import java.util.List;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializer;
import com.badlogic.gdx.utils.JsonValue;
import com.one2b3.endcycle.engine.language.BundleInfo;
import com.one2b3.endcycle.engine.language.FreeLocalizedMessage;
import com.one2b3.endcycle.engine.language.Localizer;

@SuppressWarnings("rawtypes")
public class FreeLocalizedMessageSerializer implements Serializer<FreeLocalizedMessage> {

	@Override
	public void write(Json json, FreeLocalizedMessage object, Class knownType) {
		if (object.group == null || object.group.length() == 0) {
			json.writeValue('|' + object.key);
		} else {
			json.writeValue(object.group + "|" + object.key);
		}
	}

	@Override
	public FreeLocalizedMessage read(Json json, JsonValue jsonData, Class type) {
		FreeLocalizedMessage message = new FreeLocalizedMessage();
		json.readFields(message, jsonData);
		String messageClass = jsonData.getString("messageClass", null);
		if (messageClass != null) {
			try {
				message.group = message.findGroup(Class.forName(messageClass));
			} catch (ClassNotFoundException e) {
				message.group = "UNKNOWN";
			}
		}
		if (message.group != null && message.key != null && message.group.length() > 0 && message.key.length() > 0) {
			if (Localizer.getBundle(message.group) == null) {
				System.err.println("Unknown group: " + message.group);
				List<BundleInfo> bundles = Localizer.getBundles();
				for (int i = 0; i < bundles.size(); i++) {
					String group = bundles.get(i).group;
					if (Localizer.getBundle(group).getProperties().containsKey(message.key)) {
						message.group = group;
						System.err.println("Found group: " + message.group);
						return message;
					}
				}
			}
			if (!Localizer.exists(message)) {
				System.err.println("Unknown string ID: " + message.group + '/' + message.key);
			}
		}
		return message;
	}
}
