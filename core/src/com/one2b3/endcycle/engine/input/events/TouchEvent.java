package com.one2b3.endcycle.engine.input.events;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.one2b3.endcycle.engine.input.InputType;

import lombok.Getter;

public class TouchEvent {

	public int positionX, positionY;

	@Getter
	public int pointer, button;

	public InputType type;

	public TouchEvent() {
	}

	public TouchEvent(InputType type, int id, int button, int xPos, int yPos) {
		setProperties(type, id, button, xPos, yPos);
	}

	public TouchEvent(InputType type, int id, int button, Vector2 position) {
		setProperties(type, id, button, (int) position.x, (int) position.y);
	}

	public void set(TouchEvent trigger) {
		setProperties(trigger.type, trigger.pointer, trigger.button, trigger.positionX, trigger.positionY);
	}

	public void setProperties(InputType type, int pointer, int button, int positionX, int positionY) {
		this.type = type;
		this.pointer = pointer;
		this.button = button;
		this.positionX = positionX;
		this.positionY = positionY;
	}

	public boolean isLeftMouse() {
		return button == 0;
	}

	public boolean isRightMouse() {
		return button == 1;
	}

	public boolean isMiddleMouse() {
		return button == 2;
	}

	public boolean isScroll() {
		return pointer == -2;
	}

	public boolean isMoved() {
		return type == InputType.MOVED;
	}

	public boolean isPressed() {
		return type == InputType.PRESSED;
	}

	public boolean isReleased() {
		return type == InputType.RELEASED;
	}

	public boolean isIn(float x, float y, float width, float height) {
		return positionX >= x && positionX <= x + width && positionY >= y && positionY <= y + height;
	}

	public boolean isIn(Rectangle rectangle) {
		return rectangle != null && isIn(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}

	@Override
	public String toString() {
		return "[Pointer: " + pointer + ", Button: " + button + ", Type:" + type.name() + ", X:" + positionX + ", Y:"
				+ positionY;
	}
}
