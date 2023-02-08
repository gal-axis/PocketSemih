package com.one2b3.endcycle.engine.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.engine.drawing.Paintable;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.utils.ID;
import com.one2b3.utils.DrawableAnimation;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class DrawableImage implements Paintable {

	public static boolean useColorblind;
	public static boolean useDirect;

	static final TextureRegion drawRegion = new TextureRegion();

	public boolean loggedError;

	public ID id;
	@Getter
	DrawableAnimation animation;
	@Getter
	private float width, height;
	@Getter
	@Setter
	NinePatch ninePatch, colorblindPatch;

	public DrawableImage(ID id) {
		this.id = id;
	}

	public DrawableImage(DrawableAnimation animation) {
		setAnimation(animation);
	}

	public void loadNinePatch() {
		if (animation != null && animation.keyFrames.length == 9) {
			Color color = (ninePatch != null ? ninePatch.getColor() : Color.WHITE);
			DrawableImageFrame[] frames = animation.keyFrames;
			try {
				ninePatch = getPatch(frames, false);
				ninePatch.setColor(color);
			} catch (NullPointerException | GdxRuntimeException | IllegalArgumentException e) {
				ninePatch = null;
			}
			try {
				colorblindPatch = getPatch(frames, true);
				colorblindPatch.setColor(color);
			} catch (NullPointerException | GdxRuntimeException | IllegalArgumentException e) {
				colorblindPatch = null;
			}
		} else if (ninePatch != null) {
			ninePatch = null;
			colorblindPatch = null;
		}
	}

	private NinePatch getPatch(DrawableImageFrame[] frames, boolean colorblind) {
		return new NinePatch( //
				frames[0].getRegion(colorblind), frames[1].getRegion(colorblind), frames[2].getRegion(colorblind), //
				frames[3].getRegion(colorblind), frames[4].getRegion(colorblind), frames[5].getRegion(colorblind), //
				frames[6].getRegion(colorblind), frames[7].getRegion(colorblind), frames[8].getRegion(colorblind));
	}

	public void setAnimation(DrawableAnimation animation) {
		this.animation = animation;
		width = height = 0;
		for (int i = animation.keyFrames.length - 1; i >= 0; i--) {
			width = Math.max(width, animation.keyFrames[i].getWidth() + animation.keyFrames[i].xOffset);
			height = Math.max(height, animation.keyFrames[i].getHeight() + animation.keyFrames[i].yOffset);
		}
		loadNinePatch();
	}

	public TextureRegion getRegion(double animState) {
		return getFrame(animState).getRegion(useColorblind);
	}

	public DrawableImageFrame getFrame(double animState) {
		return animation.getKeyFrame(animState);
	}

	public int getFrameIndex(double animState) {
		return animation.getKeyFrameIndex(animState);
	}

	public DrawableImageFrame getFrame(int index) {
		return animation.getKeyFrame(index);
	}

	public Texture getTexture(int index) {
		return animation.keyFrames[index].getTexture();
	}

	public int getFrames() {
		return animation.keyFrames.length;
	}

	public double getFrameDuration() {
		return animation.frameDuration;
	}

	public double getAnimationDuration() {
		return animation.getAnimationDuration();
	}

	public int getWidth(double animState) {
		return getFrame(animState).getWidth();
	}

	public int getHeight(double animState) {
		return getFrame(animState).getHeight();
	}

	public Texture getTexture(double animState) {
		return getFrame(animState).getTexture();
	}

	public TextureRegion getRegion(double animState, float x, float y, float width, float height) {
		return getRegion(new TextureRegion(), animState, x, y, width, height);
	}

	public TextureRegion getRegion(TextureRegion region, double animState, float x, float y, float width,
			float height) {
		DrawableImageFrame frame = getFrame(animState);
		TextureRegion frameRegion = frame.getRegion(useColorblind);
		if (frameRegion != null && frameRegion.getTexture() != null) {
			float invTexWidth = 1f / frameRegion.getTexture().getWidth();
			float invTexHeight = 1f / frameRegion.getTexture().getHeight();
			x += frameRegion.getRegionX();
			y += frameRegion.getRegionY();
			if (frameRegion.isFlipX()) {
				width = -width;
			}
			if (frameRegion.isFlipY()) {
				height = -height;
			}
			region.setTexture(frameRegion.getTexture());
			region.setRegion(x * invTexWidth, y * invTexHeight, //
					(x + width) * invTexWidth, //
					(y + height) * invTexHeight);
		} else {
			region.setTexture(null);
		}
		return region;
	}

	public void draw(Batch batch, float posX, float posY) {
		draw(batch, posX, posY, 1.0F, 1.0F, Cardinal.TIME_ACTIVE, 0.0F);
	}

	public void draw(Batch batch, float posX, float posY, float scaleX, float scaleY) {
		draw(batch, posX, posY, scaleX, scaleY, Cardinal.TIME_ACTIVE, 0.0F);
	}

	public void draw(Batch batch, float posX, float posY, float scaleX, float scaleY, double animState) {
		draw(batch, posX, posY, scaleX, scaleY, animState, 0.0F);
	}

	public void draw(Batch batch, double animState, Affine2 transform) {
		DrawableImageFrame frame = obtain(animState);
		if (frame != null) {
			transform.translate(frame.xOffset, frame.yOffset);
			TextureRegion region = frame.getRegion(useColorblind);
			batch.draw(region, region.getRegionWidth(), region.getRegionHeight(), transform);
			transform.translate(-frame.xOffset, -frame.yOffset);
		}
	}

	private DrawableImageFrame obtain(double animState) {
		DrawableImageFrame frame = (animation == null ? null : getFrame(animState));
		if (frame == null || frame.getTexture() == null) {
			if (!loggedError) {
				Gdx.app.error("Error", "Empty region for drawable: " + id);
				loggedError = true;
			}
			return null;
		} else {
			loggedError = false;
		}
		TextureRegion region = frame.getRegion(useColorblind);

		TextureFilter filter = (frame.filter == null ? TextureFilter.Nearest : frame.filter);
		Texture texture = region.getTexture();
		if (texture.getMinFilter() != filter || texture.getMagFilter() != filter) {
			texture.setFilter(filter, filter);
		}
		return frame;
	}

	public void draw(Batch batch, float posX, float posY, float scaleX, float scaleY, double animState,
			float rotation) {
		DrawableImageFrame frame = obtain(animState);
		if (frame != null) {
			TextureRegion region = frame.getRegion(useColorblind);
			float w = region.getRegionWidth() * scaleX, h = region.getRegionHeight() * scaleY;
			batch.draw(region, posX + frame.xOffset * scaleX, posY + frame.yOffset * scaleY, w * 0.5F, h * 0.5F, w, h,
					1.0F, 1.0F, rotation);
		}
	}

	public void drawNinePatch(Batch batch, float x, float y, float width, float height) {
		if ((ninePatch == null || useColorblind) && colorblindPatch != null) {
			colorblindPatch.draw(batch, x, y, width, height);
		} else if (ninePatch != null) {
			ninePatch.draw(batch, x, y, width, height);
		} else {
			DrawableImageFrame frame = animation.getKeyFrame(Cardinal.TIME_ACTIVE);
			batch.draw(frame.getRegion(useColorblind), x, y, width, height);
		}
	}

	@Override
	public void paint(Painter painter) {
		painter.batch.setColor(painter.color);
		double animState = Paintable.get(painter.animState, Cardinal.TIME_ACTIVE);
		float xScale = painter.xScale, yScale = painter.yScale;
		if (painter.width > 0.0F) {
			xScale *= painter.width / getWidth();
		}
		if (painter.height > 0.0F) {
			yScale *= painter.height / getHeight();
		}
		float x = painter.x - getWidth() * xScale * (painter.hAlign + 1) * 0.5F;
		float y = painter.y - getHeight() * yScale * (painter.vAlign + 1) * 0.5F;
		draw(painter.batch, x, y, xScale, yScale, animState);
	}

	public float getScale(float width, float height) {
		return Math.min(height / getHeight(), width / getWidth());
	}

}
