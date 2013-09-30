package com.pukhova;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;


public class MainLogicThread extends Thread{
	protected URL myUrl;

	protected int CountOfSubURLs;
	protected List<String> subURLs;
	protected URL currentURL;

	public int getCountOfSubURLs() {
		return CountOfSubURLs;
	}

	public void setCountOfSubURLs(int countOfSubURLs) {
		CountOfSubURLs = countOfSubURLs;
	}

	public List<String> getSubURLs() {
		return subURLs;
	}

	public void setSubURLs(List<String> subURLs) {
		this.subURLs = subURLs;
	}

	public URL getCurrentURL() {
		return currentURL;
	}

	public void setCurrentURL(URL currentURL) {
		this.currentURL = currentURL;
	}


	public MainLogicThread(URL url){
		myUrl = url;
	}

	@Override
	public void run() {
		try {
			URLConnection myURLConnection = myUrl.openConnection();
			myURLConnection.connect();
			InputStream in = myUrl.openStream();
			//map.put(myUrl,Downloading")
			String sc = new Scanner(in).useDelimiter("\\A").next();
			in.close();
			findText(sc);
			findUrl(sc);
			MainLogic.visitedURls.add(myUrl);
			int maxCountOfURLs = MainLogic.getMaxUrls();
			int processedURLs = MainLogic.processedURLs.get();
			List<URL> mainList = MainLogic.globalListOfUrls;
			synchronized(mainList) {
				for(URL currentURL : mainList){
					if(!MainLogic.visitedURls.contains(currentURL) && processedURLs < maxCountOfURLs){
						Thread t = new MainLogicThread(currentURL);
						t.start();
						Log.w("Search", "Processing url:" + currentURL + " " + processedURLs);
						processedURLs = MainLogic.processedURLs.incrementAndGet();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (!MainLogic.map.containsKey(myUrl)){
				MainLogic.map.put(myUrl, 0);
			}
		}
		finally {
			MainLogic.progressBar.incrementProgressBy(1);
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


	public List<URL> findUrl(String s) {
		//int totalCountOfURLs = 0;
		Pattern p = Pattern.compile("a href=\"(.*?)\"");
		Matcher m = p.matcher(s);
		ArrayList<String> links = new ArrayList<String>();
		while(m.find()){
			links.add(m.group(1));
		//	totalCountOfURLs ++;
		}
	//	setCountOfSubURLs(totalCountOfURLs);
		setSubURLs(links);
		List<URL> u = new ArrayList<URL>();
			for(String currentURL : links){
				URL nextURL;
				try {
					nextURL = new URL(currentURL);
					u.add(nextURL);
					MainLogic.globalListOfUrls.add(nextURL);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
			return u;
	}

	public URL getMyUrl() {
		return myUrl;
	}

	public void setMyUrl(URL myUrl) {
		this.myUrl = myUrl;
	}

}
