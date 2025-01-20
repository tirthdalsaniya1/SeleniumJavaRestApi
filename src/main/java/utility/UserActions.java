package utility;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import testbase.BasePage;

public class UserActions extends BasePage {

	private static Logger logger = LogManager.getLogger(UserActions.class.getName());

	private static final int EXPLICIT_WAIT_TIMEOUT = 60;

	WebDriver driver = getDriver();

	public void clickButton(final By by) {
		// set focus on the object first
		findObject(by).click();
		logger.info("Button: {} clicked", by);
	}

	public void clickLink(final By by) {

		waitForElementClickable(driver, by);
		findObject(by).click();

		logger.info("Link: ' {}' clicked", by);

	}

	/**
	 * Waits for a given element to be clickable
	 * 
	 * @param driver  WebDriver instance
	 * @param locator By to locate element to wait for
	 */
	public void waitForElementClickable(WebDriver driver, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_WAIT_TIMEOUT);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public WebElement findElement(final By by) {
		final int pollingFrequency = 500;
		try {
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
					.pollingEvery(Duration.ofMillis(pollingFrequency));
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			return wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(by)));

		} catch (Exception e) {
			logger.error("Failed to locate the element ", e);

			return null;
		}

	}

	/**
	 * Finds Object on page with wait condition to make method robust
	 * 
	 * @param by Locator to be searched
	 * @return Webelement from the page
	 */
	public WebElement findObject(final By by) {
		final int pollingFrequency = 500;

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).pollingEvery(Duration.ofMillis(pollingFrequency))
				.ignoring(NoSuchElementException.class);

		wait.until(ExpectedConditions.presenceOfElementLocated(by));

		return wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(by)));

	}

	public String getText(final By by) {
		// set focus on the object first
		String text = driver.findElement(by).getText();
		logger.info("Text found at webelement {} ", text);
		return text;
	}

	/**
	 * Waits for a given element to be visible
	 * 
	 * @param driver  WebDriver instance
	 * @param locator By of the element to wait for
	 */
	public void waitForElementVisible(WebDriver driver, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_WAIT_TIMEOUT);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void waitForPageToLoad() {

		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		try {

			WebDriverWait wait = new WebDriverWait(driver, 45);
			wait.until(expectation);
			logger.info("Page loaded sucessfully ......");
		} catch (Exception error) {
			Assert.fail("Timeout waiting for Page Load Request to complete. {}", error);

		}
	}

}