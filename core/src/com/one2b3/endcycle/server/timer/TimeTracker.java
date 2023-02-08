package com.one2b3.endcycle.server.timer;

import java.util.concurrent.TimeUnit;

import com.one2b3.endcycle.engine.language.Localizer;

public class TimeTracker {

	static final TimeUnit USING = TimeUnit.MILLISECONDS;
	static final long DAY = TimeUnit.DAYS.toHours(1);
	static final long HOUR = TimeUnit.HOURS.toMinutes(1);
	static final long MINUTE = TimeUnit.MINUTES.toSeconds(1);

	long timeStarted;

	public void start() {
		timeStarted = System.currentTimeMillis();
	}

	public long getTimeSinceStart() {
		return System.currentTimeMillis() - timeStarted;
	}

	public String getFormattedTimeSinceStart() {
		long time = getTimeSinceStart();
		long days = USING.toDays(time);
		if (days > 0L) {
			return Localizer.format("%d %s and %02d:%02d:%02d", days, (days > 1 ? "Days" : "Day"), USING.toHours(time) % DAY,
					USING.toMinutes(time) % HOUR, USING.toSeconds(time) % MINUTE);
		} else {
			return Localizer.format("%02d:%02d:%02d", USING.toHours(time), USING.toMinutes(time) % HOUR, USING.toSeconds(time) % MINUTE);
		}
	}
}
