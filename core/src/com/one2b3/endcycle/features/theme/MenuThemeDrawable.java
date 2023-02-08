package com.one2b3.endcycle.features.theme;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.graphics.DrawableImage;
import com.one2b3.endcycle.engine.graphics.Drawables;
import com.one2b3.endcycle.engine.graphics.data.DrawableLoader;
import com.one2b3.endcycle.engine.shaders.ShaderManager;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class MenuThemeDrawable {

	static final Color temp = new Color(), temp2 = new Color();

	public DrawableImage drawable, overlay;
	public MenuThemeDrawableData data;

	public MenuThemeDrawable(Drawables drawable) {
		set(drawable.getImage());
	}

	public void set(DrawableImage drawable) {
		this.drawable = drawable;
		data = new MenuThemeDrawableData();
		if (drawable != null) {
			data.drawable = drawable.id;
			NinePatch patch = drawable.getNinePatch();
			if (patch != null) {
				data.left = patch.getLeftWidth();
				data.top = patch.getTopHeight();
				data.right = patch.getRightWidth();
				data.bottom = patch.getBottomHeight();
			}
		}
	}

	public void set(MenuThemeDrawableData data) {
		drawable = DrawableLoader.get().getImage(data.drawable);
		overlay = DrawableLoader.get().getImage(data.overlay);
		this.data = data;
	}

	public void startMasking(CustomSpriteBatch batch, float x, float y, float width, float height) {
		drawNinePatch(batch, x, y, width, height);
		batch.startMasking(x + data.left, y + data.bottom, width - data.left - data.right, height - data.top - data.bottom);
	}

	public void stopMasking(CustomSpriteBatch batch, float x, float y, float width, float height) {
		batch.stopMasking();
		drawOverlay(batch, x, y, width, height);
	}

	public void draw(Batch batch, float x, float y, float width, float height) {
		if (drawable != null) {
			temp.set(batch.getColor()).mul(2.0F);
			if (data == null) {
				batch.setColor(Color.WHITE);
			} else {
				batch.setColor(batch.getColor().set(temp).mul(data.color));
			}
			drawNinePatch(batch, drawable, x, y, width, height);
			batch.setColor(temp);
		}
	}

	public void drawOverlay(Batch batch, float x, float y, float width, float height) {
		if (overlay != null) {
			temp.set(batch.getColor()).mul(2.0F);
			batch.setColor(batch.getColor().set(temp).mul(data.colorOverlay));
			drawNinePatch(batch, overlay, x, y, width, height);
			batch.setColor(temp);
		}
	}

	private void drawNinePatch(Batch batch, DrawableImage image, float x, float y, float width, float height) {
		ShaderProgram old = batch.getShader();
		if (ShaderManager.defaultOverride != null && old != ShaderManager.defaultOverride) {
			batch.setShader(ShaderManager.defaultOverride);
		}
		image.drawNinePatch(batch, x, y, width, height);
		if (old != batch.getShader()) {
			batch.setShader(old);
		}
	}

	public void drawNinePatch(Batch batch, float x, float y, float width, float height) {
		temp.set(batch.getColor()).mul(2.0F);
		if (drawable != null) {
			batch.setColor(batch.getColor().set(temp).mul(data.color));
			drawNinePatch(batch, drawable, x, y, width, height);
		}
		if (overlay != null) {
			batch.setColor(batch.getColor().set(temp).mul(data.colorOverlay));
			drawNinePatch(batch, overlay, x, y, width, height);
		}
		batch.setColor(temp);
	}

	public void drawNinePatch(Batch batch, float x, float y, float width, float height, Color c, float scale) {
		temp.set(batch.getColor()).mul(2.0F);
		if (drawable != null) {
			batch.setColor(batch.getColor().mul(2.0F).mul(c).mul(data.color));
			drawNinePatch(batch, drawable.getNinePatch(), x, y, width, height, scale);
		}
		if (overlay != null) {
			batch.setColor(batch.getColor().set(temp).mul(2.0F).mul(c).mul(data.colorOverlay));
			drawNinePatch(batch, overlay.getNinePatch(), x, y, width, height, scale);
		}
		batch.setColor(temp);
	}

	private void drawNinePatch(Batch batch, NinePatch patch, float x, float y, float width, float height, float scale) {
		if (patch != null) {
			patch.draw(batch, x, y, 0.0F, 0.0F, width / scale, height / scale, scale, scale, 0.0F);
		}
	}

	public float getLeft() {
		return data.left;
	}

	public float getRight() {
		return data.right;
	}

	public float getTop() {
		return data.top;
	}

	public float getBottom() {
		return data.bottom;
	}

	public float getCenterX(float width) {
		return data.left + (width - data.right - data.left) * 0.5F;
	}

	public float getCenterY(float width) {
		return data.bottom + (width - data.top - data.bottom) * 0.5F;
	}

	public NinePatch getNinePatch() {
		return drawable == null ? null : drawable.getNinePatch();
	}

	public NinePatch getOverlayNinePatch() {
		return overlay == null ? null : overlay.getNinePatch();
	}

}
