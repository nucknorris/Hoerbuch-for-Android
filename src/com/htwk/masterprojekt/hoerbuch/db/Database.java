package com.htwk.masterprojekt.hoerbuch.db;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.htwk.masterprojekt.hoerbuch.LastPlayedActivity;
import com.htwk.masterprojekt.hoerbuch.db.model.LastPlayed;

/*
 * class to interact with the database
 * */
public class Database {

	Context c;
	DatabaseHandler db;

	public Database(LastPlayedActivity lastPlayedActivity) {
		c = lastPlayedActivity;
		db = new DatabaseHandler(c);
		Log.d("Database", "OPEN");
	}

	public List<LastPlayed> getLastPlayedList() {
		List<LastPlayed> lp = db.getAllLastPlayed();
		Log.d("Last Played", "" + lp.size());
		return lp;
	}

	public DatabaseHandler db() {
		return db;
	}

	public void printLastPlayed(List<LastPlayed> llp) {
		for (LastPlayed lp : llp) {
			String log = "Id: " + lp.getID() + " ,Name: " + lp.getFile()
					+ " ,Path: " + lp.getPath() + " ,Time: " + lp.getTime();
			// writing lp to log
			Log.d("LP: ", log);
		}
	}
}
