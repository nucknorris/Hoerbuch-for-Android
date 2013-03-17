package com.htwk.masterprojekt.hoerbuch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.htwk.masterprojekt.hoerbuch.media.MediaFileManager;

public class PlayerActivity extends Activity implements OnCompletionListener,
		SeekBar.OnSeekBarChangeListener {
	private static final String TAG = "PlayerActivity";
	private static final int SEEK_FORWARD_TIME = 5000;
	private static final int SEEK_BACKWARD_TIME = 5000;
	private Intent intent;
	private String file;
	private String filePath;
	private ImageButton btnPlay;
	private ImageButton btnNext;
	private ImageButton btnPrev;
	private ImageButton btnFwd;
	private ImageButton btnBwd;

	private MediaPlayer player;
	private MediaFileManager mediaManager;
	private Handler mHandler = new Handler(); // for updating the progress bar
	private TextView songTitleLabel;
	private TextView songCurrentDurationLabel;
	private TextView songTotalDurationLabel;
	private SeekBar songProgressBar;
	private Utils utils;
	private HashMap<String, String> mediaFiles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// getting the information from the previous activity/intent
		intent = getIntent();
		filePath = intent.getStringExtra(FileBrowserActivity.EXTRA_FILE_PATH);

		// registering the GUI components
		btnPlay = (ImageButton) findViewById(R.id.btnPlay);
		btnNext = (ImageButton) findViewById(R.id.btnNext);
		btnPrev = (ImageButton) findViewById(R.id.btnPrevious);
		btnFwd = (ImageButton) findViewById(R.id.btnForward);
		btnBwd = (ImageButton) findViewById(R.id.btnBackward);
		songTitleLabel = (TextView) findViewById(R.id.songTitle);
		songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
		songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
		songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);

		player = new MediaPlayer();
		utils = new Utils();
		mediaManager = new MediaFileManager();

		// provide the playlist
		mediaFiles = mediaManager.getList(new File(filePath));

		// listeners
		songProgressBar.setOnSeekBarChangeListener(this); // Important
		player.setOnCompletionListener(this); // Important
		songTitleLabel.setText(file);

		playSong(filePath);

		btnPlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// check for already playing
				if (player.isPlaying()) {
					if (player != null) {
						player.pause();
						// Changing button image to play button
						btnPlay.setImageResource(R.drawable.play);
					}
				} else {
					// Resume song
					if (player != null) {
						player.start();
						// Changing button image to pause button
						btnPlay.setImageResource(R.drawable.pause);
					}
				}

			}
		});

		btnNext.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				List<String> listOfFiles = new ArrayList<String>(mediaFiles
						.keySet());
				// get the next element of the list and update the current file
				int indexOfCurrentFile = listOfFiles.indexOf(filePath);

				// play next song until end of playlist is reached
				if (indexOfCurrentFile < listOfFiles.size() - 1) {
					filePath = listOfFiles.get(indexOfCurrentFile + 1);
					file = mediaFiles.get(filePath);
					playSong(filePath);
				}

			}
		});

		btnPrev.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				List<String> listOfFiles = new ArrayList<String>(mediaFiles
						.keySet());
				// get the next element of the list and update the current file
				int indexOfCurrentFile = listOfFiles.indexOf(filePath);

				// play next song until end of playlist is reached
				if (indexOfCurrentFile > 0) {
					filePath = listOfFiles.get(indexOfCurrentFile - 1);
					file = mediaFiles.get(filePath);
					playSong(filePath);
				}

			}
		});

		btnFwd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int currentPostion = player.getCurrentPosition();

				if (currentPostion + SEEK_FORWARD_TIME <= player.getDuration()) {
					player.seekTo(currentPostion + SEEK_FORWARD_TIME);
				} else {
					player.seekTo(player.getDuration());
				}

			}
		});

		btnBwd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int currentPostion = player.getCurrentPosition();

				if (currentPostion + SEEK_BACKWARD_TIME >= 0) {
					player.seekTo(currentPostion - SEEK_BACKWARD_TIME);
				} else {
					player.seekTo(0);
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

	public void playSong(String path) {
		Log.v(TAG, "playing: " + path);
		// Play song
		try {
			player.reset();
			player.setDataSource(path);
			player.prepare();
			player.start();
			// Displaying Song title
			String songTitle = path;
			songTitleLabel.setText(songTitle);

			// Changing Button Image to pause image
			btnPlay.setImageResource(R.drawable.pause);

			// set Progress bar values
			songProgressBar.setProgress(0);
			songProgressBar.setMax(100);

			// Updating progress bar
			updateProgressBar();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update timer on seekbar
	 * */
	public void updateProgressBar() {
		mHandler.postDelayed(mUpdateTimeTask, 100);
	}

	/**
	 * Background Runnable thread
	 * */
	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			long totalDuration = player.getDuration();
			long currentDuration = player.getCurrentPosition();

			// Displaying Total Duration time
			songTotalDurationLabel.setText(""
					+ utils.milliSecondsToTimer(totalDuration));
			// Displaying time completed playing
			songCurrentDurationLabel.setText(""
					+ utils.milliSecondsToTimer(currentDuration));

			// Updating progress bar
			int progress = (int) (utils.getProgressPercentage(currentDuration,
					totalDuration));
			// Log.d("Progress", ""+progress);
			songProgressBar.setProgress(progress);

			// Running this thread after 100 milliseconds
			mHandler.postDelayed(this, 100);
		}
	};

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
	}

	/**
	 * When user starts moving the progress handler
	 * */
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// remove message Handler from updating progress bar
		mHandler.removeCallbacks(mUpdateTimeTask);
	}

	/**
	 * When user stops moving the progress hanlder
	 * */
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		mHandler.removeCallbacks(mUpdateTimeTask);
		int totalDuration = player.getDuration();
		int currentPosition = utils.progressToTimer(seekBar.getProgress(),
				totalDuration);

		// forward or backward to certain seconds
		player.seekTo(currentPosition);

		// update timer progress again
		updateProgressBar();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mHandler.removeCallbacks(mUpdateTimeTask);
		player.release();
	}
}
