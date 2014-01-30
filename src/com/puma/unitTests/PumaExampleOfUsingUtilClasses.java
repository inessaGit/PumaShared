package com.puma.unitTests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
//import org.testng.log4testng.Logger;
import org.apache.log4j.Logger;

import com.puma.util.*;



/*@Listeners({ com.puma.util.TestListener.class })

public class PumaExampleOfUsingUtilClasses {
	
	WebDriver driver;
	//this is a log4j logger method declared in com.puma.util.WebDriverManager class
	Logger log=WebDriverManager.LoggerGetInstance();
	
	ReadingProperties rp= new ReadingProperties();
	
	@BeforeClass
	public void oneTimeSetUp() {
		driver = WebDriverManager.startDriver();
		 

	}

	@AfterClass
	public void oneTimeTearDown() {
		WebDriverManager.stopDriver();
	}
	
	@Test
	
	public void openPumaHomePage()
	{
		//driver = WebDriverManager.getDriverInstance();
		String devUrl = rp.readConfigProperties("puma_devUrl");
		driver.get(devUrl);
		String homePageTitle = driver.getTitle();
		System.out.println(homePageTitle);
		
		log.info("Page title is "+ homePageTitle);

	}

}
*/
