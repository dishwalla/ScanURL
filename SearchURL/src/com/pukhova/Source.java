package com.pukhova;

import java.net.URL;
import java.util.List;

public class Source {

	protected int CountOfSubURLs;
	protected List<String> subURLs;

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

	/*public List<String> getFuck() {
		return subURLs;
	}

	public void setFuck(List<String> fuck) {
		this.subURLs = fuck;
	}*/


}
