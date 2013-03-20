package com.htwk.masterprojekt.hoerbuch.db.model;

public class Song {

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	// private variables
	int id;
	String file;
	String artist;
	String album;

	// constructor
	public Song(int id, String file, String artist, String album) {
		this.id = id;
		this.file = file;
		this.artist = artist;
		this.album = album;
	}

}
