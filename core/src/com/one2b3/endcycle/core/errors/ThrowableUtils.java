package com.one2b3.endcycle.core.errors;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

import com.one2b3.endcycle.features.revo.GameData;

public class ThrowableUtils {

	private static final String CAUSE_CAPTION = "Caused by: ", SUPPRESSED_CAPTION = "Suppressed: ";

	public static String print(Throwable throwable) {
		StringWriter stringWriter = new StringWriter();
		print(stringWriter, throwable);
		return stringWriter.toString();
	}

	public static void print(Appendable s, Throwable throwable) {
		// Guard against malicious overrides of Throwable.equals by
		// using a Set with identity equality semantics.
		Set<Throwable> dejaVu = Collections.newSetFromMap(new IdentityHashMap<Throwable, Boolean>());
		dejaVu.add(throwable);

		synchronized (s) {
			StackTraceElement[] trace = throwable.getStackTrace();
			try {
				printDirect(s, throwable, "", "", dejaVu, trace, trace.length - 1, 0);
			} catch (IOException e) {
			}
		}
	}

	public static void toString(Appendable str, StackTraceElement t) throws IOException {
		String className = t.getClassName();
		str.append(className.subSequence(className.lastIndexOf('.') + 1, className.length()));
		if (t.isNativeMethod()) {
			str.append("(Native)");
		} else if (t.getLineNumber() >= 0) {
			str.append(':');
			str.append(Integer.toString(t.getLineNumber()));
		} else {
			str.append("(Unknown)");
		}
	}

	public static String toString(Object object) {
		return GameData.toString(object);
	}

	private static void printEnclosedStackTrace(Appendable s, Throwable throwable, StackTraceElement[] enclosingTrace, String caption,
			String prefix, Set<Throwable> dejaVu) throws IOException {
		if (dejaVu.contains(throwable)) {
			s.append("\t[CIRCULAR REFERENCE:" + toString(throwable) + "]\n");
		} else {
			dejaVu.add(throwable);
			// Compute number of frames in common between this and enclosing
			// trace
			StackTraceElement[] trace = throwable.getStackTrace();
			int m = trace.length - 1;
			int n = enclosingTrace.length - 1;
			while (m >= 0 && n >= 0 && trace[m].equals(enclosingTrace[n])) {
				m--;
				n--;
			}
			int framesInCommon = trace.length - 1 - m;
			s.append("\n");
			printDirect(s, throwable, caption, prefix, dejaVu, trace, m, framesInCommon);
		}
	}

	private static void printDirect(Appendable s, Throwable throwable, String caption, String prefix, Set<Throwable> dejaVu,
			StackTraceElement[] trace, int m, int framesInCommon) throws IOException {
		// Print our stack trace
		s.append(prefix);
		s.append(caption);
		s.append(throwable.getClass().getSimpleName());
		String message = throwable.getLocalizedMessage();
		if (message != null) {
			s.append(": ");
			s.append(message);
		}
		s.append("\n\tat ");
		for (int i = 0; i <= m; i++) {
			if (i > 0) {
				s.append(", ");
			}
			s.append(prefix);
			toString(s, trace[i]);
		}
		if (framesInCommon != 0) {
			s.append("\n");
			s.append(prefix);
			s.append("\t... ");
			s.append(Integer.toString(framesInCommon));
			s.append(" more\n");
		}
		printOther(s, throwable, prefix, dejaVu, trace);
	}

	private static void printOther(Appendable s, Throwable throwable, String prefix, Set<Throwable> dejaVu, StackTraceElement[] trace)
			throws IOException {
		for (Throwable se : throwable.getSuppressed()) {
			printEnclosedStackTrace(s, se, trace, SUPPRESSED_CAPTION, prefix + "\t", dejaVu);
		}
		Throwable cause = throwable.getCause();
		if (cause != null) {
			printEnclosedStackTrace(s, cause, trace, CAUSE_CAPTION, prefix, dejaVu);
		}
	}
}
