package pages;

import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class MainPage extends Wait {
	private static Logger Log = Logger.getLogger(MainPage.class.getName());
	private final By searchWidget = By.className("ui-autocomplete-input");
	private final By priceOfProduct = By.className("price");
	private final By CurrencyEUR = By.xpath("//a[.='EUR €']");
	private final By CurrencyUAH = By.xpath("//a[.='UAH ₴']");
	private final By CurrencyUSD = By.xpath("//a[.='USD $']");
	private final By currencySelector = By.cssSelector(".expand-more._gray-darker");

	public void changeCurrencyType(Currency currency) {
		waitWhileElementIsClickable(currencySelector);
		Log.info("Clicking button currencySelector");
		driver.findElement(currencySelector).click();
		Log.info("Changing currency type to USD");
		switch (currency) {
		case EUR:
			waitWhileElementIsClickable(CurrencyEUR);
			driver.findElement(CurrencyEUR).click();
			break;
		case UAH:
			waitWhileElementIsClickable(CurrencyUAH);
			driver.findElement(CurrencyUAH).click();
			break;
		case USD:
			waitWhileElementIsClickable(CurrencyUSD);
			driver.findElement(CurrencyUSD).click();
			break;
		default:
			Log.error("Please, enter the correct currency name: EUR, UAH or USD");
		}
	}

	public boolean checkCurrency(Currency currency) {
		List<WebElement> pricesOfProducts = driver.findElements(priceOfProduct);
		Log.info("Verifying currency type");
		switch (currency) {
		case EUR:
			for (WebElement value : pricesOfProducts) {
				if (!value.getText().contains("€")) {
					Log.info("False, not EUR currency");
					return false;
				}
			}
			Log.info("true, prices shown in EUR");
			return true;
		case UAH:
			for (WebElement value : pricesOfProducts) {
				if (!value.getText().contains("₴")) {
					Log.info("False, not UAH currency");
					return false;
				}
			}
			Log.info("true, prices shown in UAH");
			return true;
		case USD:
			for (WebElement value : pricesOfProducts) {
				if (!value.getText().contains("$")) {
					Log.info("False, not USD currency");
					return false;
				}
			}
			Log.info("true, prices shown in USD");
			return true;
		}
		Log.error("Please, enter the correct currency name: EUR, UAH or USD");
		return false;
	}

	public SearchPage searchProduct(String name) {
		waitWhileElementIsClickable(searchWidget);
		WebElement searchField = driver.findElement(this.searchWidget);
		Log.info("Typing word 'dress'");
		searchField.sendKeys(name);
		searchField.submit();
		Log.info("Button 'submit' is pressed");
		Log.info("Showing SearchPage");
		return new SearchPage();
	}
}
