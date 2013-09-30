package com.pukhova;

import java.net.URL;
import java.util.List;

public class Source {

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


}
