package com.puma.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.puma.util.ObjectMap;
import com.puma.util.PumaCommonMethods;
import com.puma.util.ReadingProperties;
import com.puma.util.WebDriverManager;

public class HomeEuropeHeader  {

	WebDriver driver;
	public WebDriverWait wait;
	public WebDriverWait shortWait;
	PumaCommonMethods pcm=new PumaCommonMethods();
	Actions action;

	Logger log = WebDriverManager.LoggerGetInstance();
	ReadingProperties rp = new ReadingProperties();
	public ObjectMap map = new ObjectMap();


	@BeforeTest
	public void oneTimeSetUp() {
		driver = WebDriverManager.startDriver();
		wait = new WebDriverWait(driver, 20);
		shortWait = new WebDriverWait(driver, 10 );
		action = new Actions(driver);
	}

	@AfterTest
	public void oneTimeTearDown() {
		WebDriverManager.stopDriver();
	}

	@Test
	public void  testHeader() throws Exception   {
		pcm.openPumaHomePage(driver);

		WebElement europe;
		europe = driver.findElement(map.getLocator("header_europe"));
		action.moveToElement(europe).build().perform();
		
		//gather info about available countries and locales and print them to txt 
		printHeaderCountries(driver);

		//click on the 5 special countries
		List<WebElement>countryHeader=new ArrayList<WebElement>();
		countryHeader=driver.findElements(map.getLocator("header_onecountry"));


		StringBuilder sb= new StringBuilder();
		for (int i=0;i<countryHeader.size();i++)
		{
			WebElement e= countryHeader.get(i);
			String country = e.getAttribute("innerHTML").trim();

			//System.out.println(country);
			e.click();
			List<WebElement> lang=new ArrayList<WebElement>();
			//	lang=driver.findElements(By.cssSelector("ul.locale-container.active > li.locale > a"));
			lang=driver.findElements(map.getLocator("header_countrylanguagecss"));
			System.out.println("Country is: "+country);

			for(WebElement l:lang)
			{
				String language=l.getText().trim();
				if(language.equals(""))
					continue;
				System.out.println("Available languages are: "+language);
			}
			if(country.equalsIgnoreCase("United Kingdom"))
			{
				action.moveToElement(europe).build().perform();

			}
			else if (country.equalsIgnoreCase("France"))
			{
				action.moveToElement(europe).build().perform();

			}
			else if (country.equalsIgnoreCase("Deutschland"))
			{
				action.moveToElement(europe).build().perform();

			}
			else if (country.equalsIgnoreCase("Schweiz / Suisse / Svizzera"))
			{
				action.moveToElement(europe).build().perform();

			}
			
		
		}				
	}


	//this is a method not @Test
	public  void printHeaderCountries(WebDriver driver) throws Exception
	{
		this.driver=driver;
		WebElement europe;
		europe = driver.findElement(map.getLocator("header_europe"));
		
		List<WebElement>countryHeader=new ArrayList<WebElement>();
		countryHeader=driver.findElements(map.getLocator("header_onecountry"));


		StringBuilder sb= new StringBuilder();
		for (WebElement e:countryHeader)
		{
			String country = e.getAttribute("innerHTML").trim();
			sb.append(country+":,");

			//System.out.println(country);
			e.click();
			List<WebElement> lang=new ArrayList<WebElement>();
			//	lang=driver.findElements(By.cssSelector("ul.locale-container.active > li.locale > a"));
			lang=driver.findElements(map.getLocator("header_countrylanguagecss"));
			System.out.println("Country is: "+country);

			for(WebElement l:lang)
			{
				String language=l.getText().trim();
				if(language.equals(""))
					continue;
				System.out.println("Available languages are: "+language);
				sb.append(language+",");
			}
			sb.append("-----------------------------------------------------"+",");
			if(country.equalsIgnoreCase("United Kingdom"))
			{
				action.moveToElement(europe).build().perform();

			}
			else if (country.equalsIgnoreCase("France"))
			{
				action.moveToElement(europe).build().perform();

			}
			else if (country.equalsIgnoreCase("Deutschland"))
			{
				action.moveToElement(europe).build().perform();

			}
			else if (country.equalsIgnoreCase("Schweiz / Suisse / Svizzera"))
			{
				action.moveToElement(europe).build().perform();

			}
		}
		pcm.writeToTxt(System.getProperty("user.dir")+"/src/com/puma/config/HeaderCountries.txt", sb,",");		
	}
}


