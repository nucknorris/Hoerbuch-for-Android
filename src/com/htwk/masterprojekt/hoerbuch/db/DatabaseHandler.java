package com.htwk.masterprojekt.hoerbuch.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.htwk.masterprojekt.hoerbuch.db.model.LastPlayed;
import com.htwk.masterprojekt.hoerbuch.db.model.Song;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	protected static final int DATABASE_VERSION = 2;
	private static final String TAG = "DatabaseHandler";
	// Database Name
	protected static final String DATABASE_NAME = "hoerbuchdb";
	// lastplayed table name
	protected static final String TABLE_LAST_PLAYED = "lastplayed";
	protected static final String TABLE_SONGS = "songs";
	// lastplayed Table Columns names
	protected static final String LP_KEY_ID = "id";
	protected static final String LP_KEY_FILE = "name";
	protected static final String LP_KEY_PATH = "path";
	protected static final String LP_KEY_TIME = "time";
	protected static final String SONGS_KEY_ID = "id";
	protected static final String SONGS_KEY_FILE = "file";
	protected static final String SONGS_KEY_ARTIST = "artist";
	protected static final String SONGS_KEY_ALBUM = "album";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		// lastplayed
		String CREATE_TABLE_LAST_PLAYED = "CREATE TABLE " + TABLE_LAST_PLAYED
				+ "(" + LP_KEY_ID + " INTEGER PRIMARY KEY," + LP_KEY_FILE
				+ " TEXT," + LP_KEY_PATH + " TEXT," + LP_KEY_TIME + " TEXT"
				+ ")";
		String CREATE_TABLE_SONGS = "CREATE TABLE " + TABLE_SONGS + "("
				+ SONGS_KEY_ID + " INTEGER PRIMARY KEY," + SONGS_KEY_FILE
				+ " TEXT," + SONGS_KEY_ARTIST + " TEXT," + SONGS_KEY_ALBUM
				+ " TEXT" + ")";
		db.execSQL(CREATE_TABLE_LAST_PLAYED);
		db.execSQL(CREATE_TABLE_SONGS);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAST_PLAYED);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONGS);
		// Create tables again
		onCreate(db);
	}

	// Getting single lastplayed
	public LastPlayed getLastPlayed(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_LAST_PLAYED, new String[] { LP_KEY_ID,
				LP_KEY_FILE, LP_KEY_PATH, LP_KEY_TIME }, LP_KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		LastPlayed lastPlayed = new LastPlayed(Integer.parseInt(cursor
				.getString(0)), cursor.getString(1), cursor.getString(2),
				cursor.getString(3));
		db.close();
		// return
		return lastPlayed;
	}

	// Getting single songs
	public Song getSong(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_SONGS, new String[] { SONGS_KEY_ID,
				SONGS_KEY_FILE, SONGS_KEY_ARTIST, SONGS_KEY_ALBUM }, LP_KEY_ID
				+ "=?", new String[] { String.valueOf(id) }, null, null, null,
				null);
		if (cursor != null)
			cursor.moveToFirst();
		Song song = new Song(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3));
		db.close();
		// return
		return song;
	}

	// Getting single songs
	public Song getSong(String file) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_SONGS, new String[] { SONGS_KEY_ID,
				SONGS_KEY_FILE, SONGS_KEY_ARTIST, SONGS_KEY_ALBUM },
				SONGS_KEY_FILE + "=?", new String[] { String.valueOf(file) },
				null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		Song song = new Song(Integer.parseInt(cursor.getString(0)),
				cursor.getString(1), cursor.getString(2), cursor.getString(3));
		db.close();
		// return
		return song;
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
		db.close();
		// return contact list
		return lastPlayedList;
	}

	// Deleting single lastplayed
	public void deleteLastPlayed(LastPlayed lastPlayed) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_LAST_PLAYED, LP_KEY_ID + " = ?",
				new String[] { String.valueOf(lastPlayed.getID()) });
		db.close();
	}

	// deleting all LPs with given path
	private void deleteAllLastPlayedWithPath(String path) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_LAST_PLAYED, LP_KEY_PATH + " = ?",
				new String[] { path });
		db.close();
	}

	// Updating single lastplayed
	public int updateLastPlayed(LastPlayed lastPlayed) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(LP_KEY_FILE, lastPlayed.getFile());
		values.put(LP_KEY_PATH, lastPlayed.getPath());
		values.put(LP_KEY_TIME, lastPlayed.getTime());
		db.close();
		// updating row
		return db.update(TABLE_LAST_PLAYED, values, LP_KEY_ID + " = ?",
				new String[] { String.valueOf(lastPlayed.getID()) });
	}

	// add a single lastplayed
	public void addLastPlayed(LastPlayed lastPlayed) {
		deleteAllLastPlayedWithPath(lastPlayed.getPath());
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(LP_KEY_FILE, lastPlayed.getFile());
		values.put(LP_KEY_PATH, lastPlayed.getPath());
		values.put(LP_KEY_TIME, lastPlayed.getTime());
		// Inserting Row
		db.insert(TABLE_LAST_PLAYED, null, values);
		db.close(); // Closing database connection
	}

	// add a single lastplayed
	public void addSong(Song song) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(SONGS_KEY_FILE, song.getFile());
		values.put(SONGS_KEY_ARTIST, song.getArtist());
		values.put(SONGS_KEY_ALBUM, song.getAlbum());
		// Inserting Row
		db.insert(TABLE_SONGS, null, values);
		db.close(); // Closing database connection
	}

	// just to print it out
	public void printLastPlayed() {
		for (LastPlayed lp : getAllLastPlayed()) {
			Log.d(TAG,
					"" + lp.getPath() + " " + lp.getFile() + " " + lp.getTime());
		}
	}
}
