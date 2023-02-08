package com.one2b3.endcycle.core.painting.painters;

import com.one2b3.endcycle.core.painting.CustomSpriteBatch;

import lombok.AllArgsConstructor;

public class AndroidPainter extends ScaledPainter {

	final PaintSize portrait;
	final PaintSize landscape;

	public AndroidPainter(CustomSpriteBatch batch, int landscapeWidth, int landscapeHeight, int landscapeMaxWidth, int portraitWidth,
			int portraitHeight, int portraitMaxHeight) {
		super(batch, landscapeWidth, landscapeHeight, landscapeMaxWidth, landscapeHeight);
		landscape = new PaintSize(landscapeWidth, landscapeHeight, landscapeMaxWidth, landscapeHeight);
		portrait = new PaintSize(portraitWidth, portraitHeight, portraitWidth, portraitMaxHeight);
	}

	@Override
	public void changeOrientation(boolean landscape) {
		if (landscape) {
			this.landscape.apply();
		} else {
			this.portrait.apply();
		}
	}

	@Override
	public boolean supportsPortrait() {
		return true;
	}

	@AllArgsConstructor
	class PaintSize {
		final int prefWidth, prefHeight, maxWidth, maxHeight;

		public void apply() {
			setPreferredSize(prefWidth, prefHeight, maxWidth, maxHeight);
		}
	}
}
