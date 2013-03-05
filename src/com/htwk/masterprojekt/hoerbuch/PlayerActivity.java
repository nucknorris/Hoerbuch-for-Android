package com.htwk.masterprojekt.hoerbuch;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class PlayerActivity extends Activity implements OnCompletionListener {
	private static final String TAG = "PlayerActivity";
	private String file;
	private String filePath;
	private ImageButton btnPlay;
	private MediaPlayer mp;
	private TextView songTitleLabel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		Intent intent = getIntent();
		file = intent.getStringExtra(FileBrowserActivity.EXTRA_FILE);
		filePath = intent.getStringExtra(FileBrowserActivity.EXTRA_FILE_PATH);
		TextView songTitleView = (TextView) findViewById(R.id.songTitle);
		songTitleView.setText(file);

		btnPlay = (ImageButton) findViewById(R.id.btnPlay);
		songTitleLabel = (TextView) findViewById(R.id.songTitle);
		mp = new MediaPlayer();
		mp.setOnCompletionListener(this); // Important

		playSong();

		btnPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// check for already playing
				if (mp.isPlaying()) {
					if (mp != null) {
						mp.pause();
						// Changing button image to play button
						btnPlay.setImageResource(R.drawable.play);
					}
				} else {
					// Resume song
					if (mp != null) {
						mp.start();
						// Changing button image to pause button
						btnPlay.setImageResource(R.drawable.pause);
					}
				}

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_player, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub

	}

	public void playSong() {
		Log.v(TAG, "playing: " + filePath);
		// Play song
		try {
			mp.reset();
			mp.setDataSource(filePath);
			mp.prepare();
			mp.start();
			// Displaying Song title
			String songTitle = filePath;
			songTitleLabel.setText(songTitle);

			// Changing Button Image to pause image
			btnPlay.setImageResource(R.drawable.pause);

			// set Progress bar values
			// songProgressBar.setProgress(0);
			// songProgressBar.setMax(100);

			// Updating progress bar
			// updateProgressBar();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mp.release();
	}

}
