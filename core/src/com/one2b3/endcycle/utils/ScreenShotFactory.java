package com.one2b3.endcycle.utils;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO.PNG;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.GamePainter;
import com.one2b3.endcycle.engine.graphics.Drawable;

public final class ScreenShotFactory {

	private ScreenShotFactory() {
	}

	private static Drawable screenshot;
	private static Texture lastTexture;

	public static Pixmap saveScreenshot() {
		return saveScreenshot(true);
	}

	public static Pixmap saveScreenshot(boolean createTex) {
		try {
			Pixmap pixmap = captureScreen();
			if (createTex) {
				if (lastTexture != null) {
					lastTexture.dispose();
				}
				lastTexture = new Texture(pixmap);
				lastTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
				TextureRegion region = new TextureRegion(lastTexture);
				region.flip(false, true);
				screenshot = new Drawable(region);
			}
			return pixmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Pixmap captureScreen() {
		GamePainter painter = Cardinal.game.painter;
		FrameBuffer frameBuffer = painter.getFrameBuffer();
		Pixmap region;
		if (frameBuffer == null) {
			int marginX = (int) painter.getMarginX(), marginY = (int) painter.getMarginY();
			region = Pixmap.createFromFrameBuffer(marginX, marginY, Gdx.graphics.getWidth() - marginX * 2,
					Gdx.graphics.getHeight() - marginY);
		} else {
			frameBuffer.bind();
			region = Pixmap.createFromFrameBuffer(0, 0, frameBuffer.getWidth(), frameBuffer.getHeight());
			frameBuffer.end();
		}
		return region;
	}

	public static void savePixmap(Pixmap pixmap, FileHandle handle) {
		if (handle != null) {
			handle.file().getParentFile().mkdirs();
			PNG writer = new PNG((int) (pixmap.getWidth() * pixmap.getHeight() * 1.5f));
			try {
				writer.setFlipY(true);
				writer.write(handle, pixmap);
			} catch (IOException ex) {
				throw new GdxRuntimeException("Error writing PNG: " + handle, ex);
			} finally {
				writer.dispose();
			}
			Gdx.app.debug("Screenshot", "Created screenshot: " + handle.path());
		}
	}

	public static void setScreenshot(Drawable screenshot) {
		ScreenShotFactory.screenshot = screenshot;
	}

	public static Drawable getScreenshot() {
		return screenshot;
	}
}
