package com.liminul.libgdx.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.badlogic.gdx.files.FileHandle;

// TODO: Documentation
// TODO: Synchronization?
// TODO: final defaultBundle / dependence on Locale being first
public class GetText {
	private static final String STRINGS_FILENAME = "strings";

	private static Locale locale = Locale.getDefault();
	private static final ResourceBundle defaultBundle = getDefaultBundle();
	private static ResourceBundle bundle = defaultBundle;

	public static ResourceBundle getBundle(final Locale locale) {
		if (GetText.locale.equals(locale)) {
			return bundle;
		}

		if (Locale.getDefault().equals(locale)) {
			return defaultBundle;
		}

		List<FileHandle> handles = buildHandles(locale, false);

		if (handles.isEmpty()) {
			return defaultBundle;
		} else {
			return buildBundle(handles);
		}
	}

	public static Locale getLocale() {
		return locale;
	}

	public static String getString(final String key) {
		return bundle.getString(key);
	}

	public static void load(final Locale locale) {
		bundle = getBundle(locale);

		GetText.locale = locale;
	}

	private static ResourceBundle buildBundle(final List<FileHandle> handles) {
		if (handles.isEmpty()) {
			return defaultBundle;
		}

		ResourceBundle bundle = defaultBundle;

		try {
			for (FileHandle handle : handles) {
				bundle = new PropertyResourceBundleWithParent(handle.read(), bundle);
			}
		} catch (final Exception e) {
			Log.e(GetText.class, e);
		}

		return bundle;
	}

	private static List<FileHandle> buildHandles(final Locale locale, final boolean isDefault) {
		final List<FileHandle> handles = new ArrayList<>(isDefault ? 3 : 2);

		if (isDefault) {
			handles.add(getStringsFileHandle(STRINGS_FILENAME));
		}

		handles.add(getStringsFileHandle(STRINGS_FILENAME + "_" + locale.getLanguage()));
		handles.add(getStringsFileHandle(STRINGS_FILENAME + "_" + locale));

		handles.removeAll(Collections.singletonList(null));

		return handles;
	}

	private static ResourceBundle getDefaultBundle() {
		return buildBundle(buildHandles(locale, true));
	}

	private static FileHandle getStringsFileHandle(final String name) {
		try {
			return Assets.getProperties(name);
		} catch (final MissingResourceException e) {
			return null;
		}
	}

	private static class PropertyResourceBundleWithParent extends PropertyResourceBundle {
		public PropertyResourceBundleWithParent(final InputStream stream, final ResourceBundle parent) throws IOException {
			super(new InputStreamReader(stream, StandardCharsets.UTF_8));

			setParent(parent);
		}
	}
}
