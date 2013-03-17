package com.htwk.masterprojekt.hoerbuch.media;

import android.graphics.Bitmap;

public class MediaFile {

	public String getFileName() {
		return artist + " " + title;
	}

	public String getFileNameLong() {
		return fileNameLong;
	}

	public void setFileNameLong(String fileNameLong) {
		this.fileNameLong = fileNameLong;
	}

	public String getFileNameShort() {
		return fileNameShort;
	}

	public void setFileNameShort(String fileNameShort) {
		this.fileNameShort = fileNameShort;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private String path;
	private String fileNameLong;
	private String fileNameShort;
	private String title;
	private String artist;
	private double duration;
	private Bitmap cover;
	private String id;

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
