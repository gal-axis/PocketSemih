package com.one2b3.endcycle.engine.input.binders.bindings.images.makers;

import static com.one2b3.endcycle.engine.input.binders.bindings.images.ButtonImages.*;
import static com.one2b3.endcycle.engine.input.binders.constants.VJoyConstants2.*;

import com.badlogic.gdx.controllers.Controller;
import com.one2b3.endcycle.engine.input.binders.bindings.images.ButtonImageMaker;

public class VJoyImageCollection extends ButtonImageMaker {

	@Override
	public void init(Controller controller) {
		add(SWITCH_L1, L1);
		add(SWITCH_R1, R1);
		add(SWITCH_L2, L2);
		add(SWITCH_R2, R2);
		add(SWITCH_POV_RIGHT, POV_RIGHT);
		add(SWITCH_POV_UP, POV_UP);
		add(SWITCH_POV_DOWN, POV_DOWN);
		add(SWITCH_POV_LEFT, POV_LEFT);
		add(SWITCH_LEFT_AXIS, LEFT_AXIS);
		add(SWITCH_LEFT_AXIS_LEFT, LEFT_AXIS_LEFT);
		add(SWITCH_LEFT_AXIS_RIGHT, LEFT_AXIS_RIGHT);
		add(SWITCH_LEFT_AXIS_UP, LEFT_AXIS_UP);
	}

}
