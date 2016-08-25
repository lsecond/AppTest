package test;

import static org.junit.Assert.assertEquals;
import modules.*;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.Random;

import javax.sound.midi.MidiDevice.Info;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import bsh.This;

import com.opera.core.systems.runner.inprocess.ScreenCapture;
import com.sun.xml.internal.ws.developer.MemberSubmissionAddressing.Validation;
import com.thoughtworks.selenium.webdriven.VariableDeclaration;

import sun.util.logging.resources.logging_fr;
import utility.FileCollect;
import utility.HelperUtility;
import fit.ColumnFixture;

/**
 * @author John L
 * @version 1.0
 * @since 05/28/2016
 * 
 *        Test TheScore Android Application
 */
public class AppTest extends ColumnFixture {

	private AndroidDriver driver;
	private Properties prop;
	private String testEnv;
	private String URL;

	private String username;
	private String password;
	private String browserType;
	private String jsonString;
	private String jsonNum;
	private String homeLocation;

	private String payee;
	private String payFrom;
	private String payAmount;
	private String transferFrom;
	private String transferTo;
	private String transferAmount;

	private Boolean valid;

	private static final long PAUSE_TIME = 3000;
	private static final long PAGE_TIME = 50;
	private static final long FINAL_TIME = 5000;
	private int timeOut;

	private HelperUtility helper;
	
	private Login login;
	


	private static final String SRL2016 = "http://pri-web-orig-000/portcullis/srl2016/gwwam500";
	private static final String SRLnew = "http://vm-dom2012/portcullis/IREdev.wsc/gwwam500";

	private static final String CHROME_DRIVER = "C://workspace//PortcullisRegressionTest//chromedriver.exe";
	private static final String IE_DRIVER = "C://Workspace//PortcullisRegressionTest//IEDriverServer.exe";
	private static final String HOME_DIR = "C://Workspace//PortcullisRegressionTest//";
	static public SortedMap<String, String> dataMap = new TreeMap<String, String>();
	static public SortedMap<String, String> dataMapInit = new TreeMap<String, String>();
	static public SortedMap<String, String> dataMapAfter = new TreeMap<String, String>();

	static public LinkedHashMap<String, String> fileMap = new LinkedHashMap<String, String>();	
	boolean isInitDataIn;
	boolean isFacebookButtonShowingOnLoginPage;
	boolean isUserLoginSuccess;
	public Logger log = Logger.getRootLogger();
    
	public void setTestEnv(String url) {
		this.testEnv = url;
		log.info("test env : " + testEnv);
	}

	/**
	 * FITNESSE: Set browser type
	 * 
	 * @param browserType
	 *            Firefox, Chrome, IE
	 */
	public void setBrowserType(String browserType) {
		this.browserType = browserType;
		log.info(this.browserType);
	}

	public void setHomeLocation(String loc) {
		homeLocation = loc;
	}

	/**
	 * FITNESSE: Set time out
	 * 
	 * @param timeOut
	 *            Given time out
	 * */
	public void setTimeOut(String timeOut) {
		if (timeOut != null)
			this.timeOut = Integer.parseInt(timeOut);
		else
			this.timeOut = 0;
	}

	public void setUserName(String username) {
		this.username = username;
		log.info("username:  " + username);
	}

	public void setPassword(String password) {
		this.password = password;
		log.info("password:  " + password);
	}

	public void setPayee(String payee) {
		this.payee = payee;
		log.info("payee:  " + payee);
	}

	public void setPayFrom(String payFrom) {
		this.payFrom = payFrom;
		log.info("payFrom:  " + payFrom);
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
		log.info("payAmount:  " + payAmount);
	}

	public void setTransferFrom(String transferFrom) {
		this.transferFrom = transferFrom;
		log.info("transferFrom:  " + transferFrom);
	}

	public void setTransferTo(String transferTo) {
		this.transferTo = transferTo;
		log.info("transferTo:  " + transferTo);
	}

	public void setTransferAmount(String transferAmount) {
		this.transferAmount = transferAmount;
		log.info("transfer Amount:  " + transferAmount);
	}

	public Boolean AmountValid() {
		return this.valid;
	}

	/**
	 * Quit browser driver
	 */
	public boolean tearDown() {
		if (driver != null) {
			driver.close();
			driver.quit();
		}
		// System.exit(1);
		return true;
	}

