package com.htwk.masterprojekt.hoerbuch.media;

import android.graphics.Bitmap;

public class MediaFile {

	public boolean isDir() {
		return isDir;
	}

	public void setDir(boolean isDir) {
		this.isDir = isDir;
	}

	public String getFileName() {
		return artist + " " + title;
	}

	public String getPathShort() {
		return pathShort;
	}

	public void setPathShort(String pathShort) {
		this.pathShort = pathShort;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private String path;
	private String pathShort;
	private String title;
	private String artist;
	private Long duration;
	private Bitmap cover;
	private String id;
	boolean isDir = false;

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

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
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
