package com.one2b3.endcycle.engine.ui.messages;

import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.language.Unlocalize;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.screens.menus.elements.MenuElement;

public class MenuConfirmMessage extends ContentMessage {

	public MenuElement element;

	public MenuConfirmMessage(final MenuElement element, final MessageAction action) {
		this.element = element;
		setChoices(1, Unlocalize.get("Yes"), Unlocalize.get("No"));
		setActions(action, null);
		selection = 1;
	}

	@Override
	public void init(GameScreen screen) {
		super.init(screen);
		element.init(screen);
	}

	@Override
	public void dispose() {
		super.dispose();
		element.dispose();
	}

	@Override
	public void update(float delta) {
		element.update(delta);
		width = element.getWidth();
		height = element.getHeight();
		super.update(delta);
	}

	@Override
	public void drawContent(CustomSpriteBatch batch, float x, float y, float width, float height) {
		element.draw(batch, x - button.getLeft(), y - button.getBottom());
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		element.resize(width, height);
	}
}
