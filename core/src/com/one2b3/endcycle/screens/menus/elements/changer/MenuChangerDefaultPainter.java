package com.one2b3.endcycle.screens.menus.elements.changer;

import com.one2b3.endcycle.engine.drawing.ObjectPainter;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.FontCache;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.language.LocalizedMessage;
import com.one2b3.endcycle.engine.language.Unlocalize;
import com.one2b3.endcycle.utils.objects.DataName;
import com.one2b3.endcycle.utils.objects.Named;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MenuChangerDefaultPainter<T> implements ObjectPainter<T> {

	LocalizedMessage none = Unlocalize.get("None");

	@Override
	public void paint(T object, Painter parameters) {
		float width = parameters.width - 34.0F;
		Painter params = parameters.center();
		String name = null;
		if (object instanceof Named) {
			name = ((Named) object).getName();
		} else if (object instanceof Enum<?>) {
			name = ((Enum<?>) object).name();
		} else if (object instanceof DataName) {
			name = ((DataName) object).getDataName();
		} else if (object != null) {
			name = object.toString();
		} else {
			name = none.format();
		}
		FontCache cache = (params.font == null ? GameFonts.Text : params.font).getCache(name);
		params.xScale(Math.min(1.0F, width / cache.getWidth())).paint(cache);
	}

}
