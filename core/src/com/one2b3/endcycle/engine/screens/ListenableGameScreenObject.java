package com.one2b3.endcycle.engine.screens;

import com.badlogic.gdx.utils.Array;

public abstract class ListenableGameScreenObject extends GameScreenObject {

	Array<Runnable> onInitialize, onDispose;

	public final void addInitializeListener(Runnable runnable) {
		if (runnable == null) {
			return;
		}
		if (onInitialize == null) {
			onInitialize = new Array<>(4);
		}
		onInitialize.add(runnable);
	}

	public final void removeInitializeListener(Runnable runnable) {
		if (onInitialize != null && runnable != null) {
			onInitialize.removeValue(runnable, true);
		}
	}

	public final void addDisposeListener(Runnable runnable) {
		if (runnable == null) {
			return;
		}
		if (onDispose == null) {
			onDispose = new Array<>(4);
		}
		onDispose.add(runnable);
	}

	public final void removeDisposeListener(Runnable runnable) {
		if (onDispose != null && runnable != null) {
			onDispose.removeValue(runnable, true);
		}
	}

	@Override
	public void init(GameScreen screen) {
		super.init(screen);
		if (onInitialize != null) {
			for (int i = onInitialize.size - 1; i >= 0; i--) {
				onInitialize.get(i).run();
			}
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		if (onDispose != null) {
			for (int i = onDispose.size - 1; i >= 0; i--) {
				onDispose.get(i).run();
			}
		}
	}
}