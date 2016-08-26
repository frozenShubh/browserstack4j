package com.bs.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.bs.external.BrowserStack;
import com.bs.model.Browser;
import com.bs.model.FileFormat;
import com.bs.model.OS;
import com.bs.model.Status;

/**
 * @author Shubham
 *
 */
public class BrowserStackImpl implements BrowserStack{
	
	private static String user;
	private static String key;
	private final static String getBrowsersURL = "http://api.browserstack.com/3/browsers";
	private final static String postWorkerUrl = "http://api.browserstack.com/3/worker";
	private final static String screenShotUrl = "http://api.browserstack.com/3/worker/?/screenshot";
	private final static String statusUrl = "http://api.browserstack.com/3/worker/?";
	private final static String allStatusUrl = "http://api.browserstack.com/3/workers";
	private final static String all = "?all=true";
	
	BrowserStackImpl(String user, String key){
		this.user = user;
		this.key = key;
	}
	
	@Override
	public List<OS> getBrowsers(boolean all, boolean flat) {
		String finalUrl = getBrowsersURL;
		if(all)finalUrl+=all;
		
		JSONObject returnedObject = (JSONObject)RestClient.getJSONFromPage(finalUrl, user, key);
		List<OS> osList = new ArrayList<OS>();
		
		for(Object osName: returnedObject.keySet()){
			OS newOS = new OS();
			newOS.setName(osName.toString());
			if(osName instanceof String){
				JSONObject osNameJson = (JSONObject)returnedObject.get(osName);
				for(Object osVersion: osNameJson.keySet()){
					if(osVersion instanceof String){
						JSONArray browsersForThisVersion =  (JSONArray)osNameJson.get(osVersion);
						for(Object browser: browsersForThisVersion){
							JSONObject browserJson = (JSONObject)browser;
							Browser myBrowser = new Browser();
							myBrowser.setBrowserVersion((String)browserJson.get("browser_version"));
							myBrowser.setBrowserName((String)browserJson.get("browser"));
							newOS.addBrowser(osVersion.toString(), myBrowser);
						}
					}
				}
					
					
					
			}
			osList.add(newOS);
		}
		
		return osList;
	}

	@Override
	public String postWorker(Map<String, String> parameters) {
		JSONObject returnedObject=null;
		try {
			returnedObject = RestClient.getJSONFromPageUsingPost(postWorkerUrl, parameters, user, key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub er
		if(returnedObject!=null)
		return returnedObject.get("id").toString();
		else
			return null;
	}

	

	@Override
	public Object captureScreenshot(int workerID, FileFormat format, String filepath) {
		String finalUrl = screenShotUrl.replaceAll("\\?", Integer.toString(workerID)) + "." +format.toString().toLowerCase();
		File returnedObject = null;
		if(format.equals(FileFormat.PNG)){
			returnedObject = RestClient.getFileFromUrl(finalUrl, user, key, filepath);
		}else{
			throw new UnsupportedOperationException("JSON/XML not supported yet. Hang on ! ");
		}
		// TODO Auto-generated method stub
		return returnedObject;
	}

	@Override
	public Status getWorkerStatus(int workerID) {
		String finalUrl = statusUrl.replaceAll("\\?", Integer.toString(workerID));
		JSONObject jsonobj = (JSONObject)RestClient.getJSONFromPage(finalUrl, user, key);
		Status status = new Status();
		status.setBrowser(jsonobj.get("browser").toString());
		status.setBrowser_version(jsonobj.get("browser_version").toString());
		status.setOs(jsonobj.get("os").toString());
		status.setOs_version(jsonobj.get("os_version").toString());
		status.setStatus(jsonobj.get("status").toString());
		status.setId(jsonobj.get("id").toString());
		return status;
	}

	@Override
	public List<Status> getAllStatus() {
		
		String finalUrl = allStatusUrl;
		JSONArray array = (JSONArray)RestClient.getJSONFromPage(finalUrl, user, key);
		List<Status> statusList = new ArrayList<Status>();
		
		for(Object obj: array){
			JSONObject jsonobj = (JSONObject)obj;
			Status status = new Status();
			status.setBrowser(jsonobj.get("browser").toString());
			status.setBrowser_version(jsonobj.get("browser_version").toString());
			status.setOs(jsonobj.get("os").toString());
			status.setOs_version(jsonobj.get("os_version").toString());
			status.setStatus(jsonobj.get("status").toString());
			status.setId(jsonobj.get("id").toString());
			statusList.add(status);
		}
		
		return statusList;
	}

	@Override
	public void deleteWorker(int workerID) {
		String finalUrl = statusUrl.replaceAll("\\?", Integer.toString(workerID));
		RestClient.sendDeleteRequest(finalUrl, user, key);
	}

}
