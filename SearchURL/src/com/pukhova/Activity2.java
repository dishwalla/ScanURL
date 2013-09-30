package com.pukhova;


import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Activity2 extends ListActivity {
	protected TextView text;
	protected ListView l;
	protected Map<URL, Integer> results;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity2);
		text = (TextView)findViewById(R.id.text);
		l = (ListView)findViewById(android.R.id.list);
		//		List<URL> list2 = new ArrayList<URL>(list.keySet());              //  values from
		//		List<Integer> list3 = new ArrayList<Integer>(list.values());          // map
		Map<URL, Integer> list = MainLogic.map; 
		List<String> lst = new LinkedList<String>();

		for(Map.Entry<URL, Integer> e : list.entrySet()) {
			String first = e.getKey() + " ";
			String second = e.getValue().toString();
			String sum = (first + second);
			lst.add(sum);
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, lst);
		l.setAdapter(adapter);
		cleanUp();
		//	ArrayAdapter<URL> adapter = new ArrayAdapter<URL>(this, android.R.layout.simple_expandable_list_item_1, url);
		//ArrayAdapter<URL> adapter2 = new ArrayAdapter<URL>(this, android.R.layout.simple_expandable_list_item_1, nl);
	}

	public static void cleanUp(){
		MainLogic.visitedURls.clear(); 
		MainLogic.globalListOfUrls.clear();
		MainLogic.map.clear();
		MainLogic.processedURLs.set(0);
		MainLogic.progressBar.setProgress(0);
	}
}
