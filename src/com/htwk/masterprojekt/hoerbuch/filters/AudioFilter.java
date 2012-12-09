package com.htwk.masterprojekt.hoerbuch.filters;

import java.io.File;
import java.io.FileFilter;
import java.util.Locale;

//class to limit the choices shown when browsing to SD card to media files
public class AudioFilter implements FileFilter {

	// only want to see the following audio file types
	private static final String[] EXTENSIONS = { ".aac", ".mp3", ".wav",
			".ogg", ".3gp", ".mp4", ".m4a", ".amr", ".m4a", ".m4b" };

	@Override
	public boolean accept(File pathname) {

		// if we are looking at a directory/file that's not hidden we want to
		// see it so return TRUE
		if (pathname.isDirectory() && !pathname.isHidden()) {
			return true;
		}

		// loops through and determines the extension of all files in the
		// directory
		// returns TRUE to only show the audio files defined in the String[]
		// extension array
		for (String ext : EXTENSIONS) {
			if (pathname.getName().toLowerCase(Locale.US).endsWith(ext)) {
				return true;
			}
		}

		return false;
	}
}
