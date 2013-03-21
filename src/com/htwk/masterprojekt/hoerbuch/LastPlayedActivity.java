package com.htwk.masterprojekt.hoerbuch;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.htwk.masterprojekt.hoerbuch.db.Database;
import com.htwk.masterprojekt.hoerbuch.db.model.LastPlayed;
import com.htwk.masterprojekt.hoerbuch.media.MediaFile;
import com.htwk.masterprojekt.hoerbuch.media.MediaFileManager;

public class LastPlayedActivity extends Activity {

	File[] files;
	ArrayAdapter<String> fileList;
	private static final String TAG = "LastPlayedActivity";
	public static final String EXTRA_FILE = "EXTRA_FILE";
	public static final String EXTRA_FILE_PATH = "EXTRA_FILE_PATH";
	public static final String EXTRA_PLAYLIST_POSITION = "PLAYLIST_POSITION";
	public static final String EXTRA_FILE_POSTION = "EXTRA_FILE_POSITION";

	// private MediaFileManager mediaManager;
	private List<LastPlayed> mediaFiles;

	// for the view
	ListView list;
	LazyAdapter adapter;

	// for hashmap
	static final String KEY_SONG = "song"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_TITLE = "title";
	static final String KEY_FILE = "file";
	static final String KEY_ARTIST = "artist";
	static final String KEY_DURATION = "duration";
	static final String KEY_THUMB_URL = "thumb_url";

	// will contain the filenames and corresponding paths.
	ArrayList<HashMap<String, String>> songsList;
	Database db;
	MediaFileManager mfm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mfm = new MediaFileManager(this);
		db = new Database(this);
		setContentView(R.layout.activity_lastplayed);
		// get list of last played elements
		openLastPlayed();
	}

	/*
	 * Gets a list of all last played elements from database
	 */
	private void openLastPlayed() {
		db.printLastPlayed(db.getLastPlayedList());
		mediaFiles = db.getLastPlayedList();
		songsList = new ArrayList<HashMap<String, String>>();
		// looping through all song nodes <song>
		for (LastPlayed lp : mediaFiles) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			// adding each child node to HashMap key => value
			String url = lp.getPath() + lp.getFile();
			MediaFile mf = mfm.getMediaFileTwo(url);
			Log.d(TAG, url);
			if (mf != null) {
				map.put(KEY_ID, mf.getPath());
				map.put(KEY_TITLE, mf.getTitle());
				map.put(KEY_FILE, mf.getPath());
				map.put(KEY_ARTIST, mf.getArtist());
				map.put(KEY_DURATION,
						"Stopped at "
								+ new Utils().milliSecondsToTimer(Long
										.parseLong(lp.getTime())));
				map.put(KEY_THUMB_URL, url);
				// adding HashList to ArrayList
				songsList.add(map);
			} else {
				Log.d(TAG, "File not found or deleted");
				db.db().deleteLastPlayed(lp);
			}
		}
		list = (ListView) findViewById(R.id.list);
		adapter = new LazyAdapter(LastPlayedActivity.this, songsList);
		list.setAdapter(adapter);
		// Click event for single list row
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String file = mediaFiles.get(position).getPath()
						+ mediaFiles.get(position).getFile();
				if (new File(file).isFile()) {
					File f = new File(file);
					Intent intent = new Intent(LastPlayedActivity.this,
							PlayerActivity.class);
					intent.putExtra(EXTRA_FILE_PATH, f.getPath());
					intent.putExtra(EXTRA_PLAYLIST_POSITION, position);
					// start at this point
					Log.d(TAG, "" + mediaFiles.get(position).getTime());
					intent.putExtra(EXTRA_FILE_POSTION, Integer
							.parseInt(mediaFiles.get(position).getTime()));
					startActivity(intent);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_settings:
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
