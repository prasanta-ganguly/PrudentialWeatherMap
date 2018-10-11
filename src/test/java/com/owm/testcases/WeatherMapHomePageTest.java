package com.owm.testcases;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


import com.owm.base.TestBase;
import com.owm.pages.HomePage;
import com.owm.util.CustomListener;

/*This will listen the test cases that will pass/fail/skip
and will send result to console/extent report
*/
@Listeners(CustomListener.class)

public class WeatherMapHomePageTest extends TestBase {
	
	HomePage homePage; // HomePage object declaration
	
	public WeatherMapHomePageTest() {
		super(); // This will help accessing properties file(config.properties)
	}
	
	@BeforeMethod
	public void beforeTestTasks() {
		initialization(); // Openweathermap site will launch
		homePage = new HomePage(); // HomePage object initialized
		log = Logger.getLogger(WeatherMapHomePageTest.class); // Initializing logger object wrt class
		
	}
	
	//Validating home page title
	@Test(priority=1)
	public void homePageTitleTest() {
		
		log.info("Home page title test started");
		
		String title = homePage.validateHomePageTitle();
		System.out.println("Home page title - "+title);
		Assert.assertEquals("Сurrent weather and forecast - OpenWeatherMap", title);
		System.out.println("Home page title test passed SUCCESSFULLY");
		log.info("Home page title test passed SUCCESSFULLY");
	}
	
	//Validating weathermap logo
	@Test(priority=2)
	public void openWeatherMapLogoTest() {
		
		log.info("Home page logo test started");
		
		boolean flag = homePage.validateOpenWeatherMapImage();
		
		Assert.assertTrue(flag);
		System.out.println("Weather map logo test passed successfully");
		log.info("Weather map logo test passed successfully");
		
	}
	
	@AfterMethod
	public void tearDown() {
		extent.flush(); // Will create the extent report
		driver.quit(); // Quit driver at the end of test
	}

}
