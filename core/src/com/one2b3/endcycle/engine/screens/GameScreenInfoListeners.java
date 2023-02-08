package com.one2b3.endcycle.engine.screens;

import com.badlogic.gdx.utils.Array;

public class GameScreenInfoListeners {

	GameScreenInfo info = new GameScreenInfo();
	Array<GameScreenInfoListener> listeners = new Array<>();

	public void add(GameScreenInfoListener listener) {
		listeners.add(listener);
	}

	public void update(GameScreen screen) {
		if (screen != null && screen.info != null && listeners.size > 0 && !info.equals(screen.info)) {
			info.set(screen.info);
			for (int i = 0; i < listeners.size; i++) {
				listeners.get(i).infoChanged(screen);
			}
		}
	}
}
