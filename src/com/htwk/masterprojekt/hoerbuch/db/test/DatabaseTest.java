package com.htwk.masterprojekt.hoerbuch.db.test;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.htwk.masterprojekt.hoerbuch.HomeActivity;
import com.htwk.masterprojekt.hoerbuch.MainActivity;
import com.htwk.masterprojekt.hoerbuch.db.DatabaseHandler;
import com.htwk.masterprojekt.hoerbuch.db.model.LastPlayed;

public class DatabaseTest {

	Context c;

	@SuppressLint("SdCardPath")
	public DatabaseTest(Context c2) {
		c = c2;
		test();
	}

	public DatabaseTest(MainActivity mainActivity) {
		c = mainActivity;
		test();
	}

	private void test() {
		DatabaseHandler db = new DatabaseHandler(c);

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
