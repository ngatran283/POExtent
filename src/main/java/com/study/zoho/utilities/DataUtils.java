package com.study.zoho.utilities;

import java.util.Hashtable;

import org.testng.SkipException;

public class DataUtils {
	public static boolean isTestExecutable(String testCaseName, ExcelReader excel) {
		String sheetName = Constants.TEST_CASE_SHEET;
		for (int row = 2; row <= excel.getRowCount(sheetName); row++) {
			if (excel.getCellData(sheetName, Constants.TEST_CASE_COL, row).equalsIgnoreCase(testCaseName)) {
				if (excel.getCellData(sheetName, Constants.TEST_CASE_RUNMODE, row)
						.equalsIgnoreCase(Constants.TEST_RUNMODE_YES)) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	public static boolean isSuiteExecutable(String suiteName) {
		ExcelReader excel = new ExcelReader(Constants.SUITES_XL_PATH);
		String sheetName = Constants.TEST_SUITE_SHEET;
		for (int row = 2; row <= excel.getRowCount(sheetName); row++) {
			if (excel.getCellData(sheetName, Constants.TEST_SUITE_COL, row).equalsIgnoreCase(suiteName)) {
				if (excel.getCellData(sheetName, Constants.TEST_SUITE_RUNMODE, row)
						.equalsIgnoreCase(Constants.RUNMODE_YES)) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	public static void checkExecution(String testSuiteName, String testCaseName, String dataRunmode,
			ExcelReader excel) {
		System.out.println(isSuiteExecutable(testSuiteName));
		System.out.println(isTestExecutable(testCaseName, excel));
		// System.out.println(dataRunmode.equalsIgnoreCase(Constants.DATA_RUNMODE_NO));

		if (!isSuiteExecutable(testSuiteName)) {
			throw new SkipException(
					"Skipping the test " + testCaseName + " as the Runmode of test suite " + testSuiteName + " is No");
		}
		if (!isTestExecutable(testCaseName, excel)) {
			throw new SkipException(
					"Skipping the test " + testCaseName + " as the Runmode of test case " + testCaseName + " is No");
		}
		if (!dataRunmode.equalsIgnoreCase(Constants.DATA_RUNMODE_YES)) {
			throw new SkipException("Skipping the test " + testCaseName + "as the Runmode of test data is No");
		}
	}

	public static Object[][] getData(String testCase, ExcelReader excel) {
		String sheetName = Constants.DATA_COL;
		String testcase = testCase;
		// Test case start from
		int testCaseRowNum = 1;
		while (!excel.getCellData(sheetName, 0, testCaseRowNum).equalsIgnoreCase(testcase)) {
			testCaseRowNum++;
		}

		// total cols and rows and test data start from
		int colsStartRowNum = testCaseRowNum + 1;
		int dataStartRowNum = colsStartRowNum + 1;
		// total cols in test are
		int cols = 0;
		while (!excel.getCellData(sheetName, cols, dataStartRowNum).trim().equals("")) {
			cols++;
		}

		// total rows in test are
		int rows = 0;
		while (!excel.getCellData(sheetName, 0, dataStartRowNum + rows).trim().equals("")) {
			rows++;
		}

		Object[][] data = new Object[rows][1];
		int i = 0;
		for (int row = dataStartRowNum; row < dataStartRowNum + rows; row++) {
			Hashtable<String, String> table = new Hashtable<String, String>();
			for (int col = 0; col < cols; col++) {
				// System.out.println( excel.getCellData(sheetName, col, row));
				String testdata = excel.getCellData(sheetName, col, row);
				String colName = excel.getCellData(sheetName, col, colsStartRowNum);
				table.put(colName, testdata);
			}
			data[i][0] = table;
			i++;
		}
		return data;

	}
}
