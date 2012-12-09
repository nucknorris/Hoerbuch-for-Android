package com.htwk.masterprojekt.hoerbuch.db.test;

import java.util.List;

import android.annotation.SuppressLint;
import android.util.Log;

import com.htwk.masterprojekt.hoerbuch.HomeActivity;
import com.htwk.masterprojekt.hoerbuch.db.DatabaseHandler;
import com.htwk.masterprojekt.hoerbuch.db.model.LastPlayed;

public class DatabaseTest {

	@SuppressLint("SdCardPath")
	public DatabaseTest(HomeActivity context) {

		DatabaseHandler db = new DatabaseHandler(context);

		Log.d("Insert: ", "Inserting ..");
		db.addLastPlayed(new LastPlayed("blub.mp3", "/sdcard/Music/", "3:55"));

		// Reading all contacts
		Log.d("Reading: ", "Reading all ..");
		List<LastPlayed> listLastPlayed = db.getAllLastPlayed();

		for (LastPlayed lp : listLastPlayed) {
			String log = "Id: " + lp.getID() + " ,Name: " + lp.getFile()
					+ " ,Path: " + lp.getPath() + " ,Time: " + lp.getTime();
			// Writing lp to log
			Log.d("LP: ", log);
		}
	}
}
