package com.htwk.masterprojekt.hoerbuch.services;

import java.io.IOException;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.htwk.masterprojekt.hoerbuch.FileBrowserActivity;
import com.htwk.masterprojekt.hoerbuch.PlayerActivity;
import com.htwk.masterprojekt.hoerbuch.R;
import com.htwk.masterprojekt.hoerbuch.db.DatabaseHandler;
import com.htwk.masterprojekt.hoerbuch.db.model.LastPlayed;

public class PlayerService extends Service implements OnCompletionListener,
		MediaPlayer.OnErrorListener {

	private final IBinder binder = new PlayerServiceBinder();

	private static final String TAG = "PlayerService";
	public static final String ACTION_PLAY = "com.example.action.PLAY";
	public static boolean IS_PLAYING = false;
	private MediaPlayer player = null;
	private String filePath;
	private DatabaseHandler db;
	private int currentPosition;

	private int totalDuration;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if (intent.getAction().equals(ACTION_PLAY) && player == null) {
			filePath = intent
					.getStringExtra(FileBrowserActivity.EXTRA_FILE_PATH);
			currentPosition = intent.getIntExtra(
					FileBrowserActivity.EXTRA_FILE_POSTION, 0);

			Log.v(TAG, "postion: " + currentPosition);
			Log.v(TAG, filePath);
			player = new MediaPlayer();
			player.setOnErrorListener(this);
			player.setOnCompletionListener(this);
			play(filePath);

		}

		return START_NOT_STICKY;
	}

	private void play(String filePath) {
		player.reset();

		// data source
		try {
			player.setDataSource(filePath);
		} catch (IllegalArgumentException e) {
			Log.v(TAG, e.getMessage());
		} catch (SecurityException e) {
			Log.v(TAG, e.getMessage());
		} catch (IllegalStateException e) {
			Log.v(TAG, e.getMessage());
		} catch (IOException e) {
			Log.v(TAG, e.getMessage());
		}
		player.prepareAsync(); // prepare async to not block main thread
		player.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				Log.v(TAG, "playing... (duration: " + mp.getDuration()
						+ "), starting at position: " + currentPosition);
				mp.seekTo(currentPosition);
				mp.start();
				IS_PLAYING = true;
				totalDuration = mp.getDuration();
			}
		});
		Log.v(TAG, "finished preparing");

		// notification
		Intent i = new Intent(this, PlayerActivity.class);
		PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
		Notification noti = new Notification.Builder(this)
				.setContentTitle("Hoerbuch is playing LOL")
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentText("Now Playing: \"Ummmm, Nothing\"")
				.setContentIntent(pi).build();

		startForeground(1337, noti);

	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		Log.v(TAG, "onCompletion reached");
		this.stopSelf();
		IS_PLAYING = false;
	}

	@Override
	public void onDestroy() {
		Log.v(TAG, "onDestroy reached");
		db = new DatabaseHandler(this);
		db.addLastPlayed(new LastPlayed(filePath, ""
				+ player.getCurrentPosition()));
		db.printLastPlayed();
		super.onDestroy();
		if (player.isPlaying()) {
			player.stop();
			IS_PLAYING = false;
			player.release();
		}
	}

	@Override
	public boolean onError(MediaPlayer player, int what, int extra) {
		Log.v(TAG, "ERROR! What: " + what);
		player.reset();
		return false;
	}

	/**
	 * Playes the already loaded file.
	 */
	public void startPlaying() {
		if (!player.isPlaying()) {
			player.start();
		}
	}

	/**
	 * Playes the five file.
	 * 
	 * @param filePath
	 */
	public void startPlaying(String filePath) {
		player.reset();
		play(filePath);
	}

	public void pausePlaying() {
		player.pause();
	}

	public int getTotalDuration() {
		return totalDuration;
	}

	public int getCurrentPosition() {
		return player.getCurrentPosition();
	}

	public void seekTo(int position) {
		player.seekTo(position);
	}

	/**
	 * The service binder. Is needed to bind the playerservice to the
	 * playerActivity.
	 * 
	 * @author sebastian
	 * 
	 */
	public class PlayerServiceBinder extends Binder {
		public PlayerService getService() {
			return PlayerService.this;
		}
	}

}
