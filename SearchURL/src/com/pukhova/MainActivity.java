package com.pukhova;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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

	public static Source source = new Source();

	private static int myProgress=0;
	private ProgressBar progressBar;
	private int progressStatus=0;
	private Handler myHandler=new Handler();

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

			String threads = preferences.getString("threads", "1");
			MainLogic.setThreads(Integer.valueOf(threads));
		//	if(threads > 6){threads = preferences.getInt("threads", 6);}
			String maxUrls = preferences.getString("urls", "1");
			MainLogic.setMaxUrls(Integer.valueOf(maxUrls));
		//	if(maxUrls > 150){threads = preferences.getInt("urls", 150);}
	}

	@Override
	public void onClick(View v) {

		try{
			switch (v.getId()) 
			{
			case R.id.start:

				myProgress=0;
				progressBar=(ProgressBar)findViewById(R.id.progressBar);
				progressBar.setMax(100);

				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						while(progressStatus<100)
						{
							try {
								progressStatus=performTask();
							} catch (MalformedURLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							myHandler.post(new Runnable()
							{
								public void run() {
									progressBar.setProgress(progressStatus);
								}
							});

						}
						myHandler.post(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								Toast.makeText(getBaseContext(),"Task Completed",Toast.LENGTH_LONG).show();
								progressStatus=0; 
								myProgress=0;

							}
						});
					}
					private int performTask() throws MalformedURLException
					{
						try {
							URL myUrl = new URL(enterURL.getText().toString());
							request = textToSearch.getText().toString();
							MainLogicThread mlt = new MainLogicThread(myUrl);
							//MainLogic.visitedURls.add(myUrl);
							mlt.start();

							Thread.sleep(100);
						} catch (InterruptedException e)
						{
							e.printStackTrace();
						}
						return ++myProgress;	
					}
				}).start();
				break;			

			case R.id.stop: 
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
	
	public static Source getSource() {
		return source;
	}
	public static void setSource(Source source) {
		MainActivity.source = source;
	}
}
