package com.htwk.masterprojekt.hoerbuch.media;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.media.MediaMetadataRetriever;
import android.util.Log;

import com.htwk.masterprojekt.hoerbuch.filters.AudioFilter;

public class MediaFileManager {
	private static final String TAG = "MediaFileManager";
	// will contain the filenames and corresponding paths.
	private List<String> fileNames;
	private List<String> paths;
	private File[] files;

	final String MEDIA_PATH = new String("/sdcard/");
	private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
	private static AudioFilter filter = new AudioFilter();

	/**
	 * Generates a map of the files located in the given directory.
	 * 
	 * @param dir
	 *            directory to be used
	 */
	public HashMap<String, String> getList(File dir) {

		// map contains <path, filename>
		HashMap<String, String> mediaFiles = new HashMap<String, String>();
		if (dir.isFile()) {
			files = dir.getParentFile().listFiles(new AudioFilter());
		} else {
			files = dir.listFiles(new AudioFilter());
		}
		fileNames = new ArrayList<String>();
		paths = new ArrayList<String>();
		for (File file : files) {

			mediaFiles.put(file.getPath(),
					extractMP3Meta(file.getAbsolutePath()));
		}
		return mediaFiles;
	}

	private String extractMP3Meta(String filePath) {
		StringBuilder sb = new StringBuilder();
		if (new File(filePath).isFile()) {
			MediaMetadataRetriever mmr = new MediaMetadataRetriever();
			mmr.setDataSource(filePath);

			String titleName = mmr
					.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
			String artistName = mmr
					.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
			sb.append(artistName);
			sb.append(" - ");
			sb.append(titleName);

		} else {
			sb.append("<< ");
			sb.append(new File(filePath).getName());
			sb.append(" >>");
		}

		return sb.toString();
	}
}
