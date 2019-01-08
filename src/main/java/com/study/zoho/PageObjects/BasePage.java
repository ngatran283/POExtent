package com.study.zoho.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.study.zoho.ExtentListeners.ExtentTestNGITestListener;
import com.study.zoho.utilities.Constants;
import com.study.zoho.utilities.DriverManager;

public abstract class BasePage<T> {
	protected WebDriver driver;

	public T openPage(Class<T> pageClass) {
		T page = null;
		driver = DriverManager.getDriver();
		AjaxElementLocatorFactory ajaxElementLocatorFactory = new AjaxElementLocatorFactory(this.driver, 10);
		page = PageFactory.initElements(driver, pageClass);
		PageFactory.initElements(ajaxElementLocatorFactory, page);
		ExpectedCondition expectedCondition = ((BasePage) page).getPageLoadCondition();
		waitPageLoad(expectedCondition);
		return page;
	}

	private void waitPageLoad(ExpectedCondition pageLoadCondition) {
		WebDriverWait wait = new WebDriverWait(this.driver, Constants.TIMEOUT);
		wait.until(pageLoadCondition);
	}

	protected abstract ExpectedCondition getPageLoadCondition();

	public static void click(WebElement element, String elementName) {
		element.click();
		ExtentTestNGITestListener.logInfo("Click into [" + elementName + " ]");
	}

	public static void type(WebElement element, String text, String elementName) {
		element.sendKeys(text);
		ExtentTestNGITestListener.logInfo("Type: '" + text + "' into [" + elementName + " ]");
	}
}
