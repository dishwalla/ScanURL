package com.pukhova;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Source {

	protected int CountOfSubURLs;
	protected List<URL> subURLs;
	protected ArrayList<String> links;
	protected List<String> pureUrls;
	protected String string;

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

	public ArrayList<String> getLinks() {
		return links;
	}

	public void setLinks(ArrayList<String> links) {
		this.links = links;
	}

	public List<String> getPureUrls() {
		return pureUrls;
	}

	public void setPureUrls(List<String> pureUrls) {
		this.pureUrls = pureUrls;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}


	
}
