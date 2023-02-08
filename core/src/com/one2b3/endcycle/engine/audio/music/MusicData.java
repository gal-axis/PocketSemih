package com.one2b3.endcycle.engine.audio.music;

import com.one2b3.endcycle.engine.audio.Songs;
import com.one2b3.endcycle.engine.proguard.KeepClass;
import com.one2b3.endcycle.features.models.Description;
import com.one2b3.endcycle.features.models.FileType;
import com.one2b3.endcycle.features.models.connect.ConnectsTo;
import com.one2b3.endcycle.utils.ID;
import com.one2b3.endcycle.utils.objects.DataName;
import com.one2b3.endcycle.utils.objects.Named;
import com.one2b3.modding.diff.Moddable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@KeepClass
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
@Description("Information about songs such as their filepath, name, loop position or bpm.")
public final class MusicData extends Moddable implements Named, DataName {

	@Description("The name that the song will have in the toolbox and in-game")
	String name;
	@Description("The path of this song in the game files")
	@FileType("ogg")
	String filepath;

	@Description("The position of the loop in seconds. When set to -1 won't loop")
	float loop = -1;
	@Description("The bpm of the song")
	float bpm;

	@Description("What music should be played after the song is done being played. Only works when loop is set to -1")
	@ConnectsTo(MusicCatalog.class)
	ID next;

	@Description("Whether or not this song is selectable as a battle song in lobbies and multiplayer")
	boolean battle;

	@Description("What song should be played as the victory theme when this song is used in battle")
	@ConnectsTo(MusicCatalog.class)
	ID victory = Songs.Victory_A.getId();

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDataName() {
		return ID.combine(id, name == null || name.length() == 0 ? filepath : name);
	}
}
