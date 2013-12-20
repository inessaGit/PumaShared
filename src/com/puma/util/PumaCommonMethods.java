package com.puma.util;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

public class PumaCommonMethods {
	
	private ObjectMap map= new ObjectMap();
	
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
	
	  public static boolean waitToLoadElement(WebDriver driver, By by, int seconds) {
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

}
