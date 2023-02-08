package com.one2b3.endcycle.engine.input.binders.bindings;

import static com.one2b3.endcycle.engine.input.InputManager.AXIS_ADDER;
import static com.one2b3.endcycle.engine.input.KeyCode.*;
import static com.one2b3.endcycle.engine.input.binders.constants.XInputConstants.POV_DOWN;
import static com.one2b3.endcycle.engine.input.binders.constants.XInputConstants.POV_LEFT;
import static com.one2b3.endcycle.engine.input.binders.constants.XInputConstants.POV_RIGHT;
import static com.one2b3.endcycle.engine.input.binders.constants.XInputConstants.POV_UP;

import java.util.List;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerMapping;
import com.one2b3.endcycle.engine.input.binders.BindingMap;
import com.one2b3.endcycle.engine.input.binders.DefaultBinding;
import com.one2b3.endcycle.engine.input.binders.KeyBinding;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdvancedDefaultBinding extends XboxDefaultBinding {

	final DefaultBinding defaultBinding;

	@Override
	public List<KeyBinding> getDefaults(Controller controller) {
		ControllerMapping mapping = controller.getMapping();
		if (mapping == null) {
			return defaultBinding.getDefaults(controller);
		}
		BindingMap map = map();
		int left = mapping.axisLeftX + AXIS_ADDER, right = -left;
		int up = mapping.axisLeftY + AXIS_ADDER, down = -up;
		int l2 = -(AXIS_ADDER + 4);
		int r2 = -(AXIS_ADDER + 5);

		map.with(MENU_UP, up, POV_UP).with(MENU_DOWN, down, POV_DOWN) //
				.with(MENU_LEFT, left, POV_LEFT).with(MENU_RIGHT, right, POV_RIGHT);

		map.with(MENU_SELECT, mapping.buttonA).with(MENU_CANCEL, mapping.buttonB).with(MENU_SPECIAL, mapping.buttonX)
				.with(MENU_SPECIAL_2, mapping.buttonY) //
				.with(MENU_SWITCH_RIGHT, mapping.buttonR1).with(MENU_SWITCH_LEFT, mapping.buttonL1);

		map.with(BATTLE_MOVE_LEFT, left, POV_LEFT).with(BATTLE_MOVE_RIGHT, right, POV_RIGHT) //
				.with(BATTLE_MOVE_DOWN, down, POV_DOWN).with(BATTLE_MOVE_UP, up, POV_UP);

		map.with(BATTLE_ATTACK_1, mapping.buttonA).with(BATTLE_ATTACK_2, mapping.buttonB) //
				.with(BATTLE_ATTACK_3, mapping.buttonY).with(BATTLE_ATTACK_4, mapping.buttonX);

		map.with(BATTLE_ROLE_LEFT, mapping.buttonL1).with(BATTLE_ROLE_RIGHT, mapping.buttonR1);
		map.with(BATTLE_TURN, mapping.buttonL2, l2).with(BATTLE_ANALYZE, mapping.buttonR2, r2).with(BATTLE_PAUSE, mapping.buttonStart);

		map.with(SCREENSHOT, mapping.buttonRightStick).with(GIF, mapping.buttonBack);
		return map.build();
	}

}
