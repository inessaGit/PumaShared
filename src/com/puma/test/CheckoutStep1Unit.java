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
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.puma.util.ObjectMap;
import com.puma.util.PumaCommonMethods;
import com.puma.util.ReadingProperties;
import com.puma.util.Return2D;
import com.puma.util.TimeUtil;
import com.puma.util.WebDriverManager;

//@Listeners({ com.puma.util.TestListenerFailPass.class })

//Required fields: First, Last, Street Number, Street, Country, City, Postal Code, Email Address
public class CheckoutStep1Unit {

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
		WebDriverManager.stopDriver();
	}

	@Test(groups="initCheckoutStep1Unit")
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

	@Test(groups="unitStep1", dependsOnGroups="initCheckoutStep1Unit", dataProvider="dataCountry")
	public void step1FieldsName (Hashtable<String, String> data) throws Exception
	{

		WebElement fname=driver.findElement(map.getLocator("billing_fname"));
		fname.clear();
		fname.sendKeys(data.get("FNAME"));
		
		WebElement lname=driver.findElement(map.getLocator("billing_lname"));
		lname.clear();
		lname.sendKeys(data.get("LNAME"));


		WebElement address1 =driver.findElement(map.getLocator("billing_address1"));
		address1.clear();
		address1.sendKeys(data.get("ADDRESS1"));

		WebElement address2 =driver.findElement(map.getLocator("billing_address2"));
		address2.clear();
		address2.sendKeys(data.get("ADDRESS2"));
		
		WebElement city =driver.findElement(map.getLocator("billing_city"));
		city.clear();
		city.sendKeys(data.get("CITY"));
		
		WebElement email = driver.findElement(map.getLocator("billing_email"));
		email.clear();
		email.sendKeys(data.get("EMAIL"));
		
		WebElement zip =driver.findElement(map.getLocator("billing_zip"));
		zip.clear();
		zip.sendKeys(data.get("ZIP"));

		pcm.step1NextStep(driver);
		System.out.println(data.get("ZIP"));
		
		pcm.step1NextStep(driver);

		System.out.println(data.get("ADDRESS1"));
		System.out.println(data.get("FNAME"));
	}

	@Test(groups="unitStep1", dependsOnGroups="initCheckoutStep1Unit", dataProvider="dataCountry")
	public void step1FieldsLname (Hashtable<String, String> data) throws Exception
	{

		WebElement fname=driver.findElement(map.getLocator("billing_fname"));
		fname.clear();
		fname.sendKeys(data.get("FNAME"));
		
		WebElement lname=driver.findElement(map.getLocator("billing_lname"));
		lname.clear();
		lname.sendKeys(data.get("LNAME"));
		
		WebElement phone =driver.findElement(map.getLocator("billing_phone"));
		phone.clear();
		phone.sendKeys(data.get("PHONE"));

		WebElement email = driver.findElement(map.getLocator("billing_email"));
		email.clear();
		email.sendKeys(data.get("EMAIL"));	
		
		
		pcm.step1NextStep(driver);
		System.out.println(data.get("LNAME"));
	}
	
	@Test(groups="unitStep1", dependsOnGroups="initCheckoutStep1Unit", dataProvider="dataCountry")
	public void step1FieldsEmail (Hashtable<String, String> data) throws Exception
	{
		WebElement email = driver.findElement(map.getLocator("billing_email"));
		email.clear();
		email.sendKeys(data.get("EMAIL"));	
		
		pcm.step1NextStep(driver);
		System.out.println(data.get("EMAIL"));
	}
	
	@Test(groups="unitStep1", dependsOnGroups="initCheckoutStep1Unit", dataProvider="dataCountry")
	public void step1FieldsZip (Hashtable<String, String> data) throws Exception
	{
		//THIS ONE IS TO CHECK ZIP!

		WebElement zip =driver.findElement(map.getLocator("billing_zip"));
		zip.clear();
		zip.sendKeys(data.get("ZIP"));

		pcm.step1NextStep(driver);
		System.out.println(data.get("ZIP"));
	}
	
	@Test(groups="unitStep1", dependsOnGroups="initCheckoutStep1Unit", dataProvider="dataCountry")
	public void step1FieldsAddress1 (Hashtable<String, String> data) throws Exception
	{

		WebElement email = driver.findElement(map.getLocator("billing_email"));
		email.clear();
		email.sendKeys(data.get("EMAIL"));	
		
		pcm.step1NextStep(driver);
		System.out.println(data.get("EMAIL"));
		WebElement zip =driver.findElement(map.getLocator("billing_zip"));
		zip.clear();
		zip.sendKeys(data.get("ZIP"));

		pcm.step1NextStep(driver);
		System.out.println(data.get("ZIP"));
		WebElement address1 =driver.findElement(map.getLocator("billing_address1"));
		address1.clear();
		address1.sendKeys(data.get("ADDRESS1"));
		
		WebElement streetNum =driver.findElement(map.getLocator("billing_addresssuite"));
		streetNum.clear();
		streetNum.sendKeys(data.get("STREET_NUM"));

		pcm.step1SalutationBirthday(driver);
		pcm.step1NextStep(driver);
		System.out.println(data.get("STREET_NUM"));
		System.out.println(data.get("ADDRESS1"));
	}


	@Test(groups="unitStep1", dependsOnGroups="initCheckoutStep1Unit", dataProvider="dataCountry")
	public void step1FieldsCity (Hashtable<String, String> data) throws Exception
	{
		WebElement streetNum =driver.findElement(map.getLocator("billing_addresssuite"));
		streetNum.clear();
		streetNum.sendKeys(data.get("STREET_NUM"));

		pcm.step1NextStep(driver);
		System.out.println(data.get("STREET_NUM"));
		
		WebElement city =driver.findElement(map.getLocator("billing_city"));
		city.clear();
		city.sendKeys(data.get("CITY"));
		
		pcm.step1SalutationBirthday(driver);
		pcm.step1NextStep(driver);
		//System.out.println(data.get("FNAME"));
	}


	@DataProvider(name="dataCountry")
	public Object[][] countryProvider() throws Exception
	{
		String path = rp.readConfigProperties("xlsx.path");
		Object[][] data=null;
		Return2D return2d = new Return2D();
		return2d.open(path, "TestData");
		return2d.getData();

		data = return2d.getData();
		return data;
	}

	/***************This is END OF method to fill out fields with diff data sets; it does NOT INCLUDE DROP DOWNS!!******/
	/***************This is END OF  method to fill out fields with diff data sets; it does NOT INCLUDE DROP DOWNS!!******/
	
	
	@Test(groups="unitStep1", dependsOnGroups="initCheckoutStep1Unit")
	public void step1SalutationBirthday() throws Exception
	{
		WebElement salutation =driver.findElement(map.getLocator("salutation_toggle"));
		salutation.click();
		driver.findElement(map.getLocator("salutation_ms")).click();
		
		WebElement billing_daytoggle=driver.findElement(map.getLocator("billing_daytoggle"));
		billing_daytoggle.click();
		driver.findElement(map.getLocator("billing_day")).click();
		
		WebElement billing_monthtoggle=driver.findElement(map.getLocator("billing_monthtoggle"));
		billing_monthtoggle.click();
		driver.findElement(map.getLocator("billing_month")).click();
		
		WebElement billing_yeartoggle = driver.findElement(map.getLocator("billing_yeartoggle"));
		billing_yeartoggle.click();
		
		
		
	}
	
	
		@Test(groups="unitStep1", dependsOnGroups="initCheckoutStep1Unit")
		public void step1CountryDropDown() throws Exception

		{
			Select selectCountry= new Select(driver.findElement(map.getLocator("billing_selectcountry")));

			List<WebElement>allCountries=selectCountry.getOptions();
			List<String> countries= new ArrayList<String>();

			WebElement country_toggle=driver.findElement(map.getLocator("billing_countrytoggle"));
			
				
			for(WebElement country: allCountries)
			{
				String countryOption = country.getAttribute("innerHTML").trim();
				
				if(countryOption.equalsIgnoreCase("Select..."))
				{
					continue;
				}
				System.out.println ("This are the countries in the country drop down: "+ countryOption);
				
				countries.add(countryOption);
				
			//  Reporter.log("This are the states in the state drop down: "+ stateOption);		
			}
			
			for(int i=0;i<countries.size();i++)
			{
				
				country_toggle.click();
				WebElement country = driver.findElement(By.linkText(countries.get(i)));
				country.click();
				System.out.println("Clicked on "+countries.get(i));
				Reporter.log("Clicked on "+countries.get(i));
				//common method from PCM class
				//pcm.writeToTxt(rp.readConfigProperties("temp.countries"), countryOption);
		
			//  Reporter.log("This are the states in the state drop down: "+ stateOption);		
			}
			
			
		}
		
		
			
	@Test(groups="unitStep1", dependsOnGroups="initCheckoutStep1Unit")
	public void step1NextStepBtnUI() throws Exception

	{

		WebElement step1btn =driver.findElement(map.getLocator("billing_nextstepbtn"));
		Dimension dimensions =step1btn.getSize();
		System.out.println("'Next step' button dimensions on Step1 page: height= "+ dimensions.height+" width= "+dimensions.width);

		log.info(" 'Next step' button dimensions on Step1 page: height= "+ dimensions.height+" width= "+dimensions.width);
		//CHECK UI
		
	}
	

}
	
