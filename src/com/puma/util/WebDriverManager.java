package com.puma.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.Reporter;


public class WebDriverManager {
	private static WebDriver driver =null;
	private static Logger log;
	private static DesiredCapabilities dc;
	static ProfilesIni allProfiles = new ProfilesIni();


	//public static WebDriver startDriver (ITestContext context){
	public static WebDriver startDriver (){

		ProfilesIni allProfiles = new ProfilesIni();
		FirefoxProfile profile = allProfiles.getProfile("WebDriver");
		FirefoxProfile profile2=allProfiles.getProfile("default");
		profile.setPreference("network.http.phishy-userpass-length", 255);


		//for server authentication
		//profile.setPreference("signon.autologin.proxy", true);
		// profile.setPreference("network.automatic-ntlm-auth.trusted-uris", "development.store01.puma.demandware.net"); // Set this to your DOMAIN, instead of Server

		/*
            profile.setPreference("network.proxy.type", 1);
            profile.setPreference("network.proxy.http", "127.0.0.1");
            profile.setPreference("network.proxy.http_port", 8081);
            profile.setPreference("network.proxy.no_proxies_on", "");
		 */

		profile2.setEnableNativeEvents(true);
		profile.setEnableNativeEvents(true);
		driver=new FirefoxDriver(profile);


		//   defaultWindowSize(driver);
		return driver;
	}

	public static WebDriver startDriver(String name)
	{
		/*
		 * DesiredCapabilities capabilities = DesiredCapabilities.chrome();
// Add the WebDriver proxy capability.
Proxy proxy = new Proxy();
proxy.setHttpProxy("myhttpproxy:3337");
capabilities.setCapability("proxy", proxy);

// Add ChromeDriver-specific capabilities through ChromeOptions.
ChromeOptions options = new ChromeOptions();
options.addExtensions(new File("/path/to/extension.crx"));
chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
ChromeDriver driver = new ChromeDriver(capabilities);

ChromeOptions options = new ChromeOptions();
options.addArguments("user-data-dir=C:/Users/user_name/AppData/Local/Google/Chrome/User Data");
options.addArguments("--start-maximized");
driver = new ChromeDriver(options);

		 */
		DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
		String chromeBinary = System.getProperty(" ");
		if (name.equalsIgnoreCase("chrome"))
		{
			if (chromeBinary == null || chromeBinary.equals("")) {

				String os = System.getProperty("os.name").toLowerCase().substring(0, 3);
				chromeBinary = "lib/chromedriver-" + os + (os.equals("win") ? ".exe" : "");
				System.setProperty("webdriver.chrome.driver", chromeBinary);
				
				
				ChromeOptions options = new ChromeOptions();
				//options.addExtensions(new File("/path/to/extension.crx"));
				//   /Users/igonzalez/Desktop/ChromeProfile/Profile 1
				//options.addArguments("user-data-dir=/Users/igonzalez/Desktop/ChromeProfile/Profile 1");
				options.addArguments("user-data-dir=/Users/igonzalez/Library/Application Support/Google/Chrome/ChromeDriver");
				options.addArguments("--start-maximized");
				chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
			}
		}
		driver =new ChromeDriver(chromeCapabilities);
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

	public static void setup(String platform, String browser, String version,
			String url) throws MalformedURLException, InterruptedException, UnknownHostException 
			{

		DesiredCapabilities caps = new DesiredCapabilities();
		// Platforms

		if (platform.equalsIgnoreCase("WINDOWS"))
			caps.setPlatform(org.openqa.selenium.Platform.WINDOWS);
		System.out.println(org.openqa.selenium.Platform.WINDOWS);

		if (platform.equalsIgnoreCase("MAC"))
			caps.setPlatform(org.openqa.selenium.Platform.MAC);
		System.out.println(org.openqa.selenium.Platform.MAC);

		// Browsers
		if (browser.equalsIgnoreCase("Internet Explorer"))
			caps = DesiredCapabilities.internetExplorer();
		if (browser.equalsIgnoreCase("Firefox"))
			caps = DesiredCapabilities.firefox();
		if (browser.equalsIgnoreCase("iPad"))
			caps = DesiredCapabilities.ipad();
		if (browser.equalsIgnoreCase("Android")) {
			caps = DesiredCapabilities.android();
		}
			}


	/* File file=new File("/Users/igonzalez/Desktop/SELENIUM_jar/chromedriver");
		//chrome driver version 2.8 need to replace path with relative so it works with jenkins

		//File file=new File("/Users/igonzalez/Desktop/SELENIUM_jar/chromedriver_v2_1");

		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		driver = new ChromeDriver();
	 */
	public static void defaultSetUp()
	{

		dc = new DesiredCapabilities();
		// Platforms
		Platform platform = Platform.getCurrent();
		dc.setPlatform(platform);        

		String platformName=     platform.name();
		System.out.println("Platform is "+ platformName);

	}


	/*to use it        Logger log=WebDriverManager.LoggerGetInstance(); */
	public static Logger LoggerGetInstance() {
		log = Logger.getLogger(WebDriverManager.class);
		return log;
	}
}


class TestWebDriverManager {

	public static void main (String args[])
	{
		WebDriverManager.defaultSetUp();
	}
}