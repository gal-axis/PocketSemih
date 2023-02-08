package com.one2b3.endcycle.engine.input;

import static com.one2b3.endcycle.engine.input.KeyCodeCategory.BATTLE;
import static com.one2b3.endcycle.engine.input.KeyCodeCategory.MENU;
import static com.one2b3.endcycle.engine.input.KeyCodeCategory.MISC;

import java.util.List;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.one2b3.endcycle.engine.input.binders.KeyBinder;
import com.one2b3.endcycle.engine.input.controllers.MockController;
import com.one2b3.endcycle.engine.language.LocalizedMessage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum KeyCode {

	// ===== Menu =====
	MENU_UP(MENU, null), //
	MENU_DOWN(MENU, null), //
	MENU_LEFT(MENU, null), //
	MENU_RIGHT(MENU, null), //

	MENU_SELECT(MENU, null), //
	MENU_CANCEL(MENU, null), //
	MENU_SPECIAL(MENU, null), //
	MENU_SPECIAL_2(MENU, null), //
	MENU_CHAT(MENU, null), //

	MENU_SWITCH_RIGHT(MENU, null), //
	MENU_SWITCH_LEFT(MENU, null), //

	// ===== Battle =====
	BATTLE_MOVE_UP(BATTLE, null), //
	BATTLE_MOVE_DOWN(BATTLE, null), //
	BATTLE_MOVE_LEFT(BATTLE, null), //
	BATTLE_MOVE_RIGHT(BATTLE, null), //

	BATTLE_ATTACK_1(BATTLE, null), //
	BATTLE_ATTACK_2(BATTLE, null), //
	BATTLE_ATTACK_3(BATTLE, null), //
	BATTLE_ATTACK_4(BATTLE, null),

	BATTLE_ROLE_LEFT(BATTLE, null), //
	BATTLE_ROLE_RIGHT(BATTLE, null), //

	BATTLE_TURN(BATTLE, null), //
	BATTLE_PAUSE(BATTLE, null), //
	BATTLE_ANALYZE(BATTLE, null), //

	// ===== Misc =====
	SCREENSHOT(MISC, null), //
	GIF(MISC, null), //

	RESTART_1(MISC, null), //
	RESTART_2(MISC, null), //
	RESTART_3(MISC, null), //
	RESTART_4(MISC, null);

	final KeyCodeCategory category;
	final LocalizedMessage keyString;

	public boolean isPressed(Controller controller) {
		if (controller == null) {
			return isPressed();
		} else if (controller instanceof MockController) {
			return controller.getButton(ordinal());
		}
		KeyBinder map = InputManager.KEYBOARD.binder;
		if (map != null) {
			List<Integer> keys = map.getKeys(this);
			if (keys != null) {
				for (int i = 0; i < keys.size(); i++) {
					Integer button = keys.get(i);
					if (InputManager.isPressed(controller, button) || controller.getButton(button)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean isPressed() {
		for (Controller controller : Controllers.getControllers()) {
			if (controller != null && isPressed(controller)) {
				return true;
			}
		}
		return isPressed(InputManager.KEYBOARD) || isPressed(InputManager.TOUCH_KEYBOARD);
	}

}
