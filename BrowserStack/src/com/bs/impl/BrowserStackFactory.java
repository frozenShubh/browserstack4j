package com.bs.impl;

import com.bs.external.BrowserStack;

/**
 * @author Shubham
 *
 */
public class BrowserStackFactory {

	public static BrowserStack getBrowserStack(String user, String key){
		BrowserStack bs = new BrowserStackImpl(user, key);
		return bs;
	}
}
