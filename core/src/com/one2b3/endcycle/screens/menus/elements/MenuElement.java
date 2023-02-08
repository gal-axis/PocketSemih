package com.one2b3.endcycle.screens.menus.elements;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Pools;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.engine.screens.GameScreenObject;
import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.endcycle.screens.menus.MenuElementActionListener;
import com.one2b3.endcycle.screens.menus.elements.MenuElementAction.MenuElementActionType;
import com.one2b3.endcycle.screens.menus.elements.group.MenuElementGroup;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@Getter
public abstract class MenuElement extends GameScreenObject implements MenuElementActionListener {

	final Set<MenuElementActionListener> actionListeners = new HashSet<>(1);
	MenuElementGroup parent;
	MenuElement anchor;
	int anchorHAlign = -1, anchorVAlign = -1;
	int hAlign = -1, vAlign = -1;

	float x, y;
	float width, height;
	byte layer = Layers.LAYER_HUD;

	boolean enabled = true;
	boolean focusable = true;
	boolean focused;
	boolean hidden;

	@Setter(AccessLevel.NONE)
	@Getter(AccessLevel.NONE)
	boolean wasPressed;

	public MenuElement setAnchor(MenuElement element) {
		MenuElement outer = element;
		while (outer != null) {
			if (outer == this) {
				throw new GdxRuntimeException("Anchor cannot be recursive!");
			}
			outer = outer.getAnchor();
		}
		this.anchor = element;
		return this;
	}

	@Override
	public void actionTriggered(MenuElementAction action) {
		for (MenuElementActionListener listener : actionListeners) {
			listener.actionTriggered(action);
		}
		if (parent != null) {
			parent.actionTriggered(action);
		}
	}

	public MenuElementController findController() {
		if (parent != null) {
			return parent.findController();
		}
		return screen == null ? null : screen.getObject(MenuElementController.class);
	}

	public void calculateSize() {
	}

	public final void triggerAction(MenuElementActionType type) {
		triggerAction(type, null);
	}

	public final void triggerAction(MenuElementActionType type, Object value) {
		try (MenuElementAction action = Pools.obtain(MenuElementAction.class)) {
			actionTriggered(action.setSource(this).setType(type).setValue(value));
		}
	}

	public final void setMenuControllerSelection(MenuElement element) {
		if (screen != null && showing) {
			MenuElementController controller = findController();
			if (controller != null) {
				controller.setSelected(element);
			}
		}
	}

	public MenuElement setEnabled(boolean enabled) {
		if (this.enabled != enabled) {
			this.enabled = enabled;
			triggerAction(enabled ? MenuElementActionType.ENABLED : MenuElementActionType.DISABLED);
		}
		return this;
	}

	public MenuElement setFocused(boolean focused) {
		if (this.focused != focused) {
			this.focused = focused;
			this.wasPressed = false;
			triggerAction(focused ? MenuElementActionType.FOCUSED : MenuElementActionType.UNFOCUSED);
		}
		return this;
	}

	public MenuElement setHidden(boolean hidden) {
		if (this.hidden != hidden) {
			this.hidden = hidden;
			triggerAction(hidden ? MenuElementActionType.HIDDEN : MenuElementActionType.SHOWN);
		}
		return this;
	}

	public boolean isAbsoluteHidden() {
		return hidden || (parent != null && parent.isAbsoluteHidden());
	}

	public float calcX() {
		return x + //
				(anchor == null ? 0.0F : anchor.calcX() + anchor.getWidth() * 0.5F * (anchorHAlign + 1)) - //
				getWidth() * 0.5F * (hAlign + 1);
	}

	public float calcY() {
		return y + //
				(anchor == null ? 0.0F : anchor.calcY() + anchor.getHeight() * 0.5F * (anchorVAlign + 1)) - //
				getHeight() * 0.5F * (vAlign + 1);
	}

	public float getAbsoluteX() {
		return calcX() + (parent == null ? 0 : parent.getAbsoluteX());
	}

	public float getAbsoluteY() {
		return calcY() + (parent == null ? 0 : parent.getAbsoluteY());
	}

	public MenuElement getAbsoluteParent() {
		MenuElement parent = this.parent;
		while (parent != null && parent.getParent() != null) {
			parent = parent.getParent();
		}
		return parent;
	}

	/**
	 * Adds itself and any child objects to the array of elements.
	 *
	 * @param controller TODO
	 */
	public void buildNeighbors(MenuElementController controller, Array<MenuElement> elements) {
		elements.add(this);
	}

	public boolean touch(TouchEvent trigger) {
		if (!trigger.isScroll()) {
			if (isTouching(trigger.positionX, trigger.positionY)) {
				if (trigger.isPressed()) {
					wasPressed = true;
				}
				if (trigger.isReleased() && wasPressed) {
					select();
				}
				return true;
			} else if (trigger.isReleased()) {
				wasPressed = false;
			}
		}
		return false;
	}

	public boolean isTouching(float x, float y) {
		return x >= getAbsoluteX() && y >= getAbsoluteY() && x <= getAbsoluteX() + getWidth()
				&& y <= getAbsoluteY() + getHeight();
	}

	/**
	 * Called when the element is clicked or pressed
	 *
	 * @return Whether or not the input is valid
	 */
	public boolean select() {
		triggerAction(MenuElementActionType.SELECTED);
		return false;
	}

	public MenuElement toLast() {
		return parent == null ? null : parent.isFocusable() ? parent : parent.toLast();
	}

	public boolean canDrawCursor() {
		return !Cardinal.isPortable() || !screen.input.lastTouch;
	}

	public void setCursor(Rectangle rectangle) {
		rectangle.set(getAbsoluteX(), getAbsoluteY(), getWidth(), getHeight());
	}

	@Override
	public boolean remove() {
		return false;
	}

	public Rectangle getPosition() {
		return new Rectangle(getAbsoluteX(), getAbsoluteY(), getWidth(), getHeight());
	}
}
