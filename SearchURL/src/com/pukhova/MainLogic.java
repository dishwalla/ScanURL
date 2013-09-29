package com.pukhova;

import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MainLogic {

	public static List<URL> visitedURls= new LinkedList<URL>();
	public static List<URL> globalListOfUrls = Collections.synchronizedList(new LinkedList<URL>());
	public static Map<URL, Integer> map = new ConcurrentHashMap<URL, Integer>();
	public static int threads;
	public static int maxUrls;
	public static int processedURLs = 0;
	
	public static int getThreads() {
		return threads;
	}

	public static void setThreads(int threads) {
		MainLogic.threads = threads;
	}

	public static int getMaxUrls() {
		return maxUrls;
	}

	public static void setMaxUrls(int maxUrls) {
		MainLogic.maxUrls = maxUrls;
	}

	public void process() throws Exception {
		
	}

	public static int getProcessedURLs() {
		return processedURLs;
	}

	public static void setProcessedURLs(int processedURLs) {
		MainLogic.processedURLs = processedURLs;
	}
	
}
