package com.puma.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class GatherInfo {
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

	@Test(groups="getToStep1")
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


	@Test(groups="printToFile", dependsOnGroups="getToStep1")
	public void step1CountryDropDownSaveToTx() throws Exception

	{
		Select selectCountry= new Select(driver.findElement(map.getLocator("billing_selectcountry")));

		List<WebElement>allCountries=selectCountry.getOptions();
		List<String> countries= new ArrayList<String>();

		WebElement country_toggle=driver.findElement(map.getLocator("billing_countrytoggle"));
		PrintWriter pw=null;
		try 
		{

			File file = new File(rp.readConfigProperties("txt.countries"));
			
			// if file doesnt exists, then create it
			if (!file.exists()) 
			{
				file.createNewFile();
			}
			pw= new PrintWriter(file.getAbsoluteFile());
			pw.println("These are the countries in the dropdown list on 'Checkout Step1' page: ");
			
		for(WebElement country: allCountries)
		{
			String countryOption = country.getAttribute("innerHTML").trim();
			System.out.println ("This are the countries in the country drop down: "+ countryOption);
			//common method from PCM class
			//pcm.writeToTxt(rp.readConfigProperties("temp.countries"), countryOption);
			
			pw.println(countryOption);
		//  Reporter.log("This are the states in the state drop down: "+ stateOption);
			
		}
		System.out.println("Done writing to 'CheckoutStep1Countries.txt'");
		String timestamp = TimeUtil.getTimeStamp();
		pw.println();
		pw.println("============================="+timestamp+"================================");
		
		}
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	  
		finally
		{
			System.out.println("Closing pw stream.");
			pw.close();
			
		}
		
	}

}