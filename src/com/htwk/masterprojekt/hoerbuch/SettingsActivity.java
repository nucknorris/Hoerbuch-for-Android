package com.htwk.masterprojekt.hoerbuch;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;

import com.htwk.masterprojekt.hoerbuch.pref.PrefsFragment;

public class SettingsActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Log.d("PREF", "LOADED");
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new PrefsFragment()).commit();
	}

}