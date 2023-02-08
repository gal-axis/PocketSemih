package com.one2b3.endcycle.engine.screens.faders;

import com.one2b3.endcycle.engine.screens.faders.types.*;
import com.one2b3.utils.java.Supplier;

public enum FadeType {
	FADE_TO_BLACK(FadeToBlack::new), FADE_TO_WHITE(FadeToWhite::new),

	BATTLE_FADE_1(BattleFade1::new), BATTLE_FADE_2(BattleFade2::new),

	OPEN_BOX(OpenBox::new), CLOSE_BOX(CloseBox::new),

	FADE_TO_NEXT(FadeToNext::new);

	Supplier<FadeProcessor> supplier;

	private FadeType(Supplier<FadeProcessor> supplier) {
		this.supplier = supplier;
	}

	public FadeProcessor getProcessor() {
		return supplier.get();
	}
}
