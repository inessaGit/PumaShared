package com.puma.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;


public class TakeScreenshot {
		
		String screenshotName=null;		
		public TakeScreenshot()
		{
			//setScreenshotName();
		}
		
		public void setScreenshotName()
		{
			DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy__hh_mm_ssaa");
			String destFile = dateFormat.format(new Date()) + ".png";
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
	   	File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy__hh_mm_ssaa");
		String destDir = System.getProperty("user.dir")+"/report/screenshot/UserGen/";
		new File(destDir).mkdirs();
		String destFile = dateFormat.format(new Date()) + ".png";

		try {
			FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not take screenshot on failure");//getInstanceName =package+className
		}
	         
	       
	         //Reporter.log("<img src=\"file:///" + str +"/target/screenshot/"+ screenshotName + "\" alt=\"\"/><br/>");
	 		Reporter.setEscapeHtml(false);
	 		Reporter.log("Saved <a href=../screenshot/UserGen/" + destFile + ">Screenshot</a>");
	    }

		
	}


