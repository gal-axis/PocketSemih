package com.one2b3.endcycle.screens.menus.elements.table.drag;

import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.input.InputListener;
import com.one2b3.endcycle.engine.input.events.ButtonEvent;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.endcycle.engine.screens.ScreenObject;
import com.one2b3.endcycle.engine.ui.messages.GameScreenMessage;
import com.one2b3.endcycle.screens.menus.elements.table.MenuTable;
import com.one2b3.endcycle.utils.bounded.BoundedFloat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
public class MenuTableItemDrag<T> implements ScreenObject, InputListener {

	final BoundedFloat dragTimer = new BoundedFloat(Cardinal.isPortable() ? 0.2F : 0.0F);
	final MenuTable<T> container;

	int index = -1;
	T touching, dragging;
	int pointer, x, y;

	@Setter
	MenuTableDragListener<T> dragListener;

	public void start(int index, T touching, int pointer, int x, int y) {
		if (this.index == -1) {
			this.index = index;
			this.touching = touching;
			this.pointer = pointer;
			this.x = x;
			this.y = y;
			dragTimer.toMin();
			if (dragTimer.atMax()) {
				startDragging();
			}
		}
	}

	public void stop(int pointer) {
		if (this.pointer == pointer) {
			stop();
		}
	}

	public void stop() {
		stopDragging();
		touching = null;
		index = -1;
	}

	private void move(int x, int y) {
		this.x = x;
		this.y = y;
		if (dragListener != null) {
			dragListener.drag(container, dragging, index, x, y);
		}
	}

	@Override
	public void update(float delta) {
		if (touching != null && !dragTimer.increase(delta)) {
			startDragging();
		}
	}

	private void startDragging() {
		if (touching != null) {
			dragging = touching;
			touching = null;
			if (dragListener != null) {
				dragListener.startDrag(container, dragging, index);
			}
			container.screen.input.add(this);
			container.screen.addObject(this);
		}
	}

	private void stopDragging() {
		if (dragging != null) {
			container.screen.input.remove(this);
			container.screen.removeObject(this);
			if (dragListener != null) {
				dragListener.stopDrag(container, dragging, index, x, y);
			}
			dragging = null;
		}
	}

	@Override
	public boolean triggerButton(ButtonEvent event) {
		return true;
	}

	@Override
	public boolean triggerTouch(TouchEvent event) {
		if (event.pointer == pointer) {
			if (event.isMoved()) {
				move(event.positionX, event.positionY);
			} else if (event.isReleased()) {
				stop();
			}
			return true;
		}
		return false;
	}

	@Override
	public byte getLayer() {
		return Layers.LAYER_MESSAGES;
	}

	@Override
	public float getComparisonKey() {
		return GameScreenMessage.getCurrentComparisonKey() - 1.0F;
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		float x = getX() - container.getElementWidth() * 0.5F;
		float y = getY() - container.getElementHeight() * 0.5F;
		container.getPaintParameters().batch = batch;
		if (dragListener != null) {
			dragListener.draw(container, getDragging(), getIndex(), x, y);
		} else {
			container.drawObject(getDragging(), getIndex(), x, y);
		}
	}

}
