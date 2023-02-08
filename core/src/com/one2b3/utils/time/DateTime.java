package com.one2b3.utils.time;

import java.util.Calendar;

import com.one2b3.endcycle.engine.proguard.KeepClass;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@KeepClass
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
@EqualsAndHashCode
public final class DateTime implements Comparable<DateTime> {

	public static DateTime now() {
		return new DateTime(System.currentTimeMillis());
	}

	final Date date;
	final Time time;

	public DateTime() {
		this(new Date(), new Time());
	}

	public DateTime(long millis) {
		this();
		set(millis);
	}

	public void set(long millis) {
		Calendar instance = Calendar.getInstance();
		instance.setTimeInMillis(millis);
		date.year = instance.get(Calendar.YEAR);
		date.month = (short) (instance.get(Calendar.MONTH) + 1);
		date.day = (short) instance.get(Calendar.DAY_OF_MONTH);
		time.hour = (byte) instance.get(Calendar.HOUR_OF_DAY);
		time.minute = (byte) instance.get(Calendar.MINUTE);
		time.second = (byte) instance.get(Calendar.SECOND);
		time.millis = instance.get(Calendar.MILLISECOND);
	}

	public String format() {
		return date.format() + " " + time.format();
	}

	public String formatFilename() {
		return date.format() + "_" + time.formatFilename();
	}

	@Override
	public int compareTo(DateTime o) {
		if (o == null) {
			return 1;
		}
		int c = (date == null ? 1 : date.compareTo(o.date));
		if (c != 0) {
			return c;
		}
		return (time == null ? 1 : time.compareTo(o.time));
	}

	@Override
	public String toString() {
		return date.toString() + " " + time.toString();
	}

	public static DateTime from(String dateTime) {
		String[] split = dateTime.split(" ");
		return new DateTime(Date.from(split[0]), Time.from(split[1]));
	}

	public boolean isAfter(DateTime dateTime) {
		if (date.isAfter(dateTime.date)) {
			return true;
		} else if (date.equals(dateTime.date)) {
			return time.isAfter(dateTime.time);
		}
		return false;
	}

}
