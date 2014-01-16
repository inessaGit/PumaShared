package com.puma.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.ErrorHandler.UnknownServerException;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.Reporter;


public class PumaCommonMethods {

	private ObjectMap map= new ObjectMap();
	ReadingProperties rp = new ReadingProperties();
	Logger log = WebDriverManager.LoggerGetInstance();
	WebDriverWait shortWait;
	WebDriverWait wait;

	Actions action;
	TakeScreenshot ts=new TakeScreenshot();
	Workbook workbook;

	FileWriter fw;
	BufferedWriter bw;
	PrintWriter pw ;


	/*****************************************************START OF ALL COMMON METHODS *****************************/



	//example: type(driver, By.name("txtUserName"), username);

	public static void type (WebDriver driver, By locator, String text) {
		WebElement element = driver.findElement(locator);
		element.clear();
		element.sendKeys(text);
	}

	public static boolean isElementPresent(final WebDriver driver, By by) {
		try {
			WebElement element = driver.findElement(by);
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	public static void pause(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean waitToLoadElement(WebDriver driver, By by, int seconds) {
		boolean found = true;

		long bailOutPeriod = 1000 * seconds;
		long lStartTime = new Date().getTime();

		while (!isElementPresent(driver, by)) {
			long lEndTime = new Date().getTime();
			long difference = lEndTime - lStartTime;

			if (difference < bailOutPeriod) {
				pause(1);
			}
			else {
				found = false;
				break;
			}
		}
		return found;
	}

	//IN PROGRESS NEED TO RE WRITE 12/30 should return CellStyle 
	public void setUpCellFillColor(Workbook workbook){
		this.workbook=workbook;
		//styling for background color in a cell 
		CellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.YELLOW.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);

		//	style.setFillBackgroundColor(IndexedColors.AQUA.getIndex());

	}

	public void setUpFontBold(HSSFWorkbook workbook){
		//font bold
		this.workbook=workbook;
		HSSFFont defaultFont= workbook.createFont();
		defaultFont.setFontHeightInPoints((short)10);
		defaultFont.setFontName("Arial");
		defaultFont.setColor(IndexedColors.BLACK.getIndex());
		//defaultFont.setBoldweight((short) 20);
		defaultFont.setItalic(false);

		HSSFFont fontBold= workbook.createFont();
		fontBold.setFontHeightInPoints((short)10);
		fontBold.setFontName("ArialBold");
		fontBold.setColor(IndexedColors.BLACK.getIndex());
		fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontBold.setItalic(false);

		CellStyle style = workbook.createCellStyle();
		style.setFont(fontBold);
	}

	/*=================METHOD for APPENDING TO TXT======note not useful with loops because each loop iteration
	 * will open and close stream which is not efficient=============================================
	 */
	public void appendToTxt(String filepath, String data)
	{ 
		try 
		{

			File file = new File(filepath);
			// if file doesnt exists, then create it
			if (!file.exists()) 
			{
				file.createNewFile();
			}
			//pw= new PrintWriter(file.getAbsoluteFile());

			fw=	new FileWriter(filepath, true);
			bw=	new BufferedWriter(fw);
			pw = new PrintWriter(bw);

			pw.print(data+"\t\t");
			String timestamp = TimeUtil.getTimeStamp();
			pw.println();
			pw.println("====================="+timestamp+"================================");


			bw.close();
			fw.close();

		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		finally
		{
			System.out.println("Done writing to 'txt' file");

			pw.close();

		}		

	}

	public void writeToTxt(String filepath, StringBuilder data)
	{
		try
		{
			String [] lines=data.toString().split(" ");
			
			File file = new File (filepath);
			if( file.exists()!=true)
			{
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter (file);
			BufferedWriter bw = new BufferedWriter (fw);

			for(int i=0; i<lines.length;i++)
			{
				bw.write(lines[i]);
				bw.newLine();
			}
			
			String timestamp = TimeUtil.getTimeStamp();
			bw.write("====================="+timestamp+"================================");
			System.out.println("Done writing using FileWriter");

			bw.close();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
	}
	public void readTxtFile(String filepath)
	{
		ArrayList<String>content = new ArrayList<String>();
		try{
			//FileInputStream fis = new FileInputStream(rp.readConfigProperties("txt.countries"));
			FileInputStream fis = new FileInputStream(filepath);
			InputStreamReader ir = new InputStreamReader(fis);
			BufferedReader br=new BufferedReader(ir);

			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
				// Print the content on the console


				System.out.println (strLine);
				content.add(strLine);
			}
			fis.close();
			br.close();
			ir.close();
			System.out.println ("Successfully executed readTxtFile method");


		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public ArrayList<String> readTxtFileReturn(String filepath)
	{
		ArrayList<String>content = new ArrayList<String>();
		try{
			//FileInputStream fis = new FileInputStream(rp.readConfigProperties("txt.countries"));
			FileInputStream fis = new FileInputStream(filepath);
			InputStreamReader ir = new InputStreamReader(fis);
			BufferedReader br=new BufferedReader(ir);

			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
				// Print the content on the console		
				System.out.println (strLine);				
				content.add(strLine);
			}
			fis.close();
			br.close();
			ir.close();

			//remove the first 2 lines and last line for array

			content.remove(0);
			content.remove(1);
			content.remove((content.size()-1));
			System.out.println ("Successfully executed readTxtFile method; removed first 2 lines and last line.");


		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return content;
	}
	/***************************************************END OF ALL COMMON METHODS ************************************/
	/*****************************************************START OF PUMA COMMON METHODS *****************************/
	public void verifySearchBreadcrumbs(WebDriver driver, String item) throws Exception

	{
		//\"pants\"
		String searchItem="\""+ item+"\"";
		String text="Home Your search Results for ";
		String s = 	driver.findElement(map.getLocator("search_breadcrumbs")).getText();

		boolean search_breadcrumbs=driver.findElement(map.getLocator("search_breadcrumbs")).isDisplayed();

		Reporter.log("Breadcrumbs for search page are on the page: "+ search_breadcrumbs+" 'value' is +" +s);
		if ((text+searchItem).equals(driver.findElement(By.cssSelector("ol.breadcrumb")).getText())) 
		{
			Reporter.log("Breadcrumbs match entred 'search value'");
		}

	}

	public void openPumaHomePage(WebDriver driver) {

		// driver = WebDriverManager.getDriverInstance();
		String devUrl = rp.readConfigProperties("puma_devUrl");
		driver.get(devUrl);
		String homePageTitle = driver.getTitle();
		// System.out.println("On home page title is "+ homePageTitle);
		Reporter.log("On Home page title is " + homePageTitle);
		log.info("Page title is " + homePageTitle);
	}

	public void searchForSku(WebDriver driver) throws Exception
	{

		/*driver.findElement(rp.getLocator("searchField")).clear();
			type(driver, By.name("txtUserName"), username);
			this 2 lines are replaced with just one line using type method defined in PumaCommonMethods
			need to modify that method !*/

		String sku="562941";
		driver.findElement(map.getLocator("searchField")).clear();
		driver.findElement(map.getLocator("searchField")).sendKeys(sku);
		driver.findElement(map.getLocator("searchBtn")).click();

		try{
			shortWait.until(ExpectedConditions.alertIsPresent());
			System.out.println("alert was present");
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
		}
		catch(Exception e)
		{
			System.out.println("alert was not present");
			Reporter.log("No security alert while using 'search'");
			String s = 	driver.findElement(map.getLocator("search_breadcrumbs")).getText();
			verifySearchBreadcrumbs(driver,sku);
			//AssertJUnit.assertEquals("Checkout Confirmation", driver.getTitle());
		}
	}

	public void searchForPants(WebDriver driver) throws Exception {

		/*
		 * AssertJUnit.assertEquals("Shipping Ground",
		 * driver.findElement(By.cssSelector
		 * ("tr.order-shipping > td.totLabel")).getText()); 12/10 PDP IS STILL
		 * IN PROGRESS SO LOCATORS WILL CHANGE
		 * 
		 * *
		 */
		String pants = "pants";

		driver.navigate().back();
		//		wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("home_herocarousel")));
		//wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("herocarousel_dots")));
		driver.findElement(map.getLocator("searchField")).clear();
		driver.findElement(map.getLocator("searchField")).sendKeys(pants);
		driver.findElement(map.getLocator("searchBtn")).click();
		PumaCommonMethods.pause(1800);

		boolean search_breadcrumbs = driver.findElement(
				map.getLocator("search_breadcrumbs")).isDisplayed();
		log.info("Breadcrumbs for search page are on the page: "
				+ search_breadcrumbs);
		verifySearchBreadcrumbs(driver,pants);

	}
	/***************************************************HOME PAGE MAIN NAV ************************************/
	/*****************************************************HOME PAGE MAIN NAV *****************************/

	public void homePageMainNav(WebDriver driver) throws Exception
	{
		openPumaHomePage(driver);

		action =new Actions(driver);
		WebElement mensCat=driver.findElement(map.getLocator("nav_men"));
		action.moveToElement(mensCat).build().perform();
		//wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("nav_apparel")));
		WebElement mensSubcat=		driver.findElement(map.getLocator("nav_apparel"));
		action.moveToElement(mensSubcat).click().build().perform();


	}

	/************************************************************SUBCAT PAGE*****************************/
	/************************************************************SUBCAT PAGE*****************************/
	public void subcatPageUI(WebDriver driver) throws Exception
	{

		//driver.findElement(By.xpath("//a/img")).click();
		driver.findElement(map.getLocator("subcat_product")).click();
		//wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_zoominbtn")));

		Reporter.log("On subcategory page title is '"+ driver.getTitle()+"'");
		System.out.println("On subcategory page title is '"+ driver.getTitle()+"'");


	}	


	/************************************************************PDP PAGE*****************************/
	/************************************************************PDP PAGE*****************************/

	public void pdpPageZoomIn(WebDriver driver) throws Exception
	{ 

		//wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_zoominbtn")));

		driver.findElement(map.getLocator("pdp_zoominbtn")).click();

		log.info("UserGen screenshot taken");
		PumaCommonMethods.pause(2000);
		ts.takeScreenshot(driver);

		//select color
		//select size
		//click on the 'add to cart ' btn
		//click on 'mini cart' link

	}

	public void pdpPageSelectColor(WebDriver driver) throws Exception
	{ //select color

		driver.findElement(map.getLocator("pdp_firstswatch")).click();
		//wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("pdp_colorlabel")));
		PumaCommonMethods.pause(2000);

		// String selectedColor= driver.findElement(map.getLocator("pdp_selectedswatch")).getAttribute("innerHTML").trim();
		String selectedColor= driver.findElement(map.getLocator("pdp_selectedswatch")).getText();

		Reporter.log("Selected color on PDP is: '"+ selectedColor+"'");
		driver.findElement(map.getLocator("pdp_secondswatch")).click();
		PumaCommonMethods.pause(2000);
		String selectedColor2= driver.findElement(map.getLocator("pdp_selectedswatch")).getText();
		Reporter.log("After click on 2nd color swatch label changed to '"+selectedColor2+"'");

		System.out.println("Selected color on PDP is: '"+ selectedColor+"'");
		log.info("PDP: after click on 2nd color swatch label changed to '"+selectedColor2+"'");


		//click on the 'add to cart ' btn
		//click on 'mini cart' link

	}

	public void pdpPageSelectSize(WebDriver driver) throws Exception
	{       //select size and add to cart
		driver.findElement(map.getLocator("pdp_sizetoggle")).click();
		driver.findElement(map.getLocator("pdp_firstsize")).click();
		//  driver.findElement(map.getLocator("pdp_secondsize")).click();

		PumaCommonMethods.pause(2000);

		// String selectedColor= driver.findElement(map.getLocator("pdp_selectedswatch")).getAttribute("innerHTML").trim();
		//   String selectedSize= driver.findElement(map.getLocator("pdp_sizelabel")).getText();
		//  Reporter.log("Selected size on PDP is: '"+ selectedSize+"'");

	}
	public void pdpPageSelectQuantity(WebDriver driver) throws Exception
	{

		driver.findElement(map.getLocator("pdp_quantitytoggle")).click();
		driver.findElement(map.getLocator("pdp_quantitytwo")).click();

	}

	public void pdpPageSizeChart(WebDriver driver) throws Exception
	{ 

		try {
			String defaultWindow=driver.getWindowHandle();
			System.out.println("default window handle is " +defaultWindow );
			driver.findElement(map.getLocator("pdp_sizechart")).click();//should trigger modal window
			PumaCommonMethods.pause(2000);

			driver.findElement(By.className("modal-dialog"));
			driver.findElement(By.className("bootstrap-dialog-title")).getText();

			//check if you have multiple window handles
			Set<String> allHandles= driver.getWindowHandles();

			for (String handle: allHandles)
			{
				System.out.println(handle);
			}
		} catch (NoSuchElementException e) {
			//e.printStackTrace();
			//Thread.currentThread().getStackTrace()[0].getMethodName(); shoule be name of currently executing method
			//	String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			System.out.println(e);
			Reporter.log("ALERT: Size chart might be missing on PDP");

		}
		catch (UnknownServerException e) {
			//e.printStackTrace();
			//Thread.currentThread().getStackTrace()[0].getMethodName(); shoule be name of currently executing method
			//	String methodName=Thread.currentThread().getStackTrace()[0].getMethodName();
			System.out.println(e);
			Reporter.log("ALERT: Size chart might be missing on PDP");
		}

		// ts.takeScreenshot(driver);
		//change quantity
		//assert total
		//assert title 
		//click on 'checkout ' button

	}


	/************************************************************mini CART PAGE*****************************/
	/************************************************************CART PAGE*****************************/


public void fromMiniCartToCart(WebDriver driver) throws Exception
	{

		driver.findElement(map.getLocator("pdp_addtocart")).click();
		WebElement link = driver.findElement(map.getLocator("pdp_minicartlink"));
		shortWait = new WebDriverWait(driver,8);
		
		action.moveToElement(link).build().perform();
		shortWait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("pdp_minicartlink")));
		Thread.sleep(1000);


		driver.findElement(map.getLocator("pdp_minicartlink")).click();
		//driver.findElement(map.getLocator("pdp_minicartlink")).click();
		Reporter.log("Page title on Cart page is " + driver.getTitle());

	}

	public void fromCartToSignIn(WebDriver driver) throws Exception
	{
		//in cart click on 'checkout' for redirect to interst sign in page
		//here should go more code for CART page?? 12.27
		driver.findElement(map.getLocator("cart_checkoutbtn")).click();
	}


	/************************************************************SIGN IN  PAGE**************************************/
	/************************************************************INTERST SIGN IN PAGE*****************************/

	public void checkoutSignInGuest(WebDriver driver) throws Exception
	{ 
		//assert title 
		String title=driver.getTitle();
		Reporter.log("On checkout 'Sign In' page title is "+ title);
		//select unregistered
		//click on 'checkout ' button
		driver.findElement(map.getLocator("intercept_unregistered")).click();


	}

	public void checkoutSignInRegistered()
	{ 
		//12/27 in progress
		//select registered 
		//assert title 
		//click on 'checkout ' button

	}

	/*************************************CHECKOUT STEP1 PAGE**************************************/
	/************************************CHECKOUT STEP1 PAGE  *****************************************/

	public void step1Fields(WebDriver driver) throws Exception
	{
		//assert title 
		String title=driver.getTitle();
		Reporter.log("On checkout 'Step1' page title is "+ title);

		//salutation toggle
		driver.findElement(map.getLocator("salutation_toggle")).click();
		driver.findElement(map.getLocator("salutation_ms")).click();

		//fname and lname
		driver.findElement(map.getLocator("billing_fname"));//HERE DID NOT PUT SEND KEYS YET
		driver.findElement(map.getLocator("billing_lname"));//here did not put send keys yet

		//email
		driver.findElement(map.getLocator("billing_email"));

		//phone
		driver.findElement(map.getLocator("billing_phone"));

		//address1 =street without number
		driver.findElement(map.getLocator("billing_address1"));

		//address suite =street num
		driver.findElement(map.getLocator("billing_addresssuite"));

		//address 2 = apt, ste etc
		driver.findElement(map.getLocator("billing_address2"));


		//zip
		driver.findElement(map.getLocator("billing_zip"));

		//city
		driver.findElement(map.getLocator("billing_city"));

		//country toggle
		driver.findElement(map.getLocator("billing_countrytoggle")).click();

		/*==============CHECKBOX FOR  SEPARATE SHIPPING ADDRESS FORM===================================*/
		//checkbox for separate shipping address if diff from billing
		driver.findElement(map.getLocator("billing_checkboxseparateshipping")).click();
		//below line is optional it should be auto triggered on above click
		//driver.findElement(map.getLocator("billing_selectseparateshipping")).click();

		/*==============BIRTHDAY DROP DOWN===================================*/

		//billing day toggle
		driver.findElement(map.getLocator("billing_daytoggle")).click();

		//billing month toggle
		driver.findElement(map.getLocator("billing_monthtoggle")).click();

		//billing year toggle
		driver.findElement(map.getLocator("billing_yeartoggle")).click();
	}

	public void step1NextStep(WebDriver driver) throws Exception

	{
		driver.switchTo().defaultContent();

		action.moveToElement(driver.findElement(map.getLocator("billing_nextstepbtn"))).click().build().perform();
		Thread.sleep(4000);

		//driver.findElement(map.getLocator("billing_nextstepbtn")).click();
		String title= driver.getTitle();
		System.out.println("After click on 'next step' button on Step1 page- page title is "+title); 
		Reporter.log("After click on 'next step' button on Step1 page- page title is "+title); 
		/*// 

		long end = System.currentTimeMillis() + 5000;
		while (System.currentTimeMillis() < end) {
			WebElement result = driver.findElement(map.getLocator("billing_nextstepbtn"));
			if (result.isDisplayed()) {
				break;
			}

			else
			{
				Thread.sleep(3000);
			}
			Thread.sleep(1000);
		}   
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(map.getLocator("billing_nextstepbtn"))));
		 */
	}

	public void step1SalutationBirthday(WebDriver driver) throws Exception
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
		driver.findElement(map.getLocator("billing_year")).click();

	}

	public StringBuilder step1CountryDropDown(WebDriver driver) throws Exception

	{
		Select selectCountry= new Select(driver.findElement(map.getLocator("billing_selectcountry")));

		List<WebElement>allCountries=selectCountry.getOptions();
		List<String> countries= new ArrayList<String>();

		WebElement country_toggle=driver.findElement(map.getLocator("billing_countrytoggle"));

		System.out.println ("This are the countries in the country drop down: ");

		StringBuilder sb=new StringBuilder();
		for(WebElement country: allCountries)
		{
			String countryOption = country.getAttribute("innerHTML").trim();

			if(countryOption.equalsIgnoreCase("Select..."))
			{
				continue;
			}
			//System.out.println(countryOption);
			sb.append(countryOption+" ");

			//countries.add(countryOption);

			//  Reporter.log("This are the states in the state drop down: "+ stateOption);		
		}
		return sb;
	}
		/*
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
*/
		
	public void step1ClickOnOneCountry(WebDriver driver) throws Exception

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

		country_toggle.click();
		//	WebElement country = driver.findElement(By.linkText(countries.get(i)));
		WebElement oneCountry = driver.findElement(By.linkText(countries.get(3)));

		oneCountry.click();
		System.out.println("Clicked on "+countries.get(3));
		Reporter.log("Country drop downg -clicked on "+countries.get(3));

		//common method from PCM class
		//pcm.writeToTxt(rp.readConfigProperties("temp.countries"), countryOption);

		//  Reporter.log("This are the states in the state drop down: "+ stateOption);		
	}


	public boolean isAlertPresent(WebDriver driver)  
	{ String alertMsg=null;

	try 
	{ 
		Alert alert = driver.switchTo().alert();
		alertMsg =alert.getText(); 

		alert.accept();
		driver.switchTo().defaultContent();

		//  alert.dismiss();
		//((JavascriptExecutor)driver).executeScript("window.confirm = function(msg){return true;};");
		System.out.println("Alert present: alert message "+ alertMsg);
		Reporter.log("Aler is present");
		log.info("Alert present" +alertMsg);

		return true; 
	}   
	catch (NoAlertPresentException Ex) 
	{ 

		System.out.println("No alert!");
		return false;
	}

	catch (UnhandledAlertException Ex) 
	{ 
		System.out.println("Modal dialog present: "+ alertMsg);
		return true; 
	}   
	catch (Exception Ex) 
	{ 
		System.out.println(Ex);
		return true; 
	}   
	} 
	//=========================================AJAX===============================
	public void waitForAjax (WebDriver driver,int timeoutInSeconds)

	{
		System.out.println("Checking active ajax calls by calling jquery.active");

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
	}

	/*
	public void waitForAjaxToComplete(WebDriver driver) {

		try {

			 wait.until(new ExpectedCondition<Boolean>() 
					 {
		            public Boolean apply(WebDriver driver) 
		            {
		            	JavascriptExecutor js = (JavascriptExecutor)driver;
		            	return (Boolean)js.executeScript("return jQuery.active==0");
		            }

					 }
			 );
		}

		catch(Exception e)
		{
			e.printStackTrace();
			log.debug("Error in waitForAjaxToComplete"+e);
			System.out.println("Error in waitForAjaxToComplete"+e);

		}
	}

	 */
	public boolean isBillingZipError(WebDriver driver) throws Exception
	{
		WebElement zipError = null;
		try{
			shortWait = new WebDriverWait(driver, 10);
			shortWait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("billing_ziperror")));

			zipError = driver.findElement(map.getLocator("billing_ziperror"));
			if(zipError.isDisplayed()==true)
			{
				System.out.println("Zip field error message is dispayed");	
				Reporter.log("Zip field error message is displayed");
			}
			return true;
		}

		catch(NoSuchElementException e)
		{
			e.printStackTrace();
			Reporter.log("Zip field error message is displayed");
			//should fail because expected zip.Error.isDisplayed==false

			Reporter.log("WARNING: valid zip is not accepted. Cannot get to 'Step2'");
			System.out.println("Exception in isBillingZipError "+e);
			AssertJUnit.assertEquals(zipError.isDisplayed(),false);
			return false;
		}


		/*
		WebElement cityError=driver.findElement(map.getLocator("billing_cityerror"));
		WebElement streetError=driver.findElement(map.getLocator("billing_streeterror"));
		WebElement streetNumError = driver.findElement(map.getLocator("billing_streetnumerror"));
		WebElement fnameError = driver.findElement(map.getLocator("billing_fnameerror"));
		WebElement lnameError = driver.findElement(map.getLocator("billing_lnameerror"));
		WebElement emailError = driver.findElement(map.getLocator("billing_emailerror"));
		 */
	}

