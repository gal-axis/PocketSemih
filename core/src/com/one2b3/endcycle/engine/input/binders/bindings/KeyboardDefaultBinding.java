package com.one2b3.endcycle.engine.input.binders.bindings;

import static com.one2b3.endcycle.engine.input.KeyCode.*;

import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.engine.input.binders.BindingMap;
import com.one2b3.endcycle.engine.input.binders.DefaultBinding;
import com.one2b3.endcycle.engine.input.binders.KeyBinding;

public class KeyboardDefaultBinding implements DefaultBinding {

	@Override
	public List<KeyBinding> getDefaults(Controller controller) {
		BindingMap map = map();
		map.with(MENU_LEFT, Keys.LEFT, Keys.A).with(MENU_RIGHT, Keys.RIGHT, Keys.D).with(MENU_UP, Keys.UP, Keys.W).with(MENU_DOWN,
				Keys.DOWN, Keys.S);
		if (Cardinal.isPhone()) {
			map.with(MENU_CANCEL, Keys.ESCAPE, Keys.BACK);
			map.with(MENU_SELECT, Keys.SPACE, Keys.CENTER);
		} else {
			map.with(MENU_CANCEL, Keys.ESCAPE);
			map.with(MENU_SELECT, Keys.SPACE);
		}
		map.with(MENU_SPECIAL, Keys.TAB).with(MENU_SPECIAL_2, Keys.ENTER);
		map.with(MENU_SWITCH_RIGHT, Keys.PAGE_DOWN, Keys.E).with(MENU_SWITCH_LEFT, Keys.PAGE_UP, Keys.Q).with(MENU_CHAT, Keys.C);

		map.with(BATTLE_MOVE_DOWN, Keys.S).with(BATTLE_MOVE_LEFT, Keys.A).with(BATTLE_MOVE_RIGHT, Keys.D).with(BATTLE_MOVE_UP, Keys.W);
		map.with(BATTLE_ROLE_LEFT, Keys.CONTROL_LEFT).with(BATTLE_ROLE_RIGHT, Keys.SPACE);
		map.with(BATTLE_ATTACK_1, Keys.DOWN).with(BATTLE_ATTACK_2, Keys.RIGHT).with(BATTLE_ATTACK_3, Keys.UP).with(BATTLE_ATTACK_4,
				Keys.LEFT);
		map.with(BATTLE_TURN, Keys.SHIFT_LEFT).with(BATTLE_PAUSE, Keys.ESCAPE).with(BATTLE_ANALYZE, Keys.E);

		map.with(SCREENSHOT, Keys.F12).with(GIF, Keys.F11).with(RESTART_1, Keys.ESCAPE).with(RESTART_2, Keys.ENTER).with(RESTART_3, Keys.Q)
				.with(RESTART_4, Keys.E);
		return map.build();
	}

}
