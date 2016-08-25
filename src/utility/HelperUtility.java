package utility;

import io.appium.java_client.android.AndroidDriver;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.*;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.collections.map.StaticBucketMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import sun.util.logging.resources.logging;
import bsh.This;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sun.org.glassfish.external.statistics.Statistic;
import com.sun.xml.internal.ws.client.SenderException;

public class HelperUtility {
	private static Timer screenSaverDisabler;
	public static String driverType = "";
	public static String testType = ""; // create, maintain, ccg.
	public static String defaultAuthCode = "";//
	public static String responseString = "";
	private int delayTime;
	static public final String SUP = "Addition;Air Conditioning Added;Amenities Added;Basement Added;Basement Finished;Boathouse Added;Ceases to be in a Subclass;Change of Mailing Address;Change of Municipal Address;Classification Change;Comm/Ind Yard Work Added;Created Subordinate(s);Deleted Subordinate(s);Exempt to Taxable;Fireplace Added;Garage Added;Heating Added;Legal Description;Maintain Subordinate(s);New Primary Structure(s);No Longer Assessed as Conservation Land;No Longer Assessed as Farm;No Longer Assessed as Managed Forests;Other Secondary Structure(s) added;Owner Correction;Porches / Decks Added;Renovation Type A;Renovation Type B;Renovation Type C;Renovation Type D;Sale Processed;School Lease;Site Updates;Structure(s) Change(s);Tax Incentive - Conservation Land;Tax Incentive \u2013 Farm;Tax Incentive - Managed Forest;Unit Class Updates;Workshop Added;STC:Change of Mailing Address;STC:Change of Municipal Address;STC:Created Subordinate(s);STC:Deleted Subordinate(s);STC:Legal Description;STC:Maintain Subordinate(s);STC:Owner Correction;STC:Sale Processed;STC:School Lease;STC:Site Updates;STC:Unit Class Updates";
	static public final String OMT = "Addition;Air Conditioning Added;Amenities Added;Basement Added;Basement Finished;Boathouse Added;Ceases to be in a Subclass;Classification Change;Comm/Ind Yard Work Added;Exempt to Taxable;Fireplace Added;Garage Added;Heating Added;Land not Assessed;New Primary Structure(s);No Longer Assessed as Conservation Land;No Longer Assessed as Managed Forests;Other Secondary Structure(s) added;Porches / Decks Added;Structure(s) Change(s);Tax Incentive - Conservation Land;Tax Incentive - Farm;Tax Incentive - Managed Forest;Workshop Added;Change of Mailing Address;Change of Municipal Address;Created Subordinate(s);Deleted Subordinate(s);Legal Description;Maintain Subordinate(s);Owner Correction;Sale Processed;School Lease;Site Updates;Unit Class Updates;";
	static public final String STC = "Change of Mailing Address;Change of Municipal Address;Created Subordinate(s);Deleted Subordinate(s);Legal Description;Maintain Subordinate(s);Owner Correction;Sale Processed;School Lease;Site Updates;Unit Class Updates";
	public Logger log = Logger.getRootLogger();
	
	/**
	 * get any string from a jsonstring you already know the format.no matter
	 * include array or not.
	 * 
	 * @param jsonString
	 *            is a String have json string format,
	 * @param name
	 *            if want to find a object: use "." + "object name" ; find a
	 *            array element: use "-" + "the index of element"; Example: if a
	 *            jsonstring "json" like { "name": "john", "age": 24,
	 *            "salaryByYear":[ {"year": 2013,"salary":30000}, {"year":
	 *            2014,"salary":40000} ] } use: getJsonElementAll(json, ".name")
	 *            can get the "john"; use: getJsonElementAll(json,
	 *            ".salaryByYear-1.salary") can get year 2014's salary: 40000;
	 * @return string of the element you want to find.
	 */
	public String getJsonElementAll(String jsonString, String name) {
		String retVal = "";
		List signList = new ArrayList();
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			if (c == '-' || c == '.') {
				signList.add(c);
				log.info(signList);
			}
		}

		JsonElement current = new JsonParser().parse(jsonString);
		String ss[] = name.split("-|\\.");
		log.info(ss.length);
		log.info(ss[0] + ss[1]);

