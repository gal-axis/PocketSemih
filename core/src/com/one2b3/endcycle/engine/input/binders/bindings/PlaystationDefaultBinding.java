package com.one2b3.endcycle.engine.input.binders.bindings;

import static com.one2b3.endcycle.engine.input.KeyCode.*;
import static com.one2b3.endcycle.engine.input.binders.constants.Ps4Constants.*;

import java.util.List;

import com.badlogic.gdx.controllers.Controller;
import com.one2b3.endcycle.engine.input.binders.BindingMap;
import com.one2b3.endcycle.engine.input.binders.DefaultBinding;
import com.one2b3.endcycle.engine.input.binders.KeyBinding;

public class PlaystationDefaultBinding implements DefaultBinding {

	@Override
	public List<KeyBinding> getDefaults(Controller controller) {
		BindingMap map = map();
		map.with(MENU_UP, LEFT_AXIS_UP, POV_UP).with(MENU_DOWN, -LEFT_AXIS_UP, POV_DOWN).with(MENU_LEFT, LEFT_AXIS_LEFT, POV_LEFT)
				.with(MENU_RIGHT, -LEFT_AXIS_LEFT, POV_RIGHT);

		map.with(MENU_SELECT, CROSS).with(MENU_CANCEL, CIRCLE).with(MENU_SPECIAL, SQUARE).with(MENU_SPECIAL_2, TRIANGLE)
				.with(MENU_SWITCH_RIGHT, R1).with(MENU_SWITCH_LEFT, L1);

		map.with(BATTLE_MOVE_LEFT, LEFT_AXIS_LEFT, POV_LEFT).with(BATTLE_MOVE_RIGHT, -LEFT_AXIS_LEFT, POV_RIGHT)
				.with(BATTLE_MOVE_DOWN, LEFT_AXIS_DOWN, POV_DOWN).with(BATTLE_MOVE_UP, LEFT_AXIS_UP, POV_UP);

		map.with(BATTLE_ATTACK_1, CROSS).with(BATTLE_ATTACK_2, CIRCLE).with(BATTLE_ATTACK_3, TRIANGLE).with(BATTLE_ATTACK_4, SQUARE);
		map.with(BATTLE_ROLE_LEFT, L1).with(BATTLE_ROLE_RIGHT, R1);
		map.with(BATTLE_TURN, L2).with(BATTLE_ANALYZE, R2).with(BATTLE_PAUSE, OPTIONS);

		map.with(SCREENSHOT, RIGHT_AXIS).with(GIF, SHARE);
		return map.build();
	}

}
