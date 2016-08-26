package com.bs.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.bs.model.RequestMethod;


/**
 * @author Shubham
 *
 */
public class RestClient {

	public static void sendDeleteRequest(String webPage, String name, String password){
		try{
		HttpURLConnection con = getConnection(webPage, name, password, RequestMethod.DELETE);
		//add reuqest header
		con.setRequestMethod("DELETE");
		con.setRequestProperty("User-Agent", "Chrome");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static File getFileFromUrl(String webPage, String name, String password, String path){
		File myFile = new File(path);
		try {

			URLConnection urlConnection = getConnection(webPage, name, password, RequestMethod.GET);
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			byte[] buffer = new byte[4096];
			int n = - 1;

			OutputStream output = new FileOutputStream( myFile );
			while ( (n = is.read(buffer)) != -1)
			{
			    if (n > 0)
			    {
			        output.write(buffer, 0, n);
			    }
			}
			output.close();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return myFile;
	}
	
	public static Object getJSONFromPage(String webPage, String name, String password){

		try {

			URLConnection urlConnection = getConnection(webPage, name, password, RequestMethod.GET);
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			String result = sb.toString();
			System.out.println(result);
			
			JSONParser parser = new JSONParser();
			Object obj= null;
			try {
				obj = parser.parse(result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return obj;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	
	}
	
	public static JSONObject getJSONFromPageUsingPost(String webPage, Map<String, String> parameters, String name, String password) throws Exception{
		HttpURLConnection con = getConnection(webPage, name, password, RequestMethod.POST);
		StringBuilder builder = new StringBuilder();
		
		for(String property: parameters.keySet()){
			builder.append(property + "=" + parameters.get(property));
			builder.append("&");
		}
		String urlParameters = builder.toString();
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + webPage);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		
		JSONParser parser = new JSONParser();
		Object returnedob= null;
		try {
			returnedob = parser.parse(response.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONObject array = (JSONObject)returnedob;
		return array;
	}
	
	private static HttpURLConnection getConnection(String webPage, String name, String password, RequestMethod method){
	try{
		URL obj = new URL(webPage);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		String authString = name + ":" + password;
		System.out.println("auth string: " + authString);
		String authEncBytes = DatatypeConverter.printBase64Binary(authString.getBytes());
		System.out.println("Base64 encoded auth string: " + authEncBytes);

		con.setRequestProperty("Authorization", "Basic " + authEncBytes);
		//add reuqest header
		con.setRequestMethod(method.toString());
		con.setRequestProperty("User-Agent", "Chrome");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		return con;
	} catch (MalformedURLException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return null;
	}

}