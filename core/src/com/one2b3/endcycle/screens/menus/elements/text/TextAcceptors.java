package com.one2b3.endcycle.screens.menus.elements.text;

import java.util.HashSet;
import java.util.Set;

public class TextAcceptors {

	public static final TextAcceptor TO_UPPERCASE = (text, c) -> Character.toUpperCase(c);

	public static final TextAcceptor TO_LOWERCASE = (text, c) -> Character.toLowerCase(c);

	private static final Set<Character> FILENAME_FORBIDDEN;
	static {
		FILENAME_FORBIDDEN = new HashSet<>();
		FILENAME_FORBIDDEN.add('.');
		FILENAME_FORBIDDEN.add('/');
		FILENAME_FORBIDDEN.add('\\');
		FILENAME_FORBIDDEN.add('[');
		FILENAME_FORBIDDEN.add(']');
		FILENAME_FORBIDDEN.add('{');
		FILENAME_FORBIDDEN.add('}');
		FILENAME_FORBIDDEN.add(':');
		FILENAME_FORBIDDEN.add('?');
		FILENAME_FORBIDDEN.add('<');
		FILENAME_FORBIDDEN.add('>');
		FILENAME_FORBIDDEN.add('|');
		FILENAME_FORBIDDEN.add('*');
		FILENAME_FORBIDDEN.add('"');
	}

	public static final TextAcceptor FILENAME = (text, c) -> FILENAME_FORBIDDEN.contains(c) ? 0 : c;
}
