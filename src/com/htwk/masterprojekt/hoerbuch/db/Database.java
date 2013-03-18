package com.htwk.masterprojekt.hoerbuch.db;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.htwk.masterprojekt.hoerbuch.db.model.LastPlayed;

/*
 * class to simple interact with the database
 * */
public class Database {
	private static final String TAG = "Database";
	Context c;
	DatabaseHandler db;

	public Database(Context context) {
		c = context;
		db = new DatabaseHandler(c);
		Log.d(TAG, "OPEN");
	}

	public List<LastPlayed> getLastPlayedList() {
		List<LastPlayed> lp = db.getAllLastPlayed();
		Log.d(TAG, "" + lp.size());
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
			Log.d(TAG, log);
		}
	}
}
