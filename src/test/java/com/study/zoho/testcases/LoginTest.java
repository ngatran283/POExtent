package com.study.zoho.testcases;

import java.util.Hashtable;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.study.rough.BaseTest;
import com.study.zoho.PageObjects.ZohoHomepage;
import com.study.zoho.PageObjects.ZohoLoginPage;
import com.study.zoho.utilities.Constants;
import com.study.zoho.utilities.DataProviders;
import com.study.zoho.utilities.DataUtils;
import com.study.zoho.utilities.ExcelReader;

public class LoginTest extends BaseTest {

	@Test(dataProviderClass = DataProviders.class, dataProvider = "master")
	public void loginTest(Hashtable<String, String> data) {
		ExcelReader excel = new ExcelReader(Constants.SUITE_LOGIN_PATH);
		DataUtils.checkExecution("master", "loginTest", data.get(Constants.DATA_RUNMODE_COL), excel);
		openBrowser(data.get("browser"));
		ZohoHomepage homepage = new ZohoHomepage().open();
		ZohoLoginPage loginPage = homepage.gotoLogin();
		loginPage.doLogin(data.get("username"), data.get("password"));
	}

	@AfterMethod
	public void quitBrowser() {
		quit();
	}
}
