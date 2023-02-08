package com.one2b3.endcycle.engine.input.binders.bindings.images.makers;

import static com.one2b3.endcycle.engine.input.InputManager.AXIS_ADDER;
import static com.one2b3.endcycle.engine.input.binders.bindings.images.ButtonImages.*;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerMapping;
import com.one2b3.endcycle.engine.input.binders.constants.XInputConstants;

public class XInputImageMaker extends XboxImageMaker {

	@Override
	public void init(Controller controller) {
		ControllerMapping mapping = controller.getMapping();
		if (mapping == null) {
			super.init(controller);
			return;
		}
		int left = mapping.axisLeftX + AXIS_ADDER, right = -left;
		int up = mapping.axisLeftY + AXIS_ADDER, down = -up;

		int left2 = mapping.axisRightX + AXIS_ADDER, right2 = -left;
		int up2 = mapping.axisRightY + AXIS_ADDER, down2 = -up;

		add(XBOX_A, mapping.buttonA);
		add(XBOX_B, mapping.buttonB);
		add(XBOX_X, mapping.buttonX);
		add(XBOX_Y, mapping.buttonY);
		add(XBOX_LB, mapping.buttonL1);
		add(XBOX_RB, mapping.buttonR1);
		add(XBOX_LT, mapping.buttonL2);
		add(XBOX_RT, mapping.buttonR2);
		add(XBOX_LT, -424);
		add(XBOX_RT, -425);
		add(XBOX_BACK, mapping.buttonBack);
		add(XBOX_START, mapping.buttonStart);
		add(XBOX_POV_RIGHT, XInputConstants.POV_RIGHT);
		add(XBOX_POV_UP, XInputConstants.POV_UP);
		add(XBOX_POV_DOWN, XInputConstants.POV_DOWN);
		add(XBOX_POV_LEFT, XInputConstants.POV_LEFT);
		add(XBOX_LEFT_AXIS, mapping.buttonLeftStick);
		add(XBOX_LEFT_AXIS_LEFT, left);
		add(XBOX_LEFT_AXIS_RIGHT, right);
		add(XBOX_LEFT_AXIS_UP, up);
		add(XBOX_LEFT_AXIS_DOWN, down);
		add(XBOX_RIGHT_AXIS, mapping.buttonRightStick);
		add(XBOX_RIGHT_AXIS_LEFT, left2);
		add(XBOX_RIGHT_AXIS_RIGHT, right2);
		add(XBOX_RIGHT_AXIS_UP, up2);
		add(XBOX_RIGHT_AXIS_DOWN, down2);
	}

}
