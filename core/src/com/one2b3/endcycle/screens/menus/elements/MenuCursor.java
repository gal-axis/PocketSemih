package com.one2b3.endcycle.screens.menus.elements;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.graphics.Drawable;
import com.one2b3.endcycle.engine.graphics.Drawables;
import com.one2b3.endcycle.engine.ui.MenuUtils;
import com.one2b3.endcycle.utils.bounded.BoundedFloat;

public class MenuCursor {

	final Rectangle oldCursor = new Rectangle();
	final Rectangle newCursor = new Rectangle(0.0F, 0.0F, Cardinal.getWidth(), Cardinal.getHeight());
	final Rectangle currentCursor = new Rectangle(0.0F, 0.0F, Cardinal.getWidth(), Cardinal.getHeight());
	final Rectangle updatedCursor = new Rectangle();
	final BoundedFloat cursorTransition = new BoundedFloat(0.0F, 1.0F, 15.0F);
	final BoundedFloat clickAnimation = new BoundedFloat(0.0F, 1.0F, 6.0F);

	final Drawable cursor = Drawables.Cursor.get();

	MenuElement newElement;

	public void setNew(MenuElement selected) {
		if (selected != null) {
			this.newElement = selected;
		}
	}

	public void click() {
		clickAnimation.toMax();
	}

	public void resetCursor() {
		oldCursor.set(currentCursor);
		cursorTransition.toMin();
		clickAnimation.toMin();
	}

	public void syncCursor() {
		updateElement();
		cursorTransition.toMax();
		clickAnimation.toMin();
		currentCursor.set(newCursor);
	}

	public void update(float delta) {
		clickAnimation.decrease(delta);
		cursorTransition.increase(delta);
		float a = cursorTransition.getVal();
		currentCursor.x = MathUtils.lerp(oldCursor.x, newCursor.x, a);
		currentCursor.y = MathUtils.lerp(oldCursor.y, newCursor.y, a);
		currentCursor.width = MathUtils.lerp(oldCursor.width, newCursor.width, a);
		currentCursor.height = MathUtils.lerp(oldCursor.height, newCursor.height, a);
	}

	public void updateElement() {
		if (newElement != null) {
			newElement.setCursor(updatedCursor);
			if (!updatedCursor.equals(newCursor) && updatedCursor.width > 0.0F && updatedCursor.height > 0.0F) {
				resetCursor();
				newCursor.set(updatedCursor);
			}
		}
	}

	public boolean finishedTransition() {
		return cursorTransition.atMax();
	}

	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		updateElement();
		if (updatedCursor.width > 0 && updatedCursor.height > 0 && currentCursor.width > 0 && currentCursor.height > 0) {
			float animX = clickAnimation.getVal() * currentCursor.getWidth() * 0.15F - 2;
			float animY = clickAnimation.getVal() * currentCursor.getHeight() * 0.15F - 2;
			MenuUtils.drawCursor(batch, cursor, currentCursor.x + animX + xOfs, currentCursor.y + animY + yOfs,
					currentCursor.width - animX * 2, currentCursor.height - animY * 2);
		}
	}

	public Rectangle getCursor() {
		return currentCursor;
	}
}
