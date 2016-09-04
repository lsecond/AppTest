package test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.ClientProtocolException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;


public class mobileWebAndroid extends TestCase {
	private WebDriver driver;
	private String baseUrl;
	
	
	@Before
	public void setUp() throws Exception {
		
		//AndroidDriver driver;
		//File appDir = new File("c:/temp/");
		//File app = new File(appDir, "Blood.apk");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("device","Android");
		//mandatory capabilities
		capabilities.setCapability("deviceName","emulator-5554");
		capabilities.setCapability("platformName","Android");
		capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");
		//capabilities.setCapability("udid", Properties.udid);

		//other caps
		//capabilities.setCapability("app", app.getAbsolutePath());
		driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	}
	
	@After
	public void tearDown() throws Exception {
		driver.close();
		driver.quit();
	}
	
	@Test
	public void test() throws ClientProtocolException, IOException {
		driver.get("https://www.theweathernetwork.com/ca");
		WebElement searchInput = (WebElement) (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.elementToBeClickable(By.id("search")));		
		searchInput.sendKeys("london");
		searchInput.sendKeys(Keys.RETURN);
		
		WebElement London = (WebElement) (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='searchindex']/div[2]/ul/li[2]/a")));	
		assertTrue("search record not match london", London.getText().contains("London"));
	}
	
	
	
}
