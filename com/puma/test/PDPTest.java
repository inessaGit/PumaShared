package com.puma.test;

import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITest;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.puma.util.ObjectMap;
import com.puma.util.PumaCommonMethods;
import com.puma.util.ReadingProperties;
import com.puma.util.TakeScreenshot;
import com.puma.util.WebDriverManager;

//@Listeners({ com.puma.util.TestListenerFailPass.class })

public class PDPTest  {

	WebDriver driver;
	private WebDriverWait wait;
	private WebDriverWait shortWait;

	Actions action;
	PumaCommonMethods pcm=new PumaCommonMethods();

	String tshirt="562941";

	@BeforeClass
	public void oneTimeSetUp() {
		driver = WebDriverManager.startDriver();
		wait = new WebDriverWait(driver,25);
		action = new Actions(driver);
	}

	@AfterClass
	public void oneTimeTearDown() {
		WebDriverManager.stopDriver();
	}

	@Test(groups="init")
	public void homePageMainNav() throws Exception
	{
		pcm.homePageMainNav(driver);
	}

	@Test(dependsOnGroups="init", groups="subcategory")
	public void subcatPageUI() throws Exception
	{

		pcm.subcatPageUI(driver);


	}
	@Test(dependsOnGroups="init", groups="subcategory")
	public void subcatPageFunct()
	{

	}

	@Test(dependsOnGroups="subcategory", groups="pdp")

	public void pdpPageZoomIn() throws Exception
	{ 
		pcm.pdpPageZoomIn(driver);
		//select color
		//select size
		//click on the 'add to cart ' btn
		//click on 'mini cart' link

	}

	@Test(dependsOnGroups="subcategory", groups="pdp")
	public void pdpPageSelectColor() throws Exception
	{ //select color

		pcm.pdpPageSelectColor(driver);

		//click on the 'add to cart ' btn
		//click on 'mini cart' link

	}

	@Test(dependsOnGroups="subcategory", groups="pdp")
	public void pdpPageSelectSize() throws Exception
	{		//select size and add to cart
		pcm.pdpPageSelectSize(driver);



	}
	@Test(dependsOnGroups="subcategory", groups="pdp")
	public void pdpPageSelectQuantity() throws Exception
	{		//select size and add to cart
		pcm.pdpPageSelectQuantity(driver);



	}
	@Test(dependsOnGroups="subcategory", groups="pdp")
	public void pdpPageSizeChart() throws Exception
	{ 
		pcm.pdpPageSizeChart(driver);		

	//change quantity
	//assert total
	//assert title 
	//click on 'checkout ' button

	}




}
