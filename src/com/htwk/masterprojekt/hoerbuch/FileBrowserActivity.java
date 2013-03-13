package com.htwk.masterprojekt.hoerbuch;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.htwk.masterprojekt.hoerbuch.media.MediaFileManager;

public class FileBrowserActivity extends ListActivity {

	File[] files;
	ArrayAdapter<String> fileList;

	@SuppressWarnings("unused")
	private static final String TAG = "FileBrowserActivity";
	public static final String EXTRA_FILE = "EXTRA_FILE";
	public static final String EXTRA_FILE_PATH = "EXTRA_FILE_PATH";
	public static final String EXTRA_PLAYLIST_POSITION = "PLAYLIST_POSITION";

	// This is the Adapter being used to display the list's data
	private SimpleCursorAdapter mAdapter;
	private MediaFileManager mediaManager;
	private HashMap<String, String> mediaFiles;

	// will contain the filenames and corresponding paths.
	private List<String> fileNames;
	private List<String> paths;
	private List<String> breadcrumbs;
	private int breadcrumbsPosition;

	/**
	 * The dir where the audiobooks are supposed to be located in.
	 * 
	 * FIXME make configurable by using the settings
	 */
	private static final File ROOTDIR = new File("/mnt/sdcard/Music");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(ROOTDIR.getName());
		setContentView(R.layout.activity_browser);
		mediaManager = new MediaFileManager();

		mediaFiles = mediaManager.getList(ROOTDIR);
		fileNames = new ArrayList<String>(mediaFiles.values());
		paths = new ArrayList<String>(mediaFiles.keySet());

		fileList = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, fileNames);
		setListAdapter(fileList);

		// save the dir position in a array
		breadcrumbs = new ArrayList<String>();

		// start position for directory curser
		breadcrumbsPosition = 0;
		breadcrumbs.add(ROOTDIR.getAbsolutePath());

		// button to go one dir up
		Button buttonUp = (Button) findViewById(R.id.HomeDirUp);
		buttonUp.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				goToUpperDirectory();
			}
		});

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// creates a list of all files in the dir
		if (new File(paths.get(position)).isFile()) {
			File file = new File(paths.get(position));
			Intent intent = new Intent(this, PlayerActivity.class);
			intent.putExtra(EXTRA_FILE_PATH, file.getPath());
			intent.putExtra(EXTRA_PLAYLIST_POSITION, position);
			startActivity(intent);
		} else {
			breadcrumbsPosition++;
			breadcrumbs.add(paths.get(position));
			mediaFiles = mediaManager.getList(new File(paths.get(position)));
			fileNames = new ArrayList<String>(mediaFiles.values());
			paths = new ArrayList<String>(mediaFiles.keySet());
			fileList = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, fileNames);
			setListAdapter(fileList);
		}
	}

	/**
	 * Goes back in hierarchically order until the root directory is reached.
	 */
	private void goToUpperDirectory() {
		while (breadcrumbsPosition - 1 >= 0) {
			breadcrumbs.remove(breadcrumbsPosition);
			breadcrumbsPosition--;

			// get the new position for dir cursor
			String dir = breadcrumbs.get(breadcrumbsPosition);

			mediaFiles = mediaManager.getList(new File(dir));
			fileNames = new ArrayList<String>(mediaFiles.values());
			paths = new ArrayList<String>(mediaFiles.keySet());

			fileList = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, fileNames);

			setListAdapter(fileList);
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
