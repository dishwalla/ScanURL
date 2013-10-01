package com.pukhova;


import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Activity2 extends ListActivity {
	protected TextView text;
	protected ListView l;
	protected Map<URL, Integer> results;
	protected String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity2);
		text = (TextView)findViewById(R.id.text);
		l = (ListView)findViewById(android.R.id.list);
		getListView().setOnItemClickListener(itemListener);
		Map<URL, String> list = GlobalFields.map; 
		List<String> lst = new LinkedList<String>();
		for(Map.Entry<URL, String> e : list.entrySet()) {
			String first = e.getKey() + " text found ";
			String second = e.getValue().toString() + " times";
			String sum = (first + second);
			lst.add(sum);
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, lst);
		l.setAdapter(adapter);
		cleanUp();
	}


	OnItemClickListener itemListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
			name = parent.getItemAtPosition(position).toString();
			Toast.makeText(getApplicationContext(),	"On link " + name, Toast.LENGTH_LONG).show();

		}
	};
	public static void cleanUp(){
		GlobalFields.visitedURls.clear(); 
		GlobalFields.globalListOfUrls.clear();
		GlobalFields.map.clear();
		GlobalFields.processedURLs.set(0);
		GlobalFields.progressBar.setProgress(0);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(Activity2.this, MainActivity.class); 
			startActivity(intent);
			Activity2.this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
