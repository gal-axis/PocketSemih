package com.bikinger.semih;

import com.one2b3.endcycle.core.load.DefaultLoader;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.core.platform.GamePlatform;
import com.one2b3.endcycle.engine.input.InputListener;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.engine.objects.forms.BatchRectangle;
import com.one2b3.endcycle.engine.objects.visuals.StringDisplay;
import com.one2b3.endcycle.engine.objects.visuals.StringDisplayAnimation;
import com.one2b3.endcycle.engine.objects.visuals.StringDisplayFactory;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.Layers;

public class Cardinal extends com.one2b3.endcycle.core.Cardinal {

	public Cardinal() {
		super(new DefaultLoader() {

			@Override
			public GamePlatform createPlatform() {
				return null;
			}

			@Override
			public GameScreen createOpeningScreen() {
				GameScreen screen = new GameScreen() {
					@Override
					public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
						batch.clear();
						super.draw(batch, xOfs, yOfs);
					}
				};
				screen.input.add(new InputListener() {
					@Override
					public boolean triggerTouch(TouchEvent event) {
						if (event.isPressed()) {
							BatchRectangle rectangle = new BatchRectangle(event.positionX - 15, event.positionY - 15,
									Layers.LAYER_1, 30, 30);
							screen.addObject(rectangle);
						} else if (event.isMoved()) {
							BatchRectangle rectangle = screen.getObject(BatchRectangle.class);
							if (rectangle != null) {
								rectangle.x = event.positionX - 15;
								rectangle.y = event.positionY - 15;
							}
							StringDisplay display = StringDisplayFactory.spawn("Hallo Semih!", event.positionX,
									event.positionY);
							display.fontScale = 2.0F;
							display.animation = StringDisplayAnimation.BOUNCE;
							screen.addObject(display);
						} else if (event.isReleased()) {
							BatchRectangle rectangle = screen.getObject(BatchRectangle.class);
							screen.removeObject(rectangle);
						}
						return false;
					}
				});
				return screen;
			}

			@Override
			protected void loadGame() {
			}
		}, null, 416, 128, true);
	}

}
