package com.one2b3.endcycle.core.load;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.assets.GameLoader;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.screens.menus.Colors;
import com.one2b3.endcycle.utils.bounded.BoundedFloat;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoadingScreen extends GameScreen {

	final BoundedFloat increaseTimer = new BoundedFloat(-3.0F, 1.0F);

	final Cardinal cardinal;
	final GameLoader loader;

	String loadMessage;
	boolean done;

	@Override
	public void init() {
		done = false;
		setMessage("Loading...");
		loader.reload(false);
	}

	@Override
	public void update(float delta) {
		updateLoading(delta);
		if (!increaseTimer.increase(delta)) {
			increaseTimer.toMin();
		}
	}

	protected void updateLoading(float delta) {
		if (!done && loader.getProgress() == 1.0F) {
			finishBooting();
		}
	}

	public void setMessage(String message) {
		loadMessage = message;
	}

	public void finishBooting() {
		if (!done) {
			done = true;
			System.gc();
			cardinal.start();
		}
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		ScreenUtils.clear(Color.BLACK);
		super.draw(batch, xOfs, yOfs);
		float x = Cardinal.getWidth() - 60;
		float y = 20;
		drawArrows(batch, (increaseTimer.getVal() + 0.6F) / increaseTimer.getMax(), y);
		drawLoading(batch, x, y, 0.0F);
		drawLoading(batch, x, y, Math.max(0, increaseTimer.getVal()));
	}

	private void drawLoading(CustomSpriteBatch batch, float x, float y, float alpha) {
		Painter.on(batch).at(x, y).xScale(alpha * 0.2F + 1.0F).align(0).color(Colors.rainbow).alpha(1.0F - alpha)
				.font(GameFonts.Monospace).paint(loadMessage);
	}

	private void drawArrows(CustomSpriteBatch batch, float pos, float y) {
		float x = (pos * Cardinal.getWidth());
		drawArrow(batch, x, y, 1.0F, 0.1F, 0.1F);
		drawArrow(batch, x + 20, y, 0.8F, 0.6F, 0.1F);
		drawArrow(batch, x + 40, y, 0.1F, 1.0F, 0.5F);
		drawArrow(batch, x + 60, y, 0.1F, 0.7F, 1.0F);
	}

	private void drawArrow(CustomSpriteBatch batch, float x, float y, float r, float g, float b) {
		Painter.on(batch).at(x, y).align(0).color(r, g, b).font(GameFonts.Tiny).paint(">>");
	}

}
