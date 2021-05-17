package com.startribune.baseclass;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ThreadGuard;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;


public abstract class StarTribuneBaseClass {

	public WebDriver driver;
	public static ThreadLocal<WebDriver> currentDriver = new ThreadLocal<WebDriver>();
	private Properties prop = null;
	

	@Parameters({ "appURL", "browserType" })
	@BeforeMethod(alwaysRun = true)
	public void initializeTestBaseSetup(String appURL, String browserType) {
		try {
			setDriver(appURL, browserType);
		} catch (Exception e) {
			System.out.println("................" + e.getMessage());
		}
	}

	public Properties readPropertyFile() {
		prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(System.getProperty("user.dir") + "/" + "ConfigProperties");
			// load a properties file
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}


	public WebDriver getDriver() {
		return currentDriver.get();
	}

	
	
	protected void setDriver(String appURL, String browserType) throws Exception {
		switch (browserType) {
		case "firefox":
			driver = initalizeFirefoxDriver(appURL);
			break;
		case "chrome":
			driver = initalizeChromeDriver(appURL);
			break;
		default:
			initalizeFirefoxDriver(appURL);
		}
	}

	private synchronized WebDriver initalizeFirefoxDriver(String appURL) {
		System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		currentDriver.set(driver);
		ThreadGuard.protect(driver);
		driver.manage().window().maximize();
		driver.get(appURL);
		return driver;
	}


	private synchronized WebDriver initalizeChromeDriver(String appURL) throws Exception {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("-incognito");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		ThreadGuard.protect(driver);
		currentDriver.set(driver);
		driver.manage().window().maximize();
		driver.get(appURL);
		return driver;
	}


	
	public void logStepMessage(String Message) {
		Reporter.log(Message, true);
	}

	// Click functionality by Java Script
	public void clickOnElementUsingJavaScript(WebElement el, WebDriver driver) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", el);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	// Point To Element
	public void pointToElement(WebElement e1, WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", e1);
	}

	public WebElement waitTillElementVisible(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		WebElement elementloaded = wait.until(ExpectedConditions.visibilityOf(element));
		return elementloaded;
	}

	public WebElement waitTillElementToBeClickable(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		WebElement elementloaded = wait.until(ExpectedConditions.elementToBeClickable(element));
		return elementloaded;
	}

	public WebElement clickOnElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		WebElement elementClicked = wait.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
		return elementClicked;
	}

	public void setData(WebElement element, String text) {
		waitTillElementVisible(element);
		element.clear();
		element.sendKeys(text);
	}

	public void waitForInvisibilityOfElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

	// Wait for Page Load
	public void waitTillPageLoaded(WebDriver driver) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};

		Wait<WebDriver> wait = new WebDriverWait(driver, 30);
		try {
			wait.until(expectation);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Move to Element Function
	public void moveToElement(WebElement element, WebDriver driver) {
		try {
			Actions action = new Actions(driver);
			action.moveToElement(element).build().perform();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	// Function for provide wait for VerifyPage Title
	public void verifypageTitle(String str, WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.titleContains(str));
	}

	public void scrollDownThePage(WebDriver driver, int position) throws Exception {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(String.format("window.scrollBy(0,%d)", "", position));
		} catch (Exception e) {
			e.getMessage();
			throw e;
		}
	}

	public void scrollUpThePage(WebDriver driver) throws Exception {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,-250)");
		} catch (Exception e) {
			e.getMessage();
			throw e;
		}
	}

	// Read value from JavaScript
	public String getValueFromJS(String locator, WebDriver driver) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		String value = (String) executor.executeScript(String.format("return $('#%s').val()", locator));
		return value;
	}

	public String getCurrentDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String localDate = dtf.format(LocalDate.now());
		return localDate;
	}

	public String getOneYearFromCurrentYear() {
		DateFormat newDate = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date nextYear = cal.getTime();
		String nextYearDate = newDate.format(nextYear);
		return nextYearDate;
	}

	public String getTwoYearFromCurrentYear() {
		DateFormat newDate = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 2);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date nextYear = cal.getTime();
		String nextYearDate = newDate.format(nextYear);
		return nextYearDate;
	}

	public static String getThreeYearFromCurrentYear() {
		DateFormat newDate = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 3);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date nextYear = cal.getTime();
		String nextYearDate = newDate.format(nextYear);
		return nextYearDate;
	}

	public void selectValueFromDropdownUsingVisibleText(WebElement element, String visibleText) {
		Select select = new Select(element);
		select.selectByVisibleText(visibleText);
	}

	public static void selectValueFromDropdownUsingIndex(WebElement element, int index) {
		Select select = new Select(element);
		select.selectByIndex(index);
	}

	public void waitTillTextTobePresentInElement(String str, WebDriver driver, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.textToBePresentInElement(element, str));
	}

	public void waitTillURLMatches(WebDriver driver, String url) {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.urlToBe(url));
	}

	public void waitTillURLContains(WebDriver driver, String url) {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		wait.until(ExpectedConditions.urlContains(url));
	}
    
	
	public void close() {
		currentDriver.get().quit();
	}

}
