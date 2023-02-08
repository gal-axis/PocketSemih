package com.one2b3.endcycle.screens.menus.elements.label;

import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Paintable;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.graphics.Drawable;
import com.one2b3.endcycle.features.theme.ActiveTheme;
import com.one2b3.endcycle.features.theme.MenuThemeDrawable;
import com.one2b3.endcycle.screens.menus.elements.simple.SimpleMenuElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
public class MenuImage extends SimpleMenuElement {

	MenuThemeDrawable background;
	Paintable paintable;

	boolean fitScale, stretch;
	float border = 0.0F;
	double animState;

	public MenuImage(Drawable drawable) {
		selectable = false;
		setFocusable(false);
		setPaintable(drawable);
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		animState += delta;
	}

	@Override
	public void drawElement(CustomSpriteBatch batch, float xOfs, float yOfs) {
		float x = xOfs + calcX(), y = yOfs + calcY(), width = getWidth(), height = getHeight();
		drawBackground(batch, x, y, width, height);
		if (paintable != null) {
			width -= border * 2;
			height -= border * 2;
			x += border + width * 0.5F;
			y += border + height * 0.5F;
			if (width > 0 && height > 0) {
				Painter painter = Painter.on(batch);
				if (fitScale) {
					float w = paintable.getWidth(), h = paintable.getHeight();
					painter.xScale = (w != 0.0F ? width / w : 0.0F);
					painter.yScale = (h != 0.0F ? height / h : 0.0F);
					if (!stretch) {
						if (painter.xScale == 0.0F || painter.xScale < painter.yScale) {
							painter.yScale = painter.xScale;
						} else {
							painter.xScale = painter.yScale;
						}
					}
					if (painter.xScale == 0.0F) {
						painter.xScale = 1.0F;
					}
					if (painter.yScale == 0.0F) {
						painter.yScale = 1.0F;
					}
				}
				painter.at(x, y).animState(animState).align(0).paint(paintable);
			}
		}
	}

	public void drawBackground(CustomSpriteBatch batch, float x, float y, float width, float height) {
		if (background != null && width > 0.0F && height > 0.0F) {
			batch.setColor(ActiveTheme.menuColor);
			background.drawNinePatch(batch, x, y, width, height);
		}
	}
}
