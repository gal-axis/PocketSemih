package com.one2b3.endcycle.engine.graphics;

import com.badlogic.gdx.graphics.Color;
import com.one2b3.endcycle.engine.drawing.Paintable;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.graphics.data.DrawableData;
import com.one2b3.endcycle.engine.graphics.data.DrawableLoader;
import com.one2b3.endcycle.engine.proguard.KeepClass;
import com.one2b3.endcycle.features.models.Grow;
import com.one2b3.endcycle.features.models.Name;
import com.one2b3.endcycle.features.models.Shrink;
import com.one2b3.endcycle.features.models.connect.ConnectsTo;
import com.one2b3.endcycle.features.models.primitives.Percent;
import com.one2b3.endcycle.utils.ID;
import com.one2b3.endcycle.utils.objects.Named;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@KeepClass
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
@EqualsAndHashCode
@Shrink
public class DrawableId implements Named, Paintable {

	@Name("")
	@ConnectsTo(DrawableLoader.class)
	@Grow
	ID drawable = null;
	@Name("")
	Color color = new Color(Color.WHITE);
	@Name("Scale: X")
	@Percent
	float scaleX = 1.0F;
	@Name("Y")
	@Percent
	float scaleY = 1.0F;

	public DrawableId(Drawable drawable) {
		this.drawable = drawable.getId();
		this.scaleX = drawable.scaleX;
		this.scaleY = drawable.scaleY;
		this.color = drawable.color;
	}

	public DrawableId(Drawables drawable) {
		this.drawable = drawable.getId();
	}

	public Drawable create() {
		if (drawable == null) {
			return null;
		}
		Drawable d = new Drawable();
		set(d);
		return d;
	}

	public void set(Drawable d) {
		if (d != null) {
			d.set(DrawableLoader.get().getImage(drawable));
			d.scaleX = scaleX;
			d.scaleY = scaleY;
			d.setColor(color);
		}
	}

	public DrawableImage getImage() {
		return DrawableLoader.get().getImage(drawable);
	}

	@Override
	public void paint(Painter parameters) {
		DrawableImage image = getImage();
		if (image != null) {
			image.paint(parameters);
		}
	}

	@Override
	public String getName() {
		DrawableData data = DrawableLoader.get().getData(drawable);
		return (data == null ? null : data.name);
	}
}
