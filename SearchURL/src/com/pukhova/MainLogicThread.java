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
			//	File Root = Environment.getExternalStorageDirectory(); 
			//	File LogFile = new File(Root, "URLContent.txt");
			BufferedReader in = new BufferedReader(new InputStreamReader(myUrl.openStream()));
			String line = in.readLine();
			String sc = new Scanner(in).useDelimiter("\\A").next();
			in.close();
			findText(sc);
			findUrl(sc);
			//		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(LogFile, true),"UTF-8"));
			//			while (line != null){
			//		byte[] cp = line.getBytes();
			//		String sn = new String(cp, "UTF-8");
			//		out.write(sn);
			//		out.write("\n\n");
			//				line = in.readLine();
			//			}

		} 
		catch (MalformedURLException e) { 
			// new URL() failed
			// ...
		} 
		catch (IOException e) {   
			// openConnection() failed
			// ...
		} catch (Exception e) {
			// TODO Auto-generated catch block
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

	public static void findUrl(String s){
		Pattern pt = Pattern.compile("href");
        Matcher mt = pt.matcher(s);
        if (mt.find()) {
        //	MainLogicThread mlt = new MainLogicThread(myUrl);
		//	MainLogic.visitedURls.add(myUrl);
		//	mlt.start();
           // System.out.println(mt.group(1));
        } 
	}
}
