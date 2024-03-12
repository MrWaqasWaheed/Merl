package whizzbridge_Automation.merl_extentreports;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class merl_Genericmethods {

	public WebDriver driver;
	public WebDriverWait wait;
	public Actions actions;
	ExtentReports extent;

	public merl_Genericmethods() {
		driver = new ChromeDriver();
		actions = new Actions(driver);
	}
public void HomePage(String url) {
		
		Reporter.log("Application Launched");
		System.out.println("Application is Launching! 'Good Luck' ");
		long start = System.currentTimeMillis();
		driver.get(url);
		long finish = System.currentTimeMillis();
		long totalTime = finish - start;
		Reporter.log("Total time for application to launch is " + totalTime);
		System.out.println("Application Launched in MilliSeconds =   " + totalTime);
		

	}

	
	public void ExtentReport_config() {
		String path = System.getProperty("user.dir") + "\\reports\\index.html";

		// Create an instance of ExtentSparkReporter helper class for extent main class
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Merl Automation Results");
		reporter.config().setDocumentTitle("Test Results");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Waqas Waheed");

		ExtentTest test = extent.createTest("Initial Demo");
		// test.addScreenCaptureFromBase64String(web);
		test.fail("Intentionally failing this test");
		extent.flush();

	}
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";

	}
	public void setUp() {
		// Explicitwaitgloballydriver
		wait = new WebDriverWait(driver, Duration.ofMillis(6000)); // Initialize WebDriverWait here
		// Implicitwaitglobally
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.manage().window().maximize();
		Reporter.log("Window is Maximized");
		System.out.println("Window is Maximized");

		driver.manage().deleteAllCookies();
		Reporter.log("All Cookies Deleted");
		System.out.println("All Cookies Deleted");
	}

	


	public void waitForElementToAppear(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(500));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitForElementsToAppear(List<WebElement> elements) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElements(elements));
	}

	public void clickWebElement(String locator) {
		WebElement clickElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
		clickElement.click();
	}

	public void get_Table_RowsCount_TextofEachRow(String tableLocator_ClassName) {
		WebElement tableBody = driver.findElement(By.className(tableLocator_ClassName));
		waitForElementToAppear(tableBody);
		// Find all rows in the table body
		List<WebElement> rows = tableBody.findElements(By.tagName("tr"));
		// Print the number of rows
		int rowCount = rows.size();
		System.out.println("Number of rows in the table: " + rowCount);
		for (WebElement row : rows) {
			System.out.println("Text from row: " + row.getText());
		}
	}

	public void displayTextFromTableCell(String tableBodyClassName, int rowIndex, int columnIndex) {
		// Find the table body based on class name
		WebElement tableBody = driver.findElement(By.className(tableBodyClassName));

		// Find all rows in the table body
		List<WebElement> rows = tableBody.findElements(By.tagName("tr"));

		// Check if the specified row index is within the range
		if (rowIndex >= 0 && rowIndex < rows.size()) {
			// Get the specified row
			WebElement row = rows.get(rowIndex);

			// Find all columns (td elements) in the row
			List<WebElement> columns = row.findElements(By.tagName("td"));

			// Check if the specified column index is within the range
			if (columnIndex >= 0 && columnIndex < columns.size()) {
				// Get the specified column and print its text
				WebElement column = columns.get(columnIndex);
				System.out.println("Text from specified cell: " + column.getText());
			} else {
				System.out.println("Invalid columnIndex");
			}
		} else {
			System.out.println("Invalid rowIndex");
		}
	}

	public void scrollDown_viaActions(int pixels) {
		actions.moveByOffset(0, pixels);
	}

	public void scrollDownToBottom_viaActions() {
		actions.sendKeys(Keys.PAGE_DOWN).build().perform();
	}

	public void scrollDownToElement_viaActions(String elementId) {
		WebElement element = driver.findElement(By.id(elementId));
		actions.moveToElement(element).perform();
	}

	public void scrollDown(int pixels) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, " + pixels + ");");
	}

	public void scrollDownToBottom() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}

	public void scrollDownToElement(String elementId) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.getElementById('" + elementId + "').scrollIntoView();");
	}

	public void verify_Text(String locator, String Text) {
		WebElement clickElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
		String actual_Text = clickElement.getText();
		if (actual_Text.equalsIgnoreCase(Text)) {
			System.out.println("The Text of Ui '" + actual_Text + "' matches the designed text '" + Text + "'");

		} else {
			System.out.println("The Text of Ui '" + actual_Text + "' didn't matches the designed text '" + Text + "'");

		}
	}

	public void send_Keys_Method(String locator, String value) {

		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
		element.sendKeys(value);
	}

	public void status(String toggleButtonLocator) {

		WebElement toggleButton = driver.findElement(By.xpath(toggleButtonLocator));

		boolean isToggleButtonSelected = toggleButton.isSelected();
		System.out.println("Is Toggle Button Selected? " + isToggleButtonSelected);

	}

	public void inputName(String inputLocator, String invalidText, String Validinput, String validationLocator) {

		WebElement InputElement = driver.findElement(By.xpath(inputLocator));
		InputElement.sendKeys(invalidText);

		while (!InputElement.getAttribute("value").equals("")) {
			InputElement.sendKeys(Keys.BACK_SPACE);
		}

		WebElement validationMessage = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(validationLocator)));

		Assert.assertTrue(validationMessage.isDisplayed(), "Validation message is displayed");

		InputElement.sendKeys(Validinput);
	}

	public void sortandSearchTest(String ValuetoBeFind) throws InterruptedException {
		Thread.sleep(2000);
		// capture all webelements into list
		List<WebElement> elementsList = driver.findElements(By.xpath("//tr/td[1]"));
		// capture text of all webelements into new(original) list
		List<String> originalList = elementsList.stream().map(s -> s.getText()).collect(Collectors.toList());
		System.out.println(originalList);
		Thread.sleep(2000);
		// sort on the original list of step 3 -> sorted list
		List<String> sortedList = originalList.stream().sorted().collect(Collectors.toList());
		System.out.println(sortedList);
		// Assertion
		Assert.assertNotEquals(originalList, sortedList, "Arrays should not be equal");
		// scan the column with getText ->Value->print the Value
		List<String> SearchedValue;
		do

		{

			List<WebElement> rows = driver.findElements(By.xpath("//tr/td[1]"));

			SearchedValue = rows.stream().filter(s -> s.getText().contains(ValuetoBeFind))

					.map(s -> getFirstSiblingValue(s)).collect(Collectors.toList());

			SearchedValue.forEach(a -> System.out.println(a));

			if (SearchedValue.size() < 1)

			{

				driver.findElement(By.cssSelector("[aria-label='Next']")).click();

			}

		} while (SearchedValue.size() < 1);

	}

	public String getFirstSiblingValue(WebElement s) {

		String desiredValue = s.findElement(By.xpath("following-sibling::td[1]")).getText();

		return desiredValue;

	}
