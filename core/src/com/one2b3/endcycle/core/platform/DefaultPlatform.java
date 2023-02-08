package com.one2b3.endcycle.core.platform;

import javax.swing.filechooser.FileSystemView;

import com.badlogic.gdx.Gdx;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.engine.language.Unlocalize;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.ui.messages.ConfirmMessage;
import com.one2b3.endcycle.screens.menus.elements.buttons.MenuButton;

public abstract class DefaultPlatform implements GamePlatform {

	@Override
	public boolean isLite() {
		return false;
	}

	@Override
	public void showRewardVideo(AdRewardAction action) {
		action.onRewardOpened();
		Cardinal.game.getGameScreen().addObject(new ConfirmMessage(Unlocalize.get("This is a reward!"), a -> {
			action.onRewardEarned();
			action.onRewardClosed();
			return true;
		}));
	}

	@Override
	public void buyFull() {
		Gdx.app.getNet().openURI("http://bit.ly/2SbqaTk");
	}

	@Override
	public MenuButton createLoginButton() {
		return null;
	}

	@Override
	public MenuButton createAchievementButton() {
		return null;
	}

	@Override
	public boolean isLoggedIn() {
		return true;
	}

	@Override
	public boolean canSwitchAccount() {
		return false;
	}

	@Override
	public void login() {
	}

	@Override
	public void logout() {
	}

	@Override
	public void quit(GameScreen screen) {
		Gdx.app.exit();
	}

	@Override
	public boolean isSubscribed() {
		return true;
	}

	@Override
	public boolean createSession(boolean force) {
		return false;
	}

	@Override
	public String getUsername() {
		return System.getProperty("user.name");
	}

	@Override
	public String getDataRoot() {
		try {
			return FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/" + getGameId() + "/";
		} catch (Throwable t) {
			return System.getProperty("user.dir") + "/data/";
		}
	}

}
