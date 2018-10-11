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

//Test for invalid city weather search
public class SearchWeatherForInvalidCityTest extends TestBase {
	
	HomePage homePage; // HomePage object declaration
	
	public SearchWeatherForInvalidCityTest() {
		super(); // This will help accessing properties file(config.properties)
	}
	
	@BeforeMethod
	public void beforeTestTasks() {
		initialization(); // Openweathermap site will launch
		homePage = new HomePage(); // HomePage object initialized
		log = Logger.getLogger(SearchWeatherForInvalidCityTest.class); // Initializing logger object wrt class
		
	}
	
	// This method will enter invalid city name and verify that no weather report id found
	@Test(priority=1)
	public void searchInvalidCityWeatherTest() throws InterruptedException {
		
		log.info("Invalid city weather search started");
		
		homePage.searchCityWeather(prop.getProperty("invalid_city"));
		
		Thread.sleep(2000);
		
		String cityName = homePage.invalidateCity();
		System.out.println(cityName);
		log.info("Invalid city name "+cityName);
		
		if(cityName.contains( "Not found")) {
			System.out.println("City name entered is invalid");
			Assert.assertTrue(true);
			log.info("Invalid city weather search passed successfully");
			
		}
			else {
				System.out.println("Issue in invalid city weather search - Please check...");
				log.info("Invalid city weather search failed");
				Assert.assertTrue(false);
			
			}
		}
	
	@AfterMethod
	public void tearDown() {
		extent.flush(); // Will create the extent report
		driver.quit(); // Quit driver at the end of test
	}

}
