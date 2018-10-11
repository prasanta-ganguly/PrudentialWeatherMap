package com.owm.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.owm.base.TestBase;

	/*This HomePage.java contains necessary objects and functions required for following tests
	WeatherMapHomePageTest.java
	SearchWeatherForInvalidCityTest.java
	SearchWeatherForValidCityTest.java
	CentigradeToFarenheitTest.java
	 */
public class HomePage extends TestBase {
	
	//Page Factory - Object Repository:
	
	@FindBy(xpath="//img[@alt='openweathermap']") // OpenWeatherMap image at home page
	WebElement openWeatherMapLogo;
	
	@FindBy(xpath="//input[@name='q' and @placeholder='Your city name']") //name field of city at home page
	WebElement cityNameField;
	
	@FindBy(xpath="//button[@class='btn btn-orange']") //Weather search button at home page
	WebElement searchButton;
	
	@FindBy(xpath="//div[@class='alert alert-warning' and contains(text(), 'Not found')]") // not found alert box for invalid city weather search
	WebElement invalidCity;
	
	@FindBy(xpath="//div[@class='tab-pane active' and @id='forecast-list']") // div class when valid city weather is returned
	WebElement returnedCityWeather;
	
	//Initializing elements and driver inside the constructor
	public HomePage() {
		PageFactory.initElements(driver, this);
	}
	
	//Method to return page title as string
	public String validateHomePageTitle() {
		return driver.getTitle();
		
	}
	
	//Method to return image as boolean
	public boolean validateOpenWeatherMapImage() {
		return openWeatherMapLogo.isDisplayed();
	}
	
	//Method to search the weather of a city
	public void searchCityWeather(String cityName) throws InterruptedException {
		cityNameField.sendKeys(cityName);
		Thread.sleep(2000);
		searchButton.click();
	}
	
	//Method to get the text from alert when searching invalid city
	public String invalidateCity() {
		return invalidCity.getText();	
	}
	
	//Method to get the text from page when searching valid city
	public String validateCity() {
		return returnedCityWeather.getText();
		
	}

}
