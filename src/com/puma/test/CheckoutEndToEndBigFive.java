package com.puma.test;

import java.io.IOException;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.puma.util.ObjectMap;
import com.puma.util.PumaCommonMethods;
import com.puma.util.ReadingProperties;
import com.puma.util.Return2D;
import com.puma.util.WebDriverManager;

public class CheckoutEndToEndBigFive  {
	
	WebDriver driver;
	public WebDriverWait wait;
public WebDriverWait shortWait;
	PumaCommonMethods pcm=new PumaCommonMethods();
	Actions action;

	Logger log = WebDriverManager.LoggerGetInstance();
	
	ReadingProperties rp = new ReadingProperties();
	
	public ObjectMap map = new ObjectMap();
	
	@AfterTest
	public void oneTimeTearDown() {

		WebDriverManager.stopDriver();
		//driver.manage().deleteAllCookies();
		//driver.quit();
	}


	@BeforeTest
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
	
	
	@Test (dataProvider="getData")
	public void checkoutForFiveCountries(Hashtable<String,String>data) throws Exception
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
		pcm.appendToTxt(System.getProperty("user.dir")+ "/src/com/puma/config/CheckoutStep1Countries.txt", sb);
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
		
		//writing order numbers to txt
		pcm.appendToTxt(System.getProperty("user.dir")+"/src/com/puma/config/SubmittedOrders.txt", order);
	}

	@DataProvider (name="getData")
	public Object[][] getData() throws IOException
	{
		String path = System.getProperty("user.dir")+"/src/com/puma/config/PumaTest.xlsx";
		Return2D rd=new Return2D();
		rd.open(path, "5countries");
		Object[][] data=null;

		data=rd.getData();
		return data;
		
	}
}
