package com.htwk.masterprojekt.hoerbuch;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class FilePicker extends ListActivity {

	File[] files;
	ArrayAdapter<String> fileList;

	@SuppressWarnings("unused")
	private static final String TAG = "FilePicker";
	public static final String EXTRA_FILE = "EXTRA_FILE";
	public static final String EXTRA_FILE_PATH = "EXTRA_FILE_PATH";
	public static final String EXTRA_PLAYLIST_POSITION = "PLAYLIST_POSITION";
	private static final File ROOTDIR = new File("/mnt/sdcard");

	EditText e;

	// This is the Adapter being used to display the list's data
	private SimpleCursorAdapter mAdapter;

	// will contain the filenames and corresponding paths.
	private List<String> fileNames;
	private List<String> paths;
	private List<String> breadcrumbs;
	private int breadcrumbsPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Search for Directory");
		setContentView(R.layout.utils_filepicker);
		e = (EditText) findViewById(R.id.pathText);
		e.setText(ROOTDIR.getAbsolutePath());
		refrechLists(getDirs(ROOTDIR.getAbsolutePath()));

		fileList = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, fileNames);
		setListAdapter(fileList);

		// save the dir position in a array
		breadcrumbs = new ArrayList<String>();
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

		final SharedPreferences appPref = PreferenceManager
				.getDefaultSharedPreferences(this);

		Button buttonSave = (Button) findViewById(R.id.Save);
		buttonSave.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				SharedPreferences.Editor editor = appPref.edit();
				editor.putString("path", e.getText().toString());
				editor.commit();
				Log.d("FILEPICKER", "SAVE:" + e.getText());
				finish();
			}
		});

	}

	private void refrechLists(File[] files) {
		fileNames = new ArrayList<String>();
		paths = new ArrayList<String>();
		for (File file : files) {
			fileNames.add(file.getName());
			paths.add(file.getAbsolutePath());
			Log.d("FILEPICKER", "DIR ADD:" + file.getName());
		}
	}

	private File[] getDirs(String directory) {
		File dir = new File(directory);
		FileFilter fileFilter = new FileFilter() {
			@Override
			public boolean accept(File file) {
				return file.isDirectory();
			}
		};
		File[] files = dir.listFiles(fileFilter);
		return files;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// creates a list of all files in the dir
		if (new File(paths.get(position)).isDirectory()) {
			breadcrumbsPosition++;
			breadcrumbs.add(paths.get(position));
			e.setText(paths.get(position));
			refrechLists(getDirs(paths.get(position)));
			fileList = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, fileNames);
			setListAdapter(fileList);
		}
	}

	private void goToUpperDirectory() {
		if (breadcrumbsPosition - 1 >= 0) {
			breadcrumbs.remove(breadcrumbsPosition);
			breadcrumbsPosition--;
			String dir = breadcrumbs.get(breadcrumbsPosition);
			e.setText(dir);
			refrechLists(getDirs(dir));
			fileList = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, fileNames);
			setListAdapter(fileList);
			Log.d("FILEPICKER", "UP");
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
