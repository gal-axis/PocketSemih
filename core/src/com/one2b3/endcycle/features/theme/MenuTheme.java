package com.one2b3.endcycle.features.theme;

import com.badlogic.gdx.graphics.Color;
import com.one2b3.endcycle.engine.audio.Songs;
import com.one2b3.endcycle.engine.audio.music.MusicCatalog;
import com.one2b3.endcycle.engine.audio.sound.SoundInfo;
import com.one2b3.endcycle.engine.graphics.DrawableId;
import com.one2b3.endcycle.engine.graphics.Drawables;
import com.one2b3.endcycle.engine.language.LocalizedMessage;
import com.one2b3.endcycle.engine.language.Unlocalize;
import com.one2b3.endcycle.engine.proguard.KeepClass;
import com.one2b3.endcycle.features.background.BackgroundCatalog;
import com.one2b3.endcycle.features.background.Backgrounds;
import com.one2b3.endcycle.features.models.Description;
import com.one2b3.endcycle.features.models.connect.ConnectsTo;
import com.one2b3.endcycle.screens.menus.Colors;
import com.one2b3.endcycle.screens.menus.elements.table.MenuTableTitleData;
import com.one2b3.endcycle.utils.ID;
import com.one2b3.endcycle.utils.objects.DataName;
import com.one2b3.modding.diff.NamedModdable;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@KeepClass
@FieldDefaults(level = AccessLevel.PUBLIC)
@Description("Visuals and colors for the menus that the player can switch between")
public final class MenuTheme extends NamedModdable implements DataName {

	@Description("The name shown in the toolbox and in-game")
	LocalizedMessage name = Unlocalize.get("Menu Theme");
	@Description("When ticked, the menu theme will be changed to automatically after unlocking it")
	boolean automatic;
	@Description("The menu theme can only be unlocked when this is ticked")
	boolean visible;

	@Description("The main song of the theme")
	@ConnectsTo(MusicCatalog.class)
	ID menuSong = Songs.Main_Theme.getId();
	@Description("The song that plays in the VOC menu")
	@ConnectsTo(MusicCatalog.class)
	ID vocSong = Songs.Voc_Menu.getId();
	@Description("The song that plays in the campaign select menu")
	@ConnectsTo(MusicCatalog.class)
	ID campaignSong = Songs.Campaign.getId();
	@Description("The song that plays in the help menu")
	@ConnectsTo(MusicCatalog.class)
	ID helpSong = Songs.Tutorials_and_Tips.getId();
	@Description("The song that plays in the adventure menu")
	@ConnectsTo(MusicCatalog.class)
	ID adventureSong = Songs.Embark.getId();

	@Description("The color that is used for many background elements")
	Color menuColor = Colors.MENU_COLOR;
	@Description("The color that is usually used for menu elements")
	Color defaultColor = Colors.MENU_DEFAULT_COLOR;
	@Description("The color that is used for disabled menu elements")
	Color disabledColor = Colors.MENU_DISABLED_COLOR;
	@Description("The color that is used for the header and selected menu elements")
	Color selectColor = Colors.MENU_SELECT_COLOR;
	@Description("The color that is used for titles")
	Color titleColor = Colors.MENU_TITLE_COLOR;

	@Description("The background that will be used for the menu")
	@ConnectsTo(BackgroundCatalog.class)
	ID background = Backgrounds.Menu.getId();
	@Description("Whether or not to tint the background in menus")
	boolean tintBackground = true;

	@Description("The logo of the game when using this menu theme")
	DrawableId logo = new DrawableId(Drawables.New_Logo);
	@Description("The animation that plays before the logo flash on the title screen")
	DrawableId logoIntro = new DrawableId();

	@Description("The drawable that represents buttons")
	MenuThemeDrawableData button = new MenuThemeDrawableData(Drawables.button_9patch);
	@Description("The drawable that represents panels")
	MenuThemeDrawableData panel = new MenuThemeDrawableData(Drawables.description_9patch);
	@Description("The drawable that represents tables")
	MenuThemeDrawableData container = new MenuThemeDrawableData(Drawables.message_9patch);
	@Description("The drawable that represents tables with a title")
	MenuThemeDrawableData containerTitle = new MenuThemeDrawableData(Drawables.voxel_pack_9patch, 18.0F);
	@Description("Extra data for tables with titles")
	MenuTableTitleData containerTitleData = new MenuTableTitleData();
	@Description("The drawable that represents bigger messages with content")
	MenuThemeDrawableData bigMessage = new MenuThemeDrawableData(Drawables.message_9patch);
	@Description("The drawable that represents small popup messages")
	MenuThemeDrawableData smallMessage = new MenuThemeDrawableData(Drawables.small_message_9patch);
	@Description("The drawable that represents battle buttons on mobile platforms")
	MenuThemeDrawableData mobileButton = new MenuThemeDrawableData(Drawables.Mobile_Button_9P);

	@Description("The header bar that contains the screen titles")
	DrawableId header = new DrawableId(Drawables.Menu_Header);
	@Description("When ticked ,colors over the header with the select color")
	boolean tintHeader = true;
	@Description("The drawable that is repeated after the header")
	DrawableId headerBar = new DrawableId(Drawables.Menu_Header_Bar);
	@Description("When ticked, colors over the header bar with the select color")
	boolean tintHeaderBar = true;
	@Description("The icon for the back button on the header")
	DrawableId headerBack = new DrawableId(Drawables.Menu_Header_Back);
	@Description("When ticked, colors over the back button with the title color")
	boolean tintHeaderBack = true;

	@Description("Plays when moving between menu elements")
	SoundInfo soundNavigate = new SoundInfo();
	@Description("Plays when a menu element is selected")
	SoundInfo soundSelect = new SoundInfo();
	@Description("Plays when cancelling a menu action")
	SoundInfo soundCancel = new SoundInfo();
	@Description("Plays when a menu message is opened")
	SoundInfo soundOpen = new SoundInfo();
	@Description("Plays when a menu message is closed")
	SoundInfo soundClose = new SoundInfo();
	@Description("Plays when trying to select invalid options")
	SoundInfo soundError = new SoundInfo();

	@Override
	public String getName() {
		return name.format();
	}

	@Override
	public String getDataName() {
		return ID.combine(id, getName());
	}

}
