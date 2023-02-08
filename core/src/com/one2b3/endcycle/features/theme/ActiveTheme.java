package com.one2b3.endcycle.features.theme;

import com.one2b3.endcycle.engine.audio.sound.SoundInfo;
import com.one2b3.endcycle.engine.graphics.DrawableId;
import com.one2b3.endcycle.engine.input.InputUtils;
import com.one2b3.endcycle.features.background.BackgroundCatalog;
import com.one2b3.endcycle.screens.background.BackgroundObject;
import com.one2b3.endcycle.screens.menus.elements.table.MenuTableTitleData;
import com.one2b3.endcycle.utils.ID;
import com.one2b3.utils.CColor;

public final class ActiveTheme {

	public static final ID id = new ID(-1, -1);

	public static final BackgroundObject background = new BackgroundObject(null);
	public static final DrawableImageCopy logo = new DrawableImageCopy();
	public static final DrawableImageCopy logoIntro = new DrawableImageCopy();
	public static final MenuThemeDrawable button = new MenuThemeDrawable();
	public static final MenuThemeDrawable panel = new MenuThemeDrawable();
	public static final MenuThemeDrawable container = new MenuThemeDrawable();
	public static final MenuThemeDrawable containerTitle = new MenuThemeDrawable();
	public static final MenuThemeDrawable bigMessage = new MenuThemeDrawable();
	public static final MenuThemeDrawable smallMessage = new MenuThemeDrawable();
	public static final MenuThemeDrawable mobileButton = new MenuThemeDrawable();

	public static final MenuTableTitleData titleData = new MenuTableTitleData();

	public static final DrawableImageCopy header = new DrawableImageCopy();
	public static final DrawableImageCopy headerBar = new DrawableImageCopy();
	public static final DrawableImageCopy headerBack = new DrawableImageCopy();

	public static final CColor menuColor = new CColor();
	public static final CColor defaultColor = new CColor();
	public static final CColor disabledColor = new CColor();
	public static final CColor selectColor = new CColor();
	public static final CColor titleColor = new CColor();

	public static final ID menuSong = new ID();
	public static final ID vocSong = new ID();
	public static final ID campaignSong = new ID();
	public static final ID helpSong = new ID();
	public static final ID adventureSong = new ID();

	public static final SoundInfo navigate = new SoundInfo(), //
			select = new SoundInfo(), //
			cancel = new SoundInfo(), //
			open = new SoundInfo(), //
			close = new SoundInfo(), //
			error = new SoundInfo();

	public static boolean tintBackground, tintHeader, tintHeaderBar, tintHeaderBack;

	public static void reload() {
		load(id);
	}

	public static void load(ID themeId) {
		MenuTheme theme = MenuThemeCatalog.get(themeId);
		if (theme == null) {
			theme = MenuThemeCatalog.get(MenuThemes.Default.getId());
			if (theme == null) {
				id.set(themeId);
				return;
			}
		}
		load(theme);
	}

	public static void load(MenuTheme theme) {
		id.set(theme.getId());

		menuSong.set(theme.menuSong);
		vocSong.set(theme.vocSong);
		campaignSong.set(theme.campaignSong);
		helpSong.set(theme.helpSong);
		adventureSong.set(theme.adventureSong);

		background.createFrames(BackgroundCatalog.get(theme.background));
		set(logo, theme.logo);
		set(logoIntro, theme.logoIntro);

		button.set(theme.button);
		panel.set(theme.panel);
		container.set(theme.container);
		containerTitle.set(theme.containerTitle);
		bigMessage.set(theme.bigMessage);
		smallMessage.set(theme.smallMessage);
		mobileButton.set(theme.mobileButton);

		titleData.set(theme.containerTitleData);

		set(header, theme.header);
		set(headerBar, theme.headerBar);
		set(headerBack, theme.headerBack);

		menuColor.set(theme.menuColor);
		defaultColor.set(theme.defaultColor);
		disabledColor.set(theme.disabledColor);
		selectColor.set(theme.selectColor);
		titleColor.set(theme.titleColor);
		InputUtils.loadCursor(theme.selectColor);

		tintBackground = theme.tintBackground;
		tintHeader = theme.tintHeader;
		tintHeaderBar = theme.tintHeaderBar;
		tintHeaderBack = theme.tintHeaderBack;

		navigate.set(theme.soundNavigate);
		select.set(theme.soundSelect);
		cancel.set(theme.soundCancel);
		open.set(theme.soundOpen);
		close.set(theme.soundClose);
		error.set(theme.soundError);
	}

	private static void set(DrawableImageCopy image, DrawableId id) {
		image.color = id.color;
		image.image = id.getImage();
	}

}
