package com.one2b3.endcycle.utils.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public class FieldUtils {

	public static <T extends Annotation> T getDeclaredAnnotation(AnnotatedElement type, Class<T> annotationType) {
		Annotation[] annotations = type.getDeclaredAnnotations();
		if (annotations == null) {
			return null;
		}
		for (int i = annotations.length - 1; i >= 0; i--) {
			if (annotations[i].annotationType().equals(annotationType)) {
				return annotationType.cast(annotations[i]);
			}
		}
		return null;
	}

}
