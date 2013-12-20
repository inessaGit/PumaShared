package com.puma.test;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.puma.util.ObjectMap;
import com.puma.util.PumaCommonMethods;
import com.puma.util.ReadingProperties;
import com.puma.util.TakeScreenshot;
import com.puma.util.WebDriverManager;

public class CheckoutSignIn {
	
	WebDriver driver;
	private WebDriverWait wait; 
	Actions action;
	Logger log=WebDriverManager.LoggerGetInstance();
	ReadingProperties rp= new ReadingProperties();
	private ObjectMap map = new ObjectMap();
	TakeScreenshot ts=new TakeScreenshot();
	private WebDriverWait shortWait;
	
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
	
	
	@Test(groups="initCheckoutSignIn")
	public void interstCheckoutPage() throws Exception
	{ 

		//home page
				String devUrl = rp.readConfigProperties("puma_devUrl");
				driver.get(devUrl);
				String homePageTitle = driver.getTitle();
				// System.out.println("On home page title is "+ homePageTitle);
				Reporter.log("On Home page title is " + homePageTitle);
				log.info("Page title is " + homePageTitle);
				
	//subcat
				

				driver.switchTo().window(driver.getWindowHandle());
				wait.until(ExpectedConditions.elementToBeClickable(map.getLocator("nav_men")));
		try{
				action.moveToElement(driver.findElement(map.getLocator("nav_men"))).build().perform();
				//driver.findElement(map.getLocator("nav_apparel")).click();

				//wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("nav_apparel")));
				action.moveToElement(driver.findElement(map.getLocator("nav_apparel")));
		}

		catch (Exception e)
		{
			e.printStackTrace();
		}
				action.click().perform();
				
				//driver.findElement(By.xpath("//a/img")).click();
			       driver.findElement(map.getLocator("subcat_product")).click();
					wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_zoominbtn")));
			       
			       Reporter.log("On subcategory page title is '"+ driver.getTitle()+"'");
			       System.out.println("On subcategory page title is '"+ driver.getTitle()+"'");
			       
//PDP
			       driver.findElement(map.getLocator("pdp_firstswatch")).click();
			       
			       driver.findElement(map.getLocator("pdp_firstsize")).click();
				   PumaCommonMethods.pause(2000);
				   driver.findElement(map.getLocator("pdp_addtocart")).click();
				   PumaCommonMethods.pause(1500);	
		
				   //get to cart 
		driver.findElement(map.getLocator("pdp_minicartlink")).click();
	//in cart click on 'checkout' for redirect to interst sign in page
		driver.findElement(map.getLocator("cart_checkoutbtn")).click();
		
	}
	

	@Test(groups="checkoutSignIn", dependsOnGroups="initCheckoutSignIn")
	public void checkoutSignInGuest() throws Exception
	{ 
		//assert title 
		String title=driver.getTitle();
		Reporter.log("On checkout 'Sign In' page title is "+ title);
		//select unregistered
		//click on 'checkout ' button
		 driver.findElement(map.getLocator("intercept_unregistered")).click();
					
		
	}
	
	@Test(groups="checkoutSignIn", dependsOnGroups="initCheckoutSignIn")
	public void checkoutSignInRegistered()
	{ 
		
		//select registered 
		//assert title 
		//click on 'checkout ' button
		
	}


}
