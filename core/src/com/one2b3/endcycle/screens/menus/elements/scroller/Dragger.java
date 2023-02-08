package com.one2b3.endcycle.screens.menus.elements.scroller;

import com.badlogic.gdx.math.Vector2;
import com.one2b3.endcycle.engine.input.events.TouchEvent;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC)
@NoArgsConstructor
public final class Dragger {

	final Vector2 clicked = new Vector2();
	DragListener dragListener;
	boolean dragging, clickScrolled;
	int pointer = -1;
	float x, y;
	float dragDistance = 3;

	public Dragger(DragListener dragListener) {
		this.dragListener = dragListener;
	}

	public Dragger(float distance) {
		this.dragDistance = distance;
	}

	public Dragger(DragListener dragListener, float distance) {
		this.dragListener = dragListener;
		this.dragDistance = distance;
	}

	public void start(TouchEvent event) {
		start(event.positionX, event.positionY);
		pointer = event.getPointer();
	}

	public void start(float x, float y) {
		clickScrolled = false;
		dragging = true;
		clicked.set(x, y);
		this.x = x;
		this.y = y;
		pointer = -1;
		if (dragListener != null) {
			dragListener.startDrag(x, y);
		}
	}

	public boolean move(TouchEvent event) {
		if (dragging && event.getPointer() == pointer) {
			move(event.positionX, event.positionY);
			return true;
		}
		return false;
	}

	public boolean move(float x, float y) {
		this.x = x;
		this.y = y;
		if (dragging) {
			if (clickScrolled || clicked.dst(x, y) > dragDistance) {
				clickScrolled = true;
				if (dragListener != null) {
					dragListener.drag(getDeltaX(), getDeltaY());
				}
			}
			return true;
		}
		return false;
	}

	public float getDeltaX() {
		return x - clicked.x;
	}

	public float getDeltaY() {
		return y - clicked.y;
	}

	public boolean stop(TouchEvent event) {
		if (dragging && event.getPointer() == pointer) {
			stop();
			return true;
		}
		return false;
	}

	public void stop() {
		dragging = false;
		if (dragListener != null) {
			dragListener.stopDrag(x, y, getDeltaX(), getDeltaY(), clickScrolled);
		}
	}

	public interface DragListener {
		default void startDrag(float x, float y) {
		}

		default void drag(float deltaX, float deltaY) {
		}

		default void stopDrag(float x, float y, float deltaX, float deltaY, boolean clickScrolled) {
		}
	}
}
