package com.htwk.masterprojekt.hoerbuch.media;

import android.graphics.Bitmap;

public class MediaFile {

	private String path;
	private String fileName;
	private String title;
	private String artist;
	private double duration;
	private Bitmap cover;
	private String id;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTitle() {
		return fileName;
	}

	public void setTitle(String title) {
		this.fileName = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public Bitmap getCover() {
		return cover;
	}

	public void setCover(Bitmap cover) {
		this.cover = cover;
	}

	public MediaFile() {
	}

}
