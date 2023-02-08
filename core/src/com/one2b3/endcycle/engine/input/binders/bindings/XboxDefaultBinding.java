package com.one2b3.endcycle.engine.input.binders.bindings;

import static com.one2b3.endcycle.engine.input.KeyCode.*;
import static com.one2b3.endcycle.engine.input.binders.constants.XboxConstants.*;

import java.util.List;

import com.badlogic.gdx.controllers.Controller;
import com.one2b3.endcycle.engine.input.binders.BindingMap;
import com.one2b3.endcycle.engine.input.binders.DefaultBinding;
import com.one2b3.endcycle.engine.input.binders.KeyBinding;

public class XboxDefaultBinding implements DefaultBinding {

	@Override
	public List<KeyBinding> getDefaults(Controller controller) {
		BindingMap map = map();
		map.with(MENU_UP, LEFT_AXIS_UP, POV_UP).with(MENU_DOWN, -LEFT_AXIS_UP, POV_DOWN).with(MENU_LEFT, LEFT_AXIS_LEFT, POV_LEFT)
				.with(MENU_RIGHT, -LEFT_AXIS_LEFT, POV_RIGHT);

		map.with(MENU_SELECT, A).with(MENU_CANCEL, B).with(MENU_SPECIAL, X).with(MENU_SPECIAL_2, Y).with(MENU_SWITCH_RIGHT, RB)
				.with(MENU_SWITCH_LEFT, LB);

		map.with(BATTLE_MOVE_LEFT, LEFT_AXIS_LEFT, POV_LEFT).with(BATTLE_MOVE_RIGHT, -LEFT_AXIS_LEFT, POV_RIGHT)
				.with(BATTLE_MOVE_DOWN, LEFT_AXIS_DOWN, POV_DOWN).with(BATTLE_MOVE_UP, LEFT_AXIS_UP, POV_UP);

		map.with(BATTLE_ATTACK_1, A).with(BATTLE_ATTACK_2, B).with(BATTLE_ATTACK_3, Y).with(BATTLE_ATTACK_4, X);
		map.with(BATTLE_ROLE_LEFT, LB).with(BATTLE_ROLE_RIGHT, RB);
		map.with(BATTLE_TURN, LT).with(BATTLE_ANALYZE, RT).with(BATTLE_PAUSE, START);

		map.with(SCREENSHOT, RIGHT_AXIS).with(GIF, BACK);
		return map.build();
	}

}
