package com.one2b3.endcycle.screens.menus.elements.text;

public interface TextAcceptor {

	char getChar(String text, char c);

	default String filter(String text) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			char newChar = getChar(text, text.charAt(i));
			if (newChar != 0) {
				builder.append(newChar);
			}
		}
		return builder.toString();
	}

}
