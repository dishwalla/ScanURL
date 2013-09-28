package com.pukhova;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Source {

	protected int CountOfSubURLs;
	protected List<URL> subURLs;
	protected String string;
	protected URL[] urls;

	public int getCountOfSubURLs() {
		return CountOfSubURLs;
	}

	public void setCountOfSubURLs(int countOfSubURLs) {
		CountOfSubURLs = countOfSubURLs;
	}

	public List<URL> getSubURLs() {
		return subURLs;
	}

	public void setSubURLs(List<URL> subURLs) {
		this.subURLs = subURLs;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public URL[] getUrls() {
		return urls;
	}

	public void setUrls(URL[] urls) {
		this.urls = urls;
	}

	
}
