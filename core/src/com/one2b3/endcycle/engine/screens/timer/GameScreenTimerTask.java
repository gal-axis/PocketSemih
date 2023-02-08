package com.one2b3.endcycle.engine.screens.timer;

public abstract class GameScreenTimerTask implements Runnable {

	public static final int CANCELLED = -1, FOREVER = -2;

	double executeTime;
	float interval;

	int repeatCount = GameScreenTimerTask.CANCELLED;
	boolean execute;

	public GameScreenTimerTask() {
	}

	protected final void init(float executeTime, float interval, int repeatCount) {
		if (this.repeatCount != CANCELLED) {
			throw new IllegalArgumentException("The same GameScreenTimerTask may not be scheduled twice.");
		}
		this.executeTime = executeTime;
		this.interval = interval;
		this.repeatCount = repeatCount;
	}

	protected void update(float delta) {
		this.executeTime -= delta;
		if (executeTime <= 0.0 && (repeatCount >= 0 || repeatCount == FOREVER)) {
			if (repeatCount != FOREVER) {
				repeatCount--;
			}
			if (repeatCount >= 0 || repeatCount == FOREVER) {
				executeTime += interval;
			}
			execute = true;
		}
	}

	protected final void execute() {
		if (isExecute()) {
			run();
			execute = false;
		}
	}

	protected boolean isExecute() {
		return execute;
	}

	protected boolean isOver() {
		return repeatCount == CANCELLED;
	}

	protected final void delay(float delay) {
		this.executeTime += delay;
	}

	@Override
	abstract public void run();

	public void cancel() {
		executeTime = 0;
		repeatCount = CANCELLED;
	}

	public boolean isScheduled() {
		return repeatCount != CANCELLED;
	}
}