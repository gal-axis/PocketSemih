package com.one2b3.endcycle.screens.menus.elements.changer;

public interface MenuChangerSelectAction<T> {
	void changedSelection(int selection, T item);
}