//Search_Using_Search_SVG

	public void searchFilter(String svgLocator, String searchBarLocator, String searchValue)
			throws InterruptedException {
		WebElement clickFilterSvg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(svgLocator)));
		clickFilterSvg.click();
		System.out.println("Search SVG Clicked");

		WebElement clickSearchBar = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(searchBarLocator)));
//	    
		// Use Actions class to move to the search bar before clicking
		new Actions(driver).moveToElement(clickSearchBar).build().perform();

		System.out.println("Search Bar Clicked");
		Thread.sleep(3000);
		WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchBarLocator)));
		searchBar.click();
		Thread.sleep(3000);
		searchBar.sendKeys(searchValue);

		List<WebElement> columnList = driver.findElements(By.xpath("//tr/td[1]"));

		// Filtered results
		List<Object> filteredList = columnList.stream().filter(value -> value.getText().contains(searchValue))
				.collect(Collectors.toList());

		System.out.println(filteredList);
		Reporter.log("Filtered List" + filteredList);

		// Assert the number of filtered results
		Assert.assertEquals(columnList.size(), filteredList.size(),
				"Number of filtered results doesn't match expected.");
	}

	public void selectOptionusing_Bylocator_method(String text) throws InterruptedException {
		By optionText = By.xpath("//*[text()='" + text + "']");
		driver.findElement(optionText).click();

	}

	public void selectOption(String text) {
		WebElement optionText = driver.findElement(By.xpath("//*[text()='\"+text+\"']"));
		waitForElementToAppear(optionText);
		optionText.click();
	}

	//// *[@class='ant-select-selection-search-input']
	public void clickOndrop() {
		WebElement myElement = driver.findElement(By.xpath("//*[@class='ant-select-selection-search-input']"));
		waitForElementToAppear(myElement);
		myElement.click();
	}

//DropDown
	public void selectOptionFromDropdown(int node_Element_Index, String searchWord) throws InterruptedException {
		// Wait for the dropdown to be clickable
		List<WebElement> elements = driver.findElements(By.cssSelector("div.ant-select-selector"));

		if (elements.size() >= 0) {
			WebElement targetElement = elements.get(node_Element_Index);
			waitForElementToAppear(targetElement);
			targetElement.click();
		} else {
			System.out.println("element not found.");
		}
		Thread.sleep(3000);
		WebElement dropdownOptions = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("rc-virtual-list-holder-inner")));

		// Scroll through the dropdown options until the desired option is found
		scrollToOption(dropdownOptions, searchWord);

		// Get all the available options
		List<WebElement> allOptions = dropdownOptions
				.findElements(By.xpath(".//div[contains(@class, 'rc-virtual-list')]/div"));

		System.out.println("Available Options:");

		boolean found = false; // A flag to track if the search word is found

		for (WebElement option : allOptions) {
			String text = option.getText();
			System.out.println(text); // Output the option to the console

			if (text.contains(searchWord)) {
				System.out.println("Found: " + text);

				option.click();

				found = true; // Set the flag to true

				break;
			}
		}

		if (!found) {
			System.out.println("Search word '" + searchWord + "' not found in the dropdown options.");
		}
	}

	public void scrollToOption(WebElement dropdownOptions, String searchWord) {

		// Move to the last option to initiate scrolling
		actions.moveToElement(
				dropdownOptions.findElement(By.xpath(".//div[contains(@class, 'rc-virtual-list')]/div[last()]")))
				.perform();

		// Keep scrolling until the desired option is visible
		while (!isOptionVisible(dropdownOptions, searchWord)) {
			actions.sendKeys(org.openqa.selenium.Keys.DOWN).perform();
			try {
				Thread.sleep(500); // Add a small delay to allow the content to scroll
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isOptionVisible(WebElement dropdownOptions, String searchWord) {
		List<WebElement> allOptions = dropdownOptions
				.findElements(By.xpath(".//div[contains(@class, 'rc-virtual-list')]/div"));

		for (WebElement option : allOptions) {
			String text = option.getText();
			if (text.contains(searchWord)) {
				return true;
			}
		}
		return false;
	}

	

}
