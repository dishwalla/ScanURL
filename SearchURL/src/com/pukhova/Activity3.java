package com.pukhova;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Activity3 extends Activity {
	protected TextView someText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity3);
		someText = (TextView)findViewById(R.id.someText);
	//	MainLogicThread thisThread = (MainLogicThread)Thread.currentThread();
	//	Source source = MainLogicThread.
		Source source = MainActivity.getSource();
		//source.getLinks().toString();
//		someText.setText(source.getLinks().toString());
	//	someText.setText(source.getPureUrls().toString());
		someText.setText(source.getString());
	//	someText.setText(source.getUrls().toString());
	//	someText.setText(source.getSubURLs().toString());
	}


}
