package com.one2b3.endcycle.screens.menus.elements;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.input.InputListener;
import com.one2b3.endcycle.engine.input.KeyCode;
import com.one2b3.endcycle.engine.input.events.ButtonEvent;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.engine.objects.Direction;
import com.one2b3.endcycle.engine.screens.GameScreenObject;
import com.one2b3.endcycle.features.theme.ActiveTheme;
import com.one2b3.endcycle.screens.menus.elements.MenuElementNeighbors.MenuElementNeighbor;
import com.one2b3.endcycle.screens.menus.elements.group.MenuElementGroup;
import com.one2b3.endcycle.screens.menus.elements.table.MenuTable;
import com.one2b3.endcycle.utils.ControllerUtils;

import lombok.Getter;
import lombok.Setter;

public class MenuElementController extends GameScreenObject implements InputListener {

	@Getter
	final MenuCursor cursor = new MenuCursor();

	final Array<MenuElement> elements = new Array<>();
	final Map<MenuElement, MenuElementNeighbors> elementNeighbors = new HashMap<>();

	MenuElement masterElement;

	@Setter
	Controller controller;

	@Setter
	boolean allowEmptySelection = true, mouseInput = true, keyBoardInput = true;
	@Getter
	MenuElement selected;

	public MenuElementController() {
	}

	public MenuElementController(MenuElement masterElement) {
		setMasterElement(masterElement);
	}

	public void setMasterElement(MenuElement masterElement) {
		if (this.masterElement instanceof MenuElementGroup) {
			((MenuElementGroup) this.masterElement).setController(null);
		}
		this.masterElement = masterElement;
		if (masterElement instanceof MenuElementGroup) {
			((MenuElementGroup) masterElement).setController(this);
		}
	}

	public void buildNeighbors() {
		buildNeighbors(false, masterElement);
	}

	public void buildNeighbors(boolean separate, MenuElement... elements) {
		buildNeighbors(separate, new Array<>(elements));
	}

	public void buildNeighbors(boolean separate, Array<MenuElement> elements) {
		this.elements.clear();
		this.elementNeighbors.clear();
		Array<MenuElement> set = new Array<>();
		for (int i = 0; i < elements.size; i++) {
			MenuElement element = elements.get(i);
			if (element != null) {
				if (separate && !set.isEmpty()) {
					buildNeighbors(set);
					set.clear();
				}
				element.buildNeighbors(this, set);
			}
		}
		buildNeighbors(set);
		if (!this.elements.contains(selected, true)) {
			setSelected(null);
		}
	}

	public void buildNeighbors(MenuElement element) {
		if (element != null) {
			Array<MenuElement> set = new Array<>();
			element.buildNeighbors(this, set);
			buildNeighbors(set);
		}
	}

	public void buildNeighbors(Array<MenuElement> elements) {
		for (int i = 0; i < elements.size; i++) {
			MenuElement element = elements.get(i);
			if (!this.elements.contains(element, true)) {
				this.elements.add(element);
			}
			elementNeighbors.put(element, new MenuElementNeighbors(element, elements));
		}
	}

	public void removeElement(MenuElement element) {
		elements.removeValue(element, true);
	}

	public void start() {
		screen.input.add(this);
		if (selected != null) {
			selected.setFocused(true);
		}
	}

	public void stop() {
		screen.input.remove(this);
		if (selected != null) {
			selected.setFocused(false);
		}
	}

	public boolean move(Direction direction) {
		return move(direction, selected);
	}

	public boolean move(Direction direction, MenuElement selected) {
		MenuElementNeighbors neighbors = elementNeighbors.get(selected);
		if (neighbors != null) {
			MenuElementNeighbor neighbor = neighbors.getNeighbor(direction);
			if (neighbor != null && selected != neighbor.element) {
				if (neighbor.direct) {
					elementNeighbors.get(neighbor.element).setNeighbor(direction.invert(), selected, true);
				}
				if (setSelected(neighbor.element)) {
					playSound(ActiveTheme.navigate);
					return true;
				} else {
					return false;
				}
			} else {
				MenuElement element = getBack(selected);
				if (element != null) {
					return move(direction, element);
				}
			}
		} else if (selected == null) {
			// We have nothing selected, so select element closest to the top!
			MenuElement top = null;
			for (int i = elements.size - 1; i >= 0; i--) {
				MenuElement element = elements.get(i);
				if (element.isEnabled()) {
					if (top == null || element.calcY() > top.calcY()) {
						top = element;
					}
				}
			}
			if (top != null) {
				setSelected(top);
			}
		}
		return false;
	}

	public boolean moveContainer(Direction direction) {
		MenuElementNeighbors neighbors = elementNeighbors.get(selected);
		if (neighbors != null) {
			MenuElementNeighbor neighbor = neighbors.getNeighbor(direction);
			if (neighbor != null && neighbor.element instanceof MenuTable<?> && selected instanceof MenuTable<?>) {
				MenuTable<?> neighborTable = (MenuTable<?>) neighbor.element;
				MenuTable<?> table = (MenuTable<?>) selected;
				if (table.isSelected()) {
					if (setSelected(neighborTable)) {
						if (neighborTable.isOverviewSelectable()) {
							neighborTable.select();
						}
						return true;
					} else {
						return false;
					}
				}
			}
		}
		return false;
	}

