package com.one2b3.endcycle.core.errors;

import java.io.StringWriter;
import java.util.HashSet;

public class ErrorCache {

	final HashSet<String> errors = new HashSet<>(100);

	public boolean report(String error) {
		int start = 0, lastLine = 0;
		for (int i = 0, line = -1; line < 4 && i < error.length(); i++) {
			if (i == 0 && error.charAt(i) == '[') {
				// Tags make ID start one line later
				line--;
			} else if (error.charAt(i) == '\n' || (line >= 0 && error.charAt(i) == ',')) {
				line++;
				if (line < 1) {
					start = i + 1;
				} else {
					lastLine = i;
					if (line == 3) {
						break;
					}
				}
			}
		}
		String reportId = error;
		if (start != 0) {
			reportId = (lastLine != 0 ? error.substring(start, lastLine) : error.substring(start));
		}
		return errors.add(reportId);
	}

	// TESTING

	public static void main(String[] args) {
		ErrorCache cache = new ErrorCache();
		report(cache, "Test 1");
		report(cache, "Test 2");
		report(cache, "Multiline Test\nMmmhm\nFor sure!");
		report(cache, new NullPointerException("Huh?"), "Exception while rendering game!");
		stack1(cache);
		stack2(cache);
		stack3(cache);
	}

	public static void stack1(ErrorCache cache) {
		stack2(cache);
	}

	public static void stack2(ErrorCache cache) {
		stack3(cache);
	}

	public static void stack3(ErrorCache cache) {
		stack4(cache);
	}

	public static void stack4(ErrorCache cache) {
		for (int i = 0; i < 3; i++) {
			report(cache, new ArrayIndexOutOfBoundsException(3 - i), "Array index issue!");
		}
		for (int i = 0; i < 2; i++) {
			report(cache, new NullPointerException("Test at: " + i));
		}
	}

	private static void report(ErrorCache cache, Throwable throwable) {
		report(cache, throwable, null);
	}

	private static void report(ErrorCache cache, Throwable throwable, String message) {
		StringWriter writer = new StringWriter();
		if (message != null) {
			writer.append("[Cardinal] ");
			writer.append(message);
			writer.append('\n');
		}
		ThrowableUtils.print(writer, throwable);
		report(cache, writer.toString());
	}

	public static void report(ErrorCache cache, String error) {
		if (cache.report(error)) {
			System.out.println("Reported: " + error);
		} else {
			System.out.println("Duplicate: " + error);
		}
	}
}
