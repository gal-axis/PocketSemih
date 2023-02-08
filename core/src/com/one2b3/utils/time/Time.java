package com.one2b3.utils.time;

import com.one2b3.endcycle.engine.language.Localizer;
import com.one2b3.endcycle.engine.proguard.KeepClass;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@KeepClass
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
@EqualsAndHashCode
public final class Time implements Comparable<Time> {

	public static String formatTime(double time) {
		int millis = (int) (time * 100.0) % 100;
		int seconds = (int) (time % 60.0);
		int minutes = (int) (time / 60.0);
		return Localizer.format("%02d:%02d.%02d", minutes, seconds, millis);
	}

	byte hour, minute, second;
	int millis;

	@Override
	public int compareTo(Time o) {
		if (o == null) {
			return 1;
		}
		int c = Byte.compare(hour, o.hour);
		if (c != 0) {
			return c;
		}
		c = Byte.compare(minute, o.minute);
		if (c != 0) {
			return c;
		}
		c = Byte.compare(second, o.second);
		if (c != 0) {
			return c;
		}
		return Integer.compare(millis, o.millis);
	}

	public String format() {
		return Localizer.format("%02d:%02d:%02d", hour, minute, second);
	}

	public String formatFilename() {
		return Localizer.format("%02d-%02d-%02d", hour, minute, second);
	}

	public boolean isAfter(Time time) {
		if (hour > time.hour) {
			return true;
		} else if (hour == time.hour) {
			if (minute > time.minute) {
				return true;
			} else if (minute == time.minute) {
				if (second > time.second) {
					return true;
				} else if (second == time.second) {
					if (millis > time.millis) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		// Dont use comma for milliseconds! Use "format()" for proper formatting
		// This is used in the "from(String date)" method below.
		return hour + ":" + minute + ":" + second + ":" + millis;
	}

	public static Time from(String date) {
		String[] split = date.split(":");
		return new Time(getByte(split, 0), getByte(split, 1), getByte(split, 2), getInt(split, 3));
	}

	private static byte getByte(String[] split, int pos) {
		try {
			return Byte.parseByte(split[pos]);
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			return 0;
		}
	}

	private static int getInt(String[] split, int pos) {
		try {
			return Integer.parseInt(split[pos]);
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			return 0;
		}
	}

}
