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
			String sc = new Scanner(in).useDelimiter("\\A").next();
			in.close();
			findText(sc);
			findUrl(sc);
			GlobalFields.visitedURls.add(myUrl);
			int maxCountOfURLs = GlobalFields.getMaxUrls();
			int processedURLs = GlobalFields.processedURLs.get();
			List<URL> mainList = GlobalFields.globalListOfUrls;
			synchronized(mainList) {
				for(URL currentURL : mainList){
					if(!GlobalFields.visitedURls.contains(currentURL) && processedURLs < maxCountOfURLs){
						GlobalFields.executor.execute(new MainLogicThread(currentURL));
					//	Thread t = new MainLogicThread(currentURL);
					//	t.start();
						Log.w("Search", "Processing url:" + currentURL + " " + processedURLs);
						processedURLs = GlobalFields.processedURLs.incrementAndGet();
					}
				}
			}
		} 
		catch (MalformedURLException e) {
			GlobalFields.map.put(myUrl, "Error");
			}
			catch (IOException e) {
				GlobalFields.map.put(myUrl, "Connection failed");
			} 
		catch (Exception e) {
			e.printStackTrace();
			if (!GlobalFields.map.containsKey(myUrl)){
				Integer i = 0;
				GlobalFields.map.put(myUrl, i.toString());
			}
			
		}
		finally {
			GlobalFields.progressBar.incrementProgressBy(1);
		}

	}

	public void findText(String s) throws Exception {
		String request = MainActivity.request;
		int lastIndex = 0;
		Integer count = 0;
		while(lastIndex != -1){
			lastIndex = s.indexOf(request,lastIndex);
			if( lastIndex != -1){
				count ++;
				lastIndex+=request.length();
			}
		}
		GlobalFields.map.put(myUrl, count.toString()); 
	}	


	public List<URL> findUrl(String s) {
		Integer totalCountOfURLs = 0;
		Pattern p = Pattern.compile("a href=\"(.*?)\"");
		Matcher m = p.matcher(s);
		ArrayList<String> links = new ArrayList<String>();
		while(m.find()){
			links.add(m.group(1));
			totalCountOfURLs ++;
		}
		GlobalFields.totalCountOfURLs.getAndAdd(totalCountOfURLs); 
		setSubURLs(links);
		List<URL> u = new ArrayList<URL>();
			for(String currentURL : links){
				URL nextURL;
				try {
					nextURL = new URL(currentURL);
					u.add(nextURL);
					GlobalFields.globalListOfUrls.add(nextURL);
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
