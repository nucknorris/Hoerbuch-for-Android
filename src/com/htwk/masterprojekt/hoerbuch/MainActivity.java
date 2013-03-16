package com.htwk.masterprojekt.hoerbuch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/*
 * only a overview for all activitis
 * (only testing)
 * */
public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	Context c;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		c = this;

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// player button
		Button buttonPlayer = (Button) findViewById(R.id.PlayerButton);
		buttonPlayer.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("MAIN", "open player");
				Intent intPlayer = new Intent(MainActivity.this,
						PlayerActivity.class);
				MainActivity.this.startActivity(intPlayer);
			}
		});

		// browser button
		Button buttonBrowser = (Button) findViewById(R.id.BrowserButton);
		buttonBrowser.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("MAIN", "open file browser");
				Intent intBrowser = new Intent(MainActivity.this,
						FileBrowserActivity.class);
				MainActivity.this.startActivity(intBrowser);
			}
		});

		// last played button
		Button buttonLastPlayed = (Button) findViewById(R.id.LastPlayedButton);
		buttonLastPlayed.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("MAIN", "open last played list");
				Intent intLastPlayed = new Intent(MainActivity.this,
						LastPlayedActivity.class);
				MainActivity.this.startActivity(intLastPlayed);
			}
		});

		// settings button
		Button buttonSettings = (Button) findViewById(R.id.SettingsButton);
		buttonSettings.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("MAIN", "open settings");
				Intent intSettings = new Intent(MainActivity.this,
						SettingsActivity.class);
				MainActivity.this.startActivity(intSettings);
			}
		});

		// exit button
		Button buttonExit = (Button) findViewById(R.id.ExitButton);
		buttonExit.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("MAIN", "bring app in background");
				moveTaskToBack(true);
			}
		});

		// testing prefs

		SharedPreferences app_preferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String dir = app_preferences.getString("path", "");
		Log.d("MAIN", "Test:" + dir);

	}
}
