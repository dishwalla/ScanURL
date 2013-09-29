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


public class MainLogicThread extends Thread{
	protected Map<URL, Integer> map;
	protected URL myUrl;

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
			List<String> listOfUrls = findUrl(sc);
			MainLogic.setProcessedURLs(+1);
			MainLogic.visitedURls.add(myUrl);
			int maxCountOfURLs = MainLogic.getMaxUrls();
			int processedURLs = MainLogic.getProcessedURLs();
			for(String currentURL : listOfUrls){
				if(!MainLogic.visitedURls.contains(currentURL) && processedURLs <= maxCountOfURLs){
					Thread t = new MainLogicThread(new URL(currentURL));
					t.start();
				}
				else return;
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


	public List<String> findUrl(String s) {
		Source source = MainActivity.getSource();
		int totalCountOfURLs = 0;
		Pattern p = Pattern.compile("a href=\"(.*?)\"");
		Matcher m = p.matcher(s);
		ArrayList<String> links = new ArrayList<String>();
		//	ArrayList<URL> urls = new ArrayList<URL>();
		List<URL> url = new LinkedList<URL>();
		//	List<URL> url = MainLogic.listOfUrls;
		//	URL [] uri = new URL[links.size()];
		while(m.find()){
			links.add(m.group(1));
			totalCountOfURLs ++;
		}
		source.setCountOfSubURLs(totalCountOfURLs);
		source.setSubURLs(links);
		return links;
		
	}

	/*public static void cleanUp() throws Exception{
		CommandProcessorThread thisThread = (CommandProcessorThread)Thread.currentThread();
		Map<String, String> gamePares = MultiServer.gamePares;
		List<String> sc = MultiServer.selectedClients;
		Source source = thisThread.getSource();
		String name = Utils.obtainMyName();
		sc.remove(name);
		gamePares.remove(name);
		source.getGameData().clear();
	}*/

/*	private Context getApplicationContext() {
		return null;
	} */
	
	public URL getMyUrl() {
		return myUrl;
	}

	public void setMyUrl(URL myUrl) {
		this.myUrl = myUrl;
	}
	//Source source = new Source();
	//		MainLogicThread thisThread = (MainLogicThread)Thread.currentThread();
	//		Source source = thisThread.getSource();
}
