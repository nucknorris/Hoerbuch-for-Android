package com.htwk.masterprojekt.hoerbuch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	Context c;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		c = this;

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button buttonPlayer = (Button) findViewById(R.id.PlayerButton);
		buttonPlayer.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("MAIN", "CLICK");
				Intent intPlayer = new Intent(MainActivity.this,
						PlayerActivity.class);
				MainActivity.this.startActivity(intPlayer);
			}
		});
		Button buttonBrowser = (Button) findViewById(R.id.BrowserButton);
		buttonBrowser.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("MAIN", "CLICK");
				Intent intBrowser = new Intent(MainActivity.this,
						FileBrowserActivity.class);
				MainActivity.this.startActivity(intBrowser);
			}
		});

		Button buttonLastPlayed = (Button) findViewById(R.id.LastPlayedButton);
		buttonLastPlayed.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("MAIN", "CLICK");
				Intent intLastPlayed = new Intent(MainActivity.this,
						LastPlayedActivity.class);
				MainActivity.this.startActivity(intLastPlayed);
			}
		});

		Button buttonSettings = (Button) findViewById(R.id.SettingsButton);
		buttonSettings.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("MAIN", "CLICK");
				Intent intSettings = new Intent(MainActivity.this,
						SettingsActivity.class);
				MainActivity.this.startActivity(intSettings);
			}
		});
		Button buttonExit = (Button) findViewById(R.id.ExitButton);
		buttonExit.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("MAIN", "CLICK");
				moveTaskToBack(true);
			}
		});
	}
}
