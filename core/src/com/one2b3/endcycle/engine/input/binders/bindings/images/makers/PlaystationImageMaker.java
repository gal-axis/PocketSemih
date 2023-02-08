package com.one2b3.endcycle.engine.input.binders.bindings.images.makers;

import static com.one2b3.endcycle.engine.input.binders.bindings.images.ButtonImages.*;
import static com.one2b3.endcycle.engine.input.binders.constants.Ps4Constants.*;

import com.badlogic.gdx.controllers.Controller;
import com.one2b3.endcycle.engine.input.binders.bindings.images.ButtonImageMaker;

public class PlaystationImageMaker extends ButtonImageMaker {

	@Override
	public void init(Controller controller) {
		add(PS3_CROSS, CROSS);
		add(PS3_CIRCLE, CIRCLE);
		add(PS3_SQUARE, SQUARE);
		add(PS3_TRIANGLE, TRIANGLE);
		add(PS3_L1, L1);
		add(PS3_R1, R1);
		add(PS3_SELECT, SHARE);
		add(PS3_START, OPTIONS);
		add(PS3_DPAD_RIGHT, POV_RIGHT);
		add(PS3_DPAD_UP, POV_UP);
		add(PS3_DPAD_DOWN, POV_DOWN);
		add(PS3_DPAD_LEFT, POV_LEFT);
		add(PS3_LEFT_AXIS, LEFT_AXIS);
		add(PS3_LEFT_AXIS_LEFT, LEFT_AXIS_LEFT);
		add(PS3_LEFT_AXIS_RIGHT, LEFT_AXIS_RIGHT);
		add(PS3_LEFT_AXIS_UP, LEFT_AXIS_UP);
		add(PS3_LEFT_AXIS_DOWN, LEFT_AXIS_DOWN);
		add(PS3_RIGHT_AXIS, RIGHT_AXIS);
		add(PS3_RIGHT_AXIS_LEFT, RIGHT_AXIS_LEFT);
		add(PS3_RIGHT_AXIS_RIGHT, RIGHT_AXIS_RIGHT);
		add(PS3_RIGHT_AXIS_UP, RIGHT_AXIS_UP);
		add(PS3_RIGHT_AXIS_DOWN, RIGHT_AXIS_DOWN);
		add(PS3_L2, L2);
		add(PS3_R2, R2);
	}

}
