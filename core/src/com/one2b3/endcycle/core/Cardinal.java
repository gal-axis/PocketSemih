package com.one2b3.endcycle.core;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.one2b3.audio.AudioManager;
import com.one2b3.audio.AudioUpdateThread;
import com.one2b3.audio.Music;
import com.one2b3.endcycle.core.errors.ErrorReportLogger;
import com.one2b3.endcycle.core.load.LoadingScreen;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.core.painting.GamePainter;
import com.one2b3.endcycle.core.painting.painters.ScaledPainter;
import com.one2b3.endcycle.core.platform.GamePlatform;
import com.one2b3.endcycle.engine.EngineProperties;
import com.one2b3.endcycle.engine.EngineProperties.Platform;
import com.one2b3.endcycle.engine.assets.GameLoader;
import com.one2b3.endcycle.engine.audio.music.MusicHandler;
import com.one2b3.endcycle.engine.files.FileChooser;
import com.one2b3.endcycle.engine.input.InputManager;
import com.one2b3.endcycle.engine.input.handlers.Restarter;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.GameScreenInfoListeners;
import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.endcycle.engine.screens.faders.FadeType;
import com.one2b3.endcycle.screens.menus.Colors;

import lombok.Getter;

public abstract class Cardinal implements ApplicationListener {

	public static boolean DEBUGGING = false;

	public static double TIME_ACTIVE = 0.0F;

	public static Cardinal game;

	public static int getWidth() {
		return game == null ? Resolutions.L_WIDTH : game.width;
	}

	public static int getHeight() {
		return game == null ? Resolutions.L_HEIGHT : game.height;
	}

	public static boolean isPortable() {
		return game != null && game.isPortable(Gdx.app.getType());
	}

	public static boolean isPhone() {
		return (Gdx.app.getType() == ApplicationType.iOS || Gdx.app.getType() == ApplicationType.Android);
	}

	public static void setSize(int width, int height) {
		game.width = width;
		game.height = height;
	}

	public static float getTime() {
		return (float) TIME_ACTIVE;
	}

	public static int getScale() {
		return game.painter.getScale();
	}

	public static Music newMusic(FileHandle handle) {
		return game == null ? null : game.createMusic(handle);
	}

	public static Sound newSound(FileHandle handle) {
		return game == null ? null : game.createSound(handle);
	}

	public static AudioManager getAudio() {
		return game.audioManager;
	}

	public static GamePlatform getPlatform() {
		return game.platform;
	}

	public static InputManager getInput() {
		return game == null ? null : game.input;
	}

	public String[] args;
	int width, height;

	public final GameLoader loader;

	protected CustomSpriteBatch batch;

	public GamePainter painter;
	AudioManager audioManager;
	public InputManager input;

	@Getter
	GameScreen screen;
	public GameScreenInfoListeners screenListeners;

	public GamePlatform platform;

	int refreshRate;

	public Cardinal(GameLoader loader, String[] args, int width, int height, boolean debug) {
		this.loader = loader;
		this.args = args;
		game = this;
		this.width = width;
		this.height = height;
		DEBUGGING = debug;
	}

	public boolean isPortable(ApplicationType type) {
		return type == ApplicationType.iOS || type == ApplicationType.Android;
	}

	public void setNotifications(boolean notifications) {
	}

	public FileChooser createChooser() {
		return null;
	}

	public void setRecord(int seconds, int fps, boolean special) {
	}

	public int getRecordSeconds() {
		return 0;
	}

	@Override
	public void create() {
		loadProperties();
		redirectErrors();

		screenListeners = new GameScreenInfoListeners();

		batch = new CustomSpriteBatch();
		painter = createPainter(batch);
		createInputHandler();

		audioManager = createAudioManager();
		platform = loader.createPlatform();

		loader.initialize();

		GameScreen screen = createLoadScreen();
		if (screen != null) {
			screen.init();
			setScreen(screen);
		}
	}

	public void loadProperties() {
		GameProperties.load();
	}

	public void reloadAssets(boolean mods) {
		loader.reload(mods);
	}

	public void redirectErrors() {
		if (DEBUGGING || EngineProperties.PLATFORM == Platform.DEV) {
			Gdx.app.setLogLevel(Application.LOG_DEBUG);
		}
		Gdx.app.setApplicationLogger(createLogger());
	}

