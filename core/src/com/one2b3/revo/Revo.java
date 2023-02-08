package com.one2b3.revo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import com.badlogic.gdx.Gdx;
import com.one2b3.endcycle.features.models.Name;
import com.one2b3.endcycle.features.models.connect.ObjectCreateProvider;
import com.one2b3.endcycle.utils.StringUtil;
import com.one2b3.endcycle.utils.reflect.ClassUtils;
import com.one2b3.utils.java.Supplier;

public class Revo {

	@SuppressWarnings("unchecked")
	public static <T> T cast(Object object, Class<T> type) {
		if (object == null) {
			return null;
		}
		return type.isInstance(object) ? (T) object : null;
	}

	public static <T> T cast(Object object, Class<T> type, T defaultValue) {
		T cast = cast(object, type);
		return cast == null ? defaultValue : cast;
	}

	public static <T> Supplier<T> supplier(Object type, Class<T> javaType) {
		return () -> create(type, javaType);
	}

	public static <T> T create(Object type, Class<T> javaType) {
		if (type instanceof Class) {
			Class<?> classType = (Class<?>) type;
			Object primitive = ClassUtils.getDefaultPrimitive(classType);
			if (primitive != null) {
				return javaType.cast(primitive);
			}
			try {
				return javaType.cast(classType.newInstance());
			} catch (Exception e) {
				Gdx.app.error("Revo", "Error creating instance of " + classType, e);
			}
		}
		return null;
	}

	public static Object create(Type type) {
		if (type instanceof Class) {
			Class<?> classType = (Class<?>) type;
			Object primitive = ClassUtils.getDefaultPrimitive(classType);
			if (primitive != null) {
				return primitive;
			}
			try {
				return classType.newInstance();
			} catch (Exception e) {
				Gdx.app.error("Revo", "Error creating instance of " + classType, e);
			}
		}
		return null;
	}

	public static Type getType(Object object) {
		if (object == null) {
			return null;
		}
		return object.getClass();
	}

	public static boolean hasAnnotation(Type declaring, Class<? extends Annotation> annotation) {
		return getAnnotation(declaring, annotation) != null;
	}

	public static String getName(Object entry, ObjectCreateProvider provider) {
		if (entry == null) {
			return null;
		}
		String className = entry.getClass().getSimpleName();
		Name annotation = entry.getClass().getAnnotation(Name.class);
		if (annotation != null) {
			return annotation.value();
		}
		return StringUtil.prettyName(provider.shortenName(className));
	}

	public static <T extends Annotation> T getAnnotation(Type declaring, Class<T> annotation) {
		if (declaring instanceof Class) {
			Class<?> clazz = (Class<?>) declaring;
			return clazz.getAnnotation(annotation);
		}
		return null;
	}
}
