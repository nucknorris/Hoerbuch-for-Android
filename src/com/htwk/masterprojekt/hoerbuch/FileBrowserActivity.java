package com.htwk.masterprojekt.hoerbuch;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.htwk.masterprojekt.hoerbuch.media.MediaFile;
import com.htwk.masterprojekt.hoerbuch.media.MediaFileManager;

public class FileBrowserActivity extends Activity {

	File[] files;
	ArrayAdapter<String> fileList;

	@SuppressWarnings("unused")
	private static final String TAG = "FileBrowserActivity";
	public static final String EXTRA_FILE = "EXTRA_FILE";
	public static final String EXTRA_FILE_PATH = "EXTRA_FILE_PATH";
	public static final String EXTRA_PLAYLIST_POSITION = "PLAYLIST_POSITION";

	// This is the Adapter being used to display the list's data
	private MediaFileManager mediaManager;
	private List<MediaFile> mediaFiles;

	// will contain the filenames and corresponding paths.
	private List<String> breadcrumbs;
	private int breadcrumbsPosition;
	ArrayList<HashMap<String, String>> songsList;

	// for hashmap
	static final String KEY_SONG = "song"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_TITLE = "title";
	static final String KEY_FILE = "file";
	static final String KEY_ARTIST = "artist";
	static final String KEY_DURATION = "duration";
	static final String KEY_THUMB_URL = "thumb_url";

	// for the view
	ListView list;
	LazyAdapter adapter;
	private static String ROOTDIR;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences app_preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		ROOTDIR = app_preferences.getString("path", "");
		setTitle(ROOTDIR);
		setContentView(R.layout.activity_browser);
		mediaManager = new MediaFileManager();
		setDir(ROOTDIR);
		// Click event for single list row
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (new File(mediaFiles.get(position).getFileNameLong())
						.isFile()) {
					File file = new File(mediaFiles.get(position)
							.getFileNameLong());
					Intent intent = new Intent(FileBrowserActivity.this,
							PlayerActivity.class);
					intent.putExtra(EXTRA_FILE_PATH, file.getPath());
					intent.putExtra(EXTRA_PLAYLIST_POSITION, position);
					startActivity(intent);
				} else {
					breadcrumbsPosition++;
					breadcrumbs.add(mediaFiles.get(position).getFileNameLong());
					String dir = mediaFiles.get(position).getFileNameLong();
					Log.d("FileBrowserActivity", "File :" + dir);
					setDir(dir);
				}
			}
		});
		// save the dir position in a array
		breadcrumbs = new ArrayList<String>();
		// start position for directory curser
		breadcrumbsPosition = 0;
		breadcrumbs.add(ROOTDIR);
		// button to go one dir up
		Button buttonUp = (Button) findViewById(R.id.HomeDirUp);
		buttonUp.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				goToUpperDirectory();
			}
		});
	}

	// the list view with the given dir
	private void setDir(String dir) {
		mediaFiles = mediaManager.getList(new File(dir));
		songsList = new ArrayList<HashMap<String, String>>();
		// looping through all song nodes <song>
		for (MediaFile mf : mediaFiles) {
			// creating new HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			// adding each child node to HashMap key => value
			map.put(KEY_ID, mf.getPath());
			map.put(KEY_TITLE, mf.getTitle());
			map.put(KEY_FILE, mf.getFileNameShort());
			map.put(KEY_ARTIST, mf.getArtist());
			map.put(KEY_DURATION, "" + mf.getDuration());
			// Log.d("FileBrowserActivity", "" +
			// mf.getFileNameLong());
			map.put(KEY_THUMB_URL, mf.getFileNameLong());
			// adding HashList to ArrayList
			songsList.add(map);
		}
		list = (ListView) findViewById(R.id.list);
		adapter = new LazyAdapter(FileBrowserActivity.this, songsList);
		list.setAdapter(adapter);
	}

	/**
	 * Goes back in hierarchically order until the root directory is reached.
	 */
	private void goToUpperDirectory() {
		if (breadcrumbsPosition - 1 >= 0) {
			breadcrumbs.remove(breadcrumbsPosition);
			breadcrumbsPosition--;
			// get the new position for dir cursor
			String dir = breadcrumbs.get(breadcrumbsPosition);
			Log.d("FileBrowserActivity", "File :" + new File(dir));
			setDir(dir);
			Log.d("DEBUG", "Dir UP");
		}
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
