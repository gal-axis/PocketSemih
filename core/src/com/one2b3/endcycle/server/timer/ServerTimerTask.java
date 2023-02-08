package com.one2b3.endcycle.server.timer;

import java.util.concurrent.ScheduledFuture;

public abstract class ServerTimerTask implements Runnable {

	ScheduledFuture<?> future;

	public void execute(ServerTimer timer) {
		schedule(timer, 0);
	}

	public void schedule(ServerTimer timer, long delay) {
		future = timer.schedule(this, delay);
	}

	public void schedule(ServerTimer timer, long delay, long repeat) {
		future = timer.schedule(this, delay, repeat);
	}

	public boolean cancel() {
		return (future != null ? future.cancel(false) : false);
	}

}
