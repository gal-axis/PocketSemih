package com.one2b3.endcycle.engine.input.binders.bindings.images.makers;

import static com.one2b3.endcycle.engine.input.binders.bindings.images.ButtonImages.*;
import static com.one2b3.endcycle.engine.input.binders.constants.XboxConstants.*;

import com.badlogic.gdx.controllers.Controller;
import com.one2b3.endcycle.engine.input.binders.bindings.images.ButtonImageMaker;

public class XboxImageMaker extends ButtonImageMaker {

	@Override
	public void init(Controller controller) {
		add(XBOX_A, A);
		add(XBOX_B, B);
		add(XBOX_X, X);
		add(XBOX_Y, Y);
		add(XBOX_LB, LB);
		add(XBOX_RB, RB);
		add(XBOX_BACK, BACK);
		add(XBOX_START, START);
		add(XBOX_POV_RIGHT, POV_RIGHT);
		add(XBOX_POV_UP, POV_UP);
		add(XBOX_POV_DOWN, POV_DOWN);
		add(XBOX_POV_LEFT, POV_LEFT);
		add(XBOX_LEFT_AXIS, LEFT_AXIS);
		add(XBOX_LEFT_AXIS_LEFT, LEFT_AXIS_LEFT);
		add(XBOX_LEFT_AXIS_RIGHT, LEFT_AXIS_RIGHT);
		add(XBOX_LEFT_AXIS_UP, LEFT_AXIS_UP);
		add(XBOX_LEFT_AXIS_DOWN, LEFT_AXIS_DOWN);
		add(XBOX_RIGHT_AXIS, RIGHT_AXIS);
		add(XBOX_RIGHT_AXIS_LEFT, RIGHT_AXIS_LEFT);
		add(XBOX_RIGHT_AXIS_RIGHT, RIGHT_AXIS_RIGHT);
		add(XBOX_RIGHT_AXIS_UP, RIGHT_AXIS_UP);
		add(XBOX_RIGHT_AXIS_DOWN, RIGHT_AXIS_DOWN);
		add(XBOX_LT, LT);
		add(XBOX_RT, RT);
	}

}
