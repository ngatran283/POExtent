package com.study.zoho.ExtentListeners;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ExtentTestNGITestListener implements ITestListener {
	private static String extentFileName = "Extent_" + new Date().toString().replace(":", "_").replace(" ", "_")
			+ ".html";
	private static ExtentReports extent = ExtentManager
			.createInstance(System.getProperty("user.dir") + "\\report\\" + extentFileName);
	private static ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest>();

	public void onStart(ITestContext context) {
	}

	public void onFinish(ITestContext context) {
		if (extent != null) {
			extent.flush();
		}
	}

	public void onTestStart(ITestResult result) {
		ExtentTest test = extent
				.createTest(result.getTestClass().getName() + "    @TestCase: " + result.getMethod().getMethodName());

		testReport.set(test);
		;
	}

	public void onTestSuccess(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		String logText = "<b>" + "TESTCASE:- " + methodName.toUpperCase() + " PASSED" + "</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		testReport.get().pass(m);
	}

	public void onTestFailure(ITestResult result) {
		String exceptionMessage = Arrays.toString(result.getThrowable().getStackTrace());
		testReport.get().fail("<detail>" + "<summary>" + "<b>" + "<font color=" + "red>" + "</font>" + "</b>"
				+ "</summary>" + exceptionMessage.replaceAll(",", "<br>") + "</detail>" + "\n");

		try {
			testReport.get().fail("<b>" + "<font color=" + "red>" + "</font>" + "</b>",
					MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.captureScreenshot()).build());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		String logText = "<b>" + "TESTCASE:- " + methodName.toUpperCase() + " SKIPPED" + "</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
		testReport.get().skip(m);
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	public static void logInfo(String message) {
		testReport.get().info(message);
	}
}