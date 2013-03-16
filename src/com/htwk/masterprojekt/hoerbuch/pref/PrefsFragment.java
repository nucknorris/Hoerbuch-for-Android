package com.htwk.masterprojekt.hoerbuch.pref;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.htwk.masterprojekt.hoerbuch.R;

public class PrefsFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// Load the preferences from an XML resource
		addPreferencesFromResource(R.layout.activity_settings);
	}

}