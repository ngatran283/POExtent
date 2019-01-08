package com.study.zoho.utilities;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

public class DataProviders {

	@DataProvider(name = "master", parallel = true)
	public Object[][] getDataSuite1(Method m) {
		System.out.println(m.getName());
		ExcelReader excel = new ExcelReader(Constants.SUITE_LOGIN_PATH);
		Object[][] data = DataUtils.getData(m.getName(), excel);
		return data;
	}
}
