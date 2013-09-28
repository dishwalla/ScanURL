package com.pukhova;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;

public class MainLogicThread extends Thread{
	protected Map<URL, Integer> map;
	protected URL myUrl;
	//protected Source source; 

	public MainLogicThread(URL url){
		myUrl = url;
	}

	@Override
	public void run() {
		try {
			URLConnection myURLConnection = myUrl.openConnection();
			myURLConnection.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(myUrl.openStream()));
			String line = in.readLine();
			String sc = new Scanner(in).useDelimiter("\\A").next();
			in.close();
			findText(sc);
			findUrl(sc);
		} 
		catch (MalformedURLException e) { 
			// new URL() failed
			// ...
		} 
		catch (IOException e) {   
			// openConnection() failed
			// ...
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void findText(String s) throws Exception {
		String request = MainActivity.request;
		int lastIndex = 0;
		int count = 0;
		while(lastIndex != -1){
			lastIndex = s.indexOf(request,lastIndex);
			if( lastIndex != -1){
				count ++;
				lastIndex+=request.length();
			}
		}
		MainLogic.map.put(myUrl, count); 
	}	
	//	Toast.makeText(getApplicationContext(), R.string.notFound, Toast.LENGTH_SHORT).show();


	public void findUrl(String s) {
		//Source source = new Source();
		//		MainLogicThread thisThread = (MainLogicThread)Thread.currentThread();
		//		Source source = thisThread.getSource();

		Source source = MainActivity.getSource();
		int totalCountOfURLs = 0;

		Pattern p = Pattern.compile("a href=\"(.*?)\"");
		Matcher m = p.matcher(s);
		ArrayList<String> links = new ArrayList<String>();
	//	ArrayList<URL> urls = new ArrayList<URL>();
	//	URL [] uri = new URL[links.size()];
		while(m.find()){
			links.add(m.group(1));
			totalCountOfURLs ++;
		}
		source.setCountOfSubURLs(totalCountOfURLs);
	//	source.setString(links.toString());

		URL[] u = new URL[links.size()];
		for(int i=0; i<links.size();i++){
			//   string.append(links.get(i).toString());
			try {
				u[i] = new URL(links.get(i).toString());
			//	urls.set(i, new URL(links.get(i).toString()));
			}
			catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	 
	//	source.setString(u.toString());
		source.setUrls(u);
		//	source.setString(string.toString());
	}

	private Context getApplicationContext() {
		return null;
	}
	public URL getMyUrl() {
		return myUrl;
	}

	public void setMyUrl(URL myUrl) {
		this.myUrl = myUrl;
	}

}
