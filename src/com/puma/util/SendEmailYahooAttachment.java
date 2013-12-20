package com.puma.util;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class SendEmailYahooAttachment{

	// using getter method defined in TakeScreenshot class to get screeshot name 
	TakeScreenshot tsc=new TakeScreenshot();
String screenshotName=tsc.getScreenshotName();


	Calendar c = Calendar.getInstance();
	SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy kk:mm");
	// path to emailable-report.html
	String path1 = "/Users/igonzalez/Desktop/WORKING_WITH_ECLIPSE/Nautica/test_outputNau/emailable-report.html";//this is String attach;

	// path to screenshots
	//String path2 = "/Users/igonzalez/Desktop/WORKING_WITH_ECLIPSE/Nautica/screenshotsNautica/screenshots/"+screenshotName;
	
	//trying with one particular screenshot that exists 2013-Nov-26-13hr-56min-42 sec-400mls.png
	String path2 = "/Users/igonzalez/Desktop/WORKING_WITH_ECLIPSE/Nautica/screenshotsNautica/screenshots/2013-Nov-26-13hr-56min-42 sec-400mls.png";

	
	

	String host, port, emailid,username, password;
	Properties props = System.getProperties();
	Session l_session = null;

	public SendEmailYahooAttachment() {
		host = "smtp.mail.yahoo.com";
		port = "587";
		emailid = "software_test22@yahoo.com";
		username = "software_test22";
		password = "Jenny1983";

		emailSettings();
		createSession();
		sendMessage("software_test22@yahoo.com", "igonzalez@fluid.com", "email2@gmail.com",  "email3@yahoo.com","Report generated by TestNG","test Mail", sdf.format(c.getTime()), path1, path2);
	}

	public void emailSettings() {
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "false");
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.password", password);
		props.put("mail.smtp.user", emailid);
		props.put("mail.smtp.localhost", "localhost");
		//        props.put("mail.smtp.socketFactory.port", port);
		//        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		//        props.put("mail.smtp.socketFactory.fallback", "false");

	}

	public void createSession() {

		l_session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		l_session.setDebug(true); // Enable the debug mode

	}


	public boolean sendMessage(String emailFromUser, String toEmail1, String toEmail2, String toEmail3, String subject, String msg, String  date, String pathEmailReport, String pathScreenshots) {
		//System.out.println("Inside sendMessage 2 :: >> ");
		{
		
		try {
			MimeMessage message = new MimeMessage(l_session);
						
			FileDataSource fds = new FileDataSource(path1);
			FileDataSource fds2=new FileDataSource(path2);//path2 currently is //screenshots folder maybe need to point to a .png file??
			
			Multipart mp = new MimeMultipart();

			MimeBodyPart attachFilePart = new MimeBodyPart();//MimeBodyPart class for html report

			attachFilePart.setDataHandler(new DataHandler(fds));
			attachFilePart.setFileName(fds.getName());
			
			mp.addBodyPart(attachFilePart);
			
			
			MimeBodyPart screenshotPart = new MimeBodyPart();//MimeBodyPartClass for screenshot
			MimeBodyPart attachScreenshotPart = new MimeBodyPart();//MimeBodyPart class for html report
			attachScreenshotPart.setDataHandler(new DataHandler(fds2));
			attachScreenshotPart.setFileName(fds2.getName());
			
			mp.addBodyPart(attachScreenshotPart);

		//	screenshotPart.setContent(attachFilePart, "text/html");
			
				message.setContent(mp, "text/html");


			
			// attaching screenshots
			File dir = new File(path2);
			String[] children = dir.list();
			if (children == null) 
			{
				System.out.println("dir does not exist");
			} 
			else 
			{
				for (int i=0; i<children.length;i++)
				{
					String filename = children[i];
					System.out.println("Adding: " + filename);
					attachFilePart = new MimeBodyPart();
					fds = new FileDataSource(path2+"\\"+filename);
					attachFilePart.setDataHandler(new DataHandler(fds));
					attachFilePart.setFileName(fds.getName());
					mp.addBodyPart(attachFilePart);
				}
			}
		
		
				//System.out.println("Sending Message *********************************** ");
				emailid = emailFromUser;
				//System.out.println("mail id in property ============= >>>>>>>>>>>>>> " + emailid);
				//message.setFrom(new InternetAddress(emailid));
				message.setFrom(new InternetAddress(this.emailid));

				message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail1));
				// message.addRecipient(Message.RecipientType.BCC, new InternetAddress(AppConstants.fromEmail));
				message.setSubject(subject);
				
				//message.setText(msg);
				Transport.send(message);
				
				System.out.println("Message Sent");
				System.out.println("Mail was sent to " + toEmail1 + ", " + toEmail2 + ", " + toEmail3);

			} 
			
			catch (MessagingException mex)
			{
				mex.printStackTrace();
			} 
			
			catch (Exception e) 
			
			{
				e.printStackTrace();
			}//end catch block
			
			return true;

		}

		}
	}
