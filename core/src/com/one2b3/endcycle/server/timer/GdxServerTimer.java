package com.one2b3.endcycle.server.timer;

import java.util.concurrent.ScheduledFuture;

import com.badlogic.gdx.utils.Timer;

public class GdxServerTimer implements ServerTimer {

	@Override
	public ScheduledFuture<?> schedule(Runnable runnable, long delay) {
		GdxFuture<?> future = new GdxFuture<>(runnable);
		Timer.instance().scheduleTask(future, delay * 0.001F);
		return future;
	}

	@Override
	public ScheduledFuture<?> schedule(Runnable runnable, long delay, long repeat) {
		GdxFuture<?> future = new GdxFuture<>(runnable);
		Timer.instance().scheduleTask(future, delay * 0.001F, repeat * 0.001F);
		return future;
	}
}
