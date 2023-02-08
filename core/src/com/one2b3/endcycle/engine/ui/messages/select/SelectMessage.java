package com.one2b3.endcycle.engine.ui.messages.select;

import java.util.ArrayList;
import java.util.List;

import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.fonts.FontCache;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.graphics.Drawable;
import com.one2b3.endcycle.engine.graphics.Drawables;
import com.one2b3.endcycle.engine.input.InputListener;
import com.one2b3.endcycle.engine.input.KeyCode;
import com.one2b3.endcycle.engine.input.events.ButtonEvent;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.engine.language.LocalizedMessage;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.ui.messages.SlideInMessage;
import com.one2b3.endcycle.features.theme.ActiveTheme;
import com.one2b3.endcycle.features.theme.MenuThemeDrawable;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC)
public class SelectMessage extends SlideInMessage implements InputListener {

	final MenuThemeDrawable messageImage = ActiveTheme.bigMessage;
	final Drawable cursor = Drawables.Cursor.get();

	private FontCache message;
	float width = 120, height = 50;
	int selection;
	List<SelectMessageButton> buttons = new ArrayList<>();

	Runnable onSkip;

	public SelectMessage() {
	}

	public void setMessage(String message) {
		setMessage(message, false);
	}

	public void setMessage(String message, boolean crop) {
		if (crop) {
			this.message = GameFonts.Text.getCache(message, width - 25);
		} else {
			this.message = GameFonts.Text.getCache(message);
			width = Math.max(this.message.getWidth() + 25, 120);
		}
		height = this.message.getHeight() + 20;
	}

	public void addButton(LocalizedMessage text, LocalizedMessage description, Runnable onSelect) {
		float height = 25, offset = 0;
		float x = 0.0F;
		float y = -25 - (height + offset) * buttons.size();
		buttons.add(new SelectMessageButton(x, y, width, height, text, description, onSelect));
	}

	@Override
	public void init(GameScreen screen) {
		super.init(screen);
		screen.input.add(this);
	}

	@Override
	public void dispose() {
		super.dispose();
		screen.input.remove(this);
	}

	public void select() {
		selectChoice(selection);
		playSound(ActiveTheme.select);
		finish();
	}

	public void moveSelection(int offset) {
		setSelection(selection + offset);
		playSound(ActiveTheme.navigate);
	}

	public void setSelection(int selection) {
		this.selection = (selection + buttons.size()) % buttons.size();
	}

	public void selectChoice(int choice) {
		SelectMessageButton selected = getSelected();
		if (selected != null && selected.onSelect != null) {
			selected.onSelect.run();
		}
	}

	private SelectMessageButton getSelected() {
		return isButtonSelected() ? buttons.get(selection) : null;
	}

	private float getX() {
		return (Cardinal.getWidth() - width) * 0.5F;
	}

	private float getY() {
		return Cardinal.getHeight() * (1.0F - getPosition() * 0.5F) + buttons.size() * 5;
	}

	private boolean isButtonSelected() {
		return selection >= 0 && selection < buttons.size();
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		float x = getX();
		float y = getY();
		batch.drawScreenTint(getPosition() * 0.3F);
		if (message != null) {
			batch.setColor(ActiveTheme.menuColor);
			messageImage.draw(batch, x, y, width, height);
			Painter.on(batch).at(x + messageImage.getCenterX(width), y + messageImage.getCenterY(height)).align(0, 0)
					.paint(message);
			messageImage.drawOverlay(batch, x, y, width, height);
		}
		for (int i = 0; i < buttons.size(); i++) {
			buttons.get(i).draw(batch, cursor, x, y, i == selection);
		}
		SelectMessageButton selected = getSelected();
		if (selected != null && selected.description != null) {
			String description = selected.description.format();
			if (description.length() > 0) {
				batch.setColor(ActiveTheme.menuColor);
				float xDescription = x + width;
				float yDescription = y + height;
				float width = 120, height = 70.0F;
				yDescription -= height;
				messageImage.draw(batch, xDescription, yDescription, width, height);
				Painter.on(batch)
						.at(xDescription + messageImage.getCenterX(width),
								yDescription + messageImage.getCenterY(height))
						.align(0, 0).width(width - messageImage.getLeft() - messageImage.getRight()).paint(description);
				messageImage.drawOverlay(batch, xDescription, yDescription, width, height);
			}
		}
	}

	@Override
	public boolean triggerButton(ButtonEvent event) {
		if (event.isPressed() && isStaying()) {
			if (event.isKey(KeyCode.MENU_DOWN)) {
				moveSelection(1);
			} else if (event.isKey(KeyCode.MENU_UP)) {
				moveSelection(-1);
			} else if (event.isKey(KeyCode.MENU_SELECT)) {
				select();
			} else if (event.isKey(KeyCode.MENU_CANCEL)) {
				skip();
			}
		}
		return true;
	}

	@Override
	public boolean triggerTouch(TouchEvent event) {
		if (isStaying()) {
			float x = event.positionX - getX(), y = event.positionY - getY();
			for (int i = 0; i < buttons.size(); i++) {
				if (buttons.get(i).isTouching(x, y)) {
					setSelection(i);
					if (event.isLeftMouse() && event.isReleased()) {
						select();
					}
					return true;
				}
			}
			if (event.isReleased()) {
				skip();
			}
		}
		return true;
	}

	public void skip() {
		if (onSkip != null) {
			onSkip.run();
		}
		finish();
	}

}
