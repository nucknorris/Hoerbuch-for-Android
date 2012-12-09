package com.htwk.masterprojekt.hoerbuch.db;

import java.util.ArrayList;
import java.util.List;

import com.htwk.masterprojekt.hoerbuch.db.model.LastPlayed;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	protected static final int DATABASE_VERSION = 1;

	// Database Name
	protected static final String DATABASE_NAME = "hoerbuchdb";

	// lastplayed table name
	protected static final String TABLE_LAST_PLAYED = "lastplayed";

	// lastplayed Table Columns names
	protected static final String KEY_ID = "id";
	protected static final String KEY_FILE = "name";
	protected static final String KEY_PATH = "path";
	protected static final String KEY_TIME = "time";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		// lastplayed
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_LAST_PLAYED
				+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FILE + " TEXT,"
				+ KEY_PATH + " TEXT," + KEY_TIME + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAST_PLAYED);

		// Create tables again
		onCreate(db);
	}

	// // DATA HANDLING METHODES

	// Getting single lastplayed
	public LastPlayed getLastPlayed(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_LAST_PLAYED, new String[] { KEY_ID,
				KEY_FILE, KEY_PATH, KEY_TIME }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		LastPlayed lastPlayed = new LastPlayed(Integer.parseInt(cursor
				.getString(0)), cursor.getString(1), cursor.getString(2),
				cursor.getString(3));
		// return
		return lastPlayed;
	}

	// get all lastplayed
	public List<LastPlayed> getAllLastPlayed() {
		List<LastPlayed> lastPlayedList = new ArrayList<LastPlayed>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_LAST_PLAYED;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				LastPlayed lastPlayed = new LastPlayed();
				lastPlayed.setID(Integer.parseInt(cursor.getString(0)));
				lastPlayed.setFile(cursor.getString(1));
				lastPlayed.setPath(cursor.getString(2));
				lastPlayed.setTime(cursor.getString(3));
				// Adding contact to list
				lastPlayedList.add(lastPlayed);
			} while (cursor.moveToNext());
		}

		// return contact list
		return lastPlayedList;
	}

	// Deleting single lastplayed
	public void deleteLastPlayed(LastPlayed lastPlayed) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_LAST_PLAYED, KEY_ID + " = ?",
				new String[] { String.valueOf(lastPlayed.getID()) });
		db.close();
	}

	// Updating single lastplayed
	public int updateLastPlayed(LastPlayed lastPlayed) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_FILE, lastPlayed.getFile());
		values.put(KEY_PATH, lastPlayed.getPath());
		values.put(KEY_TIME, lastPlayed.getTime());

		// updating row
		return db.update(TABLE_LAST_PLAYED, values, KEY_ID + " = ?",
				new String[] { String.valueOf(lastPlayed.getID()) });
	}

	// add a single lastplayed

	public void addLastPlayed(LastPlayed lastPlayed) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_FILE, lastPlayed.getFile());
		values.put(KEY_PATH, lastPlayed.getPath());
		values.put(KEY_TIME, lastPlayed.getTime());

		// Inserting Row
		db.insert(TABLE_LAST_PLAYED, null, values);
		db.close(); // Closing database connection
	}
}
