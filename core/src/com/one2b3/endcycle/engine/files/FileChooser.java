package com.one2b3.endcycle.engine.files;

import com.badlogic.gdx.files.FileHandle;

public interface FileChooser {

	void chooseFile(String title, String fileType, FileChooserListener listener);

	public static interface FileChooserListener {
		void fileChosen(FileHandle handle);
	}
}
