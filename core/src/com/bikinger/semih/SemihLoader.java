package com.bikinger.semih;

import com.badlogic.gdx.graphics.Color;
import com.one2b3.endcycle.core.load.DefaultLoader;
import com.one2b3.endcycle.core.platform.GamePlatform;
import com.one2b3.endcycle.engine.audio.music.MusicHandler;
import com.one2b3.endcycle.engine.audio.sound.SoundManager;
import com.one2b3.endcycle.engine.graphics.data.DrawableLoader;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.features.theme.ActiveTheme;

public class SemihLoader extends DefaultLoader {

	@Override
	public GamePlatform createPlatform() {
		return new SemihPlatform();
	}

	@Override
	public GameScreen createOpeningScreen() {
		return new SemihScreen();
	}

	@Override
	protected void loadGame() {
		SoundManager.defaultVolume = 1.0F;
		MusicHandler.instance.setDefaultVolume(1.0F);
		ActiveTheme.button.set(DrawableLoader.getImage(0, 66));
		ActiveTheme.bigMessage.set(DrawableLoader.getImage(0, 66));
		ActiveTheme.container.set(DrawableLoader.getImage(0, 66));
		ActiveTheme.defaultColor.set(Color.SKY);
		ActiveTheme.selectColor.set(Color.PURPLE);
		ActiveTheme.menuColor.set(Color.LIGHT_GRAY);
		ActiveTheme.disabledColor.set(Color.LIGHT_GRAY);
	}

}
