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
	public By btnSearchButton = By.xpath("//span[text()='Search']");
	public By linkFirstBook = By.xpath("(//span[@role='heading'])[3]");
	public By btnAddToCart = By.xpath("//span[text()='Add to cart']");
	public By labelAddToCartUpdated = By.xpath("//span[@aria-label='Your shopping cart contains 1 items']");

	public void launchEbayWebSite() {
		// Fetch Ebay Website url from config.xml file
		String url = ConfigReader.getConfig("environment." + BasePage.environment + ".URL");

		// Hit EBAY website URL and launch it
		navigateToUrl(url);
		
		// Wait for page to load after hitting ebay url
		action.waitForPageToLoad();
	}

}
