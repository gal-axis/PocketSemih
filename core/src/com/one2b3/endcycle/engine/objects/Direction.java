package com.one2b3.endcycle.engine.objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Direction {
	LEFT(180.0F), RIGHT(0.0F), UP(90.0F), DOWN(270.0F), LEFT_UP(135.0F), LEFT_DOWN(225.0F), RIGHT_UP(45.0F),
	RIGHT_DOWN(315.0F);

	@Getter
	final float angle;

	public Direction invert() {
		switch (this) {
		case LEFT:
			return RIGHT;
		case RIGHT:
			return LEFT;
		case UP:
			return DOWN;
		case DOWN:
			return UP;
		case LEFT_DOWN:
			return RIGHT_UP;
		case LEFT_UP:
			return RIGHT_DOWN;
		case RIGHT_DOWN:
			return LEFT_UP;
		case RIGHT_UP:
			return LEFT_DOWN;
		default:
			return this;
		}
	}
}
