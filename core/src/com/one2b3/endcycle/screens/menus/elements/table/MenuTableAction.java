package com.one2b3.endcycle.screens.menus.elements.table;

public interface MenuTableAction<T> {

	default void clickedTable(int selection, T selected, float x, float y) {
		selectTable(selection, selected);
	}

	void selectTable(int selection, T selected);

	default void specialTableSelect(int selection, T selected) {
		selectTable(selection, selected);
	}

	default void moveTableSelection(int selection, T selected) {
	}

	default void updateTableSelection(int selection, T selected) {
	}

	default boolean selectTableHeader() {
		return false;
	}

}
