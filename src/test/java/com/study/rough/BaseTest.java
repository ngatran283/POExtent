package com.study.rough;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeSuite;

import com.study.zoho.utilities.Constants;
import com.study.zoho.utilities.DriverFactory;
import com.study.zoho.utilities.DriverManager;

public class BaseTest {
	// private RemoteWebDriver driver;
	// public static ThreadLocal<RemoteWebDriver> dr = new
	// ThreadLocal<RemoteWebDriver>();
	private WebDriver driver;
	private Properties config = new Properties();
	private FileInputStream fis;
	public Logger log = Logger.getLogger(BaseTest.class);
	private boolean grid = false;
	private String defaultUsername;
	private String defaultPassword;

	public String getDefaultUsername() {
		return defaultUsername;
	}

	public void setDefaultUsername(String defaultUsername) {
		this.defaultUsername = defaultUsername;
	}

	public String getDefaultPassword() {
		return defaultPassword;
	}

	public void setDefaultPassword(String defaultPassword) {
		this.defaultPassword = defaultPassword;
	}

	@BeforeSuite
	public void setupFramework() {
		configureLogging();
		DriverFactory.setGridPath(Constants.GRID_PATH);
		DriverFactory.setConfigPropertyFile(
				System.getProperty("user.dir") + "//src//test//resources//properties//Config.properties");
		if (System.getProperty("os.name").contains("Windows")) {
			DriverFactory.setConfigPropertyFile(
					System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\config.properties");
			DriverFactory.setChromeDriverExePath(
					System.getProperty("user.dir") + "\\src\\test\\resources\\executable\\chromedriver.exe");
			DriverFactory.setGeckoDriverExePath(
					System.getProperty("user.dir") + "\\src\\test\\resources\\executable\\geckodriver.exe");
			DriverFactory.setIeDriverExePath("");
		} else if (System.getProperty("os.name").contains("mac")) {

		}

		try {
			fis = new FileInputStream(DriverFactory.getConfigPropertyFile());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			config.load(fis);
			log.info("Configuration file loaded!!!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (System.getenv("ExecuteType") != null && System.getenv("ExecuteType").equals("Grid")) {
			grid = true;
			runBatFile(System.getProperty("user.dir") + "\\src\\test\\resources\\grid\\hub.bat");
			runBatFile(System.getProperty("user.dir") + "\\src\\test\\resources\\grid\\node1.bat");
			runBatFile(System.getProperty("user.dir") + "\\src\\test\\resources\\grid\\node2.bat");
		}

	}

	public void configureLogging() {
		String log4jConfigFile = System.getProperty("user.dir")
				+ "//src//test//resources//properties//log4j.properties";
		PropertyConfigurator.configure(log4jConfigFile);
	}

	public void openBrowser(String browser) {
		DriverFactory.setRemote(grid);
		if (DriverFactory.isRemote()) {
			DesiredCapabilities cap = null;
			if (browser.equals("chrome")) {
				cap = DesiredCapabilities.chrome();
				cap.setBrowserName("chrome");
				cap.setPlatform(Platform.ANY);
			} else if (browser.equals("firefox")) {
				cap = DesiredCapabilities.firefox();
				cap.setBrowserName("firefox");
				cap.setPlatform(Platform.ANY);
			}
			driver = new RemoteWebDriver(cap);
		} else {

			if (browser.equals("chrome"))

			{
				System.out.println("Launching from TC_1: " + browser);
				System.setProperty("webdriver.chrome.driver", DriverFactory.getChromeDriverExePath());
				this.driver = new ChromeDriver();
			} else if (browser.equals("firefox")) {
				System.out.println("Launching from TC_1: " + browser);
				System.setProperty("webdriver.firefox.marionette", DriverFactory.getGeckoDriverExePath());
				this.driver = new FirefoxDriver();
			}
		}

		DriverManager.setWebDriver(this.driver);
		DriverManager.getDriver().manage().window().maximize();
		DriverManager.getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		setDefaultUsername(config.getProperty("defaultUsername"));
		setDefaultPassword(config.getProperty("defaultPassword"));
	}

	public void quit() {
		DriverManager.getDriver().quit();
	}

	public static void runBatFile(String filePath) {
		try {
			Process process = Runtime.getRuntime().exec("cmd /c start " + filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
