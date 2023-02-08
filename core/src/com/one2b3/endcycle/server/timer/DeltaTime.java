package com.one2b3.endcycle.server.timer;

public class DeltaTime {

	long lastUpdate;

	public DeltaTime() {
	}

	public void updateTime() {
		updateTime(System.currentTimeMillis());
	}

	private void updateTime(long time) {
		this.lastUpdate = time;
	}

	public float tick() {
		long time = System.currentTimeMillis();
		long interval = time - lastUpdate;
		updateTime(time);
		return interval * 0.001F;
	}

}
