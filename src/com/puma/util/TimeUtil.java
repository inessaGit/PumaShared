package com.puma.util;


import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;


//import testProperties.common.config;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtil 
{
	public String testStartTime, finalTime;
	
	ReadingProperties rp = new ReadingProperties();
	//public String tempDocsPath=rp.readConfigProperties("tempDocsPath");
	//config comCfgTC = new config("./src/testProperties/common/comConfig.properties");
	
	//public String tempDocsPath=comCfgTC.Read_Property("tempDocsPath");
	
	public String CurrentDate() {
		  Calendar currentDate = Calendar.getInstance();
		  SimpleDateFormat formatter=new SimpleDateFormat("yyyy_MMM_dd");
		  String dateNow = formatter.format(currentDate.getTime());
		  return dateNow;
	}
	
	public String CurrentTime() {
		Calendar currentTime = Calendar.getInstance();
		String am_pm;
		  int hour = currentTime.get(Calendar.HOUR);
		  int minute = currentTime.get(Calendar.MINUTE);
		  int second = currentTime.get(Calendar.SECOND);
		  if(currentTime.get(Calendar.AM_PM) == 0)
		  am_pm = "AM";
		  else
		  am_pm = "PM";
		  String timeNow = am_pm+"_"+hour+"_"+minute+"_"+second;
		  return timeNow;
	}
	/*
	public String GetTestStartTime() throws IOException
	{
		String tempFilePath = tempDocsPath+"/"+"TestStartTime.txt";
		FileInputStream fstream;
		fstream = new FileInputStream(tempFilePath);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
				while ((testStartTime=br.readLine()) != null)   
				{
					finalTime=testStartTime;
				}
				in.close();
				return finalTime;	
	}
	public String Get_Current_Time() 
	{
		Calendar currentTime = Calendar.getInstance();
		String am_pm;
		  int hour = currentTime.get(Calendar.HOUR);
		  int minute = currentTime.get(Calendar.MINUTE);
		  int second = currentTime.get(Calendar.SECOND);
		  if(currentTime.get(Calendar.AM_PM) == 0)
		  am_pm = "AM";
		  else
		  am_pm = "PM";
		  String timeNow = am_pm+"_"+hour+"_"+minute+"_"+second;
		  return timeNow;
	}
	*/
}
