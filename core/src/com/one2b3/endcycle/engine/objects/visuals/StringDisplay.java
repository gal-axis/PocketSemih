package com.one2b3.endcycle.engine.objects.visuals;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.FontCache;
import com.one2b3.endcycle.engine.fonts.GameFont;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.GameScreenObject;
import com.one2b3.endcycle.engine.screens.Layers;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC)
public final class StringDisplay extends GameScreenObject {

	static int index = 0;

	final Color tint = new Color(Color.WHITE);

	@Getter
	byte layer;
	@Getter
	float comparisonKey = Float.NaN;
	float x, y;
	float fontScale = 1.0F;
	GameFont font = GameFonts.TextBorder;
	String display;
	StringDisplayCharacter[] props;
	boolean remove;
	StringDisplayAnimation animation = StringDisplayAnimation.SCALE_UP;
	float time;

	private FontCache cache;

	public StringDisplay() {
		this.layer = Layers.LAYER_9;
	}

	public void init(GameFont font, String display, float x, float y, float time) {
		index++;
		if (Float.isNaN(comparisonKey)) {
			comparisonKey = -index;
		}
		this.font = font;
		this.time = time;
		this.x = x;
		this.y = y;
		setTint(1.0F, 1.0F, 1.0F, 1.0F);
		setDisplay(display);
	}

	@Override
	public void init(GameScreen screen) {
		super.init(screen);
		for (int i = 0; i < props.length; i++) {
			props[i].init();
		}
		remove = false;
	}

	public void setCharacterSpeed(float speed) {
		for (int i = 0; i < props.length; i++) {
			props[i].alpha.setSpeed(speed);
		}
	}

	public float getCharacterSpeed() {
		return props == null || props.length == 0 ? 0.0F : props[0].alpha.getSpeed();
	}

	public void setTint(Color color) {
		this.tint.set(color == null ? Color.WHITE : color);
	}

	public void setTint(float r, float g, float b, float alpha) {
		tint.r = MathUtils.clamp(r, 0.0F, 2.0F);
		tint.g = MathUtils.clamp(g, 0.0F, 2.0F);
		tint.b = MathUtils.clamp(b, 0.0F, 2.0F);
		tint.a = MathUtils.clamp(alpha, 0.0F, 2.0F);
	}

	public void setDisplay(String display) {
		if (display != null) {
			cache = null;
			if (this.display == null || display.length() != this.display.length()) {
				props = new StringDisplayCharacter[display.length()];
				for (int i = 0; i < props.length; i++) {
					props[i] = new StringDisplayCharacter(display.charAt(i), time);
				}
			}
		}
		this.display = display;
	}

	@Override
	public void update(float delta) {
		if (display != null && !remove) {
			remove = true;
			if (props != null) {
				for (int i = 0; i < props.length; i++) {
					props[i].update(delta, animation);
					remove &= (props[i].getAlpha() == 0.0F);
					if (!props[i].canAdvance(animation.percentage)) {
						break;
					}
				}
			}
		}
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		FontCache cache = this.cache;
		cache = null;
		if (cache == null && display != null && !display.isEmpty()) {
			cache = this.cache = font.getCache(display);
		}
		if (cache != null) {
			if (cache.getWidth() > Cardinal.getWidth()) {
				cache = this.cache = font.getCache(display, Cardinal.getWidth() - 4);
			}
			float x = xOfs + this.x - cache.getWidth() * 0.5F;
			float y = yOfs + this.y;
			for (int to = 0; to < props.length; to++) {
				Painter painter = Painter.on(batch).hAlign(0).color(tint).scale(fontScale);
				animation.draw(props[to], painter);
				cache.paint(painter.moveX(x + cache.getWidth() * 0.5F).moveY(y), to, to + 1);
				if (!this.props[to].canAdvance(animation.percentage)) {
					break;
				}
			}
		}
	}

	@Override
	public void hide(GameScreen screen) {
		super.hide(screen);
		finish();
	}

	public void finish() {
		tint.a = 0.0F;
		remove = true;
	}

	@Override
	public boolean remove() {
		return remove;
	}

	@Override
	public String toString() {
		return "String Display at " + x + "-" + y + " String:\"" + display + "\"";
	}
}
