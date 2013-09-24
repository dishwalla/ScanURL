package com.pukhova;

import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {
	public static String request;
	private SharedPreferences preferences; 
	protected EditText enterURL;
	protected EditText textToSearch;
	protected Button done;
	protected Button search;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		search = (Button)findViewById(R.id.search);
		search.setOnClickListener(this);
		enterURL = (EditText)findViewById(R.id.enterURL_ET );
		textToSearch = (EditText)findViewById(R.id.search_ET);
	}


	@Override
	public void onClick(View v) {
		
		try {
			switch (v.getId()) 
			{
		
			case R.id.search:
					URL myUrl = new URL(enterURL.getText().toString());
					request = textToSearch.getText().toString();
					MainLogicThread mlt = new MainLogicThread(myUrl);
					MainLogic.visitedURls.add(myUrl);
					mlt.start();
					MainActivity.this.finish();
					break;			
			}		

		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.settings, menu);
		MenuItem pref = menu.findItem(R.id.action_prefs);
		MenuItem exit = menu.findItem(R.id.action_exit);
		return true;
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		item.setChecked(true);
		switch (item.getItemId())
		{
		case R.id.action_exit:
			android.os.Process.killProcess(android.os.Process.myPid());
			super.onDestroy();
			break; 
		case R.id.action_prefs:
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
		} 

		return super.onOptionsItemSelected(item);
	}
	
	public boolean saveCheckedMenuItems(String name, Boolean MI) 
	{
		preferences.edit().putBoolean(name, MI).commit();
		return MI;
	}

}
