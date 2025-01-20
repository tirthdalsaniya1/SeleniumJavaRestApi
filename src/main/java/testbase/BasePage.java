package testbase;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasePage {

	private static Logger logger = LogManager.getLogger(BasePage.class.getName());

	protected static String environment;
	protected static String browser;
	protected static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();

	/**
	 * @return the environment
	 */
	public static String getEnvironment() {
		return environment;
	}

	/**
	 * @param environment the environment to set
	 */
	public static void setEnvironment(String environment) {
		BasePage.environment = environment;
	}

	protected String url;

	private void deckout(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
	}

	public WebDriver getDriver() {
		// Get driver from ThreadLocalMap
		return driver.get();
	}

	public void navigateToUrl(String url) {
		WebDriver driver1 = getDriver();
		driver1.navigate().to(url);
	}

	private void openBrowser(String browser) {
		logger.info("Execution browser - {}", browser);

		switch (browser) {

		case "chromelocal":
			WebDriverManager.chromedriver().setup();
			driver.set(new ChromeDriver());
			logger.info("Initialized chrome browser driver");

			break;
		case "firefoxlocal":
			WebDriverManager.firefoxdriver().setup();
			driver.set(new FirefoxDriver());
			logger.info("Initialized firefox browser driver");

			break;

		default:
			WebDriverManager.chromedriver().setup();
			driver.set(new ChromeDriver());
			logger.info("Initialized default browser driver - chrome driver");

		}

	}

	/**
	 * tear down the WebDriver after the suite is completed
	 */
	@AfterMethod(alwaysRun = true, description = "tearing down test setup")
	public void tearDown() {
		WebDriver driver1 = getDriver();
		if (driver1 != null) {
			driver1.quit();
		}
	}

	@AfterClass
	void terminate() {
		// Remove the ThreadLocalMap element
		driver.remove();
	}

	/**
	 * reads config and setup browser for execution
	 */
	@Parameters({ "browser", "environment" })
	@BeforeMethod(alwaysRun = true, description = "Setting up the Selenium enviroment.")
	public void testSetup(@Optional("chromelocal") String browser, @Optional("testenv1") String environment) {
		BasePage.environment = environment;
		BasePage.browser = browser;
		logger.info("Execution environment - {}", environment);
		openBrowser(browser);
		deckout(getDriver());

	}

}
