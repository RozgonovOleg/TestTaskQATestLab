package pages;

import java.text.DecimalFormat;
import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchPage extends MainPage {
	private static Logger Log = Logger.getLogger(SearchPage.class.getName());

	private final By productTitle = By.className("product-title");
	private final By priceOfProduct = By.className("price");
	private final By foundProductLabels = By.className("total-products");
	private final By productPriceAndShipping = By.xpath("//div[@class='product-price-and-shipping']/span[1]");
	private final By productsWithDiscount = By.className("discount-percentage");
	private final By DiscountPercentage = By.className("discount-percentage");
	private final By realpriceOfDiscountProducts = By.xpath("//span[@class='discount-percentage']/following-sibling::span");
	private final By regularPriceOfDiscountProducts = By
			.xpath("//span[@class='discount-percentage']/preceding-sibling::span");
	private final By sortingMethodsDropdown = By.className("select-title");
	private final By sortingList = By.className("select-list");

	public boolean checkQuantityOfFoundProducts() {
		waitWhileElementIsClickable(productTitle);
		String getAllFoundProductsSize = "Товаров: " + String.valueOf(driver.findElements(productTitle).size()) + ".";
		waitWhileElementIsVisible(this.foundProductLabels);
		String getFoundProductsLabel = driver.findElement(foundProductLabels).getText();
		if (getAllFoundProductsSize.equals(getFoundProductsLabel)) {
			Log.info("Label " + getAllFoundProductsSize + " is displayed and shows the correct quantity of products");
			return true;
		}
		Log.info("Label 'Товаров:' displayed wrong quantity of products");
		return false;
	}

	public boolean checkCurrencyOfFoundProducts(Currency currency) {
		waitWhileElementIsClickable(priceOfProduct);
		List<WebElement> prices = driver.findElements(priceOfProduct);
		Log.info("Verifying currency of found products");
		switch (currency) {
		case EUR:
			for (WebElement value : prices) {
				if (!value.getText().contains("€")) {
					Log.info("False, not EUR currency");
					return false;
				}
			}
			Log.info("true, prices shown in EUR");
			return true;
		case UAH:
			for (WebElement value : prices) {
				if (!value.getText().contains("₴")) {
					Log.info("False, not UAH currency");
					return false;
				}
			}
			Log.info("true, prices shown in UAH");
			return true;
		case USD:
			for (WebElement value : prices) {
				if (!value.getText().contains("$")) {
					Log.info("False, not USD currency");
					return false;
				}
			}
			Log.info("true, prices shown in USD");
			return true;
		}
		Log.error("Only EUR, UAH or USD");
		return false;
	}

	public void changeSortingMethod(Sorting sorting) {
		waitWhileElementIsClickable(this.sortingMethodsDropdown);
		Log.info("Changing sorting method");
		driver.findElement(this.sortingMethodsDropdown).click();
		for (WebElement value : driver.findElements(sortingList)) {
			if (value.getText().trim().equals(sorting.getDescription())) {
				waitWhileElementIsClickable(sortingList);
				value.click();
				Log.info("Sorting method changed");
			}
		}
	}

	public boolean checkHighToLowSorting() {
		wait.until((ExpectedConditions.urlContains("search?controller=search&order=product.price.desc&s=")));
		List<WebElement> PriceAndShipping = driver.findElements(productPriceAndShipping);
		Log.info("Checking sorting method HIGH TO LOW");
		for (int i = 0; i + 1 < PriceAndShipping.size(); i++) {
			float price = Float.parseFloat(PriceAndShipping.get(i).getText().substring(0, 5).replace(",", "."));
			float nextPrice = Float.parseFloat(PriceAndShipping.get(i + 1).getText().substring(0, 5).replace(",", "."));
			if (price > nextPrice) {
				Log.info("Sorting method is HIGH TO LOW");
				return true;

			}
		}
		Log.info("Sorting method is not HIGH TO LOW");
		return false;
	}

	public boolean checkDiscountProductPriceAndLabel() {
		waitWhileElementIsClickable(productTitle);
		List<WebElement> productsWithDiscount = driver.findElements(this.productsWithDiscount);
		List<WebElement> DiscountPercentage = driver.findElements(this.DiscountPercentage);
		List<WebElement> regularPriceOfDiscountProducts = driver.findElements(this.regularPriceOfDiscountProducts);
		List<WebElement> realPriceOfDiscountProducts = driver.findElements(this.realpriceOfDiscountProducts);

		for (WebElement label : DiscountPercentage) {
			if (label.getText().contains("%")) {
				Log.info("Label exists" + label.getText());
			} else {
				Log.info("Label not exists");
				return false;
			}
		}
		if (productsWithDiscount.size() == DiscountPercentage.size()
				&& productsWithDiscount.size() == regularPriceOfDiscountProducts.size()
				&& productsWithDiscount.size() == realPriceOfDiscountProducts.size()) {
			Log.info(
					"For products with a discount, a percentage discount is indicated along with the price before and after the discount");
			return true;
		} else {
			Log.info(
					"Quantity of products is not equal the quantity of discount labels or price before and after the discount");
			return false;
		}
	}

	public boolean checkDiscountSize() {
		waitWhileElementIsClickable(productTitle);
		List<WebElement> productsWithDiscount = driver.findElements(this.productsWithDiscount);
		List<WebElement> DiscountPercentage = driver.findElements(this.DiscountPercentage);
		List<WebElement> regularPriceOfDiscountProducts = driver.findElements(this.regularPriceOfDiscountProducts);
		List<WebElement> realPriceOfDiscountProducts = driver.findElements(this.realpriceOfDiscountProducts);
		DecimalFormat f = new DecimalFormat("##.00");

		for (int i = 0; i < productsWithDiscount.size(); i++) {
			int discountPercentage = Integer.parseInt(DiscountPercentage.get(i).getText().replace("%", ""));
			float regularPrice = Float
					.parseFloat(regularPriceOfDiscountProducts.get(i).getText().substring(0, 5).replace(",", "."));
			float realPrice = Float
					.parseFloat(realPriceOfDiscountProducts.get(i).getText().substring(0, 5).replace(",", "."));
			float discountAmount = Float
					.parseFloat(f.format(((regularPrice * (Math.abs(discountPercentage))) / 100)).replace(",", "."));
			if ((regularPrice - discountAmount) != realPrice) {
				Log.info("Product has wrong discount");
				return false;
			}
			Log.info("Product №" + (i + 1) + " really has " + DiscountPercentage.get(i).getText() + "(" + discountAmount
					+ ")" + " discount.");
		}
		return true;
	}
}
