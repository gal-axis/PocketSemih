package com.one2b3.endcycle.engine.input.binders.bindings;

import static com.one2b3.endcycle.engine.input.KeyCode.*;
import static com.one2b3.endcycle.engine.input.binders.constants.VJoyConstants2.*;

import java.util.List;

import com.badlogic.gdx.controllers.Controller;
import com.one2b3.endcycle.engine.input.binders.BindingMap;
import com.one2b3.endcycle.engine.input.binders.DefaultBinding;
import com.one2b3.endcycle.engine.input.binders.KeyBinding;

public class VJoyDefaultBinding implements DefaultBinding {

	@Override
	public List<KeyBinding> getDefaults(Controller controller) {
		BindingMap map = map();
		map.with(MENU_UP, LEFT_AXIS_UP).with(MENU_DOWN, -LEFT_AXIS_UP).with(MENU_LEFT, LEFT_AXIS_LEFT).with(MENU_RIGHT, -LEFT_AXIS_LEFT);

		map.with(MENU_SELECT, POV_RIGHT).with(MENU_CANCEL, POV_DOWN).with(MENU_SPECIAL, POV_LEFT).with(MENU_SPECIAL_2, POV_UP)
				.with(MENU_SWITCH_RIGHT, R1).with(MENU_SWITCH_LEFT, L1);

		map.with(BATTLE_MOVE_LEFT, LEFT_AXIS_LEFT).with(BATTLE_MOVE_RIGHT, -LEFT_AXIS_LEFT).with(BATTLE_MOVE_DOWN, LEFT_AXIS_DOWN)
				.with(BATTLE_MOVE_UP, LEFT_AXIS_UP);

		map.with(BATTLE_ATTACK_1, POV_DOWN).with(BATTLE_ATTACK_2, POV_RIGHT).with(BATTLE_ATTACK_3, POV_UP).with(BATTLE_ATTACK_4, POV_LEFT);
		map.with(BATTLE_ROLE_LEFT, L1).with(BATTLE_ROLE_RIGHT, R1);
		map.with(BATTLE_TURN, L2, R2).with(BATTLE_PAUSE, START);

		return map.build();
	}

}
