package com.one2b3.endcycle.engine.drawing.processors;

import java.util.ArrayList;
import java.util.List;

import com.one2b3.endcycle.engine.drawing.processors.battle.DownscaleRenderProcessor;
import com.one2b3.endcycle.engine.drawing.processors.battle.ExperimentalRenderProcessor;
import com.one2b3.endcycle.engine.drawing.processors.battle.TowerRenderProcessor;
import com.one2b3.endcycle.features.models.connect.Connector;

public class RenderProcessors implements Connector<RenderProcessorProvider, String> {

	public static final List<RenderProcessorProvider> processors = new ArrayList<>();

	static {
		add(new RenderProcessorProvider("Downscale", DownscaleRenderProcessor::new));
		add(new RenderProcessorProvider("Tower", TowerRenderProcessor::new));
		add(new RenderProcessorProvider("Experimental", ExperimentalRenderProcessor::new));
	}

	public static void add(RenderProcessorProvider provider) {
		processors.add(provider);
	}

	public static void remove(RenderProcessorProvider provider) {
		processors.remove(provider);
	}

	public static RenderProcessorProvider get(String id) {
		for (int i = 0; i < processors.size(); i++) {
			RenderProcessorProvider skill = processors.get(i);
			if (skill.id.equals(id)) {
				return skill;
			}
		}
		return null;
	}

	@Override
	public String getName() {
		return "Render Processor";
	}

	@Override
	public List<RenderProcessorProvider> getValues() {
		return RenderProcessors.processors;
	}

	@Override
	public String getValue(RenderProcessorProvider object) {
		return object.id;
	}
}
