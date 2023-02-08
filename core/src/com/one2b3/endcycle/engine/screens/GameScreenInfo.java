package com.one2b3.endcycle.engine.screens;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC)
@EqualsAndHashCode
public class GameScreenInfo {

	String state, details;

	long startTimestamp, endTimestamp;

	String largeImageKey = "icon", largeImageText;
	String smallImageKey, smallImageText;

	String partyId;
	int partySize, partyMax;

	String spectateSecret;
	String joinSecret;

	public void set(GameScreenInfo info) {
		state = info.state;
		details = info.details;
		startTimestamp = info.startTimestamp;
		endTimestamp = info.endTimestamp;
		largeImageKey = info.largeImageKey;
		largeImageText = info.largeImageText;
		smallImageKey = info.smallImageKey;
		smallImageText = info.smallImageText;
		partyId = info.partyId;
		partySize = info.partySize;
		partyMax = info.partyMax;
		spectateSecret = info.spectateSecret;
		joinSecret = info.joinSecret;
	}
}
