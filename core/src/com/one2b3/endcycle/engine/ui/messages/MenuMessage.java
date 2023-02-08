package com.one2b3.endcycle.engine.ui.messages;

import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.input.InputListener;
import com.one2b3.endcycle.engine.input.KeyCode;
import com.one2b3.endcycle.engine.input.events.ButtonEvent;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.screens.menus.elements.MenuElement;
import com.one2b3.endcycle.screens.menus.elements.MenuElementController;
import com.one2b3.endcycle.screens.menus.elements.group.MenuElementGroup;

import lombok.Getter;
import lombok.Setter;

public class MenuMessage extends SlideInMessage implements InputListener {

	@Setter
	@Getter
	boolean closeable = true;
	float inputBlock;

	@Getter
	MenuElement menuElement;
	@Getter
	@Setter
	MenuElement defaultElement;
	@Getter
	MenuElementController controller;

	public MenuMessage() {
	}

	public MenuMessage(MenuElement menuElement) {
		setElement(menuElement);
	}

	public MenuMessage(MenuElement menuElement, MenuElement defaultElement) {
		setElement(menuElement);
		setDefaultElement(defaultElement);
	}

	public void setElement(MenuElement menuElement) {
		this.menuElement = menuElement;
		menuElement.setLayer((byte) (getLayer() + 1));
		if (menuElement instanceof MenuElementGroup) {
			setDefaultElement(((MenuElementGroup) menuElement).getDefaultElement());
		} else {
			setDefaultElement(menuElement);
		}
		if (screen != null) {
			menuElement.init(screen);
			menuElement.resize(Cardinal.getWidth(), Cardinal.getHeight());
		}
		if (showing) {
			menuElement.show(screen);
		}
		if (controller == null) {
			this.controller = new MenuElementController(menuElement);
		} else {
			this.controller.setMasterElement(menuElement);
			this.controller.buildNeighbors();
			this.controller.setSelected(getDefaultElement());
		}
	}

	@Override
	public void init(GameScreen screen) {
		super.init(screen);
		// Add listeners
		screen.input.add(this);

		menuElement.init(screen);
		controller.init(screen);

		inputBlock = 0.3F;
		menuElement.show(screen);
		controller.show(screen);
		controller.setAllowEmptySelection(false);
	}

	@Override
	public void show(GameScreen screen) {
		super.show(screen);
		menuElement.show(screen);
		controller.show(screen);
	}

	@Override
	public void hide(GameScreen screen) {
		super.hide(screen);
		menuElement.hide(screen);
		controller.hide(screen);
	}

	@Override
	public boolean isStaying() {
		return super.isStaying() && inputBlock <= 0.0F;
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		if (inputBlock > 0.0F) {
			inputBlock -= delta;
			if (inputBlock <= 0.0F) {
				controller.setSelected(getDefaultElement());
				controller.start();
			}
		}
		if (state != State.SLIDING_IN) {
			menuElement.update(delta);
		}
		controller.update(delta);
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		drawScreenTint(batch, 0.7F);
		float offset = calcOffset();
		drawMenu(batch, 0.0F, 0.0F + offset);
	}

	public float calcOffset() {
		return (1.0F - getPosition()) * Cardinal.getHeight();
	}

	public void drawScreenTint(CustomSpriteBatch batch, float alpha) {
		batch.drawScreenTint(getPosition() * alpha);
	}

	public void drawMenu(CustomSpriteBatch batch, float xOfs, float yOfs) {
		menuElement.draw(batch, xOfs, yOfs);
		controller.draw(batch, xOfs, yOfs);
	}

	@Override
	public void finish() {
		super.finish();
		controller.stop();
		menuElement.hide(screen);
	}

	@Override
	public void dispose() {
		super.dispose();
		screen.input.remove(this);
		menuElement.dispose();
		controller.stop();
		controller.dispose();
	}

	public boolean canClose() {
		return isStaying() && closeable;
	}

	@Override
	public boolean triggerButton(ButtonEvent event) {
		if (canClose()) {
			if (event.isPressed() && isBackKey(event)) {
				finish();
			}
		}
		return true;
	}

	public boolean isBackKey(ButtonEvent trigger) {
		return trigger.isKey(KeyCode.MENU_CANCEL);
	}

	@Override
	public boolean triggerTouch(TouchEvent event) {
		if (canClose()) {
			if (event.isReleased() && event.isLeftMouse()) {
				if (!event.isIn(menuElement.calcX(), menuElement.calcY(), menuElement.getWidth(), menuElement.getHeight())) {
					finish();
				}
			}
		}
		return true;
	}

	@Override
	public void resize(int width, int height) {
		menuElement.resize(width, height);
		controller.resize(width, height);
		controller.buildNeighbors();
	}

}
