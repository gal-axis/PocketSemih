package com.one2b3.endcycle.core.platform;

import com.badlogic.gdx.Input.Keys;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.screens.menus.elements.buttons.MenuButton;

public interface GamePlatform {

	String getGameId();

	boolean isLite();

	void showRewardVideo(AdRewardAction action);

	void buyFull();

	MenuButton createLoginButton();

	MenuButton createAchievementButton();

	default MenuButton createModButton() {
		return null;
	}

	default String translateKey(int keycode) {
		return Keys.toString(keycode);
	}

	default int translateKeyCode(int keycode) {
		return keycode;
	}

	boolean isLoggedIn();

	boolean canSwitchAccount();

	void login();

	void logout();

	void quit(GameScreen screen);

	boolean isSubscribed();

	boolean createSession(boolean force);

	String getUsername();

	String getDataRoot();
}
