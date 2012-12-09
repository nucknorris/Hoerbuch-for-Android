package com.htwk.masterprojekt.hoerbuch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class HomeActivity extends ListActivity {

	private static final String TAG = "HomeActivity";
	public static final String EXTRA_FILE_PATH = "EXTRA_FILE_PATH";
	public static final String EXTRA_FILE = "EXTRA_FILE";

	// This is the Adapter being used to display the list's data
	SimpleCursorAdapter mAdapter;

	// will contain the filenames and corresponding paths.
	private List<String> fileNames;
	private List<String> paths;

	/**
	 * The dir where the audiobooks are supposed to be located in.
	 * 
	 * FIXME make configurable by using the settings
	 */
	private static final File ROOTDIR = new File(
			"/storage/sdcard1/AUDIOBOOKS/World War Z D1");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		File[] files = ROOTDIR.listFiles();
		fileNames = new ArrayList<String>();
		paths = new ArrayList<String>();
		for (File file : files) {
			fileNames.add(file.getName());
			paths.add(file.getPath());
		}

		// simple list-adapter to display the items
		ArrayAdapter<String> fileList = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, fileNames);

		setListAdapter(fileList);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		File file = new File(paths.get(position));
		Intent intent = new Intent(this, PlayerActivity.class);
		intent.putExtra(EXTRA_FILE_PATH, file.getPath());
		intent.putExtra(EXTRA_FILE, file.getName());
		startActivity(intent);
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
