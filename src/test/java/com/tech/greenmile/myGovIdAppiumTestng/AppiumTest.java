package com.tech.greenmile.myGovIdAppiumTestng;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class AppiumTest {

	public static void main(String[] args) throws MalformedURLException {
		// TODO Auto-generated method stub

		File fil = new File("src");
		File f = new File("au.gov.ato.mygovid.droid.integration-Signed.apk");
		
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel 4 API 29");
		cap.setCapability(MobileCapabilityType.APP, f.getAbsolutePath());
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME,"uiautomator2");
		AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),cap);
	}

}
