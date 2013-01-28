package com.liminul.libgdx.utils;

import com.badlogic.gdx.Gdx;

// TODO: Add debug switch
public class Log {
	public static void d(final Class<?> tag, final String message) {
		Gdx.app.debug(tag.getSimpleName(), message);
	}

	public static void e(final Class<?> tag, final String message) {
		Gdx.app.error(tag.getSimpleName(), message);
	}

	public static void e(final Class<?> tag, final String message, final Throwable e) {
		Gdx.app.error(tag.getSimpleName(), message, e);
	}

	public static void i(final Class<?> tag, final String message) {
		Gdx.app.log(tag.getSimpleName(), message);
	}
}
