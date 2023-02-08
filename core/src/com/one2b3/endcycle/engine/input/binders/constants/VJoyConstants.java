package com.one2b3.endcycle.engine.input.binders.constants;

import static com.one2b3.endcycle.engine.input.InputManager.AXIS_ADDER;

public class VJoyConstants {

	public static final int LEFT_AXIS = 8;
	public static final int LEFT_AXIS_LEFT = 1 + AXIS_ADDER;
	public static final int LEFT_AXIS_UP = 0 + AXIS_ADDER;
	public static final int LEFT_AXIS_DOWN = -LEFT_AXIS_UP;
	public static final int LEFT_AXIS_RIGHT = -LEFT_AXIS_LEFT;

	public static final int POV_UP = 16;
	public static final int POV_DOWN = 19;
	public static final int POV_RIGHT = 17;
	public static final int POV_LEFT = 18;

	public static final int L1 = 21;
	public static final int R1 = 20;
	public static final int L2 = 22;
	public static final int R2 = 23;

	public static final int START = 25;

}
