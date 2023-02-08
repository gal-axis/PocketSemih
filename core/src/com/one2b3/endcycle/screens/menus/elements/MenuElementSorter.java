package com.one2b3.endcycle.screens.menus.elements;

import java.util.Comparator;

public class MenuElementSorter implements Comparator<MenuElement> {

	public static final MenuElementSorter instance = new MenuElementSorter();

	@Override
	public int compare(MenuElement o1, MenuElement o2) {
		int layer = getLayer(o1), layer2 = getLayer(o2);
		if (layer != layer2) {
			return Integer.compare(layer, layer2);
		}
		return Float.compare(getComparisonKey(o1), getComparisonKey(o2));
	}

	private float getComparisonKey(MenuElement element) {
		while (element.parent != null) {
			element = element.parent;
		}
		return element.getComparisonKey();
	}

	private int getLayer(MenuElement element) {
		while (element.parent != null) {
			element = element.parent;
		}
		return element.getLayer();
	}

}
