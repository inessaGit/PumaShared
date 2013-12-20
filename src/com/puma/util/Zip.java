 package com.puma.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {
	
	
	// make zip of reports
		public static void zip(String filepath,String reportFileName){
		 	try
		 	{
		 		File inFolder=new File(filepath);// create a file instance to represent folder 
		 		File outFolder=new File(reportFileName);//create a file instance that should represent testng html report
		 		
		 		//pass file representing testng html report to zipOutput 
		 		//ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outFolder)));
		 		
		 		BufferedInputStream in = null;
		 		
		 		FileOutputStream dest = new FileOutputStream(outFolder);//open stream for writing 
		 		ZipOutputStream out=new ZipOutputStream(new BufferedOutputStream(dest));//open stream for writing in zip format

		 		
		 		byte[] data  = new byte[1000];
		 		String files[] = inFolder.list();//all the files in the folder
		 		
		 		for (int i=0; i<files.length; i++)
		 		{
		 			in = new BufferedInputStream(new FileInputStream
		 			(inFolder.getPath() + "/" + files[i]), 1000);  
		 			out.putNextEntry(new ZipEntry(files[i])); 
		 			int count;
		 			while((count = in.read(data,0,1000)) != -1)
		 			{
		 				out.write(data, 0, count);
		 			}
		 			out.closeEntry();
	  }
	  out.flush();
	  out.close();
		 	
	}
	  catch(Exception e)
	  {
		  e.printStackTrace();
	  } 
	 }	
}