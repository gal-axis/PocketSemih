package com.one2b3.endcycle.engine.fonts;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.one2b3.endcycle.engine.drawing.Paintable;
import com.one2b3.endcycle.engine.drawing.Painter;

public interface FontCache extends Paintable {

	FontCache Empty = new FontCache() {
		@Override
		public void paint(Painter painter) {
		}

		@Override
		public float getWidth() {
			return 0;
		}

		@Override
		public float getHeight() {
			return 0;
		}

		@Override
		public void setColor(Color color) {
		}

		@Override
		public char charAt(int index) {
			return 0;
		}

		@Override
		public int length() {
			return 0;
		}

		@Override
		public int getIndex(float x, float y) {
			return 0;
		}

		@Override
		public Rectangle getPosition(int index) {
			return null;
		}

		@Override
		public void paint(Painter painter, int from, int to) {
		}

	};

	float getWidth();

	float getHeight();

	void setColor(Color color);

	char charAt(int index);

	int length();

	public int getIndex(float x, float y);

	public Rectangle getPosition(int index);

	default void paint(Painter painter, int from, int to) {
		paint(painter);
	}

}
