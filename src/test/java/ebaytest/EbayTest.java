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
	public void verifyThatCartIsUpdatedByTheFirstBookTest() throws InterruptedException {
		EbayPage ep = new EbayPage();
		UserActions action = new UserActions();
		ep.launchEbayWebSite();
		WebDriver driver = getDriver();
		// Now enter book in the search box and click on enter
		action.waitForElementVisible(driver, ep.txtAreaSearchBox);
		action.findElement(ep.txtAreaSearchBox).sendKeys("book");
		action.findElement(ep.btnSearchButton).click();
		action.waitForPageToLoad();
		action.waitForElementVisible(driver, ep.linkFirstBook);
		action.clickLink(ep.linkFirstBook);
		action.waitForPageToLoad();

		String parentWindowId = driver.getWindowHandle();

		Set<String> windowHandles = driver.getWindowHandles();

		Iterator<String> it = windowHandles.iterator();

		while (it.hasNext()) {

			String windowHandle = it.next();

			if (!windowHandle.equals(parentWindowId)) {
				driver.switchTo().window(windowHandle);
				System.out.println("new window title is " + driver.getTitle());
			}

		}

		action.waitForElementVisible(driver, ep.btnAddToCart);
		action.clickButton(ep.btnAddToCart);

		action.waitForElementVisible(driver, ep.imgAddToCartUpdated);
		String cartItems = action.getText(ep.imgAddToCartUpdated);
		logger.info("Total cart items " + cartItems);

		assertEquals(cartItems, "1", "Issue with cart items assertion");

	}

}
