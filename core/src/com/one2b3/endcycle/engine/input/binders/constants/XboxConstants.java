package com.one2b3.endcycle.engine.input.binders.constants;

import static com.one2b3.endcycle.engine.input.InputManager.AXIS_ADDER;

public class XboxConstants {

	public static final int LEFT_AXIS_LEFT = 0 + AXIS_ADDER;
	public static final int LEFT_AXIS_UP = 1 + AXIS_ADDER;
	public static final int LEFT_AXIS_DOWN = -LEFT_AXIS_UP;
	public static final int LEFT_AXIS_RIGHT = -LEFT_AXIS_LEFT;

	public static final int RIGHT_AXIS_LEFT = 2 + AXIS_ADDER;
	public static final int RIGHT_AXIS_UP = 3 + AXIS_ADDER;
	public static final int RIGHT_AXIS_DOWN = -RIGHT_AXIS_UP;
	public static final int RIGHT_AXIS_RIGHT = -RIGHT_AXIS_LEFT;

	public static final int POV_UP = 11;
	public static final int POV_DOWN = 12;
	public static final int POV_RIGHT = 14;
	public static final int POV_LEFT = 13;

	public static final int A = 0;
	public static final int B = 1;
	public static final int X = 2;
	public static final int Y = 3;

	public static final int LB = 4;
	public static final int RB = 5;

	public static final int LT = -(4 + AXIS_ADDER);
	public static final int RT = -(5 + AXIS_ADDER);

	public static final int BACK = 6;
	public static final int START = 7;

	public static final int LEFT_AXIS = 8;
	public static final int RIGHT_AXIS = 9;

}
