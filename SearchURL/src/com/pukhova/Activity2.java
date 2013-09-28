package com.pukhova;


import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Activity2 extends ListActivity {
	protected TextView text;
	protected ListView l;
	
//	protected List<LinkedHashMap<URL, Integer>> partys;
	protected Map<URL, Integer> results;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity2);
		text = (TextView)findViewById(R.id.text);
		l = (ListView)findViewById(android.R.id.list);
		//	getListView().setOnItemClickListener(itemListener);

	//	List<LinkedHashMap<URL, Integer>> list = MainLogic.results;
		
		Map<URL, Integer> list = MainLogic.map; 
		List<URL> list2 = new ArrayList<URL>(list.keySet());
		List<Integer> list3 = new ArrayList<Integer>(list.values());
		String common = "list2" + "list3"; 
		
	//	ArrayAdapter<Map<URL, Integer>> adapter = new ArrayAdapter<Map<URL, Integer>>(this, android.R.layout.simple_expandable_list_item_1, list);
	//	ArrayAdapter<LinkedHashMap<URL, Integer>> adapter = new ArrayAdapter<LinkedHashMap<URL, Integer>>(this, android.R.layout.simple_expandable_list_item_1, list2);
	
		ArrayAdapter<URL> adapter2 = new ArrayAdapter<URL>(this, android.R.layout.simple_expandable_list_item_1, list2);
		ArrayAdapter<Integer> adapter3 = new ArrayAdapter<Integer>(this, android.R.layout.simple_expandable_list_item_1, list3);
	//	l.setAdapter(adapter);
		l.setAdapter(adapter3);

		/*	OnItemClickListener itemListener = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
		};*/
	}


}
