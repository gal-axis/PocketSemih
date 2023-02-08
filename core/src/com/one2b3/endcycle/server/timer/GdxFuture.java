package com.one2b3.endcycle.server.timer;

import java.util.concurrent.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer.Task;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GdxFuture<T> extends Task implements ScheduledFuture<T> {

	final Runnable runnable;

	@Override
	public void run() {
		try {
			runnable.run();
		} catch (Throwable throwable) {
			Gdx.app.error("Task", "Error executing task: " + runnable, throwable);
		}
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return TimeUnit.MILLISECONDS.convert(getExecuteTimeMillis(), unit);
	}

	@Override
	public int compareTo(Delayed o) {
		return 0;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		if (!isCancelled()) {
			super.cancel();
			return true;
		}
		return false;
	}

	@Override
	public boolean isCancelled() {
		return !super.isScheduled();
	}

	@Override
	public boolean isDone() {
		return isCancelled();
	}

	@Override
	public T get() throws InterruptedException, ExecutionException {
		return null;
	}

	@Override
	public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return null;
	}

}