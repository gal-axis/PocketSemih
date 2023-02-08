package com.one2b3.endcycle.engine.drawing.processors;

import com.one2b3.endcycle.engine.screens.RenderProcessor;
import com.one2b3.endcycle.utils.objects.DataName;
import com.one2b3.utils.java.Supplier;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
public final class RenderProcessorProvider implements DataName {

	final String name;
	final String id;
	final Supplier<RenderProcessor> processor;

	public RenderProcessorProvider(String name, Supplier<RenderProcessor> processor) {
		this.name = id = name;
		this.processor = processor;
	}

	public RenderProcessor create() {
		return processor.get();
	}

	@Override
	public String getDataName() {
		return name;
	}
}