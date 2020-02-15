package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.TestBase;

public class Wait {
	WebDriver driver;
	WebDriverWait wait;

	 Wait() {
		driver = TestBase.getDriver();
		wait = new WebDriverWait(driver, 10);
	}

	void waitWhileElementIsClickable(By by) {
		wait.until(ExpectedConditions.elementToBeClickable(by));
	}

	void waitWhileElementIsVisible(By by) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

}
