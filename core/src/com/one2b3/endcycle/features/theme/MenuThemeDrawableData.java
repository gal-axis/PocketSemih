package com.one2b3.endcycle.features.theme;

import com.badlogic.gdx.graphics.Color;
import com.one2b3.endcycle.engine.graphics.Drawables;
import com.one2b3.endcycle.engine.graphics.data.DrawableLoader;
import com.one2b3.endcycle.features.models.Description;
import com.one2b3.endcycle.features.models.Name;
import com.one2b3.endcycle.features.models.connect.ConnectsTo;
import com.one2b3.endcycle.utils.ID;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
@EqualsAndHashCode
public class MenuThemeDrawableData {

	@Description("The drawable used. It is strongly recommended to ensure they have the Ninepatch (9P) check")
	@ConnectsTo(DrawableLoader.class)
	ID drawable = null;
	@Name("")
	@Description("The color that will be painted over the drawable")
	Color color = new Color(Color.WHITE);
	@Description("An optional overlay that will be painted on top of menu elements")
	@ConnectsTo(DrawableLoader.class)
	ID overlay = null;
	@Name("")
	@Description("The color that will be painted over the overlay")
	Color colorOverlay = new Color(Color.WHITE);
	@Description("The padding to the content of this drawable")
	float left = 3.0F, right = 3.0F, top = 3.0F, bottom = 3.0F;

	public MenuThemeDrawableData(Drawables drawable) {
		this.drawable = drawable.getId();
	}

	public MenuThemeDrawableData(Drawables drawable, float top) {
		this.drawable = drawable.getId();
		this.top = top;
	}

}
