package com.pukhova;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Activity2 extends Activity implements OnClickListener {
	protected TextView text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity2);
		text = (TextView)findViewById(R.id.text);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

}
