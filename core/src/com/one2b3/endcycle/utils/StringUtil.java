package com.one2b3.endcycle.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.utils.Array;

public final class StringUtil {

	private StringUtil() {
	}

	final static Map<String, String[]> splitCases = new HashMap<>(128);
	final static Array<Set<String>> commonSets = new Array<>(10);

	static {
		commonSets.add(set("Left", "Right", "Top", "Bottom"));
	}

	public static String[] splitCase(String word) {
		String[] split = splitCases.get(word);
		if (split == null) {
			split = word.split(" ");
			splitCases.put(word, split);
		}
		return split;
	}

	private static Set<String> set(String... args) {
		Set<String> set = new HashSet<>(args.length);
		for (int i = 0; i < args.length; i++) {
			set.add(args[i]);
		}
		return set;
	}

	public static boolean hasCommon(String name1, String name2) {
		for (int i = 0; i < commonSets.size; i++) {
			if (commonSets.get(i).contains(name1) && commonSets.get(i).contains(name2)) {
				return true;
			}
		}
		String[] split1 = splitCase(name1), split2 = splitCase(name2);
		if (split1.length != split2.length) {
			// If the two elements share the first word or their last word we
			// can assume
			// they're related
			if (split1[0].equals(split2[0]) || split1[split1.length - 1].equals(split2[split2.length - 1])) {
				return true;
			}
			return false;
		}
		if (split1.length == 1) {
			return name1.length() == 1;
		}
		for (int i = 0; i < split1.length; i++) {
			if (split1[i].equals(split2[i])) {
				return true;
			}
		}
		return false;
	}

	public static boolean find(String str, String search) {
		// TODO Improve finding algorithm
		return search == null || search.isEmpty() || str.toLowerCase().contains(search.toLowerCase());
	}

	public static String unescape(String st) {
		StringBuilder sb = new StringBuilder(st.length());
		for (int i = 1; i < st.length() - 1; i++) {
			char ch = st.charAt(i);
			if (ch == '\\') {
				char nextChar = (i == st.length() - 1) ? '\\' : st.charAt(i + 1);
				// Octal escape?
				if (nextChar >= '0' && nextChar <= '7') {
					String code = "" + nextChar;
					i++;
					if ((i < st.length() - 1) && st.charAt(i + 1) >= '0' && st.charAt(i + 1) <= '7') {
						code += st.charAt(i + 1);
						i++;
						if ((i < st.length() - 1) && st.charAt(i + 1) >= '0' && st.charAt(i + 1) <= '7') {
							code += st.charAt(i + 1);
							i++;
						}
					}
					sb.append((char) Integer.parseInt(code, 8));
					continue;
				}
				switch (nextChar) {
				case '\\':
					ch = '\\';
					break;
				case 'b':
					ch = '\b';
					break;
				case 'f':
					ch = '\f';
					break;
				case 'n':
					ch = '\n';
					break;
				case 'r':
					ch = '\r';
					break;
				case 't':
					ch = '\t';
					break;
				case '\"':
					ch = '\"';
					break;
				case '\'':
					ch = '\'';
					break;
				// Hex Unicode: u????
				case 'u':
					if (i >= st.length() - 5) {
						ch = 'u';
						break;
					}
					int code = Integer.parseInt("" + st.charAt(i + 2) + st.charAt(i + 3) + st.charAt(i + 4) + st.charAt(i + 5), 16);
					sb.append(Character.toChars(code));
					i += 5;
					continue;
				}
				i++;
			}
			sb.append(ch);
		}
		return sb.toString();
	}

	public static String repeat(String normal, String repeat, int times) {
		StringBuilder builder = (normal == null ? new StringBuilder() : new StringBuilder(normal));
		for (int i = 0; i < times; i++) {
			builder.append(repeat);
		}
		return builder.toString();
	}

	public static String cut(String str, String ending) {
		return ending.length() > 0 && str.endsWith(ending) ? str.substring(0, str.length() - ending.length()) : str;
	}

	public static String padNumber(int number, int digits) {
		return padNumber(new StringBuilder(0), number, digits).toString();
	}

	public static StringBuilder padNumber(StringBuilder stringBuilder, int number, int digits) {
		boolean negative = false;
		int value, len = 0;
		if (number >= 0) {
			value = number;
		} else {
			negative = true;
			value = -number;
			number = -number;
			len++;
		}
		if (value == 0) {
			len = 1;
		} else {
			for (; value != 0; len++) {
				value /= 10;
			}
		}
		stringBuilder.ensureCapacity(stringBuilder.length() + len);
		if (negative) {
			stringBuilder.append('-');
		}
		for (int i = digits; i > len; i--) {
			stringBuilder.append('0');
		}
		stringBuilder.append(number);
		return stringBuilder;
	}

	public static boolean startsWithIgnoreCase(String str, String prefix) {
		int to = 0, po = 0;
		int pc = prefix.length();
		if (pc > str.length()) {
			return false;
		}
		while (--pc >= 0) {
			if (Character.toUpperCase(str.charAt(to++)) != Character.toUpperCase(prefix.charAt(po++))) {
				return false;
			}
		}
		return true;
	}

	public static String prettyName(String name) {
		return prettyName(name, true);
	}

	public static String prettyName(String name, boolean upperCase) {
		StringBuilder builder = new StringBuilder();
		char last = '-';
		char[] arr = name.toCharArray();
		for (int i = 0; i < arr.length; i++) {
			char c = arr[i];
			if (builder.length() == 0) {
				builder.append(Character.toUpperCase(c));
			} else if (c == '_') {
				builder.append(' ');
			} else {
				if (Character.isAlphabetic(last)) {
					if (upperCase) {
						if (Character.isUpperCase(c)) {
							builder.append(' ');
						}
					} else {
						c = Character.toLowerCase(c);
					}
				}
				builder.append(c);
			}
			last = c;
		}
		return builder.toString();
	}
}
