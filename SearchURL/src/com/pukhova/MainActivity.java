package com.pukhova;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends Activity implements OnClickListener {
	public static String request;
	private SharedPreferences preferences; 

	protected EditText enterURL;
	protected EditText textToSearch;
	protected Button start;
	protected Button stop;
	protected Button pause;

	private static final int PROGRESS = 0x1;
	private ProgressBar mProgress;
	private int mProgressStatus = 0;

	private Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		start = (Button)findViewById(R.id.start);
		stop = (Button)findViewById(R.id.stop);
		pause = (Button)findViewById(R.id.pause);
		start.setOnClickListener(this);
		enterURL = (EditText)findViewById(R.id.enterURL_ET );
		textToSearch = (EditText)findViewById(R.id.search_ET);
		mProgress = (ProgressBar) findViewById(R.id.progressBar);
	}


	@Override
	public void onClick(View v) {

		try {
			switch (v.getId()) 
			{

			case R.id.start:

				new Thread(new Runnable() {
					public void run() {
						while (mProgressStatus < 100) {
					       mProgressStatus = doWork();
						
							// Update the progress bar
							mHandler.post(new Runnable() {
								public void run() {
									mProgress.setProgress(mProgressStatus);
								}
							});
						}
					}
				}).start();
				//	Intent intent = new Intent(MainActivity.this, Activity2.class);
				//	startActivity(intent);
				//	MainActivity.this.finish();
				//	break;			
			}		

		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int doWork(){
		try {
			URL myUrl;
			myUrl = new URL(enterURL.getText().toString());
			request = textToSearch.getText().toString();
			MainLogicThread mlt = new MainLogicThread(myUrl);
			MainLogic.visitedURls.add(myUrl);
			mlt.start();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 100;
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
