package com.one2b3.endcycle.core.errors;

import com.one2b3.endcycle.engine.EngineProperties;
import com.one2b3.endcycle.engine.proguard.KeepClass;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@KeepClass
public class ErrorReport {

	public int gameVersion = EngineProperties.CLIENT_VERSION;
	public String error, session;

	public ErrorReport(String error, String session) {
		this.error = error;
		this.session = session;
	}
}
