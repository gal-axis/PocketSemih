package com.one2b3.endcycle.engine.language;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class BundleInfo {
	public final Class<? extends Enum<? extends LocalizedMessage>> catalog;
	public final String group;

	public String getPath() {
		String path = "localization/" + group;
		return path;
	}
}