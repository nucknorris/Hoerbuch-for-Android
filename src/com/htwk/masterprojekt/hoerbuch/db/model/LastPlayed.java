package com.htwk.masterprojekt.hoerbuch.db.model;

public class LastPlayed {

	// private variables
	int id;
	String file;
	String path;
	String time;

	// constructor
	public LastPlayed(String file, String path, String time) {
		this.file = file;
		this.path = path;
		this.time = time;
	}

	// constructor
	public LastPlayed(int id, String file, String path, String time) {
		this.id = id;
		this.file = file;
		this.path = path;
		this.time = time;
	}

	public LastPlayed() {
	}

	// getting ID
	public int getID() {
		return this.id;
	}

	// setting id
	public void setID(int id) {
		this.id = id;
	}

	// getting file
	public String getFile() {
		return this.file;
	}

	// setting file
	public void setFile(String file) {
		this.file = file;
	}

	// getting path
	public String getPath() {
		return this.path;
	}

	// setting path
	public void setPath(String path) {
		this.path = path;
	}

	// getting time
	public String getTime() {
		return this.time;
	}

	// setting time
	public void setTime(String time) {
		this.time = time;
	}
}