		for (int i = 1; i < ss.length; i++) {
			if (signList.get(i - 1).equals('.')) {
				current = current.getAsJsonObject().get(ss[i]);
			} else {
				current = current.getAsJsonArray().get(Integer.parseInt(ss[i]));
			}
		}
		retVal = current.getAsString();
		log.info(retVal);
		return retVal;
	}

	public SortedMap<String, String> randomChoiceFromMap(SortedMap<String, String> changeMap){
		//randomly choise change partition ,etc 
		Random randomGenerator= new Random(System.currentTimeMillis()); 
		int size = changeMap.size();
		List<String> keys      = new ArrayList<String>(changeMap.keySet());
		String       randomKey = keys.get( randomGenerator.nextInt(keys.size()) );
		String       value     = changeMap.get(randomKey);
		SortedMap<String, String> oneSetMap= new TreeMap<String, String>();
		oneSetMap.put(randomKey, value);
		return oneSetMap;
	}
	
	
	
	

	/**
	 * get parseIn data from parseIn string and restore in a map,return the map. 
	 * @param parseIn
	 * @return
	 */
	public List<String> getParseIn(String parseIn) {
		    List<String> parshInList = new ArrayList();
			String splitString[] = parseIn.split(",");
			
				String number = splitString[0];
				String morgage = splitString[1];
				String rate = splitString[2];
				String period = splitString[3];
				String down = splitString[4];		
				log.info("account number is: " + number ); 
				log.info("morgage total is: " + morgage ); 
				log.info("rate is : " + rate ); 
				log.info("period  is: " + period ); 
				log.info("down payment is: " + down ); 
			parshInList.add(number);
			parshInList.add(morgage);
			parshInList.add(rate);
			parshInList.add(period);
			parshInList.add(down);
			
			return parshInList;
	}

	public static void setClipboardContents(String text) {
		//textTransfer = new TextTransfer();
		StringSelection stringSelection = new StringSelection(text);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
		
		//textTransfer.setClipboardContents(text);
	}

	public static void cancelScreenSaver() {
		screenSaverDisabler.cancel();
	}

	/**
	 * Moves mouse once in a minute and therefore prevents the screen saver from
	 * kicking in.
	 */
	public static void disableScreenSaver() {
		screenSaverDisabler = new Timer();
		screenSaverDisabler.scheduleAtFixedRate(new TimerTask() {
			Robot r = null;
			// initialization block
			{
				try {
					r = new Robot();
				} catch (AWTException headlessEnvironmentException) {
					screenSaverDisabler.cancel();
				}
			}

			@Override
			public void run() {
				java.awt.Point loc = MouseInfo.getPointerInfo().getLocation();
				r.mouseMove(loc.x + 1, loc.y);
				r.mouseMove(loc.x, loc.y);
			}
		}, 0, 59 * 1000);
	}

	public void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			System.out.println("In HelperUtility.sleep()");
		}
	}
	
	/**
	 * currency to int
	 * 
	 * @param currency
	 *            String
	 * 
	 * @return int
	 */
	public int curToInt(String Currency) {
		int Amount = 0;
		Amount = Integer.parseInt(Currency.replaceAll("[^\\d.]+", ""));
		return Amount;

	}
	
	public void setDelayTime(int delayTime) {
		this.delayTime = delayTime;
		log.info("delayTime:  " + delayTime);
	}
	
	public Boolean delay() {
		try {
		    Thread.sleep(delayTime);                 //300 second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		    return false;
		}
		return true;

	}
	
	public void takeScreenShot(WebDriver driver) {
	
		String destDir = "screenshots";
		DateFormat dateFormat;
		File scrFile =  ((RemoteWebDriver) driver).getScreenshotAs(OutputType.FILE); 
		dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
		new File(destDir).mkdirs();
		String destFile = dateFormat.format(new Date()) + ".png";
		
		try {
			FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void sendMail(String beginTime){
	//public static void main(String[] args) {
	
		Properties props = new Properties();
		props.put("mail.smtp.host", "nts1.stratinfotech.com");
		props.put("mail.smtp.socketFactory.port", "25");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "25");

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("johnl","Fcqqy5z");
				}
			});

		try {
			StringWriter writer = new StringWriter();
			try {
				IOUtils.copy(new FileInputStream(new File("L://johnL//apptest//test" + beginTime + "//appTestReport.html")), writer);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from@no-spam.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("johnl@stratinfotech.com"));
			message.setSubject("Testing Subject");
			message.setContent(writer.toString(), "text/html");
			//message.setText("Dear Mail Crawler," +
				//	"\n\n No spam to my email, please!");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public static void main(String [ ] args)
	{
		
		sendMail("06101051");
	}
	
	
	
}
