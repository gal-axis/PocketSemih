package com.one2b3.endcycle.features.revo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.one2b3.endcycle.core.Cardinal;

public final class Screen {

	private Screen() {
	}

	public static int width() {
		return Cardinal.getWidth();
	}

	public static int height() {
		return Cardinal.getHeight();
	}

	public static GL20 getGL() {
		return Gdx.gl;
	}

	public static GL30 getGL30() {
		return Gdx.gl30;
	}
}
