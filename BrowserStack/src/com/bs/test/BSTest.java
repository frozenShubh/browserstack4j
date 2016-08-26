package com.bs.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bs.external.BrowserStack;
import com.bs.impl.BrowserStackFactory;
import com.bs.model.FileFormat;
import com.bs.model.OS;
import com.bs.model.Status;

public class BSTest {
	public static void main(String[] args) {
		BrowserStack bs = BrowserStackFactory.getBrowserStack("YouName", "YourAPIKEY");
		List<OS> mylist = bs.getBrowsers(false, false);
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("os", "Windows");
		parameters.put("os_version", "7");
		parameters.put("browser", "opera");
		parameters.put("browser_version", "10.6");
		parameters.put("url", "https://github.com/404");
		System.out.println(Integer.parseInt(bs.postWorker(parameters)));
		//check status before calling this 
//		bs.captureScreenshot(20475670, FileFormat.PNG, "C:\\Shubham\\newfile.png");
		List<Status> list = bs.getAllStatus();
	}
}
