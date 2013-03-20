package com.htwk.masterprojekt.hoerbuch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	Context c;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		c = this;

		setContentView(R.layout.activity_main);

		// player button
		// Button buttonPlayer = (Button) findViewById(R.id.PlayerButton);
		// buttonPlayer.setOnClickListener(new Button.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Log.d(TAG, "open player");
		// Intent intPlayer = new Intent(MainActivity.this,
		// PlayerActivity.class);
		// MainActivity.this.startActivity(intPlayer);
		// }
		// });

		// browser button
		ImageButton buttonBrowser = (ImageButton) findViewById(R.id.BrowserButton);

		// 0x00ff881d, 0x00FF0000
		buttonBrowser.getBackground().setColorFilter(
				new LightingColorFilter(0x00ff881d, 0xFFFF0000));
		buttonBrowser.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "open file browser");
				Intent intBrowser = new Intent(MainActivity.this,
						FileBrowserActivity.class);
				MainActivity.this.startActivity(intBrowser);
			}
		});

		// last played button
		ImageButton buttonLastPlayed = (ImageButton) findViewById(R.id.LastPlayedButton);
		buttonLastPlayed.getBackground().setColorFilter(
				new LightingColorFilter(0x000099cc, 0xFF000000));
		buttonLastPlayed.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "open last played list");
				Intent intLastPlayed = new Intent(MainActivity.this,
						LastPlayedActivity.class);
				MainActivity.this.startActivity(intLastPlayed);
			}
		});

		// // settings button
		// Button buttonSettings = (Button) findViewById(R.id.SettingsButton);
		// buttonSettings.setOnClickListener(new Button.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Log.d(TAG, "open settings");
		// Intent intSettings = new Intent(MainActivity.this,
		// SettingsActivity.class);
		// MainActivity.this.startActivity(intSettings);
		// }
		// });
		//
		// // exit button
		// Button buttonExit = (Button) findViewById(R.id.ExitButton);
		// buttonExit.setOnClickListener(new Button.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Log.d(TAG, "bring app in background");
		// moveTaskToBack(true);
		// }
		// });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_settings:
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
