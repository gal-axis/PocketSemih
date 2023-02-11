package com.bikinger.semih;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bikinger.semih.clicker.SemihPoints;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.graphics.data.DrawableLoader;
import com.one2b3.endcycle.engine.language.Unlocalize;
import com.one2b3.endcycle.engine.ui.messages.ConfirmMessage;
import com.one2b3.endcycle.screens.menus.elements.buttons.MenuButton;
import com.one2b3.endcycle.screens.menus.elements.buttons.MenuButtonAction;

public class ShopButton extends MenuButton implements MenuButtonAction {

	static final String HIDDEN = "? ? ? ? ?";

	final SemihPoints points;

	String name, confirm;
	int cost, original;
	TextureRegion region;
	Runnable onBuy;

	public ShopButton(SemihPoints points, String name, String confirm, String texture, int cost, Runnable onBuy) {
		this.points = points;
		this.name = name;
		this.confirm = confirm;
		this.onBuy = onBuy;
		this.cost = cost;
		this.original = cost;
		region = DrawableLoader.get().loadTexture(texture);

		setWidth(110.0F).setHeight(80.0F);
		setFont(GameFonts.MonospaceBorder);
		updateCostText();
		setTextVAlign(-1);
		setAction(this);
	}

	private void updateCostText() {
		setText((canAfford() || cost != original ? name : HIDDEN) + "\n(Cost: " + cost + ")");
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		if (showing) {
			updateCostText();
		}
	}

	@Override
	public void onClick() {
		if (canAfford()) {
			ConfirmMessage message = new ConfirmMessage('"' + name + "\":\n\n" + confirm, m -> {
				points.decrease(cost);
				points.showText(name + "\ngekauft!", getAbsoluteX() + getWidth() * 0.5F,
						getAbsoluteY() + getHeight() * 0.5F - 15);
				if (original < 500000) {
					cost += original;
				}
				updateCostText();
				onBuy.run();
				return true;
			});
			message.setChoices(1, Unlocalize.get("Kaufen"), Unlocalize.get("Ne"));
			screen.addObject(message);
		}
	}

	@Override
	public void setColor(CustomSpriteBatch batch) {
		super.setColor(batch);
		if (!canAfford()) {
			batch.setColor(Color.DARK_GRAY);
		}
	}

	private boolean canAfford() {
		return points.getPoints() >= cost;
	}

	@Override
	public void drawBackground(CustomSpriteBatch batch, float x, float y, float width, float height) {
		super.drawBackground(batch, x, y, width, height);
		float scale = Math.min(width / region.getRegionWidth(), height / region.getRegionHeight());
		Painter.on(batch).at(x + width * 0.5F, y + height * 0.5F).scale(scale).align(0)
				.color(canAfford() ? Color.WHITE : (cost != original ? Color.GRAY : Color.BLACK)).paint(region);
		if (cost != original) {
			Painter.on(batch).at(x + width, y + height).font(GameFonts.MonospaceBorder).align(1)
					.paint("x" + (cost / original - 1));
		}
	}
}
