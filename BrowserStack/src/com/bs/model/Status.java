package com.bs.model;

/**
 * @author Shubham
 *
 */
public class Status {
	private String status;
	private String browser; 
	private String browser_version;
	private String os;
	private String os_version;
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public String getBrowser_version() {
		return browser_version;
	}
	public void setBrowser_version(String browser_version) {
		this.browser_version = browser_version;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getOs_version() {
		return os_version;
	}
	public void setOs_version(String os_version) {
		this.os_version = os_version;
	}
	@Override
	public String toString() {
		return "Status [status=" + status + ", browser=" + browser
				+ ", browser_version=" + browser_version + ", os=" + os
				+ ", os_version=" + os_version + ", id=" + id + "]";
	}
	
	
}
