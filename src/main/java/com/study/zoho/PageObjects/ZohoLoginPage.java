package com.study.zoho.PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ZohoLoginPage extends BasePage {

	@FindBy(how = How.ID, using = "lid")
	public WebElement email;

	@FindBy(how = How.ID, using = "pwd")
	public WebElement password;
	@FindBy(how = How.ID, using = "signin_submit")
	public WebElement btn_signIn;

	public ZohoLoginPage openLoginPage() {
		return (ZohoLoginPage) openPage(ZohoLoginPage.class);
	}

	public void doLogin(String s_username, String s_pwd) {
		type(email, s_username, "Email");
		type(password, s_pwd, "Password");
		click(btn_signIn, "Button SignIn");
	}

	@Override
	protected ExpectedCondition getPageLoadCondition() {
		// TODO Auto-generated method stub
		return ExpectedConditions.visibilityOf(email);
	}
}
