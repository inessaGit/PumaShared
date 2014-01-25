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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.puma.util.ObjectMap;
import com.puma.util.PumaCommonMethods;
import com.puma.util.ReadingProperties;
import com.puma.util.WebDriverManager;

@Listeners({ com.puma.util.TestListenerFailPass.class })
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
		pcm.openPumaHomePage(driver);
	}

	@AfterTest
	public void oneTimeTearDown() {
		WebDriverManager.stopDriver();
	}

	@Test
	public void printEuropeCountries() throws Exception
	{
		WebElement europe = driver.findElement(map.getLocator("header_europe"));
		action.moveToElement(europe).build().perform();		
		//gather info about available countries and locales and print them to txt 
		StringBuilder sb= new StringBuilder();
		printHeaderCountries(driver);
	}
	@Test
	public void  testHeader() throws Exception   {

		WebElement europe = driver.findElement(map.getLocator("header_europe"));
		action.moveToElement(europe).build().perform();		

		//click on the 5 special countries
		List<WebElement>countryHeader=new ArrayList<WebElement>();
		countryHeader=driver.findElements(map.getLocator("header_onecountry"));

		for (int i=0;i<countryHeader.size();i++)
		{
			//this should be inside of for loop--so it finds element each time to avoid stale element exception
			europe = driver.findElement(map.getLocator("header_europe"));
			action.moveToElement(europe).build().perform();
			countryHeader=driver.findElements(map.getLocator("header_onecountry"));
			WebElement e= countryHeader.get(i);
			String country = e.getAttribute("innerHTML").trim();
			//click on one country
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
				for(WebElement l:lang)

				{   //lang=driver.findElements(map.getLocator("header_countrylanguagecss"));
String href=l.getAttribute("href");
System.out.println(href);
				System.out.println("a language in UK: "+l.getText());
				europe = driver.findElement(map.getLocator("header_europe"));
				action.moveToElement(europe).build().perform();
				e.click();
				//click on language temp hard coded				
				driver.findElement(By.cssSelector("ul.locale-container.active > li.locale > a")).click();			
String base ="http://storefront:puma123@development.store01.puma.demandware.net/";

				String expectedUrl=base+"s/GB/en_GB/home";
				//wait for presence to avoid stale element exception
				//	wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("header_europe")));
				//String expectedHeader=driver.findElement(map.getLocator("header_europe")).getText();

				//AssertJUnit.assertEquals(expectedHeader, driver.findElement(map.getLocator("header_europe")).getText());
				AssertJUnit.assertEquals(expectedUrl, driver.getCurrentUrl());

				//wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pumaIcon")));
				//	wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("header_europe")));

				//driver.findElement(map.getLocator("pumaIcon")).click();
				//action.moveToElement(europe).build().perform();
				}
			}

			/*
			else if (country.equalsIgnoreCase("France"))
			{
				europe = driver.findElement(map.getLocator("header_europe"));
				action.moveToElement(europe).build().perform();
				e.click();
				//l.click();	
			driver.findElement(By.cssSelector("ul.locale-container.active > li.locale > a")).click();			
			driver.switchTo().defaultContent();
				String expectedUrl="https://development.store01.puma.demandware.net/s/FR/en_FR/home";
				//wait for presennce to avoid stale element exception
				wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("header_europe")));
				String expectedHeader=driver.findElement(map.getLocator("header_europe")).getText();

				AssertJUnit.assertEquals(expectedHeader, driver.findElement(map.getLocator("header_europe")).getText());
				AssertJUnit.assertEquals(expectedUrl, driver.getCurrentUrl());


			}
			else if (country.equalsIgnoreCase("Deutschland"))
			{
				//action.moveToElement(europe).build().perform();

			}
			else if (country.equalsIgnoreCase("Schweiz / Suisse / Svizzera"))
			{
				//action.moveToElement(europe).build().perform();

			}

			 */
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


