package com.one2b3.endcycle.features.models.connect;

import java.util.function.Supplier;

import com.one2b3.modding.diff.Moddable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ModdableCreator<T extends Moddable> {

	public final String name;
	public final ModdableConnector<T> connector;

	final Supplier<? extends T> supplier;

	public T create() {
		return supplier.get();
	}
}
