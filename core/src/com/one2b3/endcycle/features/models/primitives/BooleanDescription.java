package com.one2b3.endcycle.features.models.primitives;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BooleanDescription {

	String value() default "None";

	String onTrue() default "True";

	String onFalse() default "False";

}
