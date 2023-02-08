package com.one2b3.endcycle.engine.screens.faders;

import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.screens.GameScreen;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC)
public class ScreenFader extends GameScreen implements RenderFader {
	public static boolean active;

	GameScreen screenOut, screenIn;

	FadeType type;

	FadeProcessor processor;

	@Accessors(chain = true)
	@Setter
	boolean disposes, inits = true, changesBefore = true;
	boolean specialDraw;

	public ScreenFader(GameScreen in, FadeType fadingType) {
		this(null, in, fadingType, true);
	}

	public ScreenFader(GameScreen out, GameScreen in, FadeType fadingType) {
		this(out, in, fadingType, true);
	}

	public ScreenFader(GameScreen out, GameScreen in, FadeType fadingType, boolean disposes) {
		setOutScreen(out);
		setInScreen(in);
		this.type = fadingType;
		processor = type.getProcessor();
		setDisposes(disposes);
	}

	@Override
	public void setPreviousScreen(GameScreen before) {
		if (screenIn != null) {
			screenIn.setPreviousScreen(before);
		}
	}

	public void setOutScreen(GameScreen out) {
		this.screenOut = out;
	}

	public void setInScreen(GameScreen in) {
		this.screenIn = in;
	}

	public GameScreen getInScreen() {
		return screenIn;
	}

	@Override
	public void updateOld(float delta) {
		if (screenOut != null) {
			screenOut.render(delta);
		}
	}

	@Override
	public void disposeOld() {
		if (screenOut != null && disposes) {
			screenOut.dispose();
		}
	}

	@Override
	public void updateNew(float delta) {
		if (screenIn != null) {
			screenIn.render(delta);
		}
	}

	@Override
	public void initNew() {
		if (screenIn != null && inits) {
			screenIn.init();
			screenIn.resize(Cardinal.getWidth(), Cardinal.getHeight());
		}
	}

	@Override
	public GameScreen get() {
		return screenIn;
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void init() {
	}

	@Override
	public void show() {
		super.show();
		if (changesBefore && screenIn != null) {
			screenIn.setPreviousScreen(screenOut);
		}
		processor.setFader(this);
		processor.start();
		active = true;
	}

	@Override
	public void hide() {
		super.hide();
		active = false;
		processor.dispose();
	}

	@Override
	public void update(float delta) {
		processor.update(delta);
		if (processor.done()) {
			changeTo(screenIn, false);
			dispose();
		}
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		specialDraw = false;
		processor.draw(batch);
	}

	@Override
	public void drawSpecial(CustomSpriteBatch batch) {
		specialDraw = true;
		processor.draw(batch);
	}

	@Override
	public void drawOld(CustomSpriteBatch batch) {
		if (screenOut != null) {
			if (specialDraw) {
				screenOut.drawSpecial(batch);
			} else {
				screenOut.draw(batch);
			}
		}
	}

	@Override
	public boolean isLoading() {
		return super.isLoading();
	}

	@Override
	public void drawOld(CustomSpriteBatch batch, float xOfs, float yOfs) {
		if (screenOut != null) {
			screenOut.draw(batch, xOfs, yOfs);
		}
	}

	@Override
	public void drawNew(CustomSpriteBatch batch) {
		if (screenIn != null) {
			if (specialDraw) {
				screenIn.drawSpecial(batch);
			} else {
				screenIn.draw(batch);
			}
		}
	}

	@Override
	public void drawNew(CustomSpriteBatch batch, float xOfs, float yOfs) {
		if (screenIn != null) {
			screenIn.draw(batch, xOfs, yOfs);
		}
	}
}
