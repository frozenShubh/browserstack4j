package com.bs.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Shubham
 *
 */
public class OS {
	
	public Map<String, List<Browser>> getVersionMap() {
		return versionMap;
	}

	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private Map<String, List<Browser>> versionMap = new HashMap<String, List<Browser>>();
	
	public List<Browser> getBrowserList(String key){
		return versionMap.get(key);
	}
	
	public void setBrowserList(String key, List<Browser> list){
		 versionMap.put(key, list);
	}
	
	public void addBrowser(String key, Browser browser){
		if(versionMap.containsKey(key)){
			versionMap.get(key).add(browser);
		}else{
			List<Browser> newList = new ArrayList<Browser>();
			newList.add(browser);
			versionMap.put(key, newList);
		}
	}
	
}
