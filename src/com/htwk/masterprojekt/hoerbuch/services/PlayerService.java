package com.htwk.masterprojekt.hoerbuch.services;

import java.io.File;

import com.htwk.masterprojekt.hoerbuch.HomeActivity;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PlayerService extends Service {

	private boolean isPlaying = false;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String file = intent.getStringExtra(HomeActivity.EXTRA_FILE_PATH);
		play(new File(file));
		return START_NOT_STICKY;
	}

	private void play(File file) {
		if (!isPlaying) {
			isPlaying = true;
		}

	}
}
