package com.puma.test;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
//import org.testng.log4testng.Logger;
import org.apache.log4j.Logger;

import com.google.common.base.Function;
import com.puma.util.*;

//@Listeners({ com.puma.util.TestListenerFailPass.class })
public class PumaUtilNavSearch extends InitSuite {

	WebDriver driver;
	private WebDriverWait wait;
	private WebDriverWait shortWait;

	Actions action;
	PumaCommonMethods pcm=new PumaCommonMethods();
/*
	@BeforeSuite
	public void oneTimeSetUp() {
		driver = WebDriverManager.startDriver();
		wait = new WebDriverWait(driver, 20);
		shortWait = new WebDriverWait(driver, 10);
		action = new Actions(driver);

	}

	@AfterSuite
	public void oneTimeTearDown() {
		WebDriverManager.stopDriver();
	}

*/
	@Test(groups = "initSearch", priority = 1)
	public void openPumaHomePage() {

		pcm.openPumaHomePage(driver);

	}

	@Test(groups="search", priority=2)
	public void searchForSku() throws Exception
	{
		pcm.searchForSku(driver);
	}

	@Test(groups = "search", priority = 3)
	public void searchForPants() throws Exception {

		/*
		 * AssertJUnit.assertEquals("Shipping Ground",
		 * driver.findElement(By.cssSelector
		 * ("tr.order-shipping > td.totLabel")).getText()); 12/10 PDP IS STILL
		 * IN PROGRESS SO LOCATORS WILL CHANGE
		 * 
		 * 
		 */
		pcm.searchForPants(driver);
	}

}
