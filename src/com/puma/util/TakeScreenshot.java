package com.puma.util;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;


public class TakeScreenshot {
		
		String screenshotName=null;
		
		public TakeScreenshot()
		{
			setScreenshotName();
		}
		
		public void setScreenshotName()
		{
              Calendar c = Calendar.getInstance();
	         
	         java.text.SimpleDateFormat df2 = new java.text.SimpleDateFormat("MMM");

	         String screenshotName= "" + c.get(Calendar.YEAR) +
	                          "-" + df2.format(c.getTime())+
	                          "-" + c.get(Calendar.DAY_OF_MONTH) +
	                          "-" + c.get(Calendar.HOUR_OF_DAY) +
	                          "hr-" + c.get(Calendar.MINUTE) +
	                          "min-" + c.get(Calendar.SECOND) +
	                          " sec-"+ c.get(Calendar.MILLISECOND)+"mls.png";
	         
				this.screenshotName=screenshotName;//setter
		}
//getter method 
		
		public String getScreenshotName()
		{
			return this.screenshotName;
			
		}
		public  void takeScreenshot(WebDriver driver)
	    
	    {
	   	 WebDriverManager.getBrowser(driver);
	        
	        
	         File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	           String path= "/Users/igonzalez/Desktop/WORKING_WITH_ECLIPSE/Puma/report/screenshot/UserGen/" + screenshotName;
	           File target =new File (path);
	          
	         try {
	       	  //copying file from original directory into wanted directory
	              FileUtils.copyFile(source, target);
	         } catch (IOException e) {
	              e.printStackTrace();
	         }
	         
	         System.out.println("Screenshot taken at "+ screenshotName.substring(0, screenshotName.length()-4));
	         Reporter.log("Screenshot taken at "+ screenshotName.substring(0, screenshotName.length()-4));
	         String str = System.getProperty("user.dir") + "/"; // Current dir

	         //Reporter.log("<img src=\"file:///" + str +"/target/screenshot/"+ screenshotName + "\" alt=\"\"/><br/>");
	         Reporter.log("Saved <a href=../screenshot/UserGen/" + screenshotName + ">Screenshot</a>");
	    }

		
	}


