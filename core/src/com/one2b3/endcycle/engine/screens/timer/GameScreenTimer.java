package com.one2b3.endcycle.engine.screens.timer;

import com.badlogic.gdx.utils.Array;

public class GameScreenTimer {

	public final Array<GameScreenTimerTask> tasks = new Array<>(GameScreenTimerTask.class);
	public float time;

	public GameScreenTimerTask schedule(Runnable task, float delay) {
		return schedule(new SimpleGameScreenTimerTask(task), delay, 0, 0);
	}

	public GameScreenTimerTask schedule(Runnable task) {
		return schedule(new SimpleGameScreenTimerTask(task), 0.0F, 0, 0);
	}

	public GameScreenTimerTask schedule(Runnable task, float delay, float interval) {
		return schedule(new SimpleGameScreenTimerTask(task), delay, interval, GameScreenTimerTask.FOREVER);
	}

	public GameScreenTimerTask schedule(Runnable task, float delay, float interval, int repeatCount) {
		return schedule(new SimpleGameScreenTimerTask(task), delay, interval, repeatCount);
	}

	public GameScreenTimerTask schedule(GameScreenTimerTask task, float delay) {
		return schedule(task, delay, 0, 0);
	}

	public GameScreenTimerTask schedule(GameScreenTimerTask task, float delay, float interval) {
		return schedule(task, delay, interval, GameScreenTimerTask.FOREVER);
	}

	public GameScreenTimerTask schedule(GameScreenTimerTask task, float delay, float interval, int repeatCount) {
		if (task != null) {
			task.init(delay, interval, repeatCount);
			tasks.add(task);
		}
		return task;
	}

	public boolean cancel(GameScreenTimerTask task) {
		if (tasks.removeValue(task, false)) {
			task.cancel();
			return true;
		}
		return false;
	}

	public boolean isEmpty() {
		return tasks.isEmpty();
	}

	public void clear() {
		for (int i = tasks.size - 1; i >= 0; i--) {
			tasks.removeIndex(i).cancel();
		}
	}

	public void update(float delta) {
		time += delta;
		for (int i = tasks.size - 1; i >= 0; i--) {
			if (i >= tasks.size) {
				continue;
			}
			GameScreenTimerTask task = tasks.get(i);
			task.update(delta);
			if (task.isOver()) {
				tasks.removeIndex(i);
			}
			task.execute();
		}
	}

	public void delay(float delay) {
		for (int i = tasks.size - 1; i >= 0; i--) {
			tasks.get(i).delay(delay);
		}
	}
}
