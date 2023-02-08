package com.one2b3.endcycle.engine.files;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.PropertiesUtils;
import com.badlogic.gdx.utils.StringBuilder;

public class PropertiesUtil {

	private static final String LINE_SEPARATOR = "\n";

	private PropertiesUtil() {
	}

	public static void load(ObjectMap<String, String> properties, Reader reader) throws IOException {
		PropertiesUtils.load(properties, reader);
	}

	public static void store(ObjectMap<String, String> properties, Writer writer, boolean escapeUnicode) throws IOException {
		StringBuilder sb = new StringBuilder(200);
		Array<String> keys = properties.keys().toArray();
		keys.sort();
		for (String key : keys) {
			dumpString(sb, key, true, escapeUnicode);
			sb.append('=');
			dumpString(sb, properties.get(key), false, escapeUnicode);
			writer.write(sb.toString());
			sb.setLength(0);
			writer.write(LINE_SEPARATOR);
		}
		writer.flush();
	}

	private static void dumpString(StringBuilder outBuffer, String str, boolean escapeSpace, boolean escapeUnicode) {
		int len = str.length();
		for (int i = 0; i < len; i++) {
			char ch = str.charAt(i);
			// Handle common case first
			if ((ch > 61) && (ch < 127)) {
				outBuffer.append(ch == '\\' ? "\\\\" : ch);
				continue;
			}
			switch (ch) {
			case ' ':
				if (i == 0 || escapeSpace) {
					outBuffer.append("\\ ");
				} else {
					outBuffer.append(ch);
				}
				break;
			case '\n':
				outBuffer.append("\\n");
				break;
			case '\r':
				outBuffer.append("\\r");
				break;
			case '\t':
				outBuffer.append("\\t");
				break;
			case '\f':
				outBuffer.append("\\f");
				break;
			case '=': // Fall through
			case ':': // Fall through
			case '#': // Fall through
			case '!':
				outBuffer.append('\\').append(ch);
				break;
			default:
				if (((ch < 0x0020) || (ch > 0x007e)) & escapeUnicode) {
					String hex = Integer.toHexString(ch);
					outBuffer.append("\\u");
					for (int j = 0; j < 4 - hex.length(); j++) {
						outBuffer.append('0');
					}
					outBuffer.append(hex);
				} else {
					outBuffer.append(ch);
				}
				break;
			}
		}
	}
}
