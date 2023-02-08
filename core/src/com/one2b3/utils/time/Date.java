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
public final class Date implements Comparable<Date> {

	int year;
	short month;
	short day;

	public boolean isAfter(Date date) {
		if (year > date.year) {
			return true;
		} else if (year == date.year) {
			if (month > date.month) {
				return true;
			} else if (month == date.month) {
				if (day > date.day) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int compareTo(Date o) {
		if (o == null) {
			return 1;
		}
		int c = Integer.compare(year, o.year);
		if (c != 0) {
			return c;
		}
		c = Short.compare(month, o.month);
		if (c != 0) {
			return c;
		}
		return Short.compare(day, o.day);
	}

	public static Date from(String date) {
		String[] split = date.split("-");
		return new Date(Integer.parseInt(split[0]), Short.parseShort(split[1]), Short.parseShort(split[2]));
	}

	@Override
	public String toString() {
		return year + "-" + month + "-" + day;
	}

	public String format() {
		return Localizer.format("%04d-%02d-%02d", year, month, day);
	}
}
