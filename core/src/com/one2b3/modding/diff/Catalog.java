package com.one2b3.modding.diff;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.one2b3.endcycle.features.models.connect.ModdableConnector;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Catalog {

	Class<? extends ModdableConnector<?>> value();

}
