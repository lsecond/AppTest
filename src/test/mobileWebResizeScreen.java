package test;

import io.appium.java_client.remote.MobileCapabilityType;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.ClientProtocolException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;
import utility.HelperUtility;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

public class mobileWebResizeScreen extends TestCase {
	public ChromeDriver driver;
	private String baseUrl;

	@Before
	public void setup() {
		
		
		

	}

	@After
	public void tearDown() throws Exception {
		driver.close();
		driver.quit();
	}

	@Test
	public void test() throws ClientProtocolException, IOException {
		System.setProperty("webdriver.chrome.driver", "C://workspace//AppTest//chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--test-type");
		driver = new ChromeDriver(options);

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().setSize(new Dimension(768/2, 1280/2));
		driver.get("https://www.theweathernetwork.com/ca");

		WebElement searchInput = (WebElement) (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.elementToBeClickable(By.id("search")));
		searchInput.sendKeys("london");
		searchInput.sendKeys(Keys.RETURN);

		WebElement London = (WebElement) (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='searchindex']/div[2]/ul/li[2]/a")));
		assertTrue("search record match london", London.getText().contains("London"));
	}

}
