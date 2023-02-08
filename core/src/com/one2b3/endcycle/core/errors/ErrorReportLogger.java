package com.one2b3.endcycle.core.errors;

import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import com.badlogic.gdx.ApplicationLogger;

public class ErrorReportLogger implements UncaughtExceptionHandler, ApplicationLogger {

	final ErrorCache errors = new ErrorCache();

	final StringWriter stringWriter;
	final ApplicationLogger logger;
	final boolean server;

	public ErrorReportLogger(ApplicationLogger logger, boolean server) {
		this.logger = logger;
		this.server = server;
		this.stringWriter = new StringWriter(2048);
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread arg0, Throwable arg1) {
		error("ExceptionCatcher", "Uncaught exception", arg1);
	}

	@Override
	public void log(String tag, String message) {
		logger.log(tag, message);
	}

	@Override
	public void log(String tag, String message, Throwable exception) {
		logger.log(tag, message, exception);
	}

	@Override
	public void error(String tag, String message) {
		logger.error(tag, message);
	}

	@Override
	public void error(String tag, String message, Throwable exception) {
		logger.error(tag, message, exception);
		sendException(tag, message, exception);
	}

	@Override
	public void debug(String tag, String message) {
		logger.debug(tag, message);
	}

	@Override
	public void debug(String tag, String message, Throwable exception) {
		logger.debug(tag, message, exception);
	}

	public void sendException(String tag, String message, Throwable exception) {
	}

}