	public void submitStep2(WebDriver driver) throws Exception 
	{
		System.out.println("Executing submit Step2 method");

		String labelPayment=driver.findElement(map.getLocator("step2_legend")).getText();
		Reporter.log("On page Step2 label for radio buttons is " + labelPayment);

		WebElement total=driver.findElement(map.getLocator("step2_ordertotal"));
		total.isDisplayed();
		WebElement totalValue=driver.findElement(map.getLocator("step2_totalvaluexpath"));

		String step2Total=totalValue.getText();
		Reporter.log("Total on Step2 is "+step2Total);

		String step2Subtotal = driver.findElement(map.getLocator("step2_subtotavalue")).getText();
		Reporter.log("Subtotal on Step2 is"+ step2Subtotal);


		WebElement  creditCard = driver.findElement(map.getLocator("step2_creditcard"));
		creditCard.click();
		boolean isEnabled= driver.findElement(map.getLocator("step2_nextbtnxpath")).isEnabled();
		System.out.println("Step2 Page: next step btn is enabled "+ isEnabled);

		WebDriverWait wait1 = new WebDriverWait(driver,15);
		boolean step2Displayed=driver.findElement(map.getLocator("step2_nextbtnxpath")).isDisplayed();
		if(step2Displayed==true)
		{
			System.out.println("step2 button is displayed ");
			//action.moveToElement(driver.findElement(map.getLocator("step2_nextbtnxpath"))).build().perform();
			//	wait1.until(ExpectedConditions.elementToBeClickable(map.getLocator("step2_nextbtnname")));
			driver.findElement(map.getLocator("step2_nextbtnxpath")).click();
		}
		else
		{	//JavascriptExecutor jsDriver=(JavascriptExecutor)driver; 
			//jsDriver.executeScript("var y = document.getElementsByName('dwfrm_billing_save')[0]; y.type= 'text' ");
			step2Displayed=driver.findElement(map.getLocator("step2_nextbtnname")).isDisplayed();
			System.out.println("Step2 button is displayed "+step2Displayed);
			//action.moveToElement(driver.findElement(map.getLocator("step2_nextbtnname"))).build().perform();
			//	wait1.until(ExpectedConditions.elementToBeClickable(map.getLocator("step2_nextbtnname")));
			driver.findElement(map.getLocator("step2_nextbtnname")).click();		
		}

	}

