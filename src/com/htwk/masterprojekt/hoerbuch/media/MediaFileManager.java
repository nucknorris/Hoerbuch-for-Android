package com.htwk.masterprojekt.hoerbuch.media;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import com.htwk.masterprojekt.hoerbuch.db.DatabaseHandler;
import com.htwk.masterprojekt.hoerbuch.filters.AudioFilter;

public class MediaFileManager {
	private static final String TAG = "MediaFileManager";
	// will contain the filenames and corresponding paths.
	private File[] files;
	DatabaseHandler dbh;
	Context c;
	List<MediaFile> mediaFiles;
	MediaMetadataRetriever mmr;

	public MediaFileManager(Context context) {
		this.c = context;
		dbh = new DatabaseHandler(c);
		mediaFiles = new ArrayList<MediaFile>();
	}

	/**
	 * Generates a list of the files located in the given directory.
	 * 
	 * @param dir
	 *            directory to be used
	 */
	public List<MediaFile> getList(File dir) {
		Log.d(TAG, "get the List");
		// map contains <path, filename>
		mediaFiles.clear();
		if (dir.isFile()) {
			files = dir.getParentFile().listFiles(new AudioFilter());
		} else {
			files = dir.listFiles(new AudioFilter());
		}
		for (File file : files) {
			mediaFiles.add(getMediaFileTwo(file.getPath()));
		}
		return mediaFiles;
	}

	// just return one mediafile from given path
	@SuppressLint("UseValueOf")
	public MediaFile getMediaFileTwo(String path) {
		mmr = new MediaMetadataRetriever();
		File file = new File(path);
		if (file.isFile()) {
			mmr.setDataSource(path);
			MediaFile mediaFile = new MediaFile();
			mediaFile.setPath(path);
			mediaFile
					.setTitle(mmr
							.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
			try {
				mediaFile
						.setDuration(Long.parseLong(mmr
								.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)));
			} catch (Exception ex) {
				mediaFile.setDuration((long) 0);
			}
			mediaFile
					.setArtist(mmr
							.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
			byte[] cover_array = mmr.getEmbeddedPicture();
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			try {
				mediaFile.setCover(BitmapFactory.decodeByteArray(cover_array,
						0, cover_array.length));
			} catch (NullPointerException e) {
				Log.d(TAG, "DONT FINE A COVER");
			}
			return mediaFile;
		} else {
			MediaFile mediaFile = new MediaFile();
			mediaFile.setPath(path);
			mediaFile.setTitle(file.getName());
			mediaFile.setArtist("");
			mediaFile.setDuration((long) 0);
			mediaFile.setDir(true);
			return mediaFile;
		}
	}
}
