package com.owm.testcases;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.owm.base.TestBase;
import com.owm.pages.HomePage;
import com.owm.pages.WeatherForecastPage;
import com.owm.util.CustomListener;

/*This will listen the test cases that will pass/fail/skip
and will send result to console/extent report
*/
@Listeners(CustomListener.class)

// This test will check temperature is converting for centigrate to farenheit as expected
public class CentigradeToFarenheitTest extends TestBase{
	
	HomePage homePage; // HomePage object declaration
	WeatherForecastPage weatherForecastPage; // WeatherForecastPage object declaration
	
	public CentigradeToFarenheitTest() {
		super(); // This will help accessing properties file(config.properties)
	}
	
	@BeforeMethod
	public void beforeTestTasks() {
		initialization(); // Openweathermap site will launch
		homePage = new HomePage(); // HomePage object initialized
		weatherForecastPage = new WeatherForecastPage(); // WeatherForecastPage object initialized
		log = Logger.getLogger(CentigradeToFarenheitTest.class);
		
	}
	
	// This method will search the weather of a valid city and verify the temperature is converting to farenheit from centigrade
	@Test(priority=1)
	public void checkFarenheitTest() throws InterruptedException {
		
		log.info("Centigrade to Farenheit conversion test started");
		
		homePage.searchCityWeather(prop.getProperty("valid_city"));
		
		Thread.sleep(2000);
		
		//String cityName = firstEtoEPage.validateCity();
				String weatherReturned = homePage.validateCity();
				System.out.println(weatherReturned);
				
				if(weatherReturned.contains(prop.getProperty("valid_city")) && weatherReturned.contains("temperature from") && weatherReturned.contains("Geo coords")) {
					System.out.println("Weather of "+prop.getProperty("valid_city")+" is displayed as expected");
					Assert.assertTrue(true);
					log.info("City weather report is displayed successfully");
					weatherForecastPage.clickOnSearchedCity();
					log.info("Searched city clicked for weather forecast");
					//driver.findElement(By.xpath("//div[@id='forecast_list_ul']/table/tbody/tr/td/following-sibling::td/b/a")).click();
					Thread.sleep(2000);
					weatherForecastPage.clickOnTempUnitSlideBar();
					log.info("°C to °F slide bar clicked");
					//driver.findElement(By.xpath("//span[@id='imperial' and contains(text(), '°F')]")).click();
					Thread.sleep(4000);
				}
					else {
						System.out.println("Weather of "+prop.getProperty("valid_city")+" is NOT displayed as expected");
						log.info("°C to °F conversion failed");
						Assert.assertTrue(false);
					}
				
			String tempUnit = weatherForecastPage.checkfarenheit();
			System.out.println(tempUnit);
			
		if(tempUnit.contains("°F")) {
				System.out.println("Tempature Unit changed to Farenheit from Centigrade");
				Assert.assertTrue(true);
				log.info("Temperature unit changed from °C to °F successfully");
				System.out.println("Centigrade to Farenheit change test pass SUCCESSFULLY");
			}
			else {
				System.out.println("Problem in changing from Centigrade to Farenheit - Please check");
				log.info("Issue in changing temperature unit from °C to °F");
				Assert.assertFalse(true);
			}
				
				
	}
	
	@AfterMethod
	public void tearDown() {
		extent.flush(); // Will create the extent report
		driver.quit(); // Quit driver at the end of test
	}

}
