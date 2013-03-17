package com.htwk.masterprojekt.hoerbuch.media;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Element;

import android.media.MediaMetadataRetriever;

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
	 * Generates a list of the files located in the given directory.
	 * 
	 * @param dir
	 *            directory to be used
	 */
	public List<MediaFile> getList(File dir) {

		// map contains <path, filename>
		List<MediaFile> mediaFiles = new ArrayList<MediaFile>();
		if (dir.isFile()) {
			files = dir.getParentFile().listFiles(new AudioFilter());
		} else {
			files = dir.listFiles(new AudioFilter());
		}
		fileNames = new ArrayList<String>();
		paths = new ArrayList<String>();
		for (File file : files) {
			MediaFile mediaFile = new MediaFile();
			mediaFile.setPath(file.getPath());
			mediaFile.setTitle(extractMP3Meta(file.getAbsolutePath()));
			mediaFiles.add(mediaFile);
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
