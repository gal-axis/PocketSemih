package com.bikinger.semih.clicker;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.graphics.data.DrawableLoader;
import com.one2b3.endcycle.engine.input.InputListener;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.engine.objects.visuals.StringDisplay;
import com.one2b3.endcycle.engine.objects.visuals.StringDisplayAnimation;
import com.one2b3.endcycle.engine.objects.visuals.StringDisplayFactory;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.GameScreenObject;
import com.one2b3.endcycle.utils.bounded.BoundedFloat;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SemihClicker extends GameScreenObject implements InputListener {

	final SemihPoints points;

	TextureRegion texture;
	BoundedFloat clickAnimation = new BoundedFloat(0.0F, 1.0F, 4.0F);

	@Override
	public void init(GameScreen screen) {
		super.init(screen);
		screen.input.add(this);
		texture = DrawableLoader.get().loadTexture("semih1.png");
	}

	@Override
	public void update(float delta) {
		clickAnimation.decrease(delta);
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		batch.clear();
		float scale = (float) Cardinal.getHeight() / texture.getRegionHeight();
		float scaleChange = -Interpolation.swingIn.apply(clickAnimation.getVal()) * 0.1F;
		Painter.on(batch).at(texture.getRegionWidth() * scale * 0.5F, 0).hAlign(0).scale(scale, scale + scaleChange)
				.paint(texture);
	}

	@Override
	public boolean triggerTouch(TouchEvent event) {
		if (event.isPressed()) {
			clickAnimation.toMax();
			StringDisplay spawn = StringDisplayFactory.spawn("+1 Banana", event.positionX, event.positionY);
			spawn.font = GameFonts.TitleBorder;
			spawn.animation = StringDisplayAnimation.BOUNCE;
			spawn.setCharacterSpeed(10.0F);
			screen.addObject(spawn);
			points.increase(1);
		}
		return InputListener.super.triggerTouch(event);
	}
}
