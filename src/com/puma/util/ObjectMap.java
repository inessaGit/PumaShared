package com.puma.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;

public class ObjectMap {

	Properties property;

	//public ObjectMap(String mapFile)
	
	public ObjectMap()
	{
		property=new Properties();
		try
		{
			FileInputStream fis=new FileInputStream("/Users/igonzalez/Desktop/WORKING_WITH_ECLIPSE/Puma/src/com/puma/config/OR.properties");
			property.load(fis);
			fis.close();
		}

		catch(IOException e)
		{
			System.out.println(e.getMessage());
		}
	}

	public By getLocator(String logicalElementName) throws Exception
	{

		//read value using the logical name as key
		String locator=property.getProperty(logicalElementName);

		//split the value which contains locator type and locator value
		String locatorType=locator.split(">")[0];
		String locatorValue=locator.split(">")[1];

		//retyrn an instance of By class based on type of locator

		if(locatorType.toLowerCase().equals("id"))
			return By.id(locatorValue);
		else if (locatorType.toLowerCase().equals("name"))
			return By.name(locatorValue);
		else if (locatorType.toLowerCase().equals("classname"))
			return By.className(locatorValue);
		else if (locatorType.toLowerCase().equals("class"))
			return By.className(locatorValue);
		else if (locatorType.toLowerCase().equals("tagname"))
			return By.tagName(locatorValue);
		else if (locatorType.toLowerCase().equals("tag"))
			return By.tagName(locatorValue);
		else if (locatorType.toLowerCase().equals("linktext"))
			return By.linkText(locatorValue);

		else if (locatorType.toLowerCase().equals("link"))
			return By.linkText(locatorValue);
		else if(locatorType.toLowerCase().equals("partiallinl-text"))
			return By.partialLinkText(locatorValue);
		else if(locatorType.toLowerCase().equals("cssselector"))
			return By.cssSelector(locatorValue);
		else if(locatorType.toLowerCase().equals("css"))
			return By.cssSelector(locatorValue);

		else if  (locatorType.toLowerCase().equals("xpath"))
			return By.xpath(locatorValue);

		else 
			throw new Exception ("Locator type '"+locatorType+ "' not defined");

	}


}
