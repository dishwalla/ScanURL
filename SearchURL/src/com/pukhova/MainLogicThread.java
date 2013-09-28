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


	public void findUrl(String s){
		//Source source = new Source();
		//		MainLogicThread thisThread = (MainLogicThread)Thread.currentThread();
		//		Source source = thisThread.getSource();
		Source source = MainActivity.getSource();
		int totalCountOfURLs = 0;
		Pattern p =Pattern.compile("(<a[^>]+>.+?</a>)",  Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
		Matcher m = p.matcher(s);
		ArrayList<String> links = new ArrayList<String>();
		//Object[] linksAsString = links.toArray();
		
		while(m.find()){
			links.add(m.group());
			totalCountOfURLs ++;
		}
		source.setCountOfSubURLs(totalCountOfURLs);
		source.setLinks(links);
		Pattern p2 = Pattern.compile("href=\"(.*?)\"");
	/*	Matcher m2 = p2.matcher(linksAsString);
		int x = 0;
		List<String> url = new LinkedList<String>();
		while(m2.find()) {
			url.add(m2.group());  
			x++;
		}*/
		//source.setPureUrls(url);
		//	source.setString(linksAsString);
	}

	private Context getApplicationContext() {
		// TODO Auto-generated method stub
		return null;
	}
	public URL getMyUrl() {
		return myUrl;
	}

	public void setMyUrl(URL myUrl) {
		this.myUrl = myUrl;
	}

	/*public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}*/

}
