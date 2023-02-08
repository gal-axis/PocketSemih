package com.one2b3.endcycle.engine.drawing;

public interface Paintable {

	void paint(Painter painter);

	default float getWidth() {
		return 0.0F;
	}

	default float getHeight() {
		return 0.0F;
	}

	static float get(float object, float orElse) {
		return (Float.isNaN(object) ? orElse : object);
	}

	static double get(double object, double orElse) {
		return (Double.isNaN(object) ? orElse : object);
	}

	static int get(int object, int orElse) {
		return (object == -1 ? orElse : object);
	}

	static <K> K get(K object, K orElse) {
		return (object == null ? orElse : object);
	}
}
