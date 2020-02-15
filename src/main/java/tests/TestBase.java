package tests;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class TestBase {

	private static WebDriver driver;
	private static Logger Log = Logger.getLogger(TestBase.class.getName());

	@BeforeClass
	public void setUp() {
		DOMConfigurator.configure("log4j.xml");		
		driver = getDrv();
		String homePageUrl = "http://prestashop-automation.qatestlab.com.ua/ru/";
		Log.info("Opening url http://prestashop-automation.qatestlab.com.ua/ru/");
		driver.get(homePageUrl);
		Log.info("Main page is downloaded");
	}

	@AfterClass
	public void tearDown() {
		if (driver != null) {
			driver.quit();
			Log.info("Driver is closed");
		}
	}

	private static WebDriver getDrv() {		
		System.setProperty("webdriver.chrome.driver", "resources/chromedriver");		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;
	}

	public static WebDriver getDriver() {
		return driver;
	}
}
