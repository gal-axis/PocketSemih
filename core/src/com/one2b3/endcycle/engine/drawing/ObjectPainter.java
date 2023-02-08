package com.one2b3.endcycle.engine.drawing;

public interface ObjectPainter<T> {

	void paint(T object, Painter painter);

	default float get(float object, float orElse) {
		return (Float.isNaN(object) ? orElse : object);
	}

	default double get(double object, double orElse) {
		return (Double.isNaN(object) ? orElse : object);
	}

	default int get(int object, int orElse) {
		return (object == -1 ? orElse : object);
	}

	default <K> K get(K object, K orElse) {
		return (object == null ? orElse : object);
	}
}
