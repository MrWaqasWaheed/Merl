package whizzbridge_Automation.merl_extentreports;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

public class merl_TestCases extends merl_Genericmethods {
	private ExtentTest extentTest;

	// Load properties from the config file
	private static Properties properties = loadProperties();

	private String title = properties.getProperty("title");
	private String username = properties.getProperty("username");
	private String password = properties.getProperty("password");
	private String userRole = properties.getProperty("userRole");
	private String baseUrl = properties.getProperty("baseUrl");

	private static Properties loadProperties() {
		Properties properties = new Properties();
		try {
			properties.load(merl_TestCases.class.getResourceAsStream("/resources/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	@BeforeTest
	public void ExtentreportSetup() {
		extent = ExtentReporterNG.getReportObject();
		long id = Thread.currentThread().getId();
		System.out.println("Before test-method. Thread id is: " + id);
		
	}

	@Test(priority = 1)
	public void Merl_BrowserSetup() {
		setUp();
	}

	@Test(priority = 2)
	public void Merl_TimetoLauchApp() throws InterruptedException {
		HomePage(baseUrl);
	}

	@Test(priority = 3)
	public void merl_VerifyUrl() throws InterruptedException {
		extentTest = ExtentReporterNG.createTest("merl_VerifyUrl", "Verify the URL of the application");

		String expectedURL = baseUrl;
		String actualURL = driver.getCurrentUrl();

		System.out.println("Actual URL: " + actualURL);
		System.out.println("Expected URL: " + expectedURL);

		ExtentReporterNG.logInfo(extentTest, "Actual URL: " + actualURL);
		ExtentReporterNG.logInfo(extentTest, "Expected URL: " + expectedURL);

		Assert.assertEquals(actualURL, expectedURL, "URLs do not match");

		ExtentReporterNG.logPass(extentTest, "URL is verified successfully");
	}

	@Test(priority = 4)
	public void merl_VerifyTitle() throws InterruptedException {
		extentTest = ExtentReporterNG.createTest("merl_VerifyTitle", "Verify the title of the application");

		String expectedTitle = title;
		String actualTitle = driver.getTitle();

		System.out.println("Actual Title: " + actualTitle);
		System.out.println("Expected Title: " + expectedTitle);

		ExtentReporterNG.logInfo(extentTest, "Actual Title: " + actualTitle);
		ExtentReporterNG.logInfo(extentTest, "Expected Title: " + expectedTitle);

		Assert.assertEquals(actualTitle, expectedTitle, "Titles do not match");

		ExtentReporterNG.logPass(extentTest, "Title is verified successfully");
	}

	@Test(priority = 5)
	public void verifyElementText() {
		extentTest = ExtentReporterNG.createTest("verifyElementText", "Verify the text of the span element");

		By spanLocator = By.xpath("//span[contains(text(),'Log in to your account')]");
		WebElement spanElement = driver.findElement(spanLocator);

		String expectedText = "Log in to your account";
		String actualText = spanElement.getText();

		System.out.println("Actual Text: " + actualText);
		System.out.println("Expected Text: " + expectedText);

		ExtentReporterNG.logInfo(extentTest, "Actual Text: " + actualText);
		ExtentReporterNG.logInfo(extentTest, "Expected Text: " + expectedText);

		Assert.assertEquals(actualText, expectedText, "Element text does not match");

		ExtentReporterNG.logPass(extentTest, "Element text is verified successfully");
	}

	@Test(priority = 6, groups = { "smoke" }, retryAnalyzer = Retry.class)
	public void merl_Login() throws InterruptedException {
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Locators//
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		By locatorEmail = By.id("user_email");
		WebElement elementemail = driver.findElement(locatorEmail);
		waitForElementToAppear(elementemail);

		By locatorPassword = By.id("user_password");
		WebElement elementPassword = driver.findElement(locatorPassword);
		waitForElementToAppear(elementPassword);

		By locatorSignIn = By.className("primary-button");
		WebElement elementSignIn = driver.findElement(locatorSignIn);
		waitForElementToAppear(elementSignIn);

		Thread.sleep(1000);
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Actions//
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		elementemail.sendKeys(username);
		Reporter.log("Login via Email is " + username);
		System.out.println("Login via Email is " + username);

		elementPassword.sendKeys(password);
		Reporter.log("and password is " + password);
		System.out.println("and password is " + password);

		elementSignIn.click();
		Reporter.log("Test Case: Login as " + userRole);
		System.out.println("Test Case: Login as " + userRole);
	}

	@AfterTest
	public void tearDown() {
		ExtentReporterNG.flushReports();
		if (driver != null) {
			driver.quit();
			long id = Thread.currentThread().getId();
			System.out.println("After test-method. Thread id is: " + id);
		}
	}
}