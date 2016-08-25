package modules;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.james.mime4j.field.datetime.DateTime;
import org.apache.log4j.Logger;

import io.appium.java_client.android.AndroidDriver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import utility.HelperUtility;

public class Login {
	public Logger log = Logger.getRootLogger();
	private HelperUtility helper;
	
	
	/**
	 * click first login button
	 * @param driver
	 */
	public void clickFirstLogin(AndroidDriver<?> driver) {
		WebElement login = (WebElement) (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.elementToBeClickable(By.id("btn_login")));
		login.click();
	}
	
	
	
	/**
	 * input user name
	 * @param driver
	 * @param user
	 */
	public void inputUser(AndroidDriver driver, String user) {		
		WebElement emailElement = (WebElement) (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.elementToBeClickable(By
						.name("Email Address")));
		emailElement.sendKeys(user);
	}
	
	/**
	 * input password
	 * @param driver
	 * @param password
	 */
	public void inputPassword(AndroidDriver driver, String password) {
		driver.hideKeyboard();
		log.info(password);
		WebElement passwordElement = (WebElement) (new WebDriverWait(driver, 60))
				.until(ExpectedConditions.elementToBeClickable(By
						.name("Password")));
		passwordElement.clear();
		
		passwordElement.sendKeys(password);
		//passwordElement.sendKeys(Keys.ENTER);
	}
	
	/**
	 * click login button
	 * @param driver
	 */
	public void clickLogin(AndroidDriver driver) {
		driver.hideKeyboard();
		WebElement LoginElement = (WebElement) (new WebDriverWait(driver, 1000))
				.until(ExpectedConditions.elementToBeClickable(By
						.id("sign_in_button")));
		LoginElement.click();
		
	}
	/**
	 * click skip button
	 * @param driver
	 */
	public void clickSkip(AndroidDriver driver) {
		WebElement LoginElement = (WebElement) (new WebDriverWait(driver, 1000))
				.until(ExpectedConditions.elementToBeClickable(By
						.id("skip")));
		LoginElement.click();
		
	}
	
	/**
	 * validate facebook button showing 
	 * @param driver
	 * @return
	 */
	public Boolean validateFacbookButton(AndroidDriver driver) {
		
		WebElement facebookElement = (WebElement) (new WebDriverWait(driver, 1000))
				.until(ExpectedConditions.elementToBeClickable(By
						.name("Continue with Facebook")));
		Boolean valid;
		valid = facebookElement.getText().equalsIgnoreCase("Continue with Facebook");
		return valid;
		
	}
	
	public Boolean validateUserLoginSuccess(AndroidDriver driver) {
		Boolean valid;
		WebElement usernameElement = (WebElement) (new WebDriverWait(driver, 1000))
				.until(ExpectedConditions.elementToBeClickable(By
						.id("com.fivemobile.thescore:id/profile_name")));
		log.info("user name is :"  + usernameElement.getText());
		valid = usernameElement.getText().contains("john liu");
		return valid;
		
	}
	
	
	
	public void findall(AndroidDriver driver) {
		WebElement bodyElement = (WebElement) (new WebDriverWait(driver, 1000))
				.until(ExpectedConditions.presenceOfElementLocated(By
						.tagName("body")));
		List<WebElement> childs = bodyElement.findElements(By.xpath(".//*"));
		for(WebElement sElement : childs){
			log.info(sElement.getAttribute("outerHTML"));
		}
	}
	
	


	
	
}