	public boolean hasNeighbor(Direction direction) {
		MenuElementNeighbors neighbor = elementNeighbors.get(selected);
		return neighbor != null && neighbor.getNeighbor(direction) != null;
	}

	public boolean setSelected(int x, int y) {
		elements.sort(MenuElementSorter.instance);
		for (int i = elements.size - 1; i >= 0; i--) {
			MenuElement element = elements.get(i);
			if (!element.isAbsoluteHidden() && element.isTouching(x, y)) {
				if (setSelected(element)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean setSelected(MenuElement selected) {
		if (allowEmptySelection || (selected != null && selected.isFocusable())) {
			setCursorElement(selected);
			if (this.selected != selected) {
				if (this.selected != null) {
					this.selected.setFocused(false);
				}
				this.selected = selected;
				if (selected != null && showing) {
					selected.setFocused(true);
				}
			}
			return true;
		}
		return false;
	}

	public void setCursorElement(MenuElement selected) {
		cursor.setNew(selected);
	}

	@Override
	public boolean triggerButton(ButtonEvent event) {
		if (keyBoardInput && (controller == null || event.getController() == controller
				|| !ControllerUtils.isConnected(controller))) {
			if (event.isPressed()) {
				if (event.isKey(KeyCode.MENU_DOWN)) {
					return move(Direction.DOWN);
				} else if (event.isKey(KeyCode.MENU_UP)) {
					return move(Direction.UP);
				} else if (event.isKey(KeyCode.MENU_LEFT)) {
					return move(Direction.LEFT);
				} else if (event.isKey(KeyCode.MENU_RIGHT)) {
					return move(Direction.RIGHT);
				} else if (event.isKey(KeyCode.MENU_SELECT)) {
					cursor.click();
					return selected != null && selected.select();
				} else if (event.isKey(KeyCode.MENU_CANCEL)) {
					MenuElement back = getBack(selected);
					if (back != null) {
						return setSelected(back);
					}
				} else if (event.isKey(KeyCode.MENU_SWITCH_RIGHT)) {
					return moveContainer(Direction.RIGHT);
				} else if (event.isKey(KeyCode.MENU_SWITCH_LEFT)) {
					return moveContainer(Direction.LEFT);
				}
			}
		}
		return false;
	}

	private MenuElement getBack(MenuElement selected) {
		return (selected == null ? null : selected.toLast());
	}

	@Override
	public boolean triggerTouch(TouchEvent event) {
		if (mouseInput) {
			if (!event.isScroll()) {
				setSelected(event.positionX, event.positionY);
			}
			if (masterElement.touch(event)) {
				return true;
			}
			MenuElement selected = this.selected;
			while (selected != null) {
				if (selected.touch(event)) {
					return true;
				}
				selected = selected.parent;
			}
		}
		return false;
	}

	@Override
	public void update(float delta) {
		updateCursor(delta);
	}

	public void updateCursor(float delta) {
		cursor.update(delta);
	}

	public void syncCursor() {
		cursor.syncCursor();
	}

	@Override
	public boolean remove() {
		return false;
	}

	@Override
	public byte getLayer() {
		return masterElement.getLayer();
	}

	@Override
	public float getComparisonKey() {
		return masterElement.getComparisonKey() - 1.0F;
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		if (showing && selected != null) {
			if (selected.showing && !selected.isAbsoluteHidden() && selected.canDrawCursor()) {
				drawCursor(batch, xOfs, yOfs);
			}
		}
		if (Cardinal.DEBUGGING) {
			drawDebug(batch, xOfs, yOfs);
		}
	}

	public void drawCursor(CustomSpriteBatch batch, float xOfs, float yOfs) {
		cursor.draw(batch, xOfs, yOfs);
	}

	/**
	 * Draws a green overlay over the neighboring elements
	 *
	 * @param batch
	 * @param xOfs
	 * @param yOfs
	 */
	protected void drawDebug(CustomSpriteBatch batch, float xOfs, float yOfs) {
		MenuElementNeighbors neighbors = elementNeighbors.get(selected);
		if (neighbors != null) {
			for (Direction dir : Direction.values()) {
				MenuElementNeighbor neighbor = neighbors.getNeighbor(dir);
				if (neighbor != null) {
					MenuElement element = neighbor.element;
					Painter params = Painter.on(batch).x(element.getAbsoluteX()).y(element.getAbsoluteY())
							.color(Color.GREEN).alpha(0.1F);
					batch.drawRectangle(params.x, params.y, element.getWidth(), element.getHeight(), params.color);
					params.color(Color.WHITE).x(params.x + element.getWidth() * 0.5F)
							.y(params.y + element.getHeight() * 0.5F).hAlign(0).vAlign(0).font(GameFonts.TextBorder)
							.color(1.0F, 0.5F, 0.5F).paint(dir.name());
				}
			}
		}
	}
}
