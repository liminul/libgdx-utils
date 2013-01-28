package com.liminul.libgdx.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.badlogic.gdx.files.FileHandle;

public class Gettext {
	private static class PropertyResourceBundleWithParent extends PropertyResourceBundle {
		public PropertyResourceBundleWithParent(final InputStream stream, final ResourceBundle parent) throws IOException {
			super(stream);

			setParent(parent);
		}
	}

	private static final String		STRINGS_FILENAME	= "strings";

	private static ResourceBundle	bundle;
	private static ResourceBundle	defaultBundle;

	static {
		init(Locale.getDefault());
	}

	private static void buildBundle(final List<FileHandle> handles) {
		try {
			bundle = new PropertyResourceBundleWithParent(handles.get(0).read(), defaultBundle);

			for (int i = 1; i < handles.size(); i++) {
				bundle = new PropertyResourceBundleWithParent(handles.get(i).read(), bundle);
			}
		} catch (final IOException e) {
			bundle = null;
		}
	}

	public static String getString(final String key) {
		return bundle.getString(key);
	}

	private static FileHandle getStringsFile(final String name) {
		try {
			return Assets.getProperties(name);
		} catch (final MissingResourceException e) {
			return null;
		}
	}

	public static void init(final Locale locale) {
		final boolean isDefaultLocale = Locale.getDefault().equals(locale);

		if (isDefaultLocale && defaultBundle != null) {
			bundle = defaultBundle;

			return;
		}

		final List<FileHandle> handles = new ArrayList<FileHandle>(3);

		if (isDefaultLocale) {
			handles.add(getStringsFile(STRINGS_FILENAME));
		}

		handles.add(getStringsFile(STRINGS_FILENAME + "_" + locale.getLanguage()));
		handles.add(getStringsFile(STRINGS_FILENAME + "_" + locale));

		handles.removeAll(Collections.singletonList(null));

		if (handles.size() > 0) {
			buildBundle(handles);

			if (isDefaultLocale) {
				defaultBundle = bundle;
			}
		}
	}
}
