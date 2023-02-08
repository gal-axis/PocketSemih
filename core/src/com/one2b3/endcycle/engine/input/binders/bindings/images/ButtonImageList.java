package com.one2b3.endcycle.engine.input.binders.bindings.images;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.one2b3.endcycle.engine.graphics.DrawableImage;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ButtonImageList {

	List<ButtonImageEntry> images = new ArrayList<>();
	transient Map<Integer, DrawableImage> drawables = new HashMap<>();

	public void add(ButtonImages image, int... buttons) {
		for (int button : buttons) {
			images.add(new ButtonImageEntry(button, image));
		}
	}

	public void update() {
		drawables.clear();
		for (ButtonImageEntry button : images) {
			ButtonImages image = button.getDrawable();
			if (image != null) {
				drawables.put(button.getButton(), image.getImage());
			}
		}
	}

	public void clear() {
		images.clear();
		drawables.clear();
	}

	public void set(int button, ButtonImages image) {
		ButtonImageEntry entry = new ButtonImageEntry(button, image);
		images.remove(entry);
		images.add(entry);
		drawables.put(button, image.getImage());
	}

	public DrawableImage getDirect(int button) {
		return drawables.get(button);
	}

	public DrawableImage get(int button) {
		DrawableImage drawable = getDirect(button);
		if (drawable == null) {
			drawable = createUnknownImage(button);
			drawables.put(button, drawable);
		}
		return drawable;
	}

	public UnknownButtonImageDrawable createUnknownImage(int button) {
		return new UnknownButtonImageDrawable(button);
	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	@EqualsAndHashCode(of = "button")
	public static class ButtonImageEntry {
		int button;
		ButtonImages drawable;
	}
}
