package com.liminul.libgdx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Logger;

public class Log {
	static {
		// TODO: Debug switch?
		Gdx.app.setLogLevel(Logger.DEBUG);
	}

	public static void d(final Class<?> tag, final String message) {
		Gdx.app.debug(tag.getSimpleName(), message);
	}

	public static void e(final Class<?> tag, final Throwable e) {
		e(tag, null, e);
	}

	public static void e(final Class<?> tag, final String message, final Throwable e) {
		Gdx.app.error(tag.getSimpleName(), message, e);
	}

	public static void i(final Class<?> tag, final String message) {
		Gdx.app.log(tag.getSimpleName(), message);
	}
}
