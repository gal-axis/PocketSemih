package com.one2b3.endcycle.engine.screens;

import java.util.Comparator;

import com.badlogic.gdx.utils.Array;
import com.one2b3.endcycle.engine.collections.FixedList;

public class RenderList extends FixedList<RenderLayerList> {

	int size = 0;

	public RenderList() {
		super(Layers.LAYER_MASTER_FILTER + 1);
		for (int i = 0; i < size(); i++) {
			set(i, new RenderLayerList());
		}
	}

	public void add(Array<? extends Renderable> objects) {
		if (objects == null) {
			return;
		}
		for (int i = 0; i < objects.size; i++) {
			Renderable object = objects.get(i);
			if (object != null) {
				byte layer = object.getLayer();
				get(layer).add(object);
				size++;
			}
		}
	}

	public void sort(byte layer) {
		RenderLayerList list = get(layer);
		if (list.notEmpty()) {
			list.sort(DRAW_SORTER);
		}
	}

	@Override
	public void clear() {
		for (int i = 0; i < size(); i++) {
			get(i).clear();
		}
		size = 0;
	}

	public static final Comparator<Renderable> DRAW_SORTER = (o1, o2) -> {
		if (o1.getLayer() == o2.getLayer()) {
			return Float.compare(o2.getComparisonKey(), o1.getComparisonKey());
		} else {
			return Integer.compare(o1.getLayer(), o2.getLayer());
		}
	};
}
