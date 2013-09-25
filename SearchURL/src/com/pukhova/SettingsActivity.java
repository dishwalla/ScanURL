package com.pukhova;

import android.os.Bundle;
import android.preference.PreferenceActivity;


public class SettingsActivity extends PreferenceActivity {
	
	private static final boolean ALWAYS_SIMPLE_PREFS = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.settings);

	}
}
