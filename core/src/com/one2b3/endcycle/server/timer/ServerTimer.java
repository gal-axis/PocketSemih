package com.one2b3.endcycle.server.timer;

import java.util.concurrent.ScheduledFuture;

public interface ServerTimer {

	ScheduledFuture<?> schedule(Runnable runnable, long delay);

	ScheduledFuture<?> schedule(Runnable runnable, long delay, long repeat);

}
