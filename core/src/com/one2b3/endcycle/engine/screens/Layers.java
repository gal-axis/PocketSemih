package com.one2b3.endcycle.engine.screens;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.utils.ArraySet;

import lombok.Getter;

public class Layers extends ScreenObjectHandler<ScreenObject> {
	public static final byte LAYER_0 = 0, LAYER_1 = 1, LAYER_2 = 2, LAYER_3 = 3, LAYER_4 = 4,

			LAYER_5 = 5, LAYER_6 = 6, LAYER_7 = 7, LAYER_8 = 8, LAYER_9 = 9, LAYER_10 = 10, LAYER_11 = 11,

			LAYER_OBJECT_FILTER = 12,

			LAYER_HUD = 13, LAYER_MENU = 14, LAYER_MESSAGES = 15, LAYER_INTERFACE_FILTER = 16,

			LAYER_MASTER_FILTER = 17;

	public static final Layers global = new Layers(null);
	private static final Array<Throwable> EXCEPTIONS = new Array<>();

	private static void printExceptions() {
		while (EXCEPTIONS.notEmpty()) {
			Gdx.app.error("Layers", "Exception occurred!", EXCEPTIONS.pop());
		}
	}

	@Getter
	public final ArraySet<ScreenObject> objects;
	public final Array<RenderProcessor> renderProcessors;
	public final Array<UpdateCondition> updateConditions;

	final RenderList toDraw;

	List<ScreenObject> toUpdate2;

	public Layers(GameScreen parent) {
		super(parent);
		objects = new ArraySet<>(true, 30, ScreenObject.class);
		toDraw = new RenderList();
		updateConditions = new Array<>(false, 2, UpdateCondition.class);
		renderProcessors = new Array<>(false, 3, RenderProcessor.class);
	}

	public void addRenderProcessor(RenderProcessor renderProcessor) {
		if (renderProcessor != null) {
			renderProcessors.add(renderProcessor);
			renderProcessor.init(parent);
		}
	}

	public void removeRenderProcessor(RenderProcessor renderProcessor) {
		if (renderProcessors.removeValue(renderProcessor, true)) {
			renderProcessor.dispose();
		}
	}

	@Override
	public void updateObjects(float delta) {
		super.updateObjects(delta);
		EXCEPTIONS.clear();
		for (int i = renderProcessors.size - 1; i >= 0; i--) {
			try {
				renderProcessors.get(i).updateRender(delta);
			} catch (Throwable t) {
				EXCEPTIONS.add(t);
			}
		}
		printExceptions();
	}

	@Override
	public boolean isUpdatable(ScreenObject object) {
		if (updateConditions.size > 0) {
			for (int c = updateConditions.size - 1; c >= 0; c--) {
				if (!updateConditions.get(c).isUpdatable(object)) {
					return false;
				}
			}
		}
		return true;
	}

	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		toDraw.clear();
		toDraw.add(objects);
		if (parent != null && parent.isCurrentScreen()) {
			toDraw.add(global.objects);
		}
		EXCEPTIONS.clear();
		if (toDraw.size > 0) {
			for (int i = 0; i < toDraw.size(); i++) {
				byte layer = (byte) i;
				try {
					startRendering(batch, layer, xOfs, yOfs);
				} catch (Throwable t) {
					EXCEPTIONS.add(t);
				}
				RenderLayerList list = toDraw.get(i);
				for (int o = 0; o < list.size; o++) {
					try {
						list.get(o).draw(batch, xOfs, yOfs);
					} catch (Throwable t) {
						EXCEPTIONS.add(t);
					}
				}
				try {
					stopRendering(batch, layer);
				} catch (Throwable t) {
					EXCEPTIONS.add(t);
				}
			}
		}
		printExceptions();
	}

	public boolean isHidden(Renderable renderable) {
		return renderable.isHidden();
	}

	public void startRendering(CustomSpriteBatch batch, byte layer, float xOfs, float yOfs) {
		if (layer != -1) {
			for (int i = 0; i < renderProcessors.size; i++) {
				renderProcessors.get(i).startRendering(batch, toDraw, layer, xOfs, yOfs);
			}
			toDraw.sort(layer);
		}
	}

	public void stopRendering(CustomSpriteBatch batch, byte layer) {
		if (layer != -1) {
			for (int i = 0; i < renderProcessors.size; i++) {
				renderProcessors.get(i).stopRendering(batch, layer);
			}
		}
	}

}
