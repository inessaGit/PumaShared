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
public class PumaUtilNavSearch {

	WebDriver driver;
	private WebDriverWait wait;
	private WebDriverWait shortWait;

	Actions action;
	Logger log = WebDriverManager.LoggerGetInstance();
	ReadingProperties rp = new ReadingProperties();
	private ObjectMap map = new ObjectMap();

	@BeforeSuite
	public void oneTimeSetUp() {
		driver = WebDriverManager.startDriver();
		wait = new WebDriverWait(driver, 20);
		shortWait = new WebDriverWait(driver, 10 /* timeout in seconds */);
		action = new Actions(driver);

	}

	@AfterSuite
	public void oneTimeTearDown() {
		WebDriverManager.stopDriver();
	}
	
public void verifySearchBreadcrumbs(String item) throws Exception
	
	{
		//\"pants\"
		String searchItem="\""+ item+"\"";
		String text="Home Your search Results for ";
		String s = 	driver.findElement(map.getLocator("search_breadcrumbs")).getText();

		boolean search_breadcrumbs=driver.findElement(map.getLocator("search_breadcrumbs")).isDisplayed();

		Reporter.log("Breadcrumbs for search page are on the page: "+ search_breadcrumbs+" 'value' is +" +s);
		if ((text+searchItem).equals(driver.findElement(By.cssSelector("ol.breadcrumb")).getText())) 
		{
			Reporter.log("Breadcrumbs match entred 'search value'");
		}
		
	}

	@Test(groups = "initSearch", priority = 1)
	public void openPumaHomePage() {

		// driver = WebDriverManager.getDriverInstance();
		String devUrl = rp.readConfigProperties("puma_devUrl");
		driver.get(devUrl);
		String homePageTitle = driver.getTitle();
		// System.out.println("On home page title is "+ homePageTitle);
		Reporter.log("On Home page title is " + homePageTitle);
		log.info("Page title is " + homePageTitle);
	}

	@Test(groups="search", priority=2)
	public void searchForSku() throws Exception
	{

		/*driver.findElement(rp.getLocator("searchField")).clear();
		type(driver, By.name("txtUserName"), username);
		this 2 lines are replaced with just one line using type method defined in PumaCommonMethods
		need to modify that method !*/

		String sku="562941";
		driver.findElement(map.getLocator("searchField")).clear();
		driver.findElement(map.getLocator("searchField")).sendKeys(sku);
		driver.findElement(map.getLocator("searchBtn")).click();

		try{
			shortWait.until(ExpectedConditions.alertIsPresent());
			System.out.println("alert was present");
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
		}
		catch(Exception e)
		{
			System.out.println("alert was not present");
			Reporter.log("No security alert while using 'search'");
			String s = 	driver.findElement(map.getLocator("search_breadcrumbs")).getText();
            verifySearchBreadcrumbs(sku);
			//AssertJUnit.assertEquals("Checkout Confirmation", driver.getTitle());
		}
	
	}
	
	@Test(groups = "search", priority = 3)
	public void searchForPants() throws Exception {

		/*
		 * AssertJUnit.assertEquals("Shipping Ground",
		 * driver.findElement(By.cssSelector
		 * ("tr.order-shipping > td.totLabel")).getText()); 12/10 PDP IS STILL
		 * IN PROGRESS SO LOCATORS WILL CHANGE
		 * 
		 * *
		 */
		String pants = "pants";

		driver.navigate().back();
		wait.until(ExpectedConditions.visibilityOfElementLocated(map
				.getLocator("home_herocarousel")));
		driver.findElement(map.getLocator("searchField")).clear();
		driver.findElement(map.getLocator("searchField")).sendKeys(pants);
		driver.findElement(map.getLocator("searchBtn")).click();
		PumaCommonMethods.pause(1800);

		boolean search_breadcrumbs = driver.findElement(
				map.getLocator("search_breadcrumbs")).isDisplayed();
		Reporter.log("Breadcrumbs for search page are on the page: "
				+ search_breadcrumbs);
        verifySearchBreadcrumbs(pants);

	}

}
