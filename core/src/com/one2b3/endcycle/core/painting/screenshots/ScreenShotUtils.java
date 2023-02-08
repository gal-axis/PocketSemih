package com.one2b3.endcycle.core.painting.screenshots;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.engine.files.Data;
import com.one2b3.endcycle.utils.ScreenShotFactory;
import com.one2b3.utils.time.DateTime;

public class ScreenShotUtils {

	public static String getCurrentTime() {
		return DateTime.now().formatFilename();
	}

	public static byte[] createByteArray(int w, int h) {
		final int numBytes = w * h * 3;
		return new byte[numBytes];
	}

	public static void getFrameBufferPixels(ByteBuffer pixels, int x, int y, int w, int h) {
		pixels.position(0);
		pixels.order(ByteOrder.nativeOrder());
		Gdx.gl.glPixelStorei(GL20.GL_PACK_ALIGNMENT, 1);
		Gdx.gl.glReadPixels(x, y, w, h, GL20.GL_RGB, GL20.GL_UNSIGNED_BYTE, pixels);
	}

	public static FileHandle save(Pixmap pixmap) {
		FileHandle toSave = getFileHandle();
		ScreenShotFactory.savePixmap(pixmap, toSave);
		return toSave;
	}

	public static FileHandle getFileHandle() {
		int pic = 1;
		String path = Cardinal.getPlatform().getGameId() + " ";
		String imgName = ScreenShotUtils.getCurrentTime();
		FileHandle toSave = getFileHandle(path + imgName + ".png");
		while (toSave.exists()) {
			toSave = getFileHandle(path + imgName + " " + pic + ".png");
			pic++;
		}
		return toSave;
	}

	public static FileHandle getFileHandle(String file) {
		return Data.getDocumentHandle("Pictures/" + file);
	}
}
