package com.study.zoho.PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.study.zoho.utilities.DriverManager;

public class ZohoHomepage extends BasePage {

	@FindBy(how = How.XPATH, using = "//*[text()='Login']")
	public WebElement btn_login;

	public ZohoHomepage open() {
		DriverManager.getDriver().get("https://zoho.com/");
		return (ZohoHomepage) openPage(ZohoHomepage.class);
	}

	public ZohoLoginPage gotoLogin() {
		click(btn_login, "Button Login");
		return new ZohoLoginPage().openLoginPage();
	}

	@Override
	protected ExpectedCondition getPageLoadCondition() {
		// TODO Auto-generated method stub
		return ExpectedConditions.visibilityOf(btn_login);
	}

}
