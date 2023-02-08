package com.one2b3.endcycle.features.background;

import com.one2b3.endcycle.utils.ID;

public enum Backgrounds {
	Glacial_Pathways(new ID(0, 0)),

	Stars(new ID(0, 1)),

	Wooden_Passage(new ID(0, 2)),

	Arahas_Desert(new ID(0, 3)),

	X_Mas(new ID(0, 4)),

	Clockwork_Factory(new ID(0, 5)),

	LGBT(new ID(0, 641)),

	Halloween(new ID(0, 8000)),

	VS_Title_Screen(new ID(0, 6)),

	Ancient_Ruins(new ID(0, 7)),

	Galaxy(new ID(0, 8)),

	Energy(new ID(0, 9)),

	Menu(new ID(0, 10)),

	Tri_Wing(new ID(0, 11)),

	Astrat_City(new ID(0, 12)),

	Menu_Crimson(new ID(0, 13)),

	Menu_Azure(new ID(0, 14)),

	Deep_Ice_Cavern(new ID(0, 15)),

	Energy_Onslaught(new ID(0, 16)),

	Midius_Remains(new ID(0, 17)),

	Panels(new ID(0, 18)),

	Modern_Menu(new ID(0, 19)),

	Menu_Lunar(new ID(0, 20)),

	Myste_Edun(new ID(0, 21)),

	Networld(new ID(0, 22)),

	Phantom_Realm(new ID(0, 23)),

	Corrupt_Tower(new ID(0, 24)),

	Del_Apartment(new ID(0, 25)),

	Everod(new ID(0, 26)),

	Midius_Normal(new ID(0, 27)),

	Everod_Digital(new ID(0, 28));

	private final ID id;

	private Backgrounds(ID id) {
		this.id = id;
	}

	public ID getId() {
		return this.id;
	}

	public BackgroundData get() {
		return BackgroundCatalog.get(this.id);
	}
}
