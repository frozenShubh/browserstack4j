package com.bs.external;

import java.util.List;
import java.util.Map;

import com.bs.model.FileFormat;
import com.bs.model.OS;
import com.bs.model.Status;

/**
 * @author Shubham
 *
 */
public interface BrowserStack {
	List<OS> getBrowsers(boolean all, boolean flat);
	String postWorker(Map<String, String> parameters);
	Object captureScreenshot(int workerID, FileFormat format, String filepath);
	Status getWorkerStatus(int workerID);
	List<Status> getAllStatus();
	void deleteWorker(int workerID);
}
