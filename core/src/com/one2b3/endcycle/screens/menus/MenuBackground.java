package com.one2b3.endcycle.screens.menus;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.endcycle.engine.screens.ScreenObject;
import com.one2b3.endcycle.features.theme.ActiveTheme;
import com.one2b3.endcycle.screens.background.BackgroundObject;
import com.one2b3.endcycle.utils.Modulator;
import com.one2b3.endcycle.utils.pools.SizedObjectPool;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MenuBackground implements ScreenObject {

	static final float SPEED = 10.0F;
	static final float SIZE_DST = 8.0F;
	static final int SIZE_MIN = 1, SIZE_MAX = 6;

	float width, height;

	final BackgroundObject background = ActiveTheme.background;
	final SizedObjectPool<BackgroundBlock> blockPool = new SizedObjectPool<>(BackgroundBlock::new, 60);

	List<BackgroundBlock> blocks = new ArrayList<>();

	@Getter
	final Color color;

	@Override
	public byte getLayer() {
		return Layers.LAYER_0;
	}

	@Override
	public void init(GameScreen screen) {
		updateBlocks();
	}

	private void updateBlocks() {
		blocks.clear();
		blockPool.reset();
		width = Cardinal.getWidth();
		height = Cardinal.getHeight();
		float maxSize = SIZE_MAX * (SIZE_DST + 1.0F);
		int xSteps = MathUtils.floor(width / maxSize);
		int ySteps = MathUtils.ceil(height / maxSize);
		float minRange = -SIZE_MAX * SIZE_DST * 0.2F, maxRange = SIZE_MAX * SIZE_DST * 0.2F;
		boolean vertical = false;
		for (int x = 0; x <= xSteps; x++) {
			for (int y = 0; y <= ySteps; y++) {
				BackgroundBlock block = blockPool.getNext();
				float xPos = (int) MathUtils.random(minRange, maxRange) + x * maxSize;
				float yPos = (int) MathUtils.random(minRange, maxRange) + y * maxSize;
				float size = MathUtils.random(SIZE_MIN, SIZE_MAX) * SIZE_DST;
				block.init(xPos, yPos, size, vertical);
				blocks.add(block);
				vertical = !vertical;
			}
			vertical = !vertical;
		}
	}

	@Override
	public void update(float delta) {
		for (int i = 0; i < blocks.size(); i++) {
			BackgroundBlock block = blocks.get(i);
			block.update(delta);
		}
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		float linear = Modulator.getCosine(0.9F, 1.0F, 0.45F);
		batch.drawScreenTint(linear * color.r, linear * color.g, linear * color.b, color.a);
		for (int i = 0; i < blocks.size(); i++) {
			BackgroundBlock block = blocks.get(i);
			block.draw(batch, xOfs, yOfs, color);
		}
		if (ActiveTheme.tintBackground) {
			background.color = color;
		}
		background.draw(batch, xOfs, yOfs);
	}

	@Override
	public boolean remove() {
		return false;
	}

	@Override
	public void dispose() {
		blockPool.reset();
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		background.resize(width, height);
	}

	@NoArgsConstructor
	class BackgroundBlock {
		float xPos, yPos;
		boolean vertical;
		float size;

		public void init(float xPos, float yPos, float size, boolean vertical) {
			this.xPos = xPos;
			this.yPos = yPos;
			this.size = size;
			this.vertical = vertical;
		}

		public void update(float delta) {
			float maxSize = SIZE_MAX * SIZE_DST;
			if (vertical) {
				xPos += delta * SPEED;
				xPos %= width + maxSize;
			} else {
				yPos += delta * SPEED;
				yPos %= height + maxSize;
			}
		}

		public void draw(CustomSpriteBatch batch, float xOfs, float yOfs, Color color) {
			float c = (1.0F - ((size / SIZE_DST) / SIZE_MAX)) * 0.5F + 0.5F;
			c *= Modulator.getCosine(0.9F, 1.0F, 0.34F);
			batch.drawRectangle(xOfs + width - xPos, yOfs + yPos - size, size, size, color.r * c, color.g * c, color.b * c, color.a);
		}
	}
}
