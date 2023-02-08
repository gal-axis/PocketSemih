package com.one2b3.endcycle.engine.drawing;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.one2b3.endcycle.engine.drawing.painters.*;
import com.one2b3.endcycle.engine.graphics.Drawables;
import com.one2b3.endcycle.utils.reflect.ClassFinder;

public final class ObjectPainterFactory {

	static Map<Class<?>, ObjectPainter<?>> painters;

	public static void loadPainters() {
		painters = new HashMap<>();
		painters.put(String.class, new StringPainter());
		painters.put(Drawables.class, new DrawablesPainter());
		painters.put(TextureRegion.class, new TextureRegionPainter());
		painters.put(Texture.class, new TexturePainter());
		painters.put(Animation.class, new AnimationPainter());
		painters.put(Object.class, new DefaultObjectPainter());
	}

	@SuppressWarnings("unchecked")
	public static <T> ObjectPainter<T> getPainter(Class<T> clazz) {
		return clazz == null ? null : (ObjectPainter<T>) painters.get(ClassFinder.findClass(painters.keySet(), clazz));
	}
}
