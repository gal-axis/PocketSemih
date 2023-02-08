package com.one2b3.endcycle.core.platform;

public interface AdRewardAction {

	void onRewardEarned();

	default void onRewardOpened() {
	}

	default void onRewardError() {
	}

	default void onRewardClosed() {
	}
}
