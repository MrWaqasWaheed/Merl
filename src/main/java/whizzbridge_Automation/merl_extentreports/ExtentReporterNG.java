package whizzbridge_Automation.merl_extentreports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {
	static ExtentReports extent;
	


	public static ExtentReports getReportObject() {
		String path = System.getProperty("user.dir") + "\\reports\\index.html";

		// Create an instance of ExtentSparkReporter helper class for extent main class
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Merl Automation Results");
		reporter.config().setDocumentTitle("Test Results");
	
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Waqas Waheed");
		
		return extent;
	}
	 public static ExtentTest createTest(String testName, String testDescription) {
	        ExtentTest test = extent.createTest(testName, testDescription);
	        return test;
	    }

	    public static void logInfo(ExtentTest test, String message) {
	        test.info(message);
	    }

	    public static void logPass(ExtentTest test, String message) {
	        test.pass(message);
	    }

	    public static void logFail(ExtentTest test, String message) {
	        test.fail(message);
	    }
		public static void flushReports() {
			if (extent != null) {
	            extent.flush();
			
		}
	
		}}
