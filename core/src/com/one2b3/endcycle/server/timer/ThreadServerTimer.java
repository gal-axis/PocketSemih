package com.one2b3.endcycle.server.timer;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.*;

import com.badlogic.gdx.Gdx;

public class ThreadServerTimer implements ServerTimer, RejectedExecutionHandler, UncaughtExceptionHandler {

	final String name;
	public final ScheduledThreadPoolExecutor executor;
	final boolean daemon;

	public ThreadServerTimer(String name, int threads, boolean daemon) {
		this.name = name;
		this.daemon = daemon;
		this.executor = new ScheduledThreadPoolExecutor(threads, this::newThread, this);
		executor.setMaximumPoolSize(threads);
		start();
	}

	private void start() {
		executor.prestartCoreThread();
	}

	private Thread newThread(Runnable target) {
		Thread thread = new Thread(target, name);
		thread.setDaemon(daemon);
		thread.setUncaughtExceptionHandler(this);
		return thread;
	}

	@Override
	public ScheduledFuture<?> schedule(Runnable runnable, long delay) {
		return executor.schedule(toRunnable(runnable), delay, TimeUnit.MILLISECONDS);
	}

	@Override
	public ScheduledFuture<?> schedule(Runnable runnable, long delay, long repeat) {
		return executor.scheduleAtFixedRate(toRunnable(runnable), delay, repeat, TimeUnit.MILLISECONDS);
	}

	@Override
	public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
		Gdx.app.error("ServerTimer", "Could not execute task: " + runnable);
	}

	public void shutdown() {
		executor.shutdown();
	}

	@Override
	public void uncaughtException(Thread thread, Throwable exception) {
		Gdx.app.error("ServerTimer", "Thread on Timer " + name + " encountered exception!", exception);
	}

	private Runnable toRunnable(Runnable runnable) {
		return () -> {
			try {
				runnable.run();
			} catch (Throwable throwable) {
				Gdx.app.error("ServerTimer", "Exception on " + runnable + " on thread " + name + "!", throwable);
			}
		};
	}
}
