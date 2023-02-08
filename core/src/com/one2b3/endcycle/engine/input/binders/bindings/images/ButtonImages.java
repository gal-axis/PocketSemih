package com.one2b3.endcycle.engine.input.binders.bindings.images;

import static com.one2b3.endcycle.engine.input.binders.bindings.images.ButtonImageType.KEYBOARD;
import static com.one2b3.endcycle.engine.input.binders.bindings.images.ButtonImageType.PLAYSTATION;
import static com.one2b3.endcycle.engine.input.binders.bindings.images.ButtonImageType.SWITCH;
import static com.one2b3.endcycle.engine.input.binders.bindings.images.ButtonImageType.XBOX;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.one2b3.endcycle.engine.assets.GameAsset;
import com.one2b3.endcycle.engine.assets.GameLoader;
import com.one2b3.endcycle.engine.graphics.DrawableImage;
import com.one2b3.endcycle.engine.graphics.DrawableImageFrame;
import com.one2b3.endcycle.engine.graphics.data.DrawableLoader;
import com.one2b3.utils.DrawableAnimation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ButtonImages implements GameAsset {

	// ===== KEYBOARD BUTTONS =====
	KEYBOARD_EMPTY(KEYBOARD, 117, 81, 16, 15), //
	KEYBOARD_ENTER(KEYBOARD, 192, 49, 42, 15), //
	KEYBOARD_ALT_RIGHT(KEYBOARD, 42, 81, 22, 15), //
	KEYBOARD_ALT_LEFT(KEYBOARD, 64, 81, 16, 15), //
	KEYBOARD_STAR(KEYBOARD, 160, 17, 16, 15), //
	KEYBOARD_BACKSPACE(KEYBOARD, 208, 17, 26, 15), //
	KEYBOARD_LEFT_BRACKET(KEYBOARD, 133, 81, 16, 15), //
	KEYBOARD_RIGHT_BRACKET(KEYBOARD, 149, 81, 16, 15), //
	KEYBOARD_CONTROL_RIGHT(KEYBOARD, 16, 81, 21, 15), //
	KEYBOARD_CONTROL_LEFT(KEYBOARD, 16, 81, 21, 15), //
	KEYBOARD_DEL(KEYBOARD, 208, 33, 26, 15), //
	KEYBOARD_END(KEYBOARD, 160, 65, 28, 15), //
	KEYBOARD_ESCAPE(KEYBOARD, 27, 1, 21, 15), //
	KEYBOARD_HOME(KEYBOARD, 13, 49, 35, 15), //
	KEYBOARD_MINUS(KEYBOARD, 234, 17, 16, 15), //
	KEYBOARD_PLUS(KEYBOARD, 234, 33, 16, 15), //
	KEYBOARD_PAGE_DOWN(KEYBOARD, 32, 17, 16, 15), //
	KEYBOARD_PAGE_UP(KEYBOARD, 16, 17, 16, 15), //
	KEYBOARD_SHIFT_RIGHT(KEYBOARD, 6, 65, 42, 15), //
	KEYBOARD_SHIFT_LEFT(KEYBOARD, 6, 65, 42, 15), //
	KEYBOARD_FORWARDSLASH(KEYBOARD, 234, 49, 16, 15), //
	KEYBOARD_BACKSLASH(KEYBOARD, 234, 65, 16, 15), //
	KEYBOARD_SPACE(KEYBOARD, 80, 81, 37, 15), //
	KEYBOARD_TAB(KEYBOARD, 16, 33, 32, 15), //
	//
	KEYBOARD_F1(KEYBOARD, 48, 1, 16, 15), //
	KEYBOARD_F2(KEYBOARD, 64, 1, 16, 15), //
	KEYBOARD_F3(KEYBOARD, 80, 1, 16, 15), //
	KEYBOARD_F4(KEYBOARD, 96, 1, 16, 15), //
	KEYBOARD_F5(KEYBOARD, 112, 1, 16, 15), //
	KEYBOARD_F6(KEYBOARD, 128, 1, 16, 15), //
	KEYBOARD_F7(KEYBOARD, 144, 1, 16, 15), //
	KEYBOARD_F8(KEYBOARD, 160, 1, 16, 15), //
	KEYBOARD_F9(KEYBOARD, 176, 1, 16, 15), //
	KEYBOARD_F10(KEYBOARD, 192, 1, 16, 15), //
	KEYBOARD_F11(KEYBOARD, 208, 1, 16, 15), //
	KEYBOARD_F12(KEYBOARD, 224, 1, 16, 15), //
	//
	KEYBOARD_DOWN(KEYBOARD, 192, 81, 16, 15), //
	KEYBOARD_UP(KEYBOARD, 192, 65, 16, 15), //
	KEYBOARD_LEFT(KEYBOARD, 176, 81, 16, 15), //
	KEYBOARD_RIGHT(KEYBOARD, 208, 81, 16, 15), //
	//
	KEYBOARD_0(KEYBOARD, 192, 17, 16, 15), //
	KEYBOARD_1(KEYBOARD, 48, 17, 16, 15), //
	KEYBOARD_2(KEYBOARD, 64, 17, 16, 15), //
	KEYBOARD_3(KEYBOARD, 80, 17, 16, 15), //
	KEYBOARD_4(KEYBOARD, 96, 17, 16, 15), //
	KEYBOARD_5(KEYBOARD, 112, 17, 16, 15), //
	KEYBOARD_6(KEYBOARD, 128, 17, 16, 15), //
	KEYBOARD_7(KEYBOARD, 144, 17, 16, 15), //
	KEYBOARD_8(KEYBOARD, 160, 17, 16, 15), //
	KEYBOARD_9(KEYBOARD, 176, 17, 16, 15), //
	//
	KEYBOARD_Q(KEYBOARD, 48, 33, 16, 15), //
	KEYBOARD_W(KEYBOARD, 64, 33, 16, 15), //
	KEYBOARD_E(KEYBOARD, 80, 33, 16, 15), //
	KEYBOARD_R(KEYBOARD, 96, 33, 16, 15), //
	KEYBOARD_T(KEYBOARD, 112, 33, 16, 15), //
	KEYBOARD_Y(KEYBOARD, 128, 33, 16, 15), //
	KEYBOARD_U(KEYBOARD, 144, 33, 16, 15), //
	KEYBOARD_I(KEYBOARD, 160, 33, 16, 15), //
	KEYBOARD_O(KEYBOARD, 176, 33, 16, 15), //
	KEYBOARD_P(KEYBOARD, 192, 33, 16, 15), //
	//
	KEYBOARD_A(KEYBOARD, 48, 49, 16, 15), //
	KEYBOARD_S(KEYBOARD, 64, 49, 16, 15), //
	KEYBOARD_D(KEYBOARD, 80, 49, 16, 15), //
	KEYBOARD_F(KEYBOARD, 96, 49, 16, 15), //
	KEYBOARD_G(KEYBOARD, 112, 49, 16, 15), //
	KEYBOARD_H(KEYBOARD, 128, 49, 16, 15), //
	KEYBOARD_J(KEYBOARD, 144, 49, 16, 15), //
	KEYBOARD_K(KEYBOARD, 160, 49, 16, 15), //
	KEYBOARD_L(KEYBOARD, 176, 49, 16, 15), //
	//
	KEYBOARD_Z(KEYBOARD, 48, 65, 16, 15), //
	KEYBOARD_X(KEYBOARD, 64, 65, 16, 15), //
	KEYBOARD_C(KEYBOARD, 80, 65, 16, 15), //
	KEYBOARD_V(KEYBOARD, 96, 65, 16, 15), //
	KEYBOARD_B(KEYBOARD, 112, 65, 16, 15), //
	KEYBOARD_N(KEYBOARD, 128, 65, 16, 15), //
	KEYBOARD_M(KEYBOARD, 144, 65, 16, 15), //

	// ===== XBOX360 BUTTONS =====
	XBOX_A(XBOX, 16, 16, 16, 16), //
	XBOX_X(XBOX, 16, 32, 16, 16), //
	XBOX_Y(XBOX, 16, 48, 16, 16), //
	XBOX_B(XBOX, 16, 64, 16, 16), //
	//
	XBOX_LB(XBOX, 161, 51, 14, 10), //
	XBOX_RB(XBOX, 161, 67, 14, 10), //
	XBOX_LT(XBOX, 162, 17, 12, 14), //
	XBOX_RT(XBOX, 162, 33, 12, 14), //
	//
	XBOX_BACK(XBOX, 64, 16, 16, 16), //
	XBOX_START(XBOX, 112, 16, 16, 16), //
	//
	XBOX_POV(XBOX, 48, 32, 16, 16), //
	XBOX_POV_RIGHT(XBOX, 64, 32, 16, 16), //
	XBOX_POV_DOWN(XBOX, 80, 32, 16, 16), //
	XBOX_POV_LEFT(XBOX, 96, 32, 16, 16), //
	XBOX_POV_UP(XBOX, 112, 32, 16, 16), //
	//
	XBOX_LEFT_AXIS(XBOX, 48, 64, 16, 16), //
	XBOX_LEFT_AXIS_RIGHT(XBOX, 64, 64, 16, 16), //
	XBOX_LEFT_AXIS_DOWN(XBOX, 80, 64, 16, 16), //
	XBOX_LEFT_AXIS_LEFT(XBOX, 96, 64, 16, 16), //
	XBOX_LEFT_AXIS_UP(XBOX, 112, 64, 16, 16), //
	XBOX_LEFT_AXIS_PRESS(XBOX, 128, 64, 16, 16), //
	//
	XBOX_RIGHT_AXIS(XBOX, 48, 48, 16, 16), //
	XBOX_RIGHT_AXIS_RIGHT(XBOX, 64, 48, 16, 16), //
	XBOX_RIGHT_AXIS_DOWN(XBOX, 80, 48, 16, 16), //
	XBOX_RIGHT_AXIS_LEFT(XBOX, 96, 48, 16, 16), //
	XBOX_RIGHT_AXIS_UP(XBOX, 112, 48, 16, 16), //
	XBOX_RIGHT_AXIS_PRESS(XBOX, 128, 48, 16, 16), //

	// ===== PS3 BUTTONS =====
	PS3_CROSS(PLAYSTATION, 16, 16, 16, 16), //
	PS3_SQUARE(PLAYSTATION, 16, 32, 16, 16), //
	PS3_TRIANGLE(PLAYSTATION, 16, 48, 16, 16), //
	PS3_CIRCLE(PLAYSTATION, 16, 64, 16, 16), //
	//
	PS3_L1(PLAYSTATION, 161, 51, 14, 11), //
	PS3_R1(PLAYSTATION, 161, 67, 14, 11), //
	PS3_L2(PLAYSTATION, 162, 17, 12, 14), //
	PS3_R2(PLAYSTATION, 162, 33, 12, 14), //
	//
	PS3_SELECT(PLAYSTATION, 16, 0, 20, 15), //
	PS3_START(PLAYSTATION, 37, 0, 24, 15), //
	//
	PS3_DPAD(PLAYSTATION, 48, 32, 16, 16), //
	PS3_DPAD_RIGHT(PLAYSTATION, 64, 32, 16, 16), //
	PS3_DPAD_DOWN(PLAYSTATION, 80, 32, 16, 16), //
	PS3_DPAD_LEFT(PLAYSTATION, 96, 32, 16, 16), //
	PS3_DPAD_UP(PLAYSTATION, 112, 32, 16, 16), //
	//
	PS3_LEFT_AXIS(PLAYSTATION, 48, 64, 16, 16), //
	PS3_LEFT_AXIS_RIGHT(PLAYSTATION, 64, 64, 16, 16), //
	PS3_LEFT_AXIS_DOWN(PLAYSTATION, 80, 64, 16, 16), //
	PS3_LEFT_AXIS_LEFT(PLAYSTATION, 96, 64, 16, 16), //
	PS3_LEFT_AXIS_UP(PLAYSTATION, 112, 64, 16, 16), //
	PS3_LEFT_AXIS_PRESS(PLAYSTATION, 128, 64, 16, 16), //
	//
	PS3_RIGHT_AXIS(PLAYSTATION, 48, 48, 16, 16), //
	PS3_RIGHT_AXIS_RIGHT(PLAYSTATION, 64, 48, 16, 16), //
	PS3_RIGHT_AXIS_DOWN(PLAYSTATION, 80, 48, 16, 16), //
	PS3_RIGHT_AXIS_LEFT(PLAYSTATION, 96, 48, 16, 16), //
	PS3_RIGHT_AXIS_UP(PLAYSTATION, 112, 48, 16, 16), //
	PS3_RIGHT_AXIS_PRESS(PLAYSTATION, 128, 48, 16, 16), //

	// ===== SWITCH BUTTONS =====
	SWITCH_B(SWITCH, 16, 16, 16, 16), //
	SWITCH_Y(SWITCH, 16, 32, 16, 16), //
	SWITCH_X(SWITCH, 16, 48, 16, 16), //
	SWITCH_A(SWITCH, 16, 64, 16, 16), //
	//
	SWITCH_L1(SWITCH, 129, 51, 14, 10), //
	SWITCH_R1(SWITCH, 145, 51, 14, 10), //
	SWITCH_L2(SWITCH, 160, 19, 16, 12), //
	SWITCH_R2(SWITCH, 160, 35, 16, 12), //
	//
	SWITCH_POV_RIGHT(SWITCH, 48, 32, 16, 16), //
	SWITCH_POV_DOWN(SWITCH, 64, 32, 16, 16), //
	SWITCH_POV_LEFT(SWITCH, 80, 32, 16, 16), //
	SWITCH_POV_UP(SWITCH, 96, 32, 16, 16), //
	//
	SWITCH_LEFT_AXIS(SWITCH, 48, 80, 16, 16), //
	SWITCH_LEFT_AXIS_RIGHT(SWITCH, 64, 80, 16, 16), //
	SWITCH_LEFT_AXIS_DOWN(SWITCH, 80, 80, 16, 16), //
	SWITCH_LEFT_AXIS_LEFT(SWITCH, 96, 80, 16, 16), //
	SWITCH_LEFT_AXIS_UP(SWITCH, 112, 80, 16, 16), //
	SWITCH_LEFT_AXIS_PRESS(SWITCH, 128, 80, 16, 16), //
	//
	SWITCH_RIGHT_AXIS(SWITCH, 48, 64, 16, 16), //
	SWITCH_RIGHT_AXIS_RIGHT(SWITCH, 64, 64, 16, 16), //
	SWITCH_RIGHT_AXIS_DOWN(SWITCH, 80, 64, 16, 16), //
	SWITCH_RIGHT_AXIS_LEFT(SWITCH, 96, 64, 16, 16), //
	SWITCH_RIGHT_AXIS_UP(SWITCH, 112, 64, 16, 16), //
	SWITCH_RIGHT_AXIS_PRESS(SWITCH, 128, 64, 16, 16), //
	//
	SWITCH_MINUS(SWITCH, 64, 16, 16, 16), //
	SWITCH_PLUS(SWITCH, 80, 16, 16, 16), //
	//
	MOUSE_LEFT(KEYBOARD, 1, 1, 11, 15), //
	MOUSE_RIGHT(KEYBOARD, 13, 1, 11, 15), //
	;

	final ButtonImageType type;
	final int x, y, w, h;
	@Getter
	TextureRegion region;
	@Getter
	final DrawableImage image = new DrawableImage();

	@Override
	public void load(GameLoader loader) {
		if (region == null) {
			TextureRegion buttons = DrawableLoader.get().loadTexture(type.getPath());
			region = new TextureRegion(buttons, x, y, w, h);
			DrawableImageFrame frame = new DrawableImageFrame(region, null, 0, 0, TextureFilter.Nearest,
					TextureWrap.ClampToEdge);
			image.setAnimation(new DrawableAnimation(0.0, frame));
		}
	}

	@Override
	public void dispose() {
		region = null;
	}

	public static void loadAll(GameLoader loader) {
		for (ButtonImages image : ButtonImages.values()) {
			image.dispose();
			if (loader == null) {
				image.load(loader);
			} else {
				loader.loadAsync(image);
			}
		}
	}
}
