package com.pukhova;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainLogicThread extends Thread{
	protected Map<URL, Integer> map;
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
			//Source source = MainActivity.getSource();
			//source.setCurrentURL(myUrl);
			MainLogicThread thisThread = (MainLogicThread)Thread.currentThread();
			thisThread.setCurrentURL(myUrl);
			
			URLConnection myURLConnection = myUrl.openConnection();
			myURLConnection.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(myUrl.openStream()));
			String line = in.readLine();
			String sc = new Scanner(in).useDelimiter("\\A").next();
			in.close();
			findText(sc);
			findUrl(sc);
			//List<String> listOfUrls = findUrl(sc);
			URL thisUrl = thisThread.getCurrentURL();
			MainLogic.visitedURls.add(thisUrl);
		//	MainLogic.visitedURls.add(myUrl);
			int maxCountOfURLs = MainLogic.getMaxUrls();
			int processedURLs = MainLogic.getProcessedURLs();
			//for(String currentURL : listOfUrls){
			List<URL> mainList = MainLogic.globalListOfUrls;
			synchronized(mainList) {
				//   Iterator i = list.iterator();
				for(URL currentURL : mainList){
					if(!MainLogic.visitedURls.contains(currentURL) && processedURLs < maxCountOfURLs){
						Thread t = new MainLogicThread(currentURL);
						t.start();
					}
					//else return;
				}
			}
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
	//	Source source = MainActivity.getSource();
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
		MainLogicThread thisThread = (MainLogicThread)Thread.currentThread();
		URL url =thisThread.getCurrentURL();
		//URL url = source.getCurrentURL();
		MainLogic.map.put(url, count); 

	}	


	public void findUrl(String s) {
	//	Source source = MainActivity.getSource();
		int totalCountOfURLs = 0;
		Pattern p = Pattern.compile("a href=\"(.*?)\"");
		Matcher m = p.matcher(s);
		ArrayList<String> links = new ArrayList<String>();
		while(m.find()){
			links.add(m.group(1));
			totalCountOfURLs ++;
		}
		MainLogicThread thisThread = (MainLogicThread)Thread.currentThread();
		thisThread.setCountOfSubURLs(totalCountOfURLs);
		thisThread.setSubURLs(links);
	//	source.setCountOfSubURLs(totalCountOfURLs);
	//	source.setSubURLs(links);
		try {
			for(String currentURL : links){
				URL nextURL = new URL(currentURL);
				MainLogic.globalListOfUrls.add(nextURL);}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		MainLogic.setProcessedURLs(+1);

	}

	public static void cleanUp() throws Exception{
		List<URL> visitedURls= MainLogic.visitedURls;
		List<URL> globalListOfUrls = MainLogic.globalListOfUrls;
		Map<URL, Integer> map = MainLogic.map; 
		visitedURls.removeAll(visitedURls);
		globalListOfUrls.removeAll(globalListOfUrls);
		map.remove(map);
	}


	public URL getMyUrl() {
		return myUrl;
	}

	public void setMyUrl(URL myUrl) {
		this.myUrl = myUrl;
	}

}
