package com.puma.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.puma.util.PumaCommonMethods;
import com.puma.util.WebDriverManager;

public class InitSuite {

        WebDriver driver;
        private WebDriverWait wait;
        private WebDriverWait shortWait;

        Actions action;
        PumaCommonMethods pcm=new PumaCommonMethods();

        @BeforeSuite
        public void oneTimeSetUp() {
                driver = WebDriverManager.startDriver();
                wait = new WebDriverWait(driver, 20);
                shortWait = new WebDriverWait(driver, 10 /* timeout in seconds */);
                action = new Actions(driver);

        }

        @AfterSuite
        public void oneTimeTearDown() {
                WebDriverManager.stopDriver();
        }
        
        public WebDriver getDriverObject()
        {
        	return driver;
        }

}