	public ApplicationLogger createLogger() {
		return new ErrorReportLogger(Gdx.app.getApplicationLogger(), false);
	}

	public void start() {
		screen.setScreen(loader.createOpeningScreen(), FadeType.FADE_TO_WHITE);
	}

	public AudioManager createAudioManager() {
		AudioManager manager = new AudioManager();
		new AudioUpdateThread(manager).start();
		return manager;
	}

	public final Music createMusic(FileHandle handle) {
		return audioManager.createMusic(handle);
	}

	public final Sound createSound(FileHandle handle) {
		return audioManager.createSound(handle);
	}

	protected void createInputHandler() {
		input = new InputManager(this);
		input.addPermanentListener(new Restarter());
	}

	protected GameScreen createLoadScreen() {
		return new LoadingScreen(this, loader);
	}

	protected GamePainter createPainter(CustomSpriteBatch batch) {
		return new ScaledPainter(batch, Resolutions.L_WIDTH, Resolutions.L_HEIGHT, Resolutions.L_WIDTH_MAX,
				Resolutions.L_HEIGHT);
	}

	public GameScreen getGameScreen() {
		return (screen == null ? null : screen.get());
	}

	public void setScreen(GameScreen screen) {
		if (this.screen != null) {
			try {
				this.screen.hide();
			} catch (Throwable t) {
				Gdx.app.error("Cardinal", "Error hiding screen!", t);
			}
			input.removePermanentListener(this.screen.input);
		}
		this.screen = screen;
		if (this.screen != null) {
			input.addPermanentListener(screen.input);
			try {
				this.screen.show();
			} catch (Throwable t) {
				Gdx.app.error("Cardinal", "Error showing screen!", t);
			}
			try {
				this.screen.resize(width, height);
			} catch (Throwable t) {
				Gdx.app.error("Cardinal", "Error resizing screen!", t);
			}
		}
	}

	@Override
	public void render() {
		loader.update();
		if (Cardinal.DEBUGGING) {
			renderGame();
		} else {
			try {
				renderGame();
			} catch (Throwable t) {
				Gdx.app.error("Cardinal", "Exception while rendering game!", t);
			}
		}
	}

	private void renderGame() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		if (refreshRate != 0.0F) {
			float r = 1.0F / Gdx.graphics.getFramesPerSecond();
			if (deltaTime > r) {
				deltaTime = r;
			} else {
				r = (1.0F / refreshRate);
				if (!isPhone()) {
					if (deltaTime / r < 1.035F && deltaTime / r > 0.999F) {
						deltaTime = r;
					}
				}
			}
		}
		update(deltaTime);
		try {
			draw();
		} catch (Throwable throwable) {
			Gdx.app.error("Cardinal", "Exception while drawing game!", throwable);
		}
		updateFps(deltaTime);
	}

	public void update(float delta) {
		TIME_ACTIVE += delta;
		Colors.rainbow.update();
		updateMusic(delta);
		if (screen != null) {
			try {
				screen.render(delta);
				Layers.global.updateObjects(delta);
				screenListeners.update(screen);
			} catch (Throwable throwable) {
				Gdx.app.error("Cardinal", "Exception while updating game!", throwable);
			}
		}
	}

	public void updateFps(float delta) {
	}

	public void draw() {
		painter.draw(screen);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	public void updateMusic(float delta) {
		if (MusicHandler.instance != null) {
			MusicHandler.instance.update(Gdx.graphics.getDeltaTime());
		}
	}

	@Override
	public void dispose() {
		if (screen != null) {
			screen.hide();
			screen.dispose();
		}
		loader.dispose();
	}

	@Override
	public void resize(int width, int height) {
		refreshRate = Gdx.graphics.getDisplayMode().refreshRate;
		Gdx.app.debug("Screen", "Screen size changed " + width + ":" + height);
		if (width != 0 && height != 0) {
			painter.resize(width, height);
			width = getWidth();
			height = getHeight();
			if (screen != null) {
				try {
					screen.resize(width, height);
				} catch (Throwable throwable) {
					Gdx.app.error("Cardinal", "Exception while resizing screen!", throwable);
				}
			}
		}
		Gdx.graphics.setVSync(true);
	}

	public void reloadTheme() {
	}

	public void action(Object object, DataAction action) {
	}

}
