package com.puma.test;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.puma.util.ObjectMap;
import com.puma.util.PumaCommonMethods;
import com.puma.util.ReadingProperties;
import com.puma.util.TakeScreenshot;
import com.puma.util.WebDriverManager;


@Listeners({ com.puma.util.TestListenerFailPass.class })

public class CheckoutSignIn {

	WebDriver driver;
	private WebDriverWait wait; 
	private WebDriverWait shortWait;
	PumaCommonMethods pcm=new PumaCommonMethods();
	Actions action;

	//Logger log=WebDriverManager.LoggerGetInstance();
	//ReadingProperties rp= new ReadingProperties();
	private ObjectMap map = new ObjectMap();
	//TakeScreenshot ts=new TakeScreenshot();

	@BeforeSuite
	public void oneTimeSetUp() throws Exception {
		driver = WebDriverManager.startDriver();
		wait = new WebDriverWait(driver, 20);
		shortWait = new WebDriverWait(driver, 10 /* timeout in seconds */);
		action = new Actions(driver);
		
		pcm.homePageMainNav(driver);

		//subcat
		pcm.subcatPageUI(driver);

		//PDP		
		pcm.pdpPageSelectColor(driver);
		pcm.pdpPageSelectSize(driver);
		pcm.pdpPageSelectQuantity(driver);
		pcm.pdpPageSizeChart(driver);		

	}

	@AfterSuite
	public void oneTimeTearDown() {
		WebDriverManager.stopDriver();
	}


	@BeforeMethod()
	public void interstCheckoutPage() throws Exception
	{ 

		//home page
		

		//get to cart 
		pcm.fromMiniCartToCart(driver);
		//in cart click on 'checkout' for redirect to interst sign in page
		pcm.fromCartToSignIn(driver);		
	}
	
	 @DataProvider(name = "registeredCheckout")
	  public Object[][] createData1() {
	          return new Object[][] {
	        		  
	        		  //valid email+pswd
	            { "software_test22@hotmail.com", "kipling1"},
	            //invalid email format
	            { "software_test", "kipling1"},
	            //email non registered
	            { "software@yahoo.com", "kipling1"},
	            //empty email +pswd
	            { "", ""},
	            //valid email+invalid pswd
	            { "software_test22@hotmail.com", "kipling22"},
	                       
	          };
	  }
	 
	@Test(groups="checkoutSignIn")
	public void checkoutSignInGuest() throws Exception
	{ 
		//assert title 
		//select unregistered
		//click on 'checkout ' button
		pcm.checkoutSignInGuest(driver);	
	String title=	driver.getTitle();
	String expected="SiteGenesis Checkout";
		AssertJUnit.assertEquals(expected, title);
		Reporter.log("After click on 'checkout' button for guest Checkout got redirected to "+title +" page");

	}

	
	@Test(groups="checkoutSignIn", dataProvider="registeredCheckout")
	public void checkoutSignInRegistered(String email, String password) throws Exception
	{ 

		//select registered 
		//assert title 
		//click on 'checkout ' button
		WebElement regEmail = driver.findElement(map.getLocator("intercept_registeredemail"));
		regEmail.clear();
		regEmail.sendKeys(email);
		
		
		WebElement regPswd = driver.findElement(map.getLocator("intercept_registeredpswd"));
		regPswd.clear();
		regPswd.sendKeys(password);
		WebElement regLogin =driver.findElement(map.getLocator("intercept_registeredbtn"));

	}
/*
 * registered_emailerror=xpath>//fieldset/div/ul/li
registered_pswderror xpath>//fieldset/div[2]/ul/li
registered_didnotmatchrecords=xpath>.//*[@id='dwfrm_login']/div
registered_didnotmatchrecordscss=cssSelector>div.error-form


 * 
 * 
 * #returns 2 nodes the: 1st is email field and 2nd is pswd; 1st field will be clicked
intercept_registeredemail=xpath>//fieldset/div/input

#looks like last part is dynamic
intercept_registeredemailid=id>id=dwfrm_login_username_d0robwngrskz

intercept_registeredrememberme=id>dwfrm_login_rememberme

#will bring a new frame
intercept_registeredforgotpswd=id>password-reset
frame_email = id>dwfrm_requestpassword_email

frame_sendbtn=xpath>.//*[@id='PasswordResetForm']/fieldset/button

frame_closecss=cssSelector>button.close
frame_closexpath=xpath>//div[2]/button

valid_email =software_test22@hotmail.com
valid_password=kipling1

invalid_emailnohandle = software_test
invalid_emailnotsignedup = software_test@yahoo.com

 */

}
