package com.puma.util;

import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.Reporter;

//this is a stub

public class WebDriverManager {
	private static WebDriver driver =null;
	private static Logger log;

	//public static WebDriver startDriver (ITestContext context){
	public static WebDriver startDriver (){

		ProfilesIni allProfiles = new ProfilesIni();
		FirefoxProfile profile = allProfiles.getProfile("WebDriver");
		FirefoxProfile profile2=allProfiles.getProfile("default");
		profile2.setEnableNativeEvents(true);
		profile.setEnableNativeEvents(true);

		driver=new FirefoxDriver(profile);
		
		
		defaultWindowSize(driver);
		return driver;


	}

	public static void stopDriver()
	{
		driver.quit();
	}

	public static WebDriver getDriverInstance()
	{
		//in progress
		return driver;

	}

	public static  void defaultWindowSize(WebDriver driver)

	{

		//diver.manage().window().maximize(); //this would work only for Firefox and IE
		driver.manage().window().setPosition(new Point(0,0));
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dim = new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight());
		driver.manage().window().setSize(dim);
		Reporter.log("Currently testing URL: " +driver.getCurrentUrl());

	}

	
	  public static void getBrowser(WebDriver driver)
	 	{
	 		if (driver instanceof FirefoxDriver) {
	 			System.out.println("Firefox DRIVER");
	 			Reporter.log("Using Firefox browser version 24");
	 		} 
	 		
	 		else if (driver instanceof ChromeDriver) {
	 			System.out.println("Chrome DRIVER");
	 			Reporter.log("Using Chrome version 30 browser");
	 		}
	 		
	 		else if (driver instanceof SafariDriver) {
	 			System.out.println("Safari DRIVER");
	 			Reporter.log("Using Safari 6 browser");
	 		}
	 	}
	  
	  /*to use it	Logger log=WebDriverManager.LoggerGetInstance(); */
	public static Logger LoggerGetInstance() {
		log = Logger.getLogger(WebDriverManager.class);
		return log;
	}
}