	/**
	 * main process for ems test
	 * 
	 * @return
	 */
	public boolean runTest() {
		boolean runStatus = false;
		try {
			log.info("test begin");
			helper = new HelperUtility();
			
			login = new Login();
			
			
		
		
			
			HelperUtility.disableScreenSaver();

			HelperUtility.driverType = browserType;

			// Select browser type
		//	fileCollect.cleanPicFile("screenshots");
			File appDir = new File("c://temp//");
			File app = new File(appDir, "android-debug.apk");

			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("deviceName", "Android");
			capabilities.setCapability("platformVersion", "6.0.1");
			capabilities.setCapability("app", app.getAbsolutePath());
			//capabilities.setCapability("autoWebview", "ready");
			// capabilities.setCapability("appPackage",
			// "io.appium.android.apis");
			// capabilities.setCapability("appActivity", ".ApiDemos");
			// driver = new AppiumDriver(new
			// URL("http://0.0.0.0:4723/wd/hub"),capabilities);
			driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),
					capabilities);
		
			// load parse in property file
			/*
			for(int i = 0; i<10; i++){
				log.info(driver.getContextHandles());
				helper.sleep(2000);
			}
			*/
			/*
			Set<String> contextNames = driver.getContextHandles();
			for (String contextName : contextNames) {
				System.out.println(contextName);
				if (contextName.contains("WEBVIEW")) {
					log.info(contextName);
					driver.context(contextName);
				}
			}*/
        
			log.info("begin login");
			login.clickFirstLogin(driver);
			helper.sleep(2000);
			
			isFacebookButtonShowingOnLoginPage= login.validateFacbookButton(driver);
		    
			login.inputUser(driver,"jiangliu321@hotmail.com");
			
			// login.clickRememeberMe(driver);
			login.inputPassword(driver, "lgl102");
			
			
			helper.sleep(2000);
			login.clickLogin(driver);
			helper.sleep(2000);
			login.clickSkip(driver);
			// login.findall(driver);
			log.info("login finished");
			isUserLoginSuccess= login.validateUserLoginSuccess(driver);
			helper.sleep(6000);
			
		
		
		} catch (org.openqa.selenium.TimeoutException e) {
			System.err.println(e.getMessage());
			if (driver != null) {

				driver.quit();
			}
			System.exit(1);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			if (driver != null) {				
				driver.quit();
			}

			System.exit(1);

		}
		log.info(" test end without error");
		driver.quit();
		return true;
	}

	/**
	 * Return Firefox driver with custom profile
	 * 
	 * @return firefox driver
	 * */
	public WebDriver getFirefoxDriver() {
		File profileDir = new File("M:\\DEVPROJ\\QA-Activity\\Selenium");
		FirefoxProfile fxProfile = new FirefoxProfile(profileDir);
		fxProfile.setPreference("browser.download.dir", ""); // Insert custom
																// download dir
																// here
		return new FirefoxDriver(fxProfile);
	}

	/**
	 * Return remote Chrome driver
	 * 
	 * @param host
	 *            remote host information with port number
	 * @return remote Chrome driver
	 * */

	public void takeScreenShot(String fileName, String desc) {
		String contextName = driver.getContext();
		driver.context("NATIVE_APP");
		String destDir = "screenshots";
		new File(destDir).mkdirs();

		File srcFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		File targetFile = new File(destDir + "/" + fileName + ".png");
		try {
			FileUtils.copyFile(srcFile, targetFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fileMap.put(fileName, desc);
		driver.context(contextName);

	}
    
	public Boolean isFacebookButtonShowingOnLoginPage() {
		return this.isFacebookButtonShowingOnLoginPage;
	}
	
	public Boolean isUserLoginSuccess() {
		return this.isUserLoginSuccess;
	}
	
	public static void main(String[] args) throws Exception {
		Logger log = Logger.getRootLogger();
		    log.info("---------------------------TheScore Login testing begining --------------------------------");
			AppTest fixture = new AppTest();
			fixture.setUserName("jiangliu321@hotmail.com");
			fixture.setPassword("lgl102");
			fixture.setHomeLocation("");
			if (fixture.runTest()) {
				log.info("test pass without error");
			} else {
				log.info("test failed");
			}

		
	}
}