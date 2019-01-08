package com.study.zoho.utilities;

public class Constants {

	// public static final String SUITE1_XL_PATH = System.getProperty("user.dir")
	// + "\\src\\test\\resources\\data\\Suite1.xlsx";
	// public static final String SUITE2_XL_PATH = System.getProperty("user.dir")
	// + "\\src\\test\\resources\\data\\Suite2.xlsx";
	public static final String SUITES_XL_PATH = System.getProperty("user.dir")
			+ "\\src\\test\\resources\\testdata\\Suite.xlsx";
	public static final String SUITE_LOGIN_PATH = System.getProperty("user.dir")
			+ "\\src\\test\\resources\\testdata\\master.xlsx";
	public static final String TEST_SUITE_SHEET = "TestSuite";
	public static final String TEST_SUITE_COL = "SuiteName";
	public static final String TEST_SUITE_RUNMODE = "Runmode";
	public static final String RUNMODE_YES = "Y";
	public static final String TEST_CASE_SHEET = "TestCases";
	public static final String TEST_CASE_COL = "TestCases";
	public static final String TEST_RUNMODE_YES = "Y";
	public static final String TEST_CASE_RUNMODE = "Runmode";
	public static final String DATA_COL = "TestData";
	public static final String DATA_RUNMODE_YES = "Y";
	public static final Object DATA_RUNMODE_COL = "Runmode";
	public static final int TIMEOUT = 30;
	public static final String GRID_PATH = "http://localhost:4444/wd/hub";
}
