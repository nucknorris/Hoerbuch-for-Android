package com.htwk.masterprojekt.hoerbuch;

import android.app.Activity;
import android.os.Bundle;

import com.htwk.masterprojekt.hoerbuch.db.Database;
import com.htwk.masterprojekt.hoerbuch.db.model.LastPlayed;

public class LastPlayedActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		openLastPlayed();
	}

	private void openLastPlayed() {
		Database db = new Database(this);
		db.db().addLastPlayed(
				new LastPlayed("blub1.mp3", "/sdcard/Music/", "1:55"));
		db.db().addLastPlayed(
				new LastPlayed("blub2.mp3", "/sdcard/Music/", "2:55"));
		db.db().addLastPlayed(
				new LastPlayed("blub3.mp3", "/sdcard/Music/", "3:55"));
		db.printLastPlayed(db.getLastPlayedList());
	}
}
