package com.htwk.masterprojekt.hoerbuch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.htwk.masterprojekt.hoerbuch.filters.AudioFilter;

public class FileBrowserActivity extends ListActivity {

	File[] files;
	ArrayAdapter<String> fileList;

	@SuppressWarnings("unused")
	private static final String TAG = "FileBrowserActivity";
	public static final String EXTRA_FILE_PATH = "EXTRA_FILE_PATH";
	public static final String EXTRA_FILE = "EXTRA_FILE";

	// This is the Adapter being used to display the list's data
	SimpleCursorAdapter mAdapter;

	// will contain the filenames and corresponding paths.
	private List<String> fileNames;
	private List<String> paths;
	private List<String> bredcrums;
	private int bredcrumsPosition;

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

		// inti fill
		getList(ROOTDIR);
		setListAdapter(fileList);
		bredcrums = new ArrayList<String>();
		bredcrumsPosition = 0;
		bredcrums.add(ROOTDIR.getAbsolutePath());

		// Intent intent = new Intent(this, MainActivity.class);
		// startActivity(intent);

		Button buttonUP = (Button) findViewById(R.id.HomeDirUp);
		buttonUP.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				up();
			}
		});

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		if (new File(paths.get(position)).isFile()) {
			File file = new File(paths.get(position));
			Intent intent = new Intent(this, PlayerActivity.class);
			intent.putExtra(EXTRA_FILE_PATH, file.getPath());
			intent.putExtra(EXTRA_FILE, file.getName());
			startActivity(intent);
		} else {
			bredcrumsPosition++;
			bredcrums.add(paths.get(position));
			getList(new File(paths.get(position)));
			setListAdapter(fileList);
		}
	}

	private void up() {
		if (bredcrumsPosition - 1 >= 0) {
			bredcrums.remove(bredcrumsPosition);
			bredcrumsPosition--;

			String dir = bredcrums.get(bredcrumsPosition);
			getList(new File(dir));
			setListAdapter(fileList);
			Log.d("DEBUG", "Dir UP");
		} else {
			Log.d("DEBUG", "BROWSER WHAT ARE U DOOOOING STAAAHHP");
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

	private void getList(File dir) {

		files = dir.listFiles(new AudioFilter());
		fileNames = new ArrayList<String>();
		paths = new ArrayList<String>();
		for (File file : files) {

			// cutting the extension
			fileNames.add(getMP3Meta(file.getAbsolutePath()));
			paths.add(file.getPath());
		}
		fileList = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, fileNames);
	}

	private String getMP3Meta(String filePath) {
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
