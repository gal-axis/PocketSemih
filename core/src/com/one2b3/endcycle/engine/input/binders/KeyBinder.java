package com.one2b3.endcycle.engine.input.binders;

import java.util.List;

import com.badlogic.gdx.controllers.Controller;
import com.one2b3.endcycle.engine.graphics.DrawableImage;
import com.one2b3.endcycle.engine.input.KeyCode;
import com.one2b3.endcycle.engine.input.KeyCodeCategory;
import com.one2b3.endcycle.engine.input.binders.bindings.images.ButtonImages;
import com.one2b3.endcycle.engine.proguard.KeepClass;

@KeepClass
public interface KeyBinder {

	boolean isChanged();

	void reset();

	void load(Controller controller);

	void load();

	void save();

	void setKey(KeyCode code, int key);

	void removeKey(KeyCode code, int key);

	void setButtonImage(int button, ButtonImages image);

	Integer getKey(KeyCode code);

	boolean isKey(KeyCode code, int button);

	List<Integer> getKeys(KeyCode code);

	KeyCode getCode(KeyCodeCategory category, int button);

	void clearImages();

	DrawableImage getButtonImage(KeyCode code);

	List<DrawableImage> getButtonImages(KeyCode code);

	void clear();
}
