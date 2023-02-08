package com.one2b3.endcycle.features.models;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Previewable {

	Class<? extends PreviewDrawable> value();

	int rows() default 0;

}
