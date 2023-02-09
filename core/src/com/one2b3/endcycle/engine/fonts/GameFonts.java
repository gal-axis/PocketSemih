package com.one2b3.endcycle.engine.fonts;

import com.one2b3.endcycle.engine.fonts.bitmap.BMFonts;

public class GameFonts {

	public static GameFont Title = new LocalizedFont(BMFonts.ChakraPetch), //
			Heading = BMFonts.ChakraPetch, //
			Text = BMFonts.ChakraPetch, //
			Small = BMFonts.ShareTech, //
			Tiny = BMFonts.JetbrainsMono, //
			Monospace = BMFonts.ShareTechMono, //

			TitleBorder = BMFonts.ChakraPetch.getBorder(), //
			HeadingBorder = BMFonts.ChakraPetch.getBorder(), //
			TextBorder = BMFonts.ChakraPetch.getBorder(), //
			SmallBorder = BMFonts.ShareTech.getBorder(), //
			TinyBorder = BMFonts.JetbrainsMono.getBorder(), //
			MonospaceBorder = BMFonts.ShareTechMono.getBorder() //
	;
}
