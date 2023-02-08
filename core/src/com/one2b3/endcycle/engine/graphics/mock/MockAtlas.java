package com.one2b3.endcycle.engine.graphics.mock;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.TextureAtlasData.Region;

public class MockAtlas extends TextureAtlas {

	public MockAtlas() {
	}

	public MockAtlas(FileHandle packFile) {
		this(packFile, packFile.parent());
	}

	public MockAtlas(FileHandle packFile, FileHandle imagesDir) {
		this(new TextureAtlasData(packFile, imagesDir, false));
	}

	public MockAtlas(TextureAtlasData data) {
		if (data != null) {
			load(data);
		}
	}

	@Override
	public void load(TextureAtlasData data) {
		for (Region region : data.getRegions()) {
			int width = region.width;
			int height = region.height;
			MockAtlasRegion atlasRegion = new MockAtlasRegion(region.left, region.top, region.rotate ? height : width,
					region.rotate ? width : height);
			atlasRegion.index = region.index;
			atlasRegion.name = region.name;
			atlasRegion.offsetX = region.offsetX;
			atlasRegion.offsetY = region.offsetY;
			atlasRegion.originalHeight = region.originalHeight;
			atlasRegion.originalWidth = region.originalWidth;
			atlasRegion.rotate = region.rotate;
			atlasRegion.names = region.names;
			atlasRegion.values = region.values;
			if (region.flip) {
				atlasRegion.flip(false, true);
			}
			getRegions().add(atlasRegion);
		}
	}
}
