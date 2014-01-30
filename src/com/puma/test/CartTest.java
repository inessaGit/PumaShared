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

public class CartTest {

	
	WebDriver driver;
	private WebDriverWait wait;
	private WebDriverWait shortWait;
	PumaCommonMethods pcm=new PumaCommonMethods();
	Actions action;
	
	//Logger log = WebDriverManager.LoggerGetInstance();
	//ReadingProperties rp = new ReadingProperties();
	//private ObjectMap map = new ObjectMap();

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

	//dependsOnGroups="pdp", 
	@Test(groups = "initCart", priority = 1)
	public void getFromHomeToCart() throws Exception {

		//home page
		pcm.homePageMainNav(driver);

		//subcat
		pcm.subcatPageUI(driver);

		//PDP		
		pcm.pdpPageSelectColor(driver);
		pcm.pdpPageSelectSize(driver);
		pcm.pdpPageSelectQuantity(driver);
		pcm.pdpPageSizeChart(driver);		

	}

	@Test(groups="cartUI", priority=2, dependsOnGroups="initCart")
	public void fromMiniCartToCart() throws Exception
	{

		pcm.fromMiniCartToCart(driver);

	}


}
