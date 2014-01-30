package com.puma.test;

import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
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

//Checkout sign in and checkout registered with DataProvider
@Listeners({ com.puma.util.TestListenerFailPass.class })

public class CheckoutSignIn  {

	WebDriver driver;
	private WebDriverWait wait; 
	private WebDriverWait shortWait;
	PumaCommonMethods pcm=new PumaCommonMethods();
	Actions action;


	Logger log=WebDriverManager.LoggerGetInstance();
	//ReadingProperties rp= new ReadingProperties();
	private ObjectMap map = new ObjectMap();
	//TakeScreenshot ts=new TakeScreenshot();

	@BeforeSuite
	public void setUp() {
		driver = WebDriverManager.startDriver();
		wait = new WebDriverWait(driver, 20);
		shortWait = new WebDriverWait(driver, 10 );
		action = new Actions(driver);

		try {

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
		} catch (Exception e) {
			log.debug("Could not get to 'Sign In' page");
			e.printStackTrace();
		}

	}

	@AfterSuite
	public void oneTimeTearDown() {
		WebDriverManager.stopDriver();
	}


	@DataProvider(name = "registeredCheckout")
	public Object[][] createData1() {
		return new Object[][] {

				//1/6 need to add expected result

				//valid email+pswd
				{ "software_test22@hotmail.com", "kipling1", "Checkout"},
				//invalid email format
				{ "software_test", "kipling1", "Sorry, this does not match our records."},
				//email non registered
				{ "software@yahoo.com", "kipling1", "Sorry, this does not match our records."},
				//empty email +pswd
				{ "", "", "eror msg"},
				//valid email+invalid pswd
				{ "software_test22@hotmail.com", "kipling22", "err msg"},

		};
	}

	@Test(priority=1)/*(groups="checkoutSignIn")*/
	public void checkoutSignInGuest() throws Exception
	{ 
		//assert title 
		//select unregistered and click on 'checkout ' button
		pcm.checkoutSignInGuest(driver);        
		String title=        driver.getTitle();
		String expected="SiteGenesis Checkout";
		AssertJUnit.assertEquals(expected, title);
		Reporter.log("After click on 'checkout' button for guest Checkout got redirected to "+title +" page");

	}


	@Test(groups="checkoutSignIn", dataProvider="registeredCheckout", priority=2)
	public void checkoutSignInRegistered(String email, String password, String expected) throws Exception
	{ 
		try
		{
			boolean canSignOut=driver.findElement(map.getLocator("intercept_signoutlink")).isDisplayed();
			if (canSignOut==true)
			{
				driver.findElement(map.getLocator("intercept_signoutlink")).click();
				pcm.homePageMainNav(driver);
				//subcat
				pcm.subcatPageUI(driver);
				//PDP                
				pcm.pdpPageSelectColor(driver);
				pcm.pdpPageSelectSize(driver);
				pcm.pdpPageSelectQuantity(driver);
				pcm.pdpPageSizeChart(driver);    
				pcm.fromMiniCartToCart(driver);

			}
		}

		catch(NoSuchElementException e)
		{
System.out.println("Sign Out link is not visible..continue testing");
		}
		//click on 'Cart' link to get back to Cart
		driver.findElement(map.getLocator("pdp_minicartlink")).click();
		pcm.fromCartToSignIn(driver);                

		//select registered and click on 'checkout ' button

		WebElement rememberMe=driver.findElement(map.getLocator("intercept_registeredrememberme"));
		AssertJUnit.assertEquals(true, rememberMe.isSelected());
		if(rememberMe.isSelected()==true)
		{
			Reporter.log("On Checkout sign in page 'remember me' is selected on default load.");
		}
		//deselect remember me checkbox
		rememberMe.click();

		WebElement regEmail = driver.findElement(map.getLocator("intercept_registeredemail"));
		regEmail.clear();
		regEmail.sendKeys(email);
		String title = driver.getTitle();

		WebElement regPswd = driver.findElement(map.getLocator("intercept_registeredpswd"));
		regPswd.clear();
		regPswd.sendKeys(password);

		//click on submit button
		WebElement regLogin =driver.findElement(map.getLocator("intercept_registeredbtn"));
		regLogin.click();
		//        AssertJUnit.assertEquals(true, actual);

		try{
			//WebElement regEmailError = driver.findElement(map.getLocator("registered_emailerror"));
			//WebElement regPswdError = driver.findElement(map.getLocator("registered_pswderror"));
			//WebElement regNoRecords = driver.findElement(map.getLocator("registered_didnotmatchrecordscss"));

		}
		catch(NoSuchElementException e)
		{

		}
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