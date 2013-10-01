package com.pukhova;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	public static String request;
	private SharedPreferences preferences; 

	protected EditText enterURL;
	protected EditText textToSearch;
	protected Button start;
	protected Button stop;
	protected Button pause;
	protected Button statistics;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		start = (Button)findViewById(R.id.start);
		stop = (Button)findViewById(R.id.stop);
		pause = (Button)findViewById(R.id.pause);
		statistics = (Button)findViewById(R.id.statistics);
		enterURL = (EditText)findViewById(R.id.enterURL_ET );
		textToSearch = (EditText)findViewById(R.id.search_ET);
		start.setOnClickListener(this);
		stop.setOnClickListener(this);
		pause.setOnClickListener(this);
		statistics.setOnClickListener(this);
		
		GlobalFields.visitedURls.clear(); 
		GlobalFields.globalListOfUrls.clear();
		GlobalFields.map.clear();
		GlobalFields.processedURLs.set(0);

	}

	@Override
	public void onClick(View v) {

		try{
			switch (v.getId()) 
			{
			case R.id.start:
				String threads = preferences.getString("threads", "1");
				GlobalFields.setThreads(Integer.valueOf(threads));
				String maxUrls = preferences.getString("urls", "1");
				GlobalFields.setMaxUrls(Integer.valueOf(maxUrls));
				GlobalFields.progressBar=(ProgressBar)findViewById(R.id.progressBar);
				GlobalFields.progressBar.setMax(GlobalFields.maxUrls);
				//GlobalFields.executor.setMaximumPoolSize(GlobalFields.threads);
				GlobalFields.executor.setCorePoolSize(GlobalFields.threads);
				new Thread(){
					@Override
					public void run() {
						URL myUrl;
						try {
							myUrl = new URL(enterURL.getText().toString());
							request = textToSearch.getText().toString();
							//MainLogicThread mlt = new 
							//mlt.start();
							Runnable worker = new MainLogicThread(myUrl);
							GlobalFields.executor.execute(worker);
							GlobalFields.processedURLs.incrementAndGet();
							GlobalFields.progressBar.incrementProgressBy(1);
						}
						catch (MalformedURLException e) {
							e.printStackTrace();}
					}
				}.start();
			//	if (GlobalFields.progressBar.getProgress() == GlobalFields.maxUrls){
			//		Toast.makeText(getBaseContext(),"Task Completed",Toast.LENGTH_LONG).show();
			//	}
				break;			
			case R.id.stop: 
				GlobalFields.executor.shutdown();
				Toast.makeText(getBaseContext(),"Task Completed",Toast.LENGTH_LONG).show();
				break;
			case R.id.pause: 
				break;
			case R.id.statistics:
				Intent intent = new Intent(MainActivity.this, Activity2.class);
				startActivity(intent);
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
