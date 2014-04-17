package com.liminul.libgdx.utils;

import java.io.File;
import java.util.MissingResourceException;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Assets {
	private static final String ASSET_MISSING = "Missing asset: %s";

	private static final String PROPS_DIR = "props";
	private static final String PROPS_EXT = ".properties";

	public static FileHandle getProperties(final String name) throws MissingResourceException {
		final String filename = PROPS_DIR + File.separator + name + PROPS_EXT;

		try {
			FileHandle handle = Gdx.files.internal(filename);

			if (handle.exists()) {
				return handle;
			}
		} catch (final GdxRuntimeException e) {
			// Fall through to below Exception
		}

		throw new MissingResourceException(String.format(ASSET_MISSING, filename), FileType.Internal.toString(), filename);
	}
}
