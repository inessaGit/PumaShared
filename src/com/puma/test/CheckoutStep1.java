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

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
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


//@Listeners({ com.puma.util.TestListenerFailPass.class })

@Listeners({ com.puma.util.TestListenerFailPass.class })

public class CheckoutStep1 {

	WebDriver driver;
	private WebDriverWait wait;
	private WebDriverWait shortWait;
	PumaCommonMethods pcm=new PumaCommonMethods();
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
	//	WebDriverManager.stopDriver();
	}

	@Test(groups="initCheckoutStep1")
	public void getFromHomeToStep1() throws Exception
	{ 

		//home page
		pcm.homePageMainNav(driver);

		//subcat
		pcm.subcatPageUI(driver);

		//PDP		
		pcm.pdpPageSelectColor(driver);
		pcm.pdpPageSelectSize(driver);
		pcm.pdpPageSelectQuantity(driver);
		pcm.pdpPageSizeChart(driver);		

		//get to cart 
		pcm.fromMiniCartToCart(driver);
		//in cart click on 'checkout' for redirect to interst sign in page
		pcm.fromCartToSignIn(driver);		
		//select unregistered
		//click on 'checkout ' button
		pcm.checkoutSignInGuest(driver);					

	}

	/***************This is  a method to fill out fields with diff data sets; it does NOT INCLUDE DROP DOWNS!!******/
	/***************This is  a method to fill out fields with diff data sets; it does NOT INCLUDE DROP DOWNS!!******/

	@Test(groups="step1", dependsOnGroups="initCheckoutStep1", dataProvider="dataCountry")
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
		//Estonia		pcm.step1ClickOnOneCountry(driver);
		
		//country drop down
		WebElement country_toggle=driver.findElement(map.getLocator("billing_countrytoggle"));
		country_toggle.click();
		driver.findElement(By.linkText(data.get("COUNTRY"))).click();

		pcm.step1NextStep(driver);
	
		
		/*
		 * AssertJUnit.assertEquals("Shipping Ground",
		 * driver.findElement(By.cssSelector
		 * ("tr.order-shipping > td.totLabel")).getText()); 12/10 PDP IS STILL
		 * IN PROGRESS SO LOCATORS WILL CHANGE
		 * 
		 * 
		 */
		
			}

	@DataProvider(name="dataCountry")
	public Object[][] countryProvider() throws Exception
	{
		String path = rp.readConfigProperties("xlsx.path");
		
		Return2D return2d = new Return2D();
		return2d.open(path, "TestData");
		Object[][] data=null;

		data = return2d.getData();
		return data;
	}

	
	/***************This is END OF method to fill out fields with diff data sets; it does NOT INCLUDE DROP DOWNS!!******/
	/***************This is END OF  method to fill out fields with diff data sets; it does NOT INCLUDE DROP DOWNS!!******/
	
	

}
	
