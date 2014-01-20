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


//this is a temp  solution; this class should be used to get to Step1
public class GetToStep1 {

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
		shortWait = new WebDriverWait(driver, 10);
		action = new Actions(driver);

	}

	@AfterSuite
	public void oneTimeTearDown() {
	WebDriverManager.stopDriver();
	}

		
	@Test
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
		
		//1/21  NEED TO REPLACE LOGIC HERE FOR SELECTING EITHER GUEST OR REGISTERED CHECKOUT?
		//click on 'checkout ' button
		pcm.checkoutSignInGuest(driver);					

	}

}