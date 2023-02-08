package com.one2b3.endcycle.screens.menus.elements.table;

import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.proguard.KeepClass;
import com.one2b3.endcycle.features.models.Description;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@KeepClass
public class MenuTableTitleData {

	@Description("How the title is aligned horizontally in relation to the heading. -1 = Left, 0 = Center, 1 = Right")
	public int hAlign = 0;
	@Description("How the title is aligned vertically in relation to the heading. -1 = Bottom, 0 = Center, 1 = Top")
	public int vAlign = 0;
	@Description("Additional offset to position the title")
	public float x, y;
	@Description("How high the heading part of the title is")
	public float height = 13;

	public void set(MenuTableTitleData data) {
		hAlign = data.hAlign;
		vAlign = data.vAlign;
		x = data.x;
		y = data.y;
		height = data.height;
	}

	public Painter getPainter(CustomSpriteBatch batch, float x, float y, float w, float h, float padTop) {
		x += w * 0.5F * (this.hAlign + 1);
		y += h - (this.height + padTop) * 0.5F * (1 - this.vAlign);
		return Painter.on(batch).x(x + this.x).y(y + this.y).align(this.hAlign, this.vAlign);
	}
}
