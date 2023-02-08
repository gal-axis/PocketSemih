package com.one2b3.endcycle.engine.files;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializer;
import com.one2b3.endcycle.engine.audio.sound.SoundInfo;
import com.one2b3.endcycle.engine.collections.CollectiveList;
import com.one2b3.endcycle.engine.files.serializers.*;
import com.one2b3.endcycle.engine.graphics.DrawableImage;
import com.one2b3.endcycle.engine.language.FreeLocalizedMessage;
import com.one2b3.endcycle.engine.language.LocalizedMessage;
import com.one2b3.endcycle.engine.language.UnlocalizedMessage;
import com.one2b3.endcycle.utils.ID;
import com.one2b3.modding.diff.Moddable;

import lombok.AllArgsConstructor;

public class Serializers {

	static final List<Entry<?>> serializers;

	static {
		serializers = new ArrayList<>();
		serializers.add(new Entry<>(CollectiveList.class, new CollectiveListSerializer()));
		serializers.add(new Entry<>(SoundInfo.class, new SoundInfoSerializer()));
		serializers.add(new Entry<>(LocalizedMessage.class, new LocalizedMessageSerializer()));
		serializers.add(new Entry<>(FreeLocalizedMessage.class, new FreeLocalizedMessageSerializer()));
		serializers.add(new Entry<>(UnlocalizedMessage.class, new UnlocalizedMessageSerializer()));
		serializers.add(new Entry<>(Moddable.class, new ModdableSerializer()));
		serializers.add(new Entry<>(DrawableImage.class, new DrawableImageSerializer()));
		serializers.add(new Entry<>(ID[].class, new IDArraySerializer()));
	}

	public static void setSerializers(Json json) {
		for (Entry<?> entry : serializers) {
			setSerializer(json, entry);
		}
	}

	public static <T> void setSerializer(Json json, Entry<T> serializers) {
		json.setSerializer(serializers.clazz, serializers.serializer);
	}

	@AllArgsConstructor
	static class Entry<T> {
		Class<T> clazz;
		Serializer<T> serializer;
	}
}
