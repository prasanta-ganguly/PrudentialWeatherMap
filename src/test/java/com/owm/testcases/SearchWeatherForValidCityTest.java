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

// Test for valid city weather search
public class SearchWeatherForValidCityTest extends TestBase {
	
	HomePage homePage; // HomePage object declaration
	
	public SearchWeatherForValidCityTest() {
		super(); // This will help accessing properties file(config.properties)
	}
	
	@BeforeMethod
	public void beforeTestTasks() {
		initialization(); // Openweathermap site will launch
		homePage = new HomePage(); // HomePage object initialized
		log = Logger.getLogger(SearchWeatherForValidCityTest.class); // Initializing logger object wrt class
		
	}
	
	//This method will enter a valid city name and verify that weather is displayed for the city
	@Test(priority=1)
	public void searchValidCityWeatherTest() throws InterruptedException {
		
		log.info("Valid city weather search started");
		
		homePage.searchCityWeather(prop.getProperty("valid_city"));
		
		Thread.sleep(2000);
		
		//String cityName = firstEtoEPage.validateCity();
		String weatherReturned = homePage.validateCity();
		System.out.println(weatherReturned);
		log.info("Weather of "+weatherReturned);
		
		if(weatherReturned.contains(prop.getProperty("valid_city")) && weatherReturned.contains("temperature from") && weatherReturned.contains("Geo coords")) {
			System.out.println("Weather of "+prop.getProperty("valid_city")+" is displayed as expected");
			Assert.assertTrue(true);
			log.info("Valid city weather search passed successfully");
		}
			else {
				System.out.println("Weather of "+prop.getProperty("valid_city")+" is NOT displayed as expected");
				log.info("Valid city weather search failed");
				Assert.assertTrue(false);
			}
	}
	
	@AfterMethod
	public void tearDown() {
		extent.flush(); // Will create the extent report
		driver.quit(); // Quit driver at the end of test
	}

}
