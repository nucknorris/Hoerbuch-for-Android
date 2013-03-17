package com.htwk.masterprojekt.hoerbuch.media;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.media.MediaMetadataRetriever;
import android.util.Log;

import com.htwk.masterprojekt.hoerbuch.Utils;
import com.htwk.masterprojekt.hoerbuch.filters.AudioFilter;

public class MediaFileManager {
	private static final String TAG = "MediaFileManager";
	// will contain the filenames and corresponding paths.
	private File[] files;
	MediaMetadataRetriever mmr = new MediaMetadataRetriever();

	/**
	 * Generates a list of the files located in the given directory.
	 * 
	 * @param dir
	 *            directory to be used
	 */
	public List<MediaFile> getList(File dir) {
		Log.d(TAG, "get the List");
		// map contains <path, filename>
		List<MediaFile> mediaFiles = new ArrayList<MediaFile>();
		if (dir.isFile()) {
			files = dir.getParentFile().listFiles(new AudioFilter());
		} else {
			files = dir.listFiles(new AudioFilter());
		}
		for (File file : files) {
			MediaFile mediaFile = new MediaFile();
			String fileName = file.getAbsolutePath();
			mediaFile.setPath(file.getPath());
			mediaFile.setTitle(extractTitel(fileName));
			try {
				mediaFile.setDuration(Double
						.parseDouble(extractDuration(fileName)));
			} catch (Exception ex) {
			}
			mediaFile.setFileNameLong(fileName);
			mediaFile.setFileNameShort(Utils.rsplit("/", fileName));
			mediaFile.setArtist(extractArtist(fileName));
			mediaFiles.add(mediaFile);
		}
		return mediaFiles;
	}

	// get the titel of a file or dir
	private String extractTitel(String filePath) {
		StringBuilder sb = new StringBuilder();
		if (new File(filePath).isFile()) {
			mmr.setDataSource(filePath);
			String titleName = mmr
					.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
			sb.append(titleName);
		} else {
			sb.append(new File(filePath).getName());
		}
		return sb.toString();
	}

	// get the duration von the mediafile
	private String extractDuration(String filePath) {
		if (new File(filePath).isFile()) {
			mmr.setDataSource(filePath);
			return mmr
					.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
		} else {
			return "";
		}
	}

	// get the auhor of the mediafile
	private String extractArtist(String filePath) {
		if (new File(filePath).isFile()) {
			mmr.setDataSource(filePath);
			return mmr
					.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
		} else {
			return "";
		}
	}
}
