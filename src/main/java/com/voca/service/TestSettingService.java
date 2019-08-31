package com.voca.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import com.voca.model.TestSetting;

public class TestSettingService {
	public static TestSetting getStoredTestSetting() {
		try {
			FileInputStream fi = new FileInputStream(new File("testSetting.txt"));
			ObjectInputStream oi = new ObjectInputStream(fi);

			// Read objects
			TestSetting storedTestSetting = (TestSetting) oi.readObject();
			//testSetting.setSelectedTopics(storedTestSetting.getSelectedTopics());
			//testSetting = storedTestSetting;

			oi.close();
			fi.close();
			
			return storedTestSetting;
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return null;
			
	}

}
