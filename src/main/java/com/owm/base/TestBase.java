package com.owm.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.owm.util.TestUtil;

/*
 This is the base class of the project and all other class file will extend this class
  */
public class TestBase {
	
	//Driver object declaration which will be used throughout the project
	public static WebDriver driver;
	//Object declaration for properties file(config.properties)
	public static Properties prop;
	
	//Object declarations for extent reports
		public static ExtentHtmlReporter htmlReporter;
		public static ExtentReports extent;
		public static ExtentTest test;
	
	//Logger object declaration
		public static Logger log;
	
//Initializing properties file inside the class constructor	which will be called(super();) from individual test file to access config.properties
public TestBase() {
		
		try {
		prop = new Properties();	
		FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\com\\owm\\config\\config.properties");
				prop.load(ip);
			} catch(FileNotFoundException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}
	}

//Browser factory method to launch browser as per the config.properties file
public static void initialization() {
	
	String browserName = prop.getProperty("browser");
	
	if(browserName.equals("Chrome")) {
		//bellow line will remove all chromedriver related logs from console
		System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
		
		System.setProperty("webdriver.chrome.driver", "C:\\eclipse_workspace\\Common Browser Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		
	} else if(browserName.equals("Mozilla")){
		System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "null");// not to get any unwanted log for geco driver
		FirefoxOptions options = new FirefoxOptions();
		options.setBinary("C:\\Program Files\\Mozilla Firefox Latest\\firefox.exe");
		//System.setProperty("webdriver.gecko.driver", "C:\\eclipse_workspace\\Common Browser Drivers\\geckodriver.exe");
		driver = new FirefoxDriver(options);
		
	}else if(browserName.equals("Edge")){
		System.setProperty(EdgeDriverService.EDGE_DRIVER_LOG_PROPERTY, "null");// not to get unwanted logs in console
		System.setProperty("webdriver.edge.driver", "C:\\eclipse_workspace\\Common Browser Drivers\\MicrosoftWebDriver.exe");
		driver = new EdgeDriver();
		
		}else if(browserName.equals("IE")){
			System.setProperty(InternetExplorerDriverService.IE_DRIVER_SILENT_PROPERTY, "true");//not to get unwanted logs in console
			System.setProperty("webdriver.ie.driver", "C:\\eclipse_workspace\\Common Browser Drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			}
	
	//driver.manage().deleteAllCookies();
	driver.manage().window().maximize();
	driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
	driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
	
	driver.get(prop.getProperty("url"));
	}
	
	//This screenshot method will be used to take screenshot of failed tests
	public void takingScreenshots(String methodName) throws IOException{
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"//screenshots//"+methodName+"_"+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		test.log(Status.FAIL, "Screenshot->"+test.addScreenCaptureFromPath(System.getProperty("user.dir")+"//screenshots//"+methodName+"_"+".png"));
	}

	//Explicit function to send keys
	public static void sendKeys(WebDriver driver, WebElement element,
			int timeout, String value) {
		new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(element));
		element.sendKeys(value);
	}

	//Explicit function to click on object
	public static void click(WebDriver driver, WebElement element, int timeout) {
		new WebDriverWait(driver, timeout).until(ExpectedConditions.elementToBeClickable(element));
		element.click();

	}
	
	//Initializing extent reports before any suite run so that individual tests can access these
	@BeforeSuite
	public void setup() {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"\\extent-reports\\extent.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		htmlReporter.loadXMLConfig(System.getProperty("user.dir")+"\\config.xml");
	}
	
	//This method will show the test case name at the left pane of test report
	@BeforeMethod
	public void getMethodNameInReport(Method methodName) {
		test = extent.createTest(methodName.getName());
		test.log(Status.INFO, methodName.getName()+" started");
	}
	
}
