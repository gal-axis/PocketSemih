package com.one2b3.endcycle.screens.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.one2b3.endcycle.engine.events.EventType;
import com.one2b3.endcycle.engine.screens.GameScreen;
import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.endcycle.engine.screens.faders.FadeType;
import com.one2b3.endcycle.features.theme.ActiveTheme;
import com.one2b3.endcycle.screens.menus.elements.MenuElementController;
import com.one2b3.endcycle.screens.menus.elements.MenuLayout;
import com.one2b3.endcycle.screens.menus.elements.group.MenuElementGroup;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PUBLIC)
public abstract class MenuScreen extends GameScreen {

	final String title;
	final MenuHeader menuHeader;

	MenuBackground background;

	MenuElementController elementController;
	MenuElementGroup objectGroup;

	public FadeType fadeType = FadeType.FADE_TO_BLACK;

	public MenuScreen(String title, float r, float g, float b) {
		this(title, new Color(r, g, b, 1.0F));
	}

	public MenuScreen(String title, Color color) {
		this.title = title;
		if (color != null) {
			background = addObject(new MenuBackground(color));
		}
		menuHeader = (title == null ? null : addObject(new MenuHeader(title, this::backButtonPressed)));
	}

	@Override
	public void setPreviousScreen(GameScreen previousScreen) {
		if (getPreviousScreen() == null || previousScreen == null) {
			super.setPreviousScreen(previousScreen);
		}
	}

	public void createMenuElements() {
		this.objectGroup = null;
		this.elementController = null;
	}

	@Override
	public void init() {
		super.init();
		if (objectGroup == null) {
			elementController = new MenuElementController();
			objectGroup = new MenuElementGroup();
			objectGroup.setLayer(Layers.LAYER_HUD);
			try {
				createMenuElements();
			} catch (Throwable t) {
				Gdx.app.error("Menu", "Error creating menu elements!", t);
			}
			if (objectGroup != null) {
				events.trigger(EventType.MENU_CREATED, this);
				if (elementController != null) {
					elementController.setMasterElement(objectGroup);
					addObject(elementController);
				}
				addObject(objectGroup);
			}
		}
	}

	public void centerElements() {
		centerElements(0.0F, -10.0F);
	}

	public void centerElements(float x, float y) {
		if (objectGroup != null) {
			objectGroup.calculateSize();
			MenuLayout.layout(objectGroup).center().at(x, y);
		}
	}

	public void buildControllerNeighbors() {
		if (elementController != null) {
			elementController.buildNeighbors();
		}
	}

	@Override
	public void show() {
		input.add(menuHeader);
		enableInput();
		super.show();
		if (objectGroup != null && elementController != null) {
			if (elementController.getSelected() == null) {
				elementController.setSelected(objectGroup.getDefaultElement());
			}
		}
	}

	public void enableInput() {
		if (elementController != null) {
			elementController.start();
		}
	}

	public void disableInput() {
		if (elementController != null) {
			elementController.stop();
		}
	}

	@Override
	public void hide() {
		super.hide();
		input.remove(menuHeader);
		disableInput();
	}

	public void backButtonPressed() {
		toPreviousScreen();
		audio.play(ActiveTheme.cancel);
	}

	@Override
	public void toPreviousScreen() {
		super.toPreviousScreen(fadeType);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		centerElements();
		updateController();
	}

	public void updateController() {
		if (elementController != null) {
			buildControllerNeighbors();
			elementController.syncCursor();
		}
	}

}
