package ebaytest;

import static org.testng.Assert.assertEquals;

import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import testbase.BasePage;
import utility.UserActions;

public class EbayTest extends BasePage {
	static Logger logger = LogManager.getLogger(EbayTest.class.getName());

	@Test
	public void verifyThatCartIsUpdatedByTheFirstBookTest() {
		/*
		 * In this test we are trying to verify that after launching ebay website we are
		 * trying to search book and then from the search result we are adding first
		 * book in the cart. After that we are checking that cart is updated with 1
		 * product
		 * 
		 */
		EbayPage ep = new EbayPage();
		UserActions action = new UserActions();
		WebDriver driver = getDriver();

		// Launch Ebay website
		ep.launchEbayWebSite();

		/*
		 * Now enter "book" text in the search box and click on enter so that it will
		 * give search results
		 */
		action.waitForElementVisible(driver, ep.txtAreaSearchBox);
		action.findElement(ep.txtAreaSearchBox).sendKeys("book");
		action.findElement(ep.btnSearchButton).click();

		// After clicking on search button wait for page to load
		action.waitForPageToLoad();

		// Wait for first search result hyperlink and click on it
		action.waitForElementVisible(driver, ep.linkFirstBook);
		action.clickLink(ep.linkFirstBook);
		/*
		 * After clicking on first hyperlink from the search result, wait for new page
		 * to load
		 */
		action.waitForPageToLoad();
		/*
		 * Here it will open new tab and our product which we want to add in the cart is
		 * in the new tab so we need to switch into the new tab and then only we will
		 * able to add it into the cart
		 */
		String parentWindowId = driver.getWindowHandle();

		Set<String> windowHandles = driver.getWindowHandles();

		Iterator<String> it = windowHandles.iterator();

		while (it.hasNext()) {

			String windowHandle = it.next();

			if (!windowHandle.equals(parentWindowId)) {
				driver.switchTo().window(windowHandle);
				logger.info("new window title is " + driver.getTitle());
			}

		}
		// Wait for add to cart button and then click on it
		action.waitForElementVisible(driver, ep.btnAddToCart);
		action.clickButton(ep.btnAddToCart);

		/*
		 * Wait for updated cart to display and then verify the number of products
		 * inside it
		 */
		action.waitForElementVisible(driver, ep.labelAddToCartUpdated);
		String cartItems = action.getText(ep.labelAddToCartUpdated);
		logger.info("Total cart items " + cartItems);

		// Verify that total cart item is one
		assertEquals(cartItems, "1", "Issue with cart items assertion");

	}

}