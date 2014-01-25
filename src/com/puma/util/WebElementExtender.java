package com.puma.util;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;

public class WebElementExtender {
	 public static void setAttribute(WebElement element, String attributeName, String value)
	   {
	       WrapsDriver wrappedElement = (WrapsDriver) element;
	       JavascriptExecutor driver = (JavascriptExecutor) wrappedElement.getWrappedDriver();
	       driver.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, attributeName, value);
	   }
}
  
	 


/*how to use:
 * WebElement email = driver.findElement(By.id("email"));
   WebElementExtender.setAttribute(userName, "value", "");
   userName.sendKeys("test@test.com");
 */
