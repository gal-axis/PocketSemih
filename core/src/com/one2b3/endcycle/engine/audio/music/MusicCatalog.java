package com.one2b3.endcycle.engine.audio.music;

import java.util.List;

import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.features.models.connect.ModdableConnector;
import com.one2b3.endcycle.utils.ID;
import com.one2b3.modding.diff.ModdedList;

public final class MusicCatalog implements ModdableConnector<MusicData> {

	public static ModdedList<MusicData> music = new ModdedList<>("data/music.json", null, MusicData.class, true);

	public static List<MusicData> getAll() {
		return music.objects;
	}

	public static MusicData get(ID id) {
		return music.get(id);
	}

	@Override
	public List<MusicData> getValues() {
		return music.objects;
	}

	public static Array<MusicData> getBattle() {
		Array<MusicData> music = new Array<>();
		for (MusicData data : MusicCatalog.getAll()) {
			if (data.battle) {
				music.add(data);
			}
		}
		return music;
	}

	@Override
	public ModdedList<MusicData> getList() {
		return music;
	}

	@Override
	public String getToolboxGroup() {
		return "Music";
	}

}
