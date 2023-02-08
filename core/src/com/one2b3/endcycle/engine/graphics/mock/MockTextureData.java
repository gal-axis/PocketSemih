package com.one2b3.endcycle.engine.graphics.mock;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.TextureData;

public class MockTextureData implements TextureData {

	@Override
	public boolean useMipMaps() {
		return false;
	}

	@Override
	public void prepare() {
	}

	@Override
	public boolean isPrepared() {
		return false;
	}

	@Override
	public boolean isManaged() {
		return false;
	}

	@Override
	public int getWidth() {
		return 0;
	}

	@Override
	public TextureDataType getType() {
		return TextureDataType.Custom;
	}

	@Override
	public int getHeight() {
		return 0;
	}

	@Override
	public Format getFormat() {
		return null;
	}

	@Override
	public boolean disposePixmap() {
		return false;
	}

	@Override
	public Pixmap consumePixmap() {
		return null;
	}

	@Override
	public void consumeCustomData(int target) {
	}

}
