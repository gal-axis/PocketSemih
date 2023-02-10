package com.bikinger.semih;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.bikinger.semih.clicker.HiddenSemihEvent;
import com.bikinger.semih.clicker.SemihClicker;
import com.bikinger.semih.clicker.SemihPoints;
import com.one2b3.endcycle.core.Cardinal;
import com.one2b3.endcycle.engine.fonts.GameFonts;
import com.one2b3.endcycle.engine.screens.Layers;
import com.one2b3.endcycle.features.theme.ActiveTheme;
import com.one2b3.endcycle.screens.menus.Colors;
import com.one2b3.endcycle.screens.menus.MenuScreen;
import com.one2b3.endcycle.screens.menus.elements.MenuElement;
import com.one2b3.endcycle.screens.menus.elements.MenuLayout;
import com.one2b3.endcycle.screens.menus.elements.anchors.MenuAnchors;
import com.one2b3.endcycle.screens.menus.elements.buttons.MenuButton;
import com.one2b3.endcycle.screens.menus.elements.group.MenuElementGroup;

public class SemihScreen extends MenuScreen {

	SemihPoints points;
	SemihClicker clicker;
	HiddenSemihEvent hiddenSemihevent;

	MenuElementGroup shopButtons;

	public SemihScreen() {
		super(null, Color.SKY);
		points = new SemihPoints();
		clicker = new SemihClicker(points);
		hiddenSemihevent = new HiddenSemihEvent(clicker);
	}

	@Override
	public void init() {
		super.init();
		addObject(points);
		addObject(clicker);
		addObject(hiddenSemihevent);
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		background.getColor().set(points.isFever() ? Colors.rainbow : Color.SKY);
	}

	@Override
	public void createMenuElements() {
		Cardinal.DEBUGGING = false;
		MenuButton shopButton = new MenuButton("Shop");
		shopButton.setFont(GameFonts.Monospace);
		MenuLayout.layout(shopButton).rightOf(MenuAnchors.LEFT, 5).topInner().y(-5).width(60).height(30);
		objectGroup.addObject(shopButton);

		shopButton.setAction(this::toggleShop);

		shopButtons = new MenuElementGroup();
		shopButtons.setBackground(ActiveTheme.container);
		shopButtons.addObject(
				new ShopButton(points, "Chadih", "+1 Banana bei Semih Klick", "chadih.png", 1000, this::buyChadih));
		shopButtons.addObject(
				new ShopButton(points, "Surfih", "*2 Banana beim Surfie Event", "surfih1.png", 4000, this::buySurfih));
		shopButtons.addObject(
				new ShopButton(points, "Oma Semih", "+100 Banana alle 10 sec", "bananabread.png", 10000, this::buyOma));
		shopButtons.addObject(
				new ShopButton(points, "banana", "erhöht Bananamplier um 1", "banana.png", 4000, this::buybanana));
		shopButtons.addObject(new ShopButton(points, "soapih", "", "soapih.png", 4000, this::buySoapih));
		shopButtons.addObject(new ShopButton(points, "Surfih", "", "surfih1.png", 4000, this::buySurfih));
		shopButtons.addObject(new ShopButton(points, "Surfih", "", "surfih1.png", 4000, this::buySurfih));
		shopButtons.addObject(new ShopButton(points, "Monkey D. Semih", "", "coolih.png", 1000000, this::monkeyDSemih));
		alignButtons();
		shopButtons.calculateSize();
		MenuLayout.layout(shopButtons).center();

		objectGroup.addObject(shopButtons);
		objectGroup.setLayer(Layers.LAYER_MENU);
		shopButtons.setHidden(true);
	}

	private void alignButtons() {
		List<MenuElement> buttons = shopButtons.getObjects();
		MenuElement last = buttons.get(0);
		for (int i = 1; i < buttons.size(); i++) {
			MenuElement button = buttons.get(i);
			if (i % 2 == 0) {
				MenuLayout.layout(button).bottomOf(last, 5.0F);
				last = button;
			} else {
				MenuLayout.layout(button).rightOf(last, 5.0F);
			}
		}
	}

	public void toggleShop() {
		shopButtons.setHidden(!shopButtons.isHidden());
		clicker.setEnabled(shopButtons.isHidden());
	}

	public void buyChadih() {
		clicker.clickBananas++;
	}

	public void buySurfih() {
		clicker.surfBananas += 10;
	}

	public void buyOma() {
//		clicker.clickBananas += 100;
	}

	public void buySoapih() {

	}

	public void buybanana() {
		// amplier erhöhen
	}

	public void monkeyDSemih() {
		// thank you for playing
	}

	@Override
	public void centerElements(float x, float y) {
	}
}
