package com.one2b3.endcycle.screens.menus.elements;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Predicate;
import com.one2b3.endcycle.engine.objects.Direction;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

public final class MenuElementNeighbors implements Comparator<Entry<MenuElement, Rectangle>> {

	static final Rectangle tempRect = new Rectangle();
	static final Array<Entry<MenuElement, Rectangle>> temp = new Array<>();

	final MenuElement element;
	final Rectangle position;
	final ArrayMap<Direction, MenuElementNeighbor> neighbors;

	public MenuElementNeighbors(MenuElement element, Array<MenuElement> allElements) {
		this.element = element;
		this.position = element.getPosition();
		this.neighbors = new ArrayMap<>(4);
		build(allElements);
	}

	private void build(Array<MenuElement> elements) {
		if (elements.size == 0 || (elements.size == 1 && elements.get(0) == element)) {
			return;
		}
		Map<MenuElement, Rectangle> positions = buildPositions(elements);

		setCompareX(1.0F).setCompareY(0.5F);
		set(Direction.LEFT, positions, //
				e -> e.x + e.width <= position.x, //
				e -> e.overlaps(tempRect.set(e.x, position.y, position.width, position.height)));

		setCompareX(0.0F).setCompareY(0.5F);
		set(Direction.RIGHT, positions, //
				e -> e.x >= position.x + position.width, //
				e -> e.overlaps(tempRect.set(e.x, position.y, position.width, position.height)));

		setCompareX(0.5F).setCompareY(1.0F);
		set(Direction.UP, positions, //
				e -> e.y >= position.y + position.height, //
				e -> e.overlaps(tempRect.set(position.x, e.y, position.width, position.height)));

		setCompareX(0.5F).setCompareY(0.0F);
		set(Direction.DOWN, positions, //
				e -> e.y + e.height <= position.y, //
				e -> e.overlaps(tempRect.set(position.x, e.y, position.width, position.height)));
	}

	private Map<MenuElement, Rectangle> buildPositions(Array<MenuElement> elements) {
		Map<MenuElement, Rectangle> positions = new HashMap<>(elements.size - 1);
		for (int i = elements.size - 1; i >= 0; i--) {
			MenuElement element = elements.get(i);
			if (element != this.element) {
				positions.put(element, element.getPosition());
			}
		}
		return positions;
	}

	private void set(Direction direction, Map<MenuElement, Rectangle> elementPositions, Predicate<Rectangle> filter,
			Predicate<Rectangle> filter2) {
		temp.clear();
		boolean direct = true;
		for (Entry<MenuElement, Rectangle> entry : elementPositions.entrySet()) {
			if (filter.evaluate(entry.getValue()) && filter2.evaluate(entry.getValue())) {
				temp.add(entry);
			}
		}
		if (temp.size == 0) {
			direct = false;
			for (Entry<MenuElement, Rectangle> entry : elementPositions.entrySet()) {
				if (filter.evaluate(entry.getValue())) {
					temp.add(entry);
				}
			}
		}
		if (temp.size > 0) {
			temp.sort(this);
			setNeighbor(direction, temp.get(0).getKey(), direct);
		}
	}

	public MenuElementNeighbor getNeighbor(Direction direction) {
		return neighbors.get(direction);
	}

	public void setNeighbor(Direction direction, MenuElement info, boolean direct) {
		if (info == null) {
			neighbors.removeKey(direction);
		} else {
			neighbors.put(direction, new MenuElementNeighbor(info, direct));
		}
	}

	@Setter
	@Accessors(chain = true)
	float compareX, compareY;

	@Override
	public int compare(Entry<MenuElement, Rectangle> o1, Entry<MenuElement, Rectangle> o2) {
		int c = Float.compare(dst(o1.getValue()), dst(o2.getValue()));
		if (c != 0) {
			return c;
		}
		c = Float.compare(o1.getValue().x, o2.getValue().x);
		if (c != 0) {
			return c;
		}
		return Float.compare(o1.getValue().y, o2.getValue().y);
	}

	public float dst(Rectangle rect) {
		return Vector2.dst(position.x + position.width * (1 - compareX), position.y + position.height * (1 - compareY),
				rect.x + rect.width * compareX, rect.y + rect.height * compareY);
	}

	@RequiredArgsConstructor
	@EqualsAndHashCode
	@FieldDefaults(level = AccessLevel.PUBLIC)
	public static class MenuElementNeighbor {
		final MenuElement element;
		final boolean direct;
	}
}
