package com.one2b3.endcycle.engine.graphics.data;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.engine.graphics.DrawableImage;
import com.one2b3.endcycle.engine.proguard.KeepClass;
import com.one2b3.endcycle.features.models.Description;
import com.one2b3.endcycle.features.models.Iconable;
import com.one2b3.endcycle.utils.ID;
import com.one2b3.endcycle.utils.objects.DataName;
import com.one2b3.endcycle.utils.objects.Named;
import com.one2b3.modding.diff.Catalog;
import com.one2b3.modding.diff.Moddable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@KeepClass
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
@Description("An image or animation that can be rendered by the game")
@Getter
@Catalog(DrawableLoader.class)
public final class DrawableData extends Moddable implements Named, DataName, DrawableDataProvider, Iconable {

	String name = "Unnamed Drawable";
	@Setter
	double speed = 1.0;
	@Setter
	PlayMode playMode = PlayMode.NORMAL;
	List<DrawableFrameData> frames = new ArrayList<>();

	@Override
	public String getDataName() {
		return ID.combine(id, name);
	}

	@Override
	public void drawIcon(Batch batch, float x, float y, float width, float height) {
		DrawableImage image = DrawableLoader.get().getImage(id);
		if (image != null) {
			if (image.getNinePatch() != null && name.contains("9")) {
				image.drawNinePatch(batch, x, y, width, height);
			} else {
				float scale = Math.min(width / image.getWidth(), height / image.getHeight());
				image.draw(batch, x + (width - image.getWidth() * scale) * 0.5F, y + (height - image.getHeight() * scale) * 0.5F, scale,
						scale, Cardinal.TIME_ACTIVE % image.getAnimationDuration());
			}
		}
	}

}
