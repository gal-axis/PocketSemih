package com.one2b3.endcycle.engine.audio.sound;

import java.io.IOException;

import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.assets.Assets;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.input.InputListener;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.endcycle.engine.screens.ScreenObject;
import com.one2b3.endcycle.engine.ui.messages.GameScreenMessage;
import com.one2b3.endcycle.utils.bounded.BoundedFloat;

public class PlayingSoundMessage implements ScreenObject, InputListener {

	Array<String> sounds = new Array<>();
	Array<BoundedFloat> timers = new Array<>();

	@Override
	public void init(GameScreen screen) {
		Cardinal.getInput().addPermanentListener(this);
	}

	@Override
	public void update(float delta) {
		for (int i = timers.size - 1; i >= 0; i--) {
			BoundedFloat timer = timers.get(i);
			if (timer != null && !timer.increase(delta)) {
				sounds.removeIndex(i);
				timers.removeIndex(i);
			}
		}
	}

	public void add(SoundContainer container) {
		String id = container.path;
		int index = sounds.indexOf(id, false);
		if (index == -1) {
			sounds.add(id);
			timers.add(new BoundedFloat(4.0F));
		} else {
			timers.get(index).toMin();
		}
	}

	@Override
	public boolean remove() {
		for (int i = 0; i < sounds.size; i++) {
			if (sounds.get(i) != null) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void dispose() {
		Cardinal.getInput().removePermanentListener(this);
	}

	@Override
	public byte getLayer() {
		return Layers.LAYER_MASTER_FILTER;
	}

	@Override
	public float getComparisonKey() {
		return GameScreenMessage.getCurrentComparisonKey();
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		Painter painter = Painter.on(batch).at(0.0F, 5).font(GameFonts.SmallBorder).height(10).align(1, 0);
		for (int i = 0; i < sounds.size; i++) {
			if (sounds.get(i) == null) {
				continue;
			}
			BoundedFloat timer = timers.get(i);
			float anim = timer.getVal();
			painter.x(getWidth(i) * Math.min(1.0F, (anim > timer.getMax() * 0.5F ? timer.getMax() - anim : anim) * 10));
			painter.width(painter.x + 4).paintRectangle(0.0F, 0.0F, 0.0F, 0.8F).width(0.0F);
			painter.paint(sounds.get(i)).moveY(painter.height);
		}
	}

	@Override
	public boolean triggerTouch(TouchEvent event) {
		if (event.isScroll()) {
			return false;
		}
		float height = 10.0F;
		for (int i = 0; i < sounds.size; i++) {
			String sound = sounds.get(i);
			if (sound == null) {
				continue;
			}
			float width = getWidth(i);
			if (event.isIn(0, height * i, width, height)) {
				if (event.isPressed()) {
					try {
						Runtime.getRuntime().exec("explorer.exe /select,"
								+ Assets.getHandle(SoundManager.PATH + sound).file().getAbsolutePath());
					} catch (IOException e) {
					}
				}
				return true;
			}
		}
		return false;
	}

	public float getWidth(int i) {
		return GameFonts.SmallBorder.getCache(sounds.get(i)).getWidth();
	}

}
