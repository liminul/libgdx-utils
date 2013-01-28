package com.liminul.libgdx.utils;

import java.io.File;
import java.util.MissingResourceException;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Assets {
	private static final String	ASSET_MISSING	= "Missing asset";

	private static final String	PROPS_DIR		= "props";
	private static final String	PROPS_EXT		= ".properties";

	public static FileHandle getProperties(final String name) throws MissingResourceException {
		final String filename = PROPS_DIR + File.separator + name + PROPS_EXT;

		try {
			return Gdx.files.internal(filename);
		} catch (final GdxRuntimeException e) {
			throw new MissingResourceException(ASSET_MISSING, FileType.Internal.toString(), filename);
		}
	}
}
