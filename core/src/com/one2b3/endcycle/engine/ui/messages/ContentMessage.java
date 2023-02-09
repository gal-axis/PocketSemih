package com.one2b3.endcycle.engine.ui.messages;

import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.core.painting.CustomSpriteBatch;
import com.one2b3.endcycle.engine.drawing.Painter;
import com.one2b3.endcycle.engine.graphics.Drawable;
import com.one2b3.endcycle.engine.graphics.Drawables;
import com.one2b3.endcycle.engine.input.InputListener;
import com.one2b3.endcycle.engine.input.KeyCode;
import com.one2b3.endcycle.engine.input.events.ButtonEvent;
import com.one2b3.endcycle.engine.input.events.TouchEvent;
import com.one2b3.endcycle.engine.language.LocalizedMessage;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.endcycle.engine.ui.MenuUtils;
import com.one2b3.endcycle.features.theme.ActiveTheme;
import com.one2b3.endcycle.features.theme.MenuThemeDrawable;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC)
public abstract class ContentMessage extends GameScreenMessage implements InputListener {
	protected static final int STATE_MOVING_IN = 0, STATE_ACTIVE = 1, STATE_MOVING_OUT = 2, STATE_DONE = 3;
	static final float MOVE_SPEED = 8.0F;

	final MenuThemeDrawable message = ActiveTheme.bigMessage;
	final MenuThemeDrawable button = ActiveTheme.button;
	final Drawable cursor = Drawables.Cursor.get();

	int state;
	float position;
	float timer, activeTime;

	float width, height;

	int selection, cancel = -1;
	MessageAction[] actions;
	LocalizedMessage[] choices;

	public ContentMessage() {
	}

	public void setChoices(int cancel, LocalizedMessage... choices) {
		this.cancel = cancel;
		this.choices = choices;
	}

	public void setActions(MessageAction... actions) {
		this.actions = actions;
	}

	@Override
	public byte getLayer() {
		return Layers.LAYER_MESSAGES;
	}

	@Override
	public void init(GameScreen screen) {
		super.init(screen);
		screen.input.add(this);
		playSound(ActiveTheme.open);
		state = STATE_MOVING_IN;
		position = 0.0F;
		timer = 0.0F;
	}

	@Override
	public void update(float delta) {
		if (state == STATE_MOVING_IN) {
			if (position < 1.0F) {
				position += MOVE_SPEED * delta;
				if (position >= 1.0F) {
					position = 1.0F;
					state = STATE_ACTIVE;
				}
			}
		} else if (state == STATE_ACTIVE) {
			if ((choices == null || choices.length == 0) && !Float.isNaN(activeTime)) {
				timer += delta;
				if (timer >= activeTime) {
					state = STATE_MOVING_OUT;
					playSound(ActiveTheme.close);
				}
			}
		} else if (state == STATE_MOVING_OUT) {
			position -= MOVE_SPEED * delta;
			if (position < 0.0F) {
				state = STATE_DONE;
			}
		}
	}

	public boolean active() {
		return state == STATE_ACTIVE;
	}

	@Override
	public void dispose() {
		screen.input.remove(this);
		timer = activeTime;
		super.dispose();
	}

	public void select() {
		select(selection);
	}

	public void select(int selection) {
		if (actions == null || selection < 0 || selection >= actions.length || actions[selection] == null
				|| actions[selection].onPress(this)) {
			state = STATE_MOVING_OUT;
			playSound(ActiveTheme.close);
		}
	}

	public void moveSelection(int offset) {
		if (getChoiceAmount() > 1) {
			selection += offset;
			if (selection < 0) {
				selection += getChoiceAmount();
			} else if (selection >= getChoiceAmount()) {
				selection -= getChoiceAmount();
			}

			playSound(ActiveTheme.navigate);
		}
	}

	public int getChoiceAmount() {
		return (choices != null ? choices.length : 0);
	}

	@Override
	public void draw(CustomSpriteBatch batch, float xOfs, float yOfs) {
		float x = calcX(), y = calcY();
		batch.setColor(ActiveTheme.menuColor);
		message.draw(batch, x, y, width, height);
		drawContent(batch, x + message.getLeft(), y + message.getBottom(),
				width - message.getLeft() - message.getRight(), height - message.getBottom() - message.getTop());
		batch.setColor(ActiveTheme.menuColor);
		message.drawOverlay(batch, x, y, width, height);
		drawButtons(batch, x, y);
	}

	public float calcX() {
		return (Cardinal.getWidth() - width) * 0.5F;
	}

	public float calcY() {
		return (Cardinal.getHeight() - height) * 0.5F + (1.0F - position) * (Cardinal.getHeight() + height) * 0.5F;
	}

	private void drawButtons(CustomSpriteBatch batch, float x, float y) {
		if (choices != null && choices.length > 0) {
			float buttonWidth = getButtonWidth();
			float buttonHeight = getButtonHeight();
			float buttonY = y - buttonHeight;
			for (int i = 0; i < choices.length; i++) {
				boolean selected = (i == selection);
				batch.setColor(selected ? ActiveTheme.defaultColor : ActiveTheme.disabledColor);
				float buttonX = x + i * buttonWidth;
				button.draw(batch, buttonX, buttonY, buttonWidth, buttonHeight);
				float choiceX = buttonX + (int) (buttonWidth * 0.5F);
				float choiceY = buttonY + (int) (buttonHeight * 0.5F);
				Painter.on(batch).at(choiceX, choiceY).align(0).paint(choices[i]);
				button.drawOverlay(batch, buttonX, buttonY, buttonWidth, buttonHeight);
				if (selected) {
					MenuUtils.drawCursor(batch, cursor, buttonX, buttonY, buttonWidth, buttonHeight);
				}

			}
		}
	}

	private float getButtonHeight() {
		return 40.0F;
	}

	private float getButtonWidth() {
		return width / choices.length;
	}

	public abstract void drawContent(CustomSpriteBatch batch, float x, float y, float width, float height);

	@Override
	public boolean triggerButton(ButtonEvent event) {
		if (state == STATE_ACTIVE) {
			if (event.isPressed()) {
				if (event.isKey(KeyCode.MENU_LEFT)) {
					moveSelection(-1);
				}
				if (event.isKey(KeyCode.MENU_RIGHT)) {
					moveSelection(1);
				}
				if (event.isKey(KeyCode.MENU_SELECT)) {
					select();
				}
				if (event.isKey(KeyCode.MENU_CANCEL)) {
					if (cancel != -1) {
						select(cancel);
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean triggerTouch(TouchEvent event) {
		int choices = getChoiceAmount();
		if (state == STATE_ACTIVE && choices > 0) {
			float x = calcX();
			float y = calcY();
			float buttonWidth = getButtonWidth();
			float buttonHeight = getButtonHeight();
			if (event.positionX >= x && event.positionX <= x + width && event.positionY >= y - buttonHeight
					&& event.positionY <= y) {
				selection = (int) ((event.positionX - x) / buttonWidth);
				if (event.isReleased()) {
					select();
				}
			}
		}
		return true;
	}

	@Override
	public boolean remove() {
		return state == STATE_DONE;
	}

	public interface MessageAction {
		boolean onPress(ContentMessage message);
	}
}
