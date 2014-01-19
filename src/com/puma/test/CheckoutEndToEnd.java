package com.puma.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.Reporter;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.puma.util.ObjectMap;
import com.puma.util.PumaCommonMethods;
import com.puma.util.ReadingProperties;
import com.puma.util.Return2D;
import com.puma.util.TimeUtil;
import com.puma.util.WebDriverManager;
import com.sun.media.sound.InvalidFormatException;

import org.testng.Assert;


@Listeners({ com.puma.util.TestListenerFailPass.class })

public class CheckoutEndToEnd {

	WebDriver driver;
	private WebDriverWait wait;
	private WebDriverWait shortWait;
	PumaCommonMethods pcm=new PumaCommonMethods();
	Actions action;

	Logger log = WebDriverManager.LoggerGetInstance();

	ReadingProperties rp = new ReadingProperties();

	private ObjectMap map = new ObjectMap();

	@AfterMethod
	public void oneTimeTearDown() {

		WebDriverManager.stopDriver();
		//driver.manage().deleteAllCookies();
		//driver.quit();
	}


	@BeforeMethod
	public void getToStep1() throws Exception
	{
		driver = WebDriverManager.startDriver();
		driver.manage().deleteAllCookies();

		wait = new WebDriverWait(driver, 15);
		shortWait = new WebDriverWait(driver, 7 /* timeout in seconds */);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		action = new Actions(driver);					

		//home page
		pcm.homePageMainNav(driver);
		//subcat
		pcm.subcatPageUI(driver);

		//PDP		
		pcm.pdpPageSelectColor(driver);
		pcm.pdpPageSelectSize(driver);
		pcm.pdpPageSelectQuantity(driver);
		//pcm.pdpPageSizeChart(driver);		

		//get to cart 
		pcm.fromMiniCartToCart(driver);
		//in cart click on 'checkout' for redirect to interst sign in page
		pcm.fromCartToSignIn(driver);		
		//select unregistered
		//click on 'checkout ' button
		pcm.checkoutSignInGuest(driver);
	}
	@AfterSuite
	public void sendOrdersToTxt()
	{

	}
	/***************This is  a method to fill out fields with diff data sets; it does NOT INCLUDE DROP DOWNS!!******/
	/***************This is  a method to fill out fields with diff data sets; it does NOT INCLUDE DROP DOWNS!!******/

	@Test(groups="checkoutETE", dataProvider="dataCountry")
	public void step1Fields(Hashtable<String, String> data) throws Exception
	{

		WebElement fname=driver.findElement(map.getLocator("billing_fname"));
		fname.clear();
		fname.sendKeys(data.get("FNAME"));

		WebElement lname=driver.findElement(map.getLocator("billing_lname"));
		lname.clear();
		lname.sendKeys(data.get("LNAME"));
		//	System.out.println("=============="+data.get("LNAME"));


		WebElement phone =driver.findElement(map.getLocator("billing_phone"));
		phone.clear();
		phone.sendKeys(data.get("PHONE"));

		WebElement email = driver.findElement(map.getLocator("billing_email"));
		email.clear();
		email.sendKeys(data.get("EMAIL"));

		WebElement zip =driver.findElement(map.getLocator("billing_zip"));
		zip.clear();
		zip.sendKeys(data.get("ZIP"));

		WebElement address1 =driver.findElement(map.getLocator("billing_address1"));
		address1.clear();
		address1.sendKeys(data.get("ADDRESS1"));

		WebElement address2 =driver.findElement(map.getLocator("billing_address2"));
		address2.clear();
		address2.sendKeys(data.get("ADDRESS2"));

		WebElement streetNum =driver.findElement(map.getLocator("billing_addresssuite"));
		streetNum.clear();
		streetNum.sendKeys(data.get("STREET_NUM"));

		WebElement city =driver.findElement(map.getLocator("billing_city"));
		city.clear();
		city.sendKeys(data.get("CITY"));


		pcm.step1SalutationBirthday(driver);

		StringBuilder sb =pcm.step1CountryDropDown(driver);
		pcm.writeToTxt(System.getProperty("user.dir")+ "/src/com/puma/config/CheckoutStep1Countries.txt", sb);
		//country drop down
		WebElement country_toggle=driver.findElement(map.getLocator("billing_countrytoggle"));
		country_toggle.click();
		driver.findElement(By.linkText(data.get("COUNTRY"))).click();


		System.out.println("Checking active ajax calls by calling jquery.active");
		int timeoutInSeconds=15;
		try{
			JavascriptExecutor jsDriver=(JavascriptExecutor)driver; 
			if (driver instanceof JavascriptExecutor)
			{
				for (int i=0;i<timeoutInSeconds;i++)
				{
					Object numberOfAjaxConnections = jsDriver.executeScript("return jQuery.active");
					//return should be a number
					if (numberOfAjaxConnections instanceof Long)
					{
						Long n=(Long)numberOfAjaxConnections;
						System.out.println("Number of active jquery ajax calls: "+n);
						if (n.longValue()==0L)
							break;

					}
					Thread.sleep(1000);
				}
			}
			else
			{
				System.out.println("Web driver: "+driver +" cannot execute javascript");
			}
		}
		catch(InterruptedException e)
		{
			System.out.println(e);
		}

		boolean enabled =driver.findElement(By.id("checkout-continue")).isDisplayed();//this works
		System.out.println("step1 button is displayed "+ enabled);

		if(enabled==true)
		{
			driver.findElement(By.id("checkout-continue")).click();//this works
		}
		else
		{
			pcm.waitForAjax(driver, 15);
			pcm.waitToLoadElement(driver,(By.id("checkout-continue")),15);
			driver.findElement(By.id("checkout-continue")).click();//this works
		}

		//but if wrap in a method it does not work
		//pcm.step1NextStep(driver);
		pcm.isAlertPresent(driver);

		pcm.submitStep2(driver);
		pcm.submitStep3(driver);

		pcm.submitWirecard(driver);
		String order=pcm.submitConfirmation(driver);
		pcm.appendToTxt(System.getProperty("user.dir")+"/src/com/puma/config/SubmittedOrders.txt", order);
	}

	/*
	 * AssertJUnit.assertEquals("Shipping Ground",
	 * driver.findElement(By.cssSelector
	 * ("tr.order-shipping > td.totLabel")).getText()); 12/10 PDP IS STILL
	 */

	@DataProvider(name="dataCountry")
	public Object[][] countryProvider() throws Exception
	{
		String path = System.getProperty("user.dir")+"/src/com/puma/config/PumaTest.xlsx";

		Return2D return2d = new Return2D();
		return2d.open(path, "TestData");
		Object[][] data=null;

		data = return2d.getData();
		return data;
	}


	/***************This is END OF method to fill out fields with diff data sets; it does NOT INCLUDE DROP DOWNS!!******/
	/***************This is END OF  method to fill out fields with diff data sets; it does NOT INCLUDE DROP DOWNS!!******/

}