	public void submitStep3(WebDriver driver) throws Exception
	{
		
		System.out.println("Executing submitStep3");
		log.info("On Step 3");
		String title = driver.getTitle();
		System.out.println(title);
		
		Reporter.log("Page title is "+ title);

		wait=new WebDriverWait(driver,7);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(map.getLocator("step3_nextbtnname"))));

		WebElement step3submit=driver.findElement(map.getLocator("step3_nextbtnname"));

		boolean isDisplayed=step3submit.isDisplayed();
		if (isDisplayed==true)
		{
			step3submit.click();
		}
	}

	public void submitWirecard(WebDriver driver) throws Exception

	{
		System.out.println("Executing submitWirecard method");
		log.info("executing submitWirecard");
		
		String title = driver.getTitle();
		System.out.println(title);	
		Reporter.log("Page title is "+ title);
		/*
	wirecard_orderNum=xpath>.//*[@id='orderinfo']/div[4]
	wirecard_cardholder=id>cardholder
	wirecard_cardnum=id>pan
	wirecard_security=id>cvc

	wirecard_nextbtnxpath=xpath>.//*[@id='button_next']/a
	wirecard_nextbtnxpath2=xpath>//li[3]/a
	<select id="expMonth" class="i_select" size="1" name="expMonth">
		 */

		WebElement cardHolder =driver.findElement(map.getLocator("wirecard_cardholder"));
		if (cardHolder.isDisplayed()==true)
		{
			cardHolder.clear();
			cardHolder.sendKeys("FNAME");
		}
		
		else
		{
			wait=new WebDriverWait(driver, 15);
		//	wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("wirecard_cardholder")));
			wait.until(ExpectedConditions.visibilityOfElementLocated(map.getLocator("wirecard_cardholderxpath")));

			cardHolder.clear();
			cardHolder.sendKeys("FNAME");		
		}

		//CARD NUMBER
		WebElement cardNum=driver.findElement(map.getLocator("wirecard_cardnum"));
		cardNum.clear();
		cardNum.sendKeys("9400000000000003");

		//security code for card 
		WebElement securityCode = driver.findElement(map.getLocator("wirecard_security"));
		securityCode.clear();
		securityCode.sendKeys("123");

		//month and year for expiration 
		Select selectMonth= new Select(driver.findElement(map.getLocator("wirecard_month")));
		selectMonth.selectByVisibleText("01");

		Select selectYear = new Select(driver.findElement(map.getLocator("wirecard_year")));
		selectYear.selectByVisibleText("2014");


		WebElement wirecardNextBtn=driver.findElement(map.getLocator("wirecard_nextbtn"));
		wirecardNextBtn.click();



	}
}
