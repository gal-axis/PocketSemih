package com.one2b3.endcycle.features.models;

import java.lang.reflect.Type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class TestParam {

	public final String name;
	public final Type type;
	public final Object defaultValue;

}
