package com.tech.greenmile.myGovIdAppiumTestng;

import java.util.HashMap;
import java.util.Map;

public class DeviceConfiguration {

	CommandPrompt cmd = new CommandPrompt();
	Map<String, String> devices = new HashMap<String, String>();
	
	/**
	 * This method start adb server
	 */
	//In Windows
	public void startADB() throws Exception{
		String output = cmd.runCommand("adb start-server");
		String[] lines = output.split("\n");
		if(lines.length==1)
			System.out.println("adb service already started");
		else if(lines[1].equalsIgnoreCase("* daemon started successfully *"))
			System.out.println("adb service started");
		else if(lines[0].contains("internal or external command")){
			System.out.println("adb path not set in system varibale");
			System.exit(0);
		}
	}
	
	//For Mac
	public void startADBInMac() throws Exception{
		Process p = Runtime.getRuntime().exec("adb start-server");
	//	String output = cmd.runCommand("adb start-server");
		String output =  String.valueOf(p);
		String[] lines = output.split("\n");
		if(lines.length==1)
			System.out.println("adb service already started");
		else if(lines[1].equalsIgnoreCase("* daemon started successfully *"))
			System.out.println("adb service started");
		else if(lines[0].contains("internal or external command")){
			System.out.println("adb path not set in system varibale");
			System.exit(0);
		}
	}
	
	
	/**
	 * This method stop adb server
	 */
	
	//In Windows
	public void stopADB() throws Exception{
		cmd.runCommand("adb kill-server");
	}
	
	//In Mac
		public void stopADBInMac() throws Exception{
			Runtime.getRuntime().exec("adb kill-server");
		}
	
	/**
	 * This method return connected devices
	 * @return hashmap of connected devices information
	 */
	
	//For windows machine
	public Map<String, String> getDivces() throws Exception	{
		
		startADB(); // start adb service
		String output = cmd.runCommand("adb devices");
		String[] lines = output.split("\n");

		if(lines.length<=1){
			System.out.println("No Device Connected");
			stopADB();
			System.exit(0);	// exit if no connected devices found
		}
		
		for(int i=1;i<lines.length;i++){
			lines[i]=lines[i].replaceAll("\\s+", "");
			
			if(lines[i].contains("device")){
				lines[i]=lines[i].replaceAll("device", "");
				String deviceID = lines[i];
				String model = cmd.runCommand("adb -s "+deviceID+" shell getprop ro.product.model").replaceAll("\\s+", "");
				String brand = cmd.runCommand("adb -s "+deviceID+" shell getprop ro.product.brand").replaceAll("\\s+", "");
				String osVersion = cmd.runCommand("adb -s "+deviceID+" shell getprop ro.build.version.release").replaceAll("\\s+", "");
				String deviceName = brand+" "+model;
				
				devices.put("deviceID"+i, deviceID);
				devices.put("deviceName"+i, deviceName);
				devices.put("osVersion"+i, osVersion);
				
				System.out.println("Following device is connected");
				System.out.println(deviceID+" "+deviceName+" "+osVersion+"\n");
			}else if(lines[i].contains("unauthorized")){
				lines[i]=lines[i].replaceAll("unauthorized", "");
				String deviceID = lines[i];
				
				System.out.println("Following device is unauthorized");
				System.out.println(deviceID+"\n");
			}else if(lines[i].contains("offline")){
				lines[i]=lines[i].replaceAll("offline", "");
				String deviceID = lines[i];
				
				System.out.println("Following device is offline");
				System.out.println(deviceID+"\n");
			}
		}
		return devices;
	}
	
	

	// For mac machine
public Map<String, String> getDivcesInMac() throws Exception	{
		
		startADBInMac(); // start adb service
		Process p1 = Runtime.getRuntime().exec("adb devices");
		String output = String.valueOf(p1);
		String[] lines = output.split("\n");

		if(lines.length<=1){
			System.out.println("No Device Connected");
			stopADBInMac();
			System.exit(0);	// exit if no connected devices found
		}
		
		for(int i=1;i<lines.length;i++){
			lines[i]=lines[i].replaceAll("\\s+", "");
			
			if(lines[i].contains("device")){
				lines[i]=lines[i].replaceAll("device", "");
				String deviceID = lines[i];
				Process model = Runtime.getRuntime().exec("adb -s "+deviceID+" shell getprop ro.product.model");
				String deviceModel = String.valueOf(model).replaceAll("\\s+", "");
				Process brand = Runtime.getRuntime().exec("adb -s "+deviceID+" shell getprop ro.product.brand");
				String devicebrand = String.valueOf(brand).replaceAll("\\s+", "");
				Process osVersion = Runtime.getRuntime().exec("adb -s "+deviceID+" shell getprop ro.build.version.release");
				String deviceOsVersion = String.valueOf(osVersion).replaceAll("\\s+", "");
				
				String deviceName = devicebrand+" "+deviceModel;
				
				devices.put("deviceID"+i, deviceID);
				devices.put("deviceName"+i, deviceName);
				devices.put("osVersion"+i, deviceOsVersion);
				
				System.out.println("Following device is connected");
				System.out.println(deviceID+" "+deviceName+" "+deviceOsVersion+"\n");
			}else if(lines[i].contains("unauthorized")){
				lines[i]=lines[i].replaceAll("unauthorized", "");
				String deviceID = lines[i];
				
				System.out.println("Following device is unauthorized");
				System.out.println(deviceID+"\n");
			}else if(lines[i].contains("offline")){
				lines[i]=lines[i].replaceAll("offline", "");
				String deviceID = lines[i];
				
				System.out.println("Following device is offline");
				System.out.println(deviceID+"\n");
			}
		}
		return devices;
	}
	
	
//	public static void main(String[] args) throws Exception {
//		DeviceConfiguration gd = new DeviceConfiguration();
//		gd.startADB();	
//		gd.getDivces();
//		gd.stopADB();
//	}
}
