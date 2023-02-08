package com.one2b3.endcycle.features.background;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.one2b3.endcycle.engine.graphics.DrawableId;
import com.one2b3.endcycle.engine.language.LocalizedMessage;
import com.one2b3.endcycle.engine.language.UnlocalizedMessage;
import com.one2b3.endcycle.engine.proguard.KeepClass;
import com.one2b3.endcycle.features.models.Description;
import com.one2b3.endcycle.features.models.Previewable;
import com.one2b3.endcycle.utils.ID;
import com.one2b3.endcycle.utils.objects.DataName;
import com.one2b3.modding.diff.Moddable;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@KeepClass
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
@Description("A background that can be seen in various situations such as battles or menu themes")
@Previewable(value = BackgroundDataPreview.class, rows = 7)
public final class BackgroundData extends Moddable implements DataName {

	@Description("The name of this background in the toolbox and possibly game configurations")
	LocalizedMessage name = new UnlocalizedMessage();
	@Description("Whether or not this background is selectable for players when configuring games")
	boolean visible;
	@Description("What drawable the panels of a battlefield should use on this background. Leave empty for the default")
	DrawableId neutralFrame = new DrawableId(), allyFrame = new DrawableId(), enemyFrame = new DrawableId(), noFrame = new DrawableId();
	@Description("What color should be overlayed behind the frames of the background")
	Color background = new Color(Color.BLACK);
	@Description("The frames of this background. These are drawn in the order they appear here")
	List<BackgroundFrame> frames = new ArrayList<>();

	public BackgroundData(ID id) {
		this.id = id;
	}

	@Override
	public String getDataName() {
		return ID.combine(id, name.format());
	}

}
