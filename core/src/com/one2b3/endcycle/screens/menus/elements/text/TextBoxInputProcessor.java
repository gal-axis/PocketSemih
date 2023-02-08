package com.one2b3.endcycle.screens.menus.elements.text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.engine.input.InputListener;
import com.one2b3.endcycle.engine.input.InputManager;
import com.one2b3.endcycle.engine.input.KeyCode;
import com.one2b3.endcycle.engine.input.events.ButtonEvent;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.screens.menus.elements.MenuElementAction.MenuElementActionType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TextBoxInputProcessor implements InputListener {

	@Getter
	final MenuTextBox textBox;

	MenuTextBoxMobilePreview preview;
	long frame;

	public void select() {
		preview = new MenuTextBoxMobilePreview(textBox);
		textBox.screen.addObject(preview);
		frame = Gdx.graphics.getFrameId();
		Gdx.input.setOnscreenKeyboardVisible(true);
		textBox.screen.input.add(this);
		textBox.setSelected(true);
	}

	public void enter() {
		deselect();
	}

	public boolean addCharacter(char character) {
		return textBox.addCharacter(character);
	}

	public void deselect() {
		textBox.screen.input.remove(this);
		Gdx.input.setOnscreenKeyboardVisible(false);
		textBox.screen.removeObject(preview);
		if (textBox.isSelected()) {
			textBox.setSelected(false);
			textBox.triggerAction(MenuElementActionType.CHANGED_VALUE);
		}
	}

	@Override
	public int getInputPriority() {
		return 1;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.ESCAPE) {
			deselect();
		} else if (keycode == Keys.ENTER) {
			return false;
		} else if (keycode == Keys.TAB) {
			// Tab to next element
		} else if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
			if (keycode == Keys.V) {
				String copied = Gdx.app.getClipboard().getContents();
				if (copied != null) {
					for (char c : copied.toCharArray()) {
						if (!addCharacter(c)) {
							break;
						}
					}
				}
			} else if (keycode == Keys.C) {
			}
		} else if (keycode == Keys.LEFT) {
			textBox.moveCursor(-1);
		} else if (keycode == Keys.RIGHT) {
			textBox.moveCursor(1);
		}
		return true;
	}

	@Override
	public boolean triggerType(int keycode) {
		if (frame != Gdx.graphics.getFrameId()) {
			if (keycode == '\b') {
				textBox.removeCharacter();
			} else if (keycode == 127) {
				textBox.deleteCharacter();
			} else {
				addCharacter((char) keycode);
			}
		}
		return true;
	}

	@Override
	public boolean triggerButton(ButtonEvent event) {
		if (event.isPressed()) {
			if (event.isKey(KeyCode.MENU_CANCEL)) {
				deselect();
			} else if (event.controller == InputManager.KEYBOARD && event.buttonId == Keys.ENTER) {
				enter();
			}
		}
		return true;
	}

	@Override
	public boolean triggerTouch(TouchEvent event) {
		if (event.isLeftMouse() && (Cardinal.isPortable() ? event.isReleased() : event.isPressed())) {
			int x = event.positionX, y = event.positionY;
			if (preview.isHidden() ? !textBox.clickText(x, y) : !preview.touch(x, y)) {
				deselect();
				return Cardinal.isPortable();
			}
		}
		return true;
	}

}
