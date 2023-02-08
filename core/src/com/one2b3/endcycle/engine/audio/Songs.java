package com.one2b3.endcycle.engine.audio;

import com.one2b3.endcycle.engine.audio.music.MusicCatalog;
import com.one2b3.endcycle.engine.audio.music.MusicData;
import com.one2b3.endcycle.utils.ID;

public enum Songs {
	Fanfare(new ID(0, 1)),

	Enemy_Engaged(new ID(0, 2)),

	VS_Noise(new ID(0, 3)),

	Victory_A(new ID(0, 4)),

	Game_Over(new ID(0, 5)),

	Tutorials_and_Tips(new ID(0, 9)),

	Voc_Menu(new ID(0, 10)),

	VS_Boss(new ID(0, 17)),

	Victory_B(new ID(0, 18)),

	Victory_C(new ID(0, 19)),

	Victory_Perfecti(new ID(0, 20)),

	Main_Theme(new ID(0, 25)),

	Voxel_Generation(new ID(0, 26)),

	Campaign(new ID(0, 0)),

	Campaign_Theme(new ID(0, 6)),

	Halloween_Main_Theme(new ID(0, 7)),

	Phantom_Realm(new ID(0, 8)),

	The_Core(new ID(0, 11)),

	Myste_Edun(new ID(0, 12)),

	Glacial_Pathways(new ID(0, 14)),

	Ice_Cavern(new ID(0, 15)),

	Astrat(new ID(0, 16)),

	Arahas_Desert(new ID(0, 21)),

	Midius(new ID(0, 22)),

	Networld(new ID(0, 23)),

	Clockwork_Factory(new ID(0, 24)),

	Outer_Galaxy(new ID(0, 27)),

	Ancient_Ruins(new ID(0, 28)),

	Wooden_Passage(new ID(0, 29)),

	Battle_Lunar(new ID(0, 30)),

	Regolithia(new ID(0, 31)),

	Victory_Lunar(new ID(0, 32)),

	Embark(new ID(0, 33)),

	VS_Final_Boss(new ID(0, 34)),

	Intro_Ending(new ID(0, 13));

	private final ID id;

	private Songs(ID id) {
		this.id = id;
	}

	public ID getId() {
		return this.id;
	}

	public MusicData get() {
		return MusicCatalog.get(this.id);
	}
}
