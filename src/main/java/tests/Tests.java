package tests;

import org.testng.annotations.Test;
import pages.Currency;
import pages.MainPage;
import pages.SearchPage;
import pages.Sorting;

public class Tests extends TestBase {

	@Test
	public void TitleTest() {
		MainPage mainPage = new MainPage();
		
		mainPage.changeCurrencyType(Currency.USD);
		mainPage.checkCurrency(Currency.USD);
		
		SearchPage searchPage = mainPage.searchProduct("dress");
		
		searchPage.checkQuantityOfFoundProducts();
		searchPage.checkCurrencyOfFoundProducts(Currency.USD);
		searchPage.changeSortingMethod(Sorting.PRICE_FROM_HIGH_TO_LOW);
		searchPage.checkHighToLowSorting();
		searchPage.checkDiscountProductPriceAndLabel();
		searchPage.checkDiscountSize();
	}
}
