package com.one2b3.endcycle.core.painting.loading;

import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.FontCache;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.language.LocalizedMessage;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.endcycle.engine.screens.ScreenObject;
import com.one2b3.endcycle.engine.ui.messages.GameScreenMessage;
import com.one2b3.endcycle.features.theme.ActiveTheme;
import com.one2b3.endcycle.utils.bounded.BoundedFloat;

public class QuickMessage implements ScreenObject {

	final FontCache message;
	final BoundedFloat stayTime;

	final BoundedFloat slideIn = new BoundedFloat(0.0F, 1.0F, 10.0F);

	float comparisonKey;

	public QuickMessage(LocalizedMessage message) {
		this(message.format());
	}

	public QuickMessage(String text) {
		this(text, 4.0F);
	}

	public QuickMessage(String text, float time) {
		message = GameFonts.Text.getCache(text);
		stayTime = new BoundedFloat(time);
	}

	@Override
	public void init(GameScreen screen) {
		comparisonKey = -2 + GameScreenMessage.nextComparisonKey();
	}

	@Override
	public void update(float delta) {
		slideIn.move(!stayTime.atMax(), delta);
		if (slideIn.atMax()) {
			stayTime.increase(delta);
		}
	}

	@Override
	public byte getLayer() {
		return Layers.LAYER_MESSAGES;
	}

	@Override
	public float getComparisonKey() {
		return comparisonKey;
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		float x = 5.0F - 100 * (1.0F - slideIn.getVal());
		float y = Cardinal.getHeight() - 5.0F;

		batch.setColor(ActiveTheme.disabledColor.r, ActiveTheme.disabledColor.g, ActiveTheme.disabledColor.b,
				ActiveTheme.disabledColor.a * slideIn.getVal());
		ActiveTheme.smallMessage.draw(batch, x - 5.0F, y - message.getHeight() - 5.0F, message.getWidth() + 10.0F,
				message.getHeight() + 10.0F);
		Painter params = Painter.on(batch).x(x).y(y).hAlign(-1).vAlign(1).alpha(slideIn.getVal());
		params.paint(message);
	}

	@Override
	public boolean remove() {
		return stayTime.atMax() && slideIn.atMin();
	}
}
