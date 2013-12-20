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
	Actions action;
	Logger log=WebDriverManager.LoggerGetInstance();
	ReadingProperties rp= new ReadingProperties();
	private ObjectMap map = new ObjectMap();
	TakeScreenshot ts=new TakeScreenshot();
	
	String tshirt="562941";
	
	@BeforeClass
	public void oneTimeSetUp() {
		driver = WebDriverManager.startDriver();
		wait = new WebDriverWait(driver,25);
		action = new Actions(driver);
	}

	@AfterClass
	public void oneTimeTearDown() {
		//WebDriverManager.stopDriver();
	}
	
	@Test(groups="init")
	public void homePageMainNav() throws Exception
	{
		
		String devUrl = rp.readConfigProperties("puma_devUrl");
		driver.get(devUrl);
		String homePageTitle = driver.getTitle();
		System.out.println("On home page title is "+ homePageTitle);
		Reporter.log("On Home page title is "+ homePageTitle);
		
/*
WebElement mensCat=driver.findElement(map.getLocator("nav_men"));
		wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("nav_menapparel")));
WebElement mensSubcat=		driver.findElement(map.getLocator("nav_apparel"));
		action.moveToElement(mensCat).moveToElement(mensSubcat).click().build().perform();
*/
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
		
	}
	
	@Test(dependsOnGroups="init", groups="subcategory")
	public void subcatPageUI() throws Exception
	{
   
     //driver.findElement(By.xpath("//a/img")).click();
       driver.findElement(map.getLocator("subcat_product")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_zoominbtn")));
       
       Reporter.log("On subcategory page title is '"+ driver.getTitle()+"'");
       System.out.println("On subcategory page title is '"+ driver.getTitle()+"'");
       
		
	}
	@Test(dependsOnGroups="init", groups="subcategory")
	public void subcatPageFunct()
	{
		
	}
	
	@Test(dependsOnGroups="subcategory", groups="pdp")
	
	public void pdpPageZoomIn() throws Exception
	{ 

		wait.until(ExpectedConditions.presenceOfElementLocated(map.getLocator("pdp_zoominbtn")));

	       driver.findElement(map.getLocator("pdp_zoominbtn")).click();
	       
	       log.info("UserGen screenshot taken");
	       PumaCommonMethods.pause(2000);
	       ts.takeScreenshot(driver);

		//select color
		//select size
		//click on the 'add to cart ' btn
		//click on 'mini cart' link
		
	}
	
	@Test(dependsOnGroups="subcategory", groups="pdp")
	public void pdpPageSelectColor() throws Exception
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
	
	@Test(dependsOnGroups="subcategory", groups="pdp")
	public void pdpPageSelectSize() throws Exception
	{		//select size and add to cart
		   driver.findElement(map.getLocator("pdp_firstsize")).click();
		   PumaCommonMethods.pause(2000);

	      // String selectedColor= driver.findElement(map.getLocator("pdp_selectedswatch")).getAttribute("innerHTML").trim();
		   String selectedSize= driver.findElement(map.getLocator("pdp_sizelabel")).getText();
	       Reporter.log("Selected size on PDP is: '"+ selectedSize+"'");
	       
	       

		
	}
	@Test(dependsOnGroups="subcategory", groups="pdp")
	public void pdpPageSizeChart() throws Exception
	{ 
		
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
		
		
		driver.findElement(map.getLocator("pdp_addtocart")).click();
		   PumaCommonMethods.pause(1500);

	       ts.takeScreenshot(driver);
		//change quantity
		//assert total
		//assert title 
		//click on 'checkout ' button
		
	}
	
	


}
