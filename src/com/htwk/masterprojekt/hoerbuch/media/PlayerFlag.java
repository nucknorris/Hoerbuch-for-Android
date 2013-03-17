package com.htwk.masterprojekt.hoerbuch.media;

public enum PlayerFlag {
	PLAYING(1), NOT_PLAYING(0);

	private int state;

	private PlayerFlag(int state) {
	}

	public int getState() {
		return this.state;
	}
}
