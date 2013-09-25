package com.pukhova;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.widget.Toast;

public class MainLogicThread extends Thread{

	protected URL myUrl;
	public int textFound;

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
		if (s.toUpperCase().indexOf(request.toUpperCase()) != -1)
		{
			MainLogic.requestedWordFound.add(myUrl);
			textFound +=1;
			
		}
		else Toast.makeText(getApplicationContext(), R.string.notFound, Toast.LENGTH_SHORT).show();
	}

	private Context getApplicationContext() {
		// TODO Auto-generated method stub
		return null;
	}

	public void findUrl(String s){
		/*   try {
	            Pattern patt = Pattern.compile(pattern);
	            Matcher matcher = patt.matcher(s);
	            return matcher.matches();
	        } catch (RuntimeException e) {
	        return URL;
	        
	        final Pattern pattern = Pattern.compile("<a href=>(.+?)<>>");
	        final Pattern pattern = Pattern.compile(<a[^>]+href=["']?([^'"> ]+)["']?[^>]*>/i);
	        final Matcher matcher = pattern.matcher(s);
	        matcher.find();
	        System.out.println(matcher.group(1)); // Prints String I want to extract
	    }     */

		Pattern p = Pattern.compile("<a href='(.*?)'>");
		Matcher m = p.matcher(s);
		while(m.find()) {
		//   System.out.println(m.group(0));
		   System.out.println(m.group(1));
		   String newURL = m.group(1);
		}
         
	}
}
