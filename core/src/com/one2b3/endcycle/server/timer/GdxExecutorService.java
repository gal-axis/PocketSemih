package com.one2b3.endcycle.server.timer;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

import com.badlogic.gdx.utils.Timer;

public class GdxExecutorService implements ExecutorService {

	@Override
	public void execute(Runnable command) {
		Timer.post(new GdxFuture<>(command));
	}

	@Override
	public void shutdown() {
	}

	@Override
	public List<Runnable> shutdownNow() {
		return null;
	}

	@Override
	public boolean isShutdown() {
		return false;
	}

	@Override
	public boolean isTerminated() {
		return false;
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return false;
	}

	@Override
	public <T> Future<T> submit(Callable<T> task) {
		return null;
	}

	@Override
	public <T> Future<T> submit(Runnable task, T result) {
		GdxFuture<T> future = new GdxFuture<>(task);
		Timer.post(future);
		return future;
	}

	@Override
	public Future<?> submit(Runnable task) {
		GdxFuture<?> future = new GdxFuture<>(task);
		Timer.post(future);
		return future;
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
		return null;
	}

	@Override
	public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
		return null;
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
		return null;
	}

	@Override
	public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		return null;
	}

}
