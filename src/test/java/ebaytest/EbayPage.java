package ebaytest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import config.ConfigReader;
import testbase.BasePage;
import utility.UserActions;

public class EbayPage extends BasePage {
	static Logger logger = LogManager.getLogger(EbayPage.class.getName());
	WebDriver driver = getDriver();
	UserActions action = new UserActions();

	public By txtAreaSearchBox = By.xpath("//input[@placeholder='Search for anything']");
	public By btnSearchButton = By.xpath("//input[@type='submit']");
	public By linkFirstBook = By.xpath("(//span[@role='heading'])[3]");
	public By btnAddToCart = By.xpath("//span[text()='Add to cart']");
	public By imgAddToCartUpdated = By.xpath("//i[@id='gh-cart-n']");

	public void launchEbayWebSite() {

		String url = ConfigReader.getConfig("environment." + BasePage.environment + ".URL");

		action.navigateToUrl(url);
		action.waitForPageToLoad();
	}

}